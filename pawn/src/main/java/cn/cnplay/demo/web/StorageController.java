package cn.cnplay.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.model.StorageIn;
import cn.cnplay.demo.model.StorageItem;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.service.StorageInService;
import cn.cnplay.demo.service.StorageService;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@RequestMapping("storage")
public class StorageController {
	@Autowired StorageService storageService;
	@Autowired StorageInService storageInService;
	
	@PostMapping("audit")
	public ResultVo<?> audit(User user,HttpServletRequest request,String auditorId){
		User loginUser = (User)request.getAttribute("currentUser");
		return storageService.audit(loginUser, user, auditorId);
	}
	
	@RequestMapping("list")
	public ResultVo<?> list(StorageItem item,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo = 1;
		}
		if(pageSize==null){
			pageSize = 15;
		}
		StorageIn in = new StorageIn();
		in.setItem(item);
		return storageInService.list(in, pageNo, pageSize);
	}
}
