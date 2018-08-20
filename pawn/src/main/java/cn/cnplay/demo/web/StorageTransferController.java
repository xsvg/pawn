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
import cn.cnplay.demo.model.StorageTransfer;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.service.StorageTransferService;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@RestController
@RequestMapping("transfer")
@Description("物品调拨管理")
public class StorageTransferController {

	@Autowired StorageTransferService transferService;
	
	@PostMapping("add")
	@Description("调拨")
	@UserRole({"operator","storeman"})
	public ResultVo<?> add(String ids,String fromAuditor,String toDept, @RequestHeader String auditor,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		
		return transferService.add(ids, fromAuditor, toDept, user, auditor);
	}
	
	@PostMapping("audit/from/{id}")
	@UserRole("auditor")
	public ResultVo<?> fromAudit(@PathVariable String id,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		return transferService.fromAudit(id, user);
	}
	 
	@RequestMapping("{id}")
	public ResultVo<?> get(@PathVariable String id){
		StorageTransfer transfer = transferService.get(StorageTransfer.class, id);
		return ResultVOUtil.success(transfer);
	}
	
	@PostMapping("revice")
	@UserRole({"operator","storeman"})
	public ResultVo<?> recive(StorageTransfer transfer,@RequestHeader String auditor,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		transfer.setToOperator(user);
		return transferService.recive(transfer, auditor);
	}
	
	@RequestMapping("list")
	public ResultVo<?> list(StorageTransfer transfer,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo = 1;
		}
		if(pageSize==null){
			pageSize = 15;
		}
		return transferService.list(transfer, pageNo, pageSize);
	}
	
	
	@PostMapping("audit/to/{id}")
	@UserRole("auditor")
	public ResultVo<?> toAudit(@PathVariable String id,HttpServletRequest request){
		User user = (User)request.getAttribute("currentUser");
		return transferService.toAudit(id, user);
	}
}
