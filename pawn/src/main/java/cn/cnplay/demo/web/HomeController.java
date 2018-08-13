package cn.cnplay.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.annotation.IgnoreLoginCheck;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.service.HomeService;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;


@RestController
public class HomeController
{

	@Autowired
	private HomeService homeService;
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	@IgnoreLoginCheck
	@Description("登录")
	public ResultVo<?> login(User user,HttpServletRequest request){
		ResultVo<?> result = homeService.login(user);
		request.setAttribute("currentUser", user);
		return result;
	}
	
	@RequestMapping(value="logout",method=RequestMethod.GET)
	@IgnoreLoginCheck
	@Description("注销")
	public ResultVo<?> logout(@RequestHeader String auth_token){
		homeService.logout(auth_token);
		return ResultVOUtil.success();
	}
	
	@GetMapping("current")
	public ResultVo<?> currentUser(HttpServletRequest request){
		return ResultVOUtil.success(request.getAttribute("currentUser"));
	}
	
	@RequestMapping("audit")
	public ResultVo<?> audit(User user,HttpServletRequest request){
		User loginUser = (User)request.getAttribute("currentUser");
		return homeService.audit(loginUser, user);
	}
}