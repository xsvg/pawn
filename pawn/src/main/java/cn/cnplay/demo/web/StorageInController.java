package cn.cnplay.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.model.StorageIn;
import cn.cnplay.demo.model.StorageService;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@RequestMapping("storageIn")
public class StorageInController {
	
	@Autowired StorageService storageService;
	
	@PostMapping("add")
	@Description("入库")
	public ResultVo<?> add(StorageIn in,@RequestHeader String auditor,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		in.setOperator(user);
		return storageService.storageIn(in, auditor);
	}
	
	
}
