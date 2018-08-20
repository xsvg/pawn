package cn.cnplay.demo.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.Department;
import cn.cnplay.demo.model.ItemRecord;
import cn.cnplay.demo.model.StorageIn;
import cn.cnplay.demo.model.StorageItem;
import cn.cnplay.demo.model.StorageTransfer;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class StorageTransferService extends AbstractService {

	public ResultVo<?> add(String ids,String fromAuditor,String toDept,User user,String auditor){
		String[] idArray = StringUtils.split(ids, ',');
		for(String id:idArray){
			StorageTransfer transfer = new StorageTransfer();
			StorageItem item = new StorageItem();
			item.setId(id);
			transfer.setId(id);
			transfer.setFromOperator(user);
			transfer.setFrom(user.getDepartment());
			Department dept = new Department();
			dept.setId(toDept);
			transfer.setTo(dept);
			User auditorUser = new User();
			auditorUser.setId(fromAuditor);
			transfer.setFromAuditor(auditorUser);
			if(StringUtils.equals(fromAuditor, auditor)){
				transfer.setFromAuditResult(true);
				transfer.setFromAuditDate(new Date());
			}
			insert(transfer);
			ItemRecord record = new ItemRecord();
			record.setRefRecordId(transfer.getId());
			record.setRecordDesc("调拨");
			insert(record);
		}
		return ResultVOUtil.success();
	}
	
	
	
	public ResultVo<?> fromAudit(String id,User user ){
		StorageTransfer transfer = get(StorageTransfer.class, id);
		if(transfer==null){
			return ResultVOUtil.error("记录不存在");
		}
		if(StringUtils.equals(user.getId(), transfer.getFromAuditor().getId())){
			return ResultVOUtil.error("不能审核不是由您审核的记录");
		}
		transfer.setFromAuditResult(true);
		transfer.setFromAuditDate(new Date());
		update(transfer);
		return ResultVOUtil.success();
	}
	
	public ResultVo<?> recive(StorageTransfer transfer,String auditor){
		StorageTransfer po = get(StorageTransfer.class, transfer.getId());
		po.setToAuditor(transfer.getToAuditor());
		po.setStorageArea(transfer.getStorageArea());
		po.setStoreman(transfer.getStoreman());
		po.setToOperator(transfer.getToOperator());
		if(StringUtils.isNotBlank(auditor)){
			transfer.setToAuditDate(new Date());
			transfer.setToAuditResult(true);
			StorageIn in = new StorageIn();
			transfer.setItem(transfer.getItem());
			transfer.setToOperator(transfer.getToOperator());
			transfer.setStorageArea(transfer.getStorageArea());
			transfer.setToAuditor(transfer.getToAuditor());
			transfer.setStoreman(transfer.getStoreman());
			transfer.setToAuditResult(true);
			transfer.setToAuditDate(new Date());
			insert(in);
		}
		update(transfer);
		return ResultVOUtil.success();
	}
	
	public ResultVo<?> toAudit(String id,User user ){
		StorageTransfer transfer = get(StorageTransfer.class, id);
		if(transfer==null){
			return ResultVOUtil.error("记录不存在");
		}
		if(StringUtils.equals(user.getId(), transfer.getToAuditor().getId())){
			return ResultVOUtil.error("不能审核不是由您审核的记录");
		}
		transfer.setToAuditResult(true);
		transfer.setToAuditDate(new Date());
		update(transfer);
		StorageIn in = new StorageIn();
		transfer.setItem(transfer.getItem());
		transfer.setToOperator(transfer.getToOperator());
		transfer.setStorageArea(transfer.getStorageArea());
		transfer.setToAuditor(transfer.getToAuditor());
		transfer.setStoreman(transfer.getStoreman());
		transfer.setToAuditResult(true);
		transfer.setToAuditDate(new Date());
		insert(in);
		return ResultVOUtil.success();
	}
	
	public ResultVo<?> list(StorageTransfer transfer,Integer page,Integer pageSize){
		StringBuilder builder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		StorageItem item = transfer.getItem();
		if(item!=null){
			if(StringUtils.isNotBlank(item.getContractNo())){
				builder.append(" and transfer.item.contractNo like ?");
				params.add("%"+item.getContractNo()+"%");
			}
			if(StringUtils.isNotBlank(item.getBorrowerName())){
				builder.append(" and transfer.item.borrowerName like ?");
				params.add("%"+item.getBorrowerName()+"%");
			}
			if(StringUtils.isNotBlank(item.getBorrowerId())){
				builder.append(" and transfer.item.borrowerId like ?");
				params.add("%"+item.getBorrowerId()+"%");
			}
			if(StringUtils.isNotBlank(item.getOwnerId())){
				builder.append(" and transfer.item.ownerId like ?");
				params.add("%"+item.getOwnerId()+"%");
			}
			if(StringUtils.isNotBlank(item.getOwnerName())){
				builder.append(" and transfer.item.ownerName like ?");
				params.add("%"+item.getOwnerName()+"%");
			}
		}
		if(transfer.getFromAuditor()!=null && StringUtils.isNotBlank(transfer.getFromAuditor().getId())){
			builder.append(" and transfer.fromAuditor.id = ? and transfer.fromAuditResult is null");
			params.add(transfer.getFromAuditor().getId());
		}
		
		if(transfer.getToAuditor()!=null && StringUtils.isNotBlank(transfer.getToAuditor().getId())){
			builder.append(" and transfer.toAuditor.id = ? and transfer.toAuditResult is null");
			params.add(transfer.getToAuditor().getId());
		}
		StringBuilder countHql = new StringBuilder( "select count(transfer.id) from StorageTransfer transfer ");
		StringBuilder hql = new StringBuilder("from StorageTransfer transfer ");
		if(builder.length()>0){
			String filter = builder.toString();
			filter = StringUtils.substringAfter(filter, "and");
			countHql.append(" where ").append(filter);
			hql.append(" where ").append(filter);
		}
		Map<String, Object> map = new HashMap<>();
		long count = count(countHql.toString(), params);
		map.put("total", count);
		if(count==0){
			map.put("rows", new ArrayList<>());
		}else{
			map.put("rows", list(hql.toString(), params) );
		}
		return ResultVOUtil.success(map);
	}
}
