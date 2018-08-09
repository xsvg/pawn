package cn.cnplay.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
	private final static Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultVo<?> handleException(Exception e){
		LOG.error("服务器异常",e);
		return ResultVOUtil.error("服务器错误");
	}
	
}
