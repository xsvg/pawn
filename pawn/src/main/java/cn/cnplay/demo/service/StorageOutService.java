package cn.cnplay.demo.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.ItemRecord;
import cn.cnplay.demo.model.StorageItem;
import cn.cnplay.demo.model.StorageOut;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class StorageOutService extends AbstractService {

	public ResultVo<?> storageOut(StorageOut out,String auditor){
		if(StringUtils.isNotBlank(auditor) && !StringUtils.equals(auditor, out.getAuditor().getId())){
			return ResultVOUtil.error("审批人与选择不一致");
		}
		if(StringUtils.isNotBlank(auditor) && StringUtils.equals(auditor, out.getAuditor().getId())){
			out.setAuditResult(true);
			out.setAutidDate(new Date());
		}
		insert(out);
		ItemRecord record = new ItemRecord();
		record.setRefRecordId(out.getId());
		record.setRecordDesc("出库");
		insert(record);
		return ResultVOUtil.success();
	}
	
	public  ResultVo<?> audit(String id,User user){
		StorageOut out = get(StorageOut.class, id);
		if(out==null){
			return ResultVOUtil.error("记录不存在");
		}
		if(StringUtils.equals(user.getId(), out.getAuditor().getId())){
			return ResultVOUtil.error("不能审核不是由您审核的记录");
		}
		out.setAuditResult(true);
		out.setAutidDate(new Date());
		update(out);
		return ResultVOUtil.success();
	}
	
	
	public ResultVo<?> list(StorageOut out,Integer page,Integer pageSize){
		StringBuilder builder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		if(out.getDepartment()!=null && StringUtils.isNotBlank(out.getDepartment().getId())){
			builder.append(" and out.department.id = ?");
			params.add(out.getDepartment().getId());
		}
		if(out.getAuditor()!=null&&StringUtils.isNotBlank(out.getAuditor().getId())){
			builder.append(" and out.auditor.id = ? and out.auditResult is null");
			params.add(out.getAuditor().getId());
		}
		StorageItem item = out.getItem();
		if(item!=null){
			if(StringUtils.isNotBlank(item.getContractNo())){
				builder.append(" and out.item.contractNo like ?");
				params.add("%"+item.getContractNo()+"%");
			}
			if(StringUtils.isNotBlank(item.getBorrowerName())){
				builder.append(" and out.item.borrowerName like ?");
				params.add("%"+item.getBorrowerName()+"%");
			}
			if(StringUtils.isNotBlank(item.getBorrowerId())){
				builder.append(" and out.item.borrowerId like ?");
				params.add("%"+item.getBorrowerId()+"%");
			}
			if(StringUtils.isNotBlank(item.getOwnerId())){
				builder.append(" and out.item.ownerId like ?");
				params.add("%"+item.getOwnerId()+"%");
			}
			if(StringUtils.isNotBlank(item.getOwnerName())){
				builder.append(" and out.item.ownerName like ?");
				params.add("%"+item.getOwnerName()+"%");
			}
		}
		StringBuilder countHql = new StringBuilder( "select count(out.id) from StorageOut out ");
		StringBuilder hql = new StringBuilder("from StorageIn in ");
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
