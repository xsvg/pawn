package cc.cnplay.platform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cc.cnplay.core.util.JWTUtils;
import cc.cnplay.platform.domain.User;

import com.auth0.jwt.JWTVerifyException;

public class Platform
{
	protected static final Logger logger = Logger.getLogger(Platform.class);
	
	public static User getSessionUser(HttpServletRequest request)
	{
		if (request == null) {
			return null;
		}
		User user = null;
		try {
			user = getTokenUser(request);
		} catch (JWTVerifyException e) {
			return user;
		}	
		HttpSession session = request.getSession();
		if (session == null)
		{
			return null;
		}
		user = (User) session.getAttribute(Platform.getUserSessionKey(request));
		return user;
	}
	
	protected static User getTokenUser(HttpServletRequest request) throws JWTVerifyException 
	{
		String token = request.getHeader("token");
		User user = null;
		try {
			user = JWTUtils.verify(token, User.class);
		} catch (JWTVerifyException e) {
			throw e;
		} catch (Exception e) {
			logger.error("JWTUtils.verify Error ", e);
		}
		return user;
	}
	
	public static String getUserSessionKey(HttpServletRequest request)
	{
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		StringBuilder url = new StringBuilder("USER");
		url.append(serverName);
		url.append(":").append(serverPort);
		url.append(request.getSession().getId());
		return url.toString();
	}

	public static String getCheckKey(HttpServletRequest request)
	{
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		StringBuilder url = new StringBuilder("CHECK");
		url.append(serverName);
		url.append(":").append(serverPort);
		url.append(request.getSession().getId());
		return url.toString();
	}

	public static String getCheckInfoKey(HttpServletRequest request)
	{
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		StringBuilder url = new StringBuilder("CHECKINFO");
		url.append(serverName);
		url.append(":").append(serverPort);
		url.append(request.getSession().getId());
		return url.toString();
	}

	public static String getConfirmKey(HttpServletRequest request)
	{
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		StringBuilder url = new StringBuilder("CONFIRM");
		url.append(serverName);
		url.append(":").append(serverPort);
		url.append(request.getSession().getId());
		return url.toString();
	}

	public static User getConfirmUser(HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute(getConfirmKey(request));
		request.getSession().removeAttribute(Platform.getConfirmKey(request));
		return user;
	}
}
