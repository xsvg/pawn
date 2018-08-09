package cn.cnplay.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.model.User;
import cn.cnplay.demo.service.UserService;
import cn.cnplay.demo.vo.ResultVo;

/**
 * 微信消息的接收和响应
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("user")
@Description("用户")
public class UserController
{
	
	@Autowired UserService userService;
	
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	@Description("添加")
	public ResultVo<?> add(User user){
		ResultVo<User> resultVo = userService.add(user);
		resultVo.setData(null);
		return resultVo;
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Description("更新")
	public ResultVo<?> update(User user){
		return userService.update(user);
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@Description("删除")
	public ResultVo<?> delete(String id){
		return userService.delete(id);
	}
	
	@RequestMapping(value="pswd",method=RequestMethod.POST)
	@Description("修改密码")
	public ResultVo<?> pswd(HttpServletRequest request, String oldPswd,String newPswd){
		User user = (User)request.getAttribute("currentUser");
		return userService.updatePswd(user.getId(), oldPswd, newPswd);
	}
}