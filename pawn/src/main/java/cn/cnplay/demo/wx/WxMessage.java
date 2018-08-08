package cn.cnplay.demo.wx;

/**
 * 微信响应信息
 * @author peixere@qq.com
 *
 */
public class WxMessage
{
	/**
	 * 公众帐号
	 */
	private String ToUserName;

	/**
	 * 发送方帐号（open_id）
	 */
	private String FromUserName;

	/**
	 * 消息时间
	 */
	private long CreateTime = System.currentTimeMillis();

	/**
	 * 消息类型
	 */
	private String MsgType;

	/**
	 * 消息内容
	 */
	private String Content;

	/**
	 * 消息ID
	 */
	private String MsgId;

	/**
	 * 事件类型
	 */
	private String Event;

	/**
	 * 事件KEY值，与创建自定义菜单时指定的KEY值对应
	 */
	private String EventKey;

	public String getToUserName()
	{
		return ToUserName;
	}

	public void setToUserName(String toUserName)
	{
		ToUserName = toUserName;
	}

	public String getFromUserName()
	{
		return FromUserName;
	}

	public void setFromUserName(String fromUserName)
	{
		FromUserName = fromUserName;
	}

	public long getCreateTime()
	{
		return CreateTime;
	}

	public void setCreateTime(long createTime)
	{
		CreateTime = createTime;
	}

	public String getMsgType()
	{
		return MsgType;
	}

	public void setMsgType(String msgType)
	{
		MsgType = msgType;
	}

	public String getContent()
	{
		return Content;
	}

	public void setContent(String content)
	{
		Content = content;
	}

	public String getMsgId()
	{
		return MsgId;
	}

	public void setMsgId(String msgId)
	{
		MsgId = msgId;
	}

	public String getEvent()
	{
		return Event;
	}

	public void setEvent(String event)
	{
		Event = event;
	}

	public String getEventKey()
	{
		return EventKey;
	}

	public void setEventKey(String eventKey)
	{
		EventKey = eventKey;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[FromUserName=" + getFromUserName());
		sb.append(",ToUserName=" + getToUserName());
		if (getEvent() != null)
		{
			sb.append(",Event=" + getEvent());
		}
		if (EventKey != null)
		{
			sb.append(",EventKey=" + EventKey);
		}
		sb.append(",MsgType=" + getMsgType());
		sb.append(",Content=" + getContent());
		if (MsgId != null)
		{
			sb.append(",MsgId=" + MsgId);
		}
		sb.append("]");
		return sb.toString();
	}
}
