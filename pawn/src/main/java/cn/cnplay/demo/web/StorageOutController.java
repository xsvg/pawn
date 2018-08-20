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
import cn.cnplay.demo.model.StorageOut;
import cn.cnplay.demo.model.StorageOut;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.service.StorageOutService;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@Description("出库管理")
@RequestMapping("storageOut")
public class StorageOutController {

	@Autowired StorageOutService outService;
	
	@PostMapping("add")
	@Description("出库")
	@UserRole({"operator","storeman"})
	public ResultVo<?> add(StorageOut out,@RequestHeader String auditor,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		out.setOperator(user);
		return outService.storageOut(out, auditor);
	}
	
	@RequestMapping("list")
	public ResultVo<?> list(StorageOut out,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo = 1;
		}
		if(pageSize==null){
			pageSize = 15;
		}
		return outService.list(out, pageNo, pageNo);
	}
	
	@PostMapping("auditor/{id}")
	@Description("出库审核")
	@UserRole("auditor")
	public ResultVo<?> auditor(@PathVariable String id,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		return outService.audit(id, user);
	}
	
	@RequestMapping("{id}")
	public ResultVo<?> get(@PathVariable String id){
		StorageOut in = outService.get(StorageOut.class, id);
		if(in==null){
			return ResultVOUtil.error("记录不存在");
		}else{
			return ResultVOUtil.success(in);
		}
	}
}
