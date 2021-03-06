package cn.cnplay.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnplay.demo.annotation.UserRole;
import cn.cnplay.demo.model.StorageIn;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.service.StorageInService;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@RequestMapping("storageIn")
@Description("入库管理")
public class StorageInController {
	
	@Autowired StorageInService storageService;
	
	@PostMapping("add")
	@Description("入库")
	@UserRole({"operator","storeman"})
	public ResultVo<?> add(StorageIn in,@RequestHeader String auditor,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		in.setOperator(user);
		return storageService.storageIn(in, auditor);
	}
	
	@RequestMapping("list")
	public ResultVo<?> list(StorageIn in,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo = 1;
		}
		if(pageSize==null){
			pageSize = 15;
		}
		return storageService.list(in, pageNo, pageNo);
	}
	
	@PostMapping("auditor/{id}")
	@Description("入库审核")
	@UserRole("auditor")
	public ResultVo<?> auditor(@PathVariable String id,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		return storageService.audit(id, user);
	}
	
	@RequestMapping("{id}")
	public ResultVo<?> get(@PathVariable String id){
		StorageIn in = storageService.get(StorageIn.class, id);
		if(in==null){
			return ResultVOUtil.error("记录不存在");
		}else{
			return ResultVOUtil.success(in);
		}
	}
}
