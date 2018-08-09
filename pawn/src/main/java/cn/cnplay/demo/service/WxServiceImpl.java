package cn.cnplay.demo.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.cnplay.demo.model.User;
import cn.cnplay.demo.model.WxAuthLog;
import cn.cnplay.demo.model.WxMessageLog;
import cn.cnplay.demo.repository.UserRepository;
import cn.cnplay.demo.repository.WxAuthLogRepository;
import cn.cnplay.demo.repository.WxMessageLogRepository;

@Service
public class WxServiceImpl implements WxService
{
	@Resource
	private UserRepository userRepository;

	@Resource
	private WxAuthLogRepository wxAuthLogRepository;

	@Resource
	private WxMessageLogRepository wxMessageLogRepository;

	@Transactional
	@Override
	public void save(User user)
	{
		userRepository.save(user);
		throw new RuntimeException();
	}

	@Transactional
	@Override
	public void save(WxMessageLog log)
	{
		wxMessageLogRepository.save(log);
	}

	@Transactional
	@Override
	public void save(WxAuthLog log)
	{
		wxAuthLogRepository.save(log);
	}
}
