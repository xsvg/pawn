package cn.cnplay.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.cnplay.demo.exception.UnAuthorizedException;
import cn.cnplay.demo.exception.UnLoginException;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
	private final static Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	@ExceptionHandler(UnAuthorizedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResultVo<?> handleUnAuthorizedException(UnAuthorizedException e){
		LOG.warn("无权限",e.getMessage());
		return ResultVOUtil.error(e.getMessage());
	}
	
	@ExceptionHandler(UnLoginException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResultVo<?> handleUnLoginExceptionn(UnLoginException e){
		LOG.warn("未登录或已过期",e.getMessage());
		return ResultVOUtil.error(e.getMessage());
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultVo<?> handleException(Exception e){
		LOG.error("服务器异常",e);
		return ResultVOUtil.error("服务器错误");
	}
	
}
