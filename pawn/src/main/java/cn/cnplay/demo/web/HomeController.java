package cn.cnplay.demo.web;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.model.User;
import cn.cnplay.demo.service.WxService;

/**
 * 微信消息的接收和响应
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/")
public class HomeController
{

	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private DiscoveryClient client;

	@Resource
	private HttpServletRequest request;

	@Resource
	private WxService wxService;

	/**
	 * 接收微信服务器发送的4个参数并返回echostr
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Response get()
	{
		User user = new User();
		user.setUsername("peixere");
		user.setName("name");
		wxService.save(user);
		log.info("URI=" + request.getRequestURI());
		log.info("URL=" + request.getRequestURL());
		Enumeration<String> em = request.getHeaderNames();
		while (em.hasMoreElements())
		{
			String name = em.nextElement();
			log.info(name + "=" + request.getHeader(name));
		}
		return Response.ok(user).build();
		//return "hello";
	}
}