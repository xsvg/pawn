package cn.cnplay.demo.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.cnplay.demo.annotation.IgnoreLoginCheck;
import cn.cnplay.demo.config.JwtHelper;
import cn.cnplay.demo.exception.UnLoginException;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.model.UserTokenLog;
import cn.cnplay.demo.service.UserTokenLogService;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	@Autowired JwtHelper jwtHelper;
	@Autowired UserTokenLogService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(handler instanceof HandlerMethod ){
			HandlerMethod method = (HandlerMethod)handler;
			IgnoreLoginCheck ignoreLoginCheck = method.getMethodAnnotation(IgnoreLoginCheck.class);
			if(ignoreLoginCheck==null){
				String token = request.getHeader("auth_token");
				if(StringUtils.isBlank(token)){
					throw new UnLoginException("未登录");
				}
				User user = jwtHelper.verifyToken(token);
				if(user==null){
					throw new UnLoginException("登录信息已过期");
				}
				UserTokenLog tokenLog = tokenService.getUserToken(user.getId());
				if(tokenLog==null){
					throw new UnLoginException("登录信息已过期");
				}
				if(!StringUtils.equals(token, tokenLog.getJwtToken())){
					throw new UnLoginException("登录信息已过期");
				}
				request.setAttribute("currentUser", user);
				return true;
			}
		}
		
		return super.preHandle(request, response, handler);
	}

}
