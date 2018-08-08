package cn.cnplay.demo.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.model.WxAuthLog;
import cn.cnplay.demo.model.WxMessageLog;
import cn.cnplay.demo.service.WxService;
import cn.cnplay.demo.wx.WxAuthInfo;
import cn.cnplay.demo.wx.WxMessage;
import cn.cnplay.demo.wx.WxMsgType;
import cn.cnplay.demo.wx.WxUtils;

/**
 * 微信消息的接收和响应
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/wx")
public class WxController
{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private WxService wxService;

	@Resource
	private DiscoveryClient client;

	@Resource
	private HttpServletRequest request;

	/**
	 * 接收微信服务器发送的4个参数并返回echostr
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String auth(WxAuthInfo wxAuth, HttpServletRequest request)
	{
		WxAuthLog log = new WxAuthLog();
		BeanUtils.copyProperties(wxAuth, log);
		log.setSuccess(0);
		WxUtils.logger(request);
		if (WxUtils.verify(wxAuth))
		{
			log.setSuccess(1);
			wxService.save(log);
			return wxAuth.getEchostr();
		}
		else
		{
			wxService.save(log);
		}
		return "";
	}

	/**
	 * 微信消息的处理
	 * 
	 * @param request
	 * @param out
	 * @throws IOException
	 */
	@RequestMapping(method = { RequestMethod.POST })
	public void post(WxAuthInfo wxAuth, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		WxUtils.logger(request);
		WxAuthLog log = new WxAuthLog();
		BeanUtils.copyProperties(wxAuth, log);
		log.setSuccess(0);
		WxUtils.logger(request);
		if (WxUtils.verify(wxAuth))
		{
			log.setSuccess(1);
			wxService.save(log);
		}
		else
		{
			wxService.save(log);
			logger.info("验证失败：" + wxAuth.toString());
		}
		// 调用核心业务类接收消息、处理消息
		String respMessage = post(request);
		logger.info(respMessage);
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	private String post(HttpServletRequest request)
	{
		WxMessage reqMsg = WxUtils.toMsg(request);
		WxMessageLog reqlog = new WxMessageLog();
		BeanUtils.copyProperties(reqMsg, reqlog);
		logger.info(reqMsg.toString());
		WxMessage resMsg = new WxMessage();
		resMsg.setToUserName(reqMsg.getFromUserName());
		resMsg.setFromUserName(reqMsg.getToUserName());
		resMsg.setCreateTime(System.currentTimeMillis());
		resMsg.setContent(reqMsg.getContent());
		resMsg.setMsgId(reqMsg.getMsgId());
		resMsg.setMsgType(WxMsgType.RES_MSG_TEXT);
		if (WxMsgType.REQ_MSG_TEXT.equals(reqMsg.getMsgType()))
		{
			// TODO 做个机器人自动回复
			resMsg.setContent("~_~，为你服务！T" + reqMsg.getContent());
		}
		else if (WxMsgType.REQ_MSG_IMAGE.equals(reqMsg.getMsgType()))
		{
			// TODO 做个机器人自动回复
			resMsg.setContent("~_~，为你服务！I" + reqMsg.getContent());
		}
		else if (WxMsgType.REQ_MSG_LINK.equals(reqMsg.getMsgType()))
		{
			// TODO 做个机器人自动回复
			resMsg.setContent("~_~，为你服务！L" + reqMsg.getContent());
		}
		else if (WxMsgType.REQ_MSG_LOCATION.equals(reqMsg.getMsgType()))
		{
			// TODO 做个机器人自动回复
			resMsg.setContent("~_~，为你服务！A" + reqMsg.getContent());
		}
		else if (WxMsgType.REQ_MSG_VOICE.equals(reqMsg.getMsgType()))
		{
			// TODO 做个机器人自动回复
			resMsg.setContent("~_~，为你服务！V" + reqMsg.getContent());
		}
		else if (WxMsgType.REQ_MSG_EVENT.equals(reqMsg.getMsgType()))
		{// 事件推送
			if (WxMsgType.EVENT_SUBSCRIBE.equals(reqMsg.getEvent()))
			{// 关注或订阅
				WxMessage text = new WxMessage();
				text.setContent("欢迎关注，~_~");
			}
			else if (WxMsgType.EVENT_UNSUBSCRIBE.equals(reqMsg.getEvent()))
			{// 取消订阅
				// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				logger.info("取消关注：" + reqMsg.toString());
			}
			else if (WxMsgType.EVENT_CLICK.equals(reqMsg.getEvent()))
			{// 自定义菜单点击事件
				String eventKey = reqMsg.getEventKey();
				resMsg.setContent("操作菜单：" + eventKey);
			}
		}
		WxMessageLog resLog = new WxMessageLog();
		BeanUtils.copyProperties(resMsg, resLog);
		resLog.setPid(reqlog.getId());
		resLog.setDirection(1);
		wxService.save(reqlog);
		wxService.save(resLog);
		return WxUtils.toXML(resMsg);
	}
}