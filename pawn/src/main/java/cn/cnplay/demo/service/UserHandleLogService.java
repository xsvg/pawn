package cn.cnplay.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cnplay.demo.model.UserHandleLog;
import cn.cnplay.demo.repository.UserHandleLogRepository;

@Service
public class UserHandleLogService {

	@Autowired UserHandleLogRepository handleLogRepository;
	
	@Transactional
	public UserHandleLog addLog(UserHandleLog log){
		return handleLogRepository.save(log);
	}
}
