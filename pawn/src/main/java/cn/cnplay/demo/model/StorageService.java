package cn.cnplay.demo.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.service.AbstractService;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class StorageService extends AbstractService {

	public ResultVo<?> addStorageItem(StorageItem item){
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
		insert(item);
		return ResultVOUtil.success(); 
	}
}
