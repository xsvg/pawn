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
import cn.cnplay.demo.model.StorageIn;
import cn.cnplay.demo.model.StorageItem;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class StorageInService extends AbstractService {

	public ResultVo<?> storageIn(StorageIn in,String auditor){
		StorageItem item = in.getItem();
		if(StringUtils.isBlank(item.getContractNo())){
			return ResultVOUtil.error("贷款合同号不能为空");
		}
		if(StringUtils.isBlank(item.getOwnerName())){
			return ResultVOUtil.error("抵质押物所有人姓名不能为空");
		}
		if(StringUtils.isBlank(item.getOwnerId())){
			return ResultVOUtil.error("抵质押物所有人身份证号不能为空");
		}
		if(StringUtils.isBlank(item.getRegisterDate())){
			return ResultVOUtil.error("登记日期不能为空");
		}
		if(StringUtils.isBlank(item.getContractStartDate())){
			return ResultVOUtil.error("合同起始日期不能为空");
		}
		if(StringUtils.isBlank(item.getContractEndDate())){
			return ResultVOUtil.error("合同到期日期不能为空");
		}
		if(StringUtils.isBlank(item.getCertificateNo())){
			return ResultVOUtil.error("产权证号码不能为空");
		}
		if(StringUtils.isBlank(item.getBorrowerName())){
			return ResultVOUtil.error("借款人姓名不能为空");
		}
		if(item.getAssessMoney()==null){
			return ResultVOUtil.error("评估金额不能为空");
		}
		if(item.getActualMoney()==null){
			return ResultVOUtil.error("借款金额不能为空");
		}
		if(in.getAuditor()==null || StringUtils.isBlank(in.getAuditor().getId())){
			return ResultVOUtil.error("审批人不能为空");
		}
		User user = get(User.class, in.getAuditor().getId());
		if(user==null){
			return ResultVOUtil.error("审批人不存在");
		}
		if(in.getStoreman()==null || StringUtils.isBlank(in.getStoreman().getId())){
			return ResultVOUtil.error("保管人不能为空");
		}
		user = get(User.class, in.getStoreman().getId());
		if(user==null){
			return ResultVOUtil.error("保管人不存在");
		}
		if(StringUtils.isNotBlank(auditor) && !StringUtils.equals(auditor, in.getAuditor().getId())){
			return ResultVOUtil.error("审批人与选择不一致");
		}
		if(StringUtils.isNotBlank(auditor) && StringUtils.equals(auditor, in.getAuditor().getId())){
			in.setAuditResult(true);
		}
		insert(item);
		insert(in);
		ItemRecord record = new ItemRecord();
		record.setRefRecordId(in.getId());
		record.setRecordDesc("入库");
		insert(record);
		return ResultVOUtil.success();
	}
	
	public  ResultVo<?> audit(String id,User user){
		StorageIn in = get(StorageIn.class, id);
		if(in==null){
			return ResultVOUtil.error("记录不存在");
		}
		if(StringUtils.equals(user.getId(), in.getAuditor().getId())){
			return ResultVOUtil.error("不能审核不是由您审核的记录");
		}
		in.setAuditResult(true);
		in.setAutidDate(new Date());
		update(in);
		return ResultVOUtil.success();
	}
	
	public ResultVo<?> list(StorageIn in,Integer page,Integer pageSize){
		StringBuilder builder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		if(in.getDepartment()!=null && StringUtils.isNotBlank(in.getDepartment().getId())){
			builder.append(" and in.department.id = ?");
			params.add(in.getDepartment().getId());
		}
		if(in.getAuditor()!=null&&StringUtils.isNotBlank(in.getAuditor().getId())){
			builder.append(" and in.auditor.id = ? and in.auditResult is null");
			params.add(in.getAuditor().getId());
		}
		StorageItem item = in.getItem();
		if(item!=null){
			if(StringUtils.isNotBlank(item.getContractNo())){
				builder.append(" and in.item.contractNo like ?");
				params.add("%"+item.getContractNo()+"%");
			}
			if(StringUtils.isNotBlank(item.getBorrowerName())){
				builder.append(" and in.item.borrowerName like ?");
				params.add("%"+item.getBorrowerName()+"%");
			}
			if(StringUtils.isNotBlank(item.getBorrowerId())){
				builder.append(" and in.item.borrowerId like ?");
				params.add("%"+item.getBorrowerId()+"%");
			}
			if(StringUtils.isNotBlank(item.getOwnerId())){
				builder.append(" and in.item.ownerId like ?");
				params.add("%"+item.getOwnerId()+"%");
			}
			if(StringUtils.isNotBlank(item.getOwnerName())){
				builder.append(" and in.item.ownerName like ?");
				params.add("%"+item.getOwnerName()+"%");
			}
		}
		StringBuilder countHql = new StringBuilder( "select count(in.id) from StorageIn in ");
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
