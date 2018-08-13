package cn.cnplay.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.annotation.UserRole;
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
	
	@RequestMapping("{id}")
	public ResultVo<?> get(@PathVariable String id){
		return userService.get(id);
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	@Description("添加")
	@UserRole("admin")
	public ResultVo<?> add(User user,HttpServletRequest request){
		ResultVo<?> resultVo = userService.add(user);
		resultVo.setData(null);
		return resultVo;
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Description("更新")
	@UserRole("admin")
	public ResultVo<?> update(User user){
		return userService.update(user);
	}
	
	@RequestMapping(value="delete/{id}",method=RequestMethod.POST)
	@Description("删除")
	@UserRole("admin")
	public ResultVo<?> delete(@PathVariable String id){
		return userService.delete(id);
	}
	
	@RequestMapping(value="/pswd/{id}",method=RequestMethod.POST)
	@Description("重置密码")
	@UserRole("admin")
	public ResultVo<?> resetPswd(@PathVariable String id){
		return userService.updatePswd(id);
	}
	
	@RequestMapping(value="pswd",method=RequestMethod.POST)
	@Description("修改密码")
	public ResultVo<?> pswd(HttpServletRequest request, String oldPswd,String newPswd){
		User user = (User)request.getAttribute("currentUser");
		return userService.updatePswd(user.getId(), oldPswd, newPswd);
	}
	
	@RequestMapping("list")
	public ResultVo<?> list(User user,Integer pageNo,Integer pageSize){
		return userService.list(user, pageNo, pageSize);
	}
}