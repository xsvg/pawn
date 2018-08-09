package cn.cnplay.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import cn.cnplay.demo.vo.ResultVo;

@ControllerAdvice
public class ControllerReturnAdvice implements ResponseBodyAdvice<ResultVo<?>> {
	
	@Autowired
	HttpServletRequest req;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public ResultVo<?> beforeBodyWrite(ResultVo<?> body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		req.setAttribute("@ResponseBody", body);
		return body;
	}

}
