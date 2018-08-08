package cn.cnplay.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * 微信收发信息表
 * 
 * @author peixere@qq.com
 *
 */
@Entity(name = "wx_message_log")
public class WxMessageLog extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2435143570545882700L;

	/**
	 * 公众帐号
	 */
	@Column(name = "to_username", length = 128)
	private String ToUserName;

	/**
	 * 发送方帐号（open_id）
	 */
	@Column(name = "from_username", length = 128)
	private String FromUserName;

	/**
	 * 消息时间
	 */
	@Column(name = "createtime", length = 128)
	private long CreateTime = System.currentTimeMillis();

	/**
	 * 消息类型
	 */
	@Column(name = "msgtype", length = 64)
	private String MsgType;

	/**
	 * 消息内容
	 */
	@Column(name = "content", length = 4000)
	private String Content;

	/**
	 * 消息ID
	 */
	@Column(name = "msgid", length = 128)
	private String MsgId;

	/**
	 * 事件类型
	 */
	@Column(name = "event", length = 64)
	private String Event;

	/**
	 * 事件KEY值，与创建自定义菜单时指定的KEY值对应
	 */
	@Column(name = "eventkey", length = 64)
	private String EventKey;

	/**
	 * 收发信息方向：0.收;1.发
	 */
	@Column(length = 2)
	private Integer direction = 0;

	/**
	 * 接收到的消息ID
	 */
	@Column(length = 32)
	private String pid;

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

	public Integer getDirection()
	{
		return direction;
	}

	public void setDirection(Integer direction)
	{
		this.direction = direction;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

}
