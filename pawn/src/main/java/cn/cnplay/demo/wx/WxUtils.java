package cn.cnplay.demo.wx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.thoughtworks.xstream.XStream;

/**
 * 微信工具类
 */
public final class WxUtils
{
	private static final Logger logger = LoggerFactory.getLogger(WxUtils.class);

	/**
	 * 换行符
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * 服务器配置-令牌(Token)
	 */
	private static String token = "iwangchong";

	/**
	 * 取服务器配置-令牌(Token)
	 * @return
	 */
	public static String getToken()
	{
		return token;
	}

	/**
	 * 设置服务器配置-令牌(Token)
	 * @param newToken
	 */
	public static void setToken(String newToken)
	{
		token = newToken;
	}

	/**
	 * 验证信息信息
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean verify(WxAuthInfo wxAuth)
	{
		boolean auth = false;
		if (wxAuth.getTimestamp() != null && wxAuth.getNonce() != null)
		{
			String[] arr = new String[] { token, wxAuth.getTimestamp(), wxAuth.getNonce() };
			Arrays.sort(arr);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < arr.length; i++)
			{
				sb.append(arr[i]);
			}
			String sha1Hex = DigestUtils.sha1Hex(sb.toString());
			auth = sha1Hex.equals(wxAuth.getSignature());
			if (!auth && logger.isDebugEnabled())
			{
				logger.info(wxAuth.toString());
			}
		}
		return auth;
	}

	public static void logger(HttpServletRequest request)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(LINE_SEPARATOR);
		sb.append("---------------[");
		sb.append(request.getRequestURI());
		sb.append("] START ---------------");
		sb.append(LINE_SEPARATOR);
		sb.append("******** [Header] ********");
		Enumeration<String> em = request.getHeaderNames();
		while (em.hasMoreElements())
		{
			String name = em.nextElement();
			sb.append(LINE_SEPARATOR);
			sb.append(name + "=" + request.getHeader(name));
		}
		sb.append(LINE_SEPARATOR);
		sb.append("******** [Parameter] ********");
		em = request.getParameterNames();
		while (em.hasMoreElements())
		{
			String name = em.nextElement();
			sb.append(LINE_SEPARATOR);
			sb.append(name + "=" + request.getParameter(name));
		}
		sb.append(LINE_SEPARATOR);
		sb.append("---------------[");
		sb.append(request.getRequestURI());
		sb.append("] END ---------------");
		logger.info(sb.toString());
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String toXML(WxMessage textMessage)
	{
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * XML 转换为文本信息
	 * 
	 * @param request
	 * @return
	 */
	public static WxMessage toMsg(HttpServletRequest request)
	{
		WxMessage message = new WxMessage();
		InputStream ins = null;
		try
		{
			ins = request.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(ins);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements();
			StringBuilder sb = new StringBuilder();
			Map<String, String> msgMap = new HashMap<String, String>();
			for (Element e : list)
			{
				msgMap.put(e.getName(), e.getText());
				BeanWrapper beanWrapper = new BeanWrapperImpl(message);
				if (beanWrapper.isWritableProperty(e.getName()))
				{
					beanWrapper.setPropertyValue(e.getName(), e.getText());
				}
				else
				{
					sb.append(LINE_SEPARATOR);
					sb.append(e.getName());
					sb.append("=");
					sb.append(e.getText());
				}
			}
			if (sb.length() > 0)
			{
				sb.insert(0, LINE_SEPARATOR + "******** 未处理的XML节点内容 ********");
				logger.warn(sb.toString());
			}
			doc.clearContent();
			return message;
		}
		catch (IOException ex)
		{
			logger.error("取请求数据流异常", ex);
		}
		catch (DocumentException ex)
		{
			logger.error("取请求数据流异常", ex);
		}
		finally
		{
			if (ins != null)
			{
				try
				{
					ins.close();
				}
				catch (Exception ex)
				{
					logger.error("关闭请求数据流异常：" + ex.getMessage());
				}
			}
		}
		return null;
	}
}