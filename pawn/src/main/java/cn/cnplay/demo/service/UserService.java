package cn.cnplay.demo.service;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.User;
import cn.cnplay.demo.repository.UserRepository;
import cn.cnplay.demo.utils.ResultVOUtil;
import cn.cnplay.demo.utils.Sha1Util;
import cn.cnplay.demo.vo.ResultVo;

@Service
public class UserService {

	private final static String DEFAULT_PSWD = "88888888";
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional
	public ResultVo<User> add(User user){
		byte[] salt = Sha1Util.generateSalt();
		String password = Hex.encodeHexString(salt)+ Hex.encodeHexString(Sha1Util.encrypt(DEFAULT_PSWD.getBytes(), salt));
		user.setPassword(password);
		user = userRepository.save(user);
		return ResultVOUtil.success(user);
	}
	
	
	@Transactional
	public ResultVo<User> update(User user){
		User userPo = userRepository.findOne(user.getId());
		if(userPo==null){
			return ResultVOUtil.error("更新失败,用户不存在或已被删除");
		}else{
			userPo.setName(user.getName());
			userPo.setUserType(user.getUserType());
			userPo.setDepartment(user.getDepartment());
			userPo.setTimeUpdate(System.currentTimeMillis());
			userPo.setAddress(user.getAddress());
			userPo.setContactNumber(user.getContactNumber());
			userPo.setMemo(user.getMemo());
			if(StringUtils.isNotBlank(user.getPassword())){
				String password = user.getPassword();
				byte[] salt = Sha1Util.generateSalt();
				password = Hex.encodeHexString(salt)+ Hex.encodeHexString(Sha1Util.encrypt(password.getBytes(), salt));
				userPo.setPassword(password);
			}
			userRepository.save(userPo);
			return ResultVOUtil.success();
		}
	}
	
	@Transactional
	public ResultVo<?> updatePswd(String userId,String oldPswd,String newPswd){
		User user = userRepository.findOne(userId);
		if(user==null){
			return ResultVOUtil.error("用户不存在");
		}else{
			String password = user.getPassword();
			String saltStr = StringUtils.substring(password, 0, 16);
			password = StringUtils.substring(password, 16);
			String encrypt = Sha1Util.encrypt(oldPswd, saltStr);
			if(StringUtils.equals(password, encrypt)){
				encrypt = Sha1Util.encrypt(newPswd, saltStr);
				password = saltStr + encrypt;
				user.setPassword(password);
				userRepository.save(user);
				return ResultVOUtil.success();
			}else{
				return ResultVOUtil.error("原密码错误");
			}
		}
	}
	
	@Transactional
	public ResultVo<User> delete(String id){
		userRepository.delete(id);
		return ResultVOUtil.success();
	}
}
