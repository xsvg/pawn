package cn.cnplay.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cnplay.demo.model.UserTokenLog;
import cn.cnplay.demo.repository.UserTokenLogRepository;

@Service
public class UserTokenLogService {
	
	@Autowired UserTokenLogRepository tokenLogRepository;
	
	public UserTokenLog getUserToken(String userId){
		return tokenLogRepository.getUserToken(userId);
	}
}
