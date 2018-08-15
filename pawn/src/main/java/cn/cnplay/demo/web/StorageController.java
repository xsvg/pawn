package cn.cnplay.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.model.StorageService;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@RequestMapping("storage")
public class StorageController {
	@Autowired StorageService storageService;
	
	@PostMapping("audit")
	public ResultVo<?> audit(User user,HttpServletRequest request,String auditorId){
		User loginUser = (User)request.getAttribute("currentUser");
		return storageService.audit(loginUser, user, auditorId);
	}
}
