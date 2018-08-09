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
		List<User> list = userRepository.findByUsername(user.getUsername());
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
			tokenLogRepository.delete(tokenLog);
		}
	}
}
