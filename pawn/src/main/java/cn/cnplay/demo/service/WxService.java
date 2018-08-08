package cn.cnplay.demo.service;

import cn.cnplay.demo.model.User;
import cn.cnplay.demo.model.WxAuthLog;
import cn.cnplay.demo.model.WxMessageLog;

public interface WxService
{

	void save(User user);

	void save(WxMessageLog log);

	void save(WxAuthLog log);

}
