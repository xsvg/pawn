package cn.cnplay.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import cn.cnplay.demo.wx.WxMessage;
import cn.cnplay.demo.wx.WxMsgType;
import cn.cnplay.demo.wx.WxUtils;

public class WxMsgTest
{

	private static final Logger logger = LoggerFactory.getLogger(WxMsgTest.class);

	public static void main(String[] args)
	{
		WxMessage req = new WxMessage();
		BeanWrapper beanWrapper = new BeanWrapperImpl(req);
		beanWrapper.setPropertyValue("ToUserName", "peixere");
		beanWrapper.setPropertyValue("Event", "event");
		WxMessage res = new WxMessage();
		res.setContent("内容");
		res.setCreateTime(System.currentTimeMillis());
		res.setToUserName("toUser");
		res.setFromUserName("from");
		res.setMsgType(WxMsgType.EVENT_CLICK);
		logger.info(WxUtils.toXML(res));
	}
}
