package cn.cnplay.demo.model;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.service.AbstractService;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.utils.Sha1Util;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class StorageService extends AbstractService {

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
	
	public ResultVo<?> audit(User loginUser,User auditor,String auditorId){
		if(StringUtils.isBlank(auditor.getUsername())){
			return ResultVOUtil.error("用户名不能为空");
		}
		if(StringUtils.isBlank(auditor.getPassword())){
			return ResultVOUtil.error("密码不能为空");
		}
		List<Object> params = new ArrayList<>();
		params.add(auditor.getUsername());
		params.add(false);
		List<User> list =(List<User>)list("from User user where user.username = ? and deleted = ?", params);
		if(list.size()==0){
			return ResultVOUtil.error("审批人不存在");
		}
		User user = list.get(0);
		String voPswd = auditor.getPassword();
		String poPswd = user.getPassword();
		String saltStr = StringUtils.substring(poPswd, 0, 16);
		poPswd = StringUtils.substring(poPswd, 16);
		String checksum = Sha1Util.encrypt(voPswd, saltStr);
		if(StringUtils.equals(checksum, poPswd)){
			if(StringUtils.equals("auditor", user.getUserType())){
				return ResultVOUtil.success(user.getId());
			}else{
				return ResultVOUtil.error("需要审批人员角色权限"); 
			}
		}else{
			return ResultVOUtil.error("密码错误");
		}
	}
}
