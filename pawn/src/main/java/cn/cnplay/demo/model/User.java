package cn.cnplay.demo.model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User extends BaseEntity
{

	private static final long serialVersionUID = -1L;

	private String username;
	private String name;
	private String address;

	public User()
	{

	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

}
