package cn.cnplay.demo.model;

import javax.persistence.Entity;

/**
 * 微信验证日志
 * 
 * @author peixere@qq.com
 *
 */
@Entity(name = "wx_auth_log")
public class WxAuthLog extends BaseEntity
{
	/**
	 * 微信验证日志
	 */
	private static final long serialVersionUID = 1519049436276516648L;
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;
	private String openid;
	private Integer success;

	public String getSignature()
	{
		return signature;
	}

	public void setSignature(String signature)
	{
		this.signature = signature;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getNonce()
	{
		return nonce;
	}

	public void setNonce(String nonce)
	{
		this.nonce = nonce;
	}

	public String getEchostr()
	{
		return echostr;
	}

	public void setEchostr(String echostr)
	{
		this.echostr = echostr;
	}

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}

	public Integer getSuccess()
	{
		return success;
	}

	public void setSuccess(Integer success)
	{
		this.success = success;
	}

}
