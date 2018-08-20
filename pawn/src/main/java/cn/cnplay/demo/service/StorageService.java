package cn.cnplay.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.User;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.utils.Sha1Util;
import cn.cnplay.demo.vo.ResultVo;

@Service
@Transactional
public class StorageService extends AbstractService {

	
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
