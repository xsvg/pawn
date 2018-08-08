package cn.cnplay.demo.wx;

import java.io.Serializable;

/**
 * 微信验证信息
 * 
 * @author peixere@qq.com
 *
 */
public class WxAuthInfo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;
	private String openid;

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

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		return (o != null && o.toString().equals(this.toString()));
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode()
	{
		return super.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[openid=" + openid);
		sb.append(",signature=" + getSignature());
		sb.append(",timestamp=" + getTimestamp());
		sb.append(",nonce=" + getNonce());
		sb.append(",echostr=" + getEchostr());
		sb.append("]");
		return sb.toString();
	}
}
