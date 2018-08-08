package cn.cnplay.demo.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.model.User;
import cn.cnplay.demo.repository.UserRepository;
import cn.cnplay.demo.service.WxService;

/**
 * 微信消息的接收和响应
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserController
{
	@Resource
	private UserRepository userRepository;

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
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response get(@PathVariable String id)
	{
		User user = userRepository.getOne(id);
		return Response.ok(user).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response remove(@PathVariable String id)
	{
		User user = userRepository.getOne(id);
		userRepository.delete(user);
		return Response.ok(user).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response post(@RequestBody User user)
	{
		wxService.save(user);
		return Response.ok(user).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public Response findAll()
	{
		return Response.ok(userRepository.findAll()).build();
	}

}