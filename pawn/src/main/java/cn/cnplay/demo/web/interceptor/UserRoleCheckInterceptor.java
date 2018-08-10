package cn.cnplay.demo.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.cnplay.demo.annotation.UserRole;
import cn.cnplay.demo.exception.UnAuthorizedException;
import cn.cnplay.demo.model.User;

public class UserRoleCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(handler instanceof HandlerMethod ){
			HandlerMethod method = (HandlerMethod)handler;
			UserRole userRole = method.getMethodAnnotation(UserRole.class);
			if(userRole!=null){
				String[] roles = userRole.value();
				if(roles!=null && roles.length>0){
					User user = (User)request.getAttribute("currentUser");
					if(user==null){
						throw new UnAuthorizedException("权限不足,需要先进行登录");
					}
					String userType = user.getUserType();
					if(StringUtils.isBlank(userType)){
						throw new UnAuthorizedException("未被授予角色");
					}
					boolean authorized = false;
					for(String role : roles){
						if(StringUtils.equals(role, userType)){
							authorized = true;
							break;
						}
					}
					
					if(!authorized){
						throw new UnAuthorizedException("权限不足");
					}
				}
			}
		}
		
		return super.preHandle(request, response, handler);
	}

}
