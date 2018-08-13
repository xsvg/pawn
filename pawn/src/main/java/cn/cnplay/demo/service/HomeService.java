package cn.cnplay.demo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.config.JwtHelper;
import cn.cnplay.demo.model.User;
import cn.cnplay.demo.model.UserTokenLog;
import cn.cnplay.demo.repository.UserRepository;
import cn.cnplay.demo.repository.UserTokenLogRepository;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.utils.Sha1Util;
import cn.cnplay.demo.vo.ResultVo;

@Service
public class HomeService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserTokenLogRepository tokenLogRepository;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Transactional
	public ResultVo<?> login(User user){
		if(StringUtils.isBlank(user.getUsername())){
			return ResultVOUtil.error("用户名不能为空");
		}
		if(StringUtils.isBlank(user.getPassword())){
			return ResultVOUtil.error("密码不能为空");
		}
		List<User> list = userRepository.findByUsernameAndDeleted(user.getUsername(),false);
		if(list.size()==0){
			return ResultVOUtil.error("无此用户");
		}
		User userPO = list.get(0);
		String voPswd = user.getPassword();
		String poPswd = userPO.getPassword();
		String saltStr = StringUtils.substring(poPswd, 0, 16);
		poPswd = StringUtils.substring(poPswd, 16);
		String checksum = Sha1Util.encrypt(voPswd, saltStr);
		if(StringUtils.equals(checksum, poPswd)){
			user = userPO;
			String token = jwtHelper.createToken(user);
			tokenLogRepository.deleteUserToken(user.getId());
			UserTokenLog tokenLog = new UserTokenLog();
			tokenLog.setJwtToken(token);
			tokenLog.setUser(user);
			tokenLogRepository.save(tokenLog);
			return ResultVOUtil.success(token);
		}else{
			return ResultVOUtil.error("密码错误");
		}
	}
	
	
	
	@Transactional
	public void logout(String token){
		if(StringUtils.isBlank(token)){
			return;
		}
		User user = jwtHelper.verifyToken(token);
		if(user!=null){
			UserTokenLog tokenLog = tokenLogRepository.getUserToken(user.getId());
			if(StringUtils.equals(token, tokenLog.getJwtToken())){
				tokenLogRepository.delete(tokenLog);
			}
		}
	}
	
	public ResultVo<?> audit(User loginUser,User auditUser){
		List<User> list = userRepository.findByUsernameAndDeleted(auditUser.getUsername(),false);
		if(list.size()==0){
			return ResultVOUtil.error("用户不存在");
		}
		User userPO = list.get(0);
		if(!StringUtils.equals("admin", userPO.getUserType())){
			return ResultVOUtil.error("管理员权限才能进行复核");
		}
		if(loginUser.getDepartment()==null||auditUser.getDepartment()==null){
			return ResultVOUtil.error("登录账号与复核账号存在机构信息为空");
		}
		if(!StringUtils.equals(loginUser.getDepartment().getId(), auditUser.getDepartment().getId())){
			return ResultVOUtil.error("只有同一机构才能进行复核");
		}
		String voPswd = auditUser.getPassword();
		String poPswd = userPO.getPassword();
		String saltStr = StringUtils.substring(poPswd, 0, 16);
		poPswd = StringUtils.substring(poPswd, 16);
		String checksum = Sha1Util.encrypt(voPswd, saltStr);
		if(StringUtils.equals(checksum, poPswd)){
			return ResultVOUtil.success(userPO.getId());
		}else{
			return ResultVOUtil.error("密码错误");
		}
	}
}
