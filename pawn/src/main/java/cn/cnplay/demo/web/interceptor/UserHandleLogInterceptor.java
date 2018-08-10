package cn.cnplay.demo.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.cnplay.demo.model.User;
import cn.cnplay.demo.model.UserHandleLog;
import cn.cnplay.demo.service.UserHandleLogService;
import cn.cnplay.demo.utils.IpUtil;
import cn.cnplay.demo.vo.ResultVo;

public class UserHandleLogInterceptor  extends HandlerInterceptorAdapter  {
	
	@Autowired UserHandleLogService logService;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(handler instanceof HandlerMethod ){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Description description = handlerMethod.getMethodAnnotation(Description.class);
			if(description!=null){
				Class<?> clazz = handlerMethod.getBeanType();
				Description parentDesc = clazz.getAnnotation(Description.class);
				StringBuffer buffer = new StringBuffer();
				if(parentDesc==null){
					buffer.append(description.value());
				}else{
					buffer.append(parentDesc.value()).append("/").append(description.value());
				}
				String uri = request.getRequestURI();
				UserHandleLog handleLog = new UserHandleLog();
				User user = (User)request.getAttribute("currentUser");
				
				if(user!=null){
					if(StringUtils.isBlank(user.getName())){
						handleLog.setUserNo(user.getUsername());
					}else{
						handleLog.setUser(user);
					}
				}
				handleLog.setRequestUri(uri);
				handleLog.setRequestDesc(buffer.toString());
				handleLog.setClientIp(IpUtil.getIpAddr(request));
				ResultVo<?> result =  (ResultVo<?>)request.getAttribute("@ResponseBody");
				if(result!=null){
					handleLog.setSuccess(result.isSuccess());
					handleLog.setMessage(result.getMsg()); 
					logService.addLog(handleLog);
				}
			}
		}
		super.afterCompletion(request, response, handler, ex);
	}
}
