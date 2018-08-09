package cn.cnplay.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User extends BaseEntity
{

	private static final long serialVersionUID = -1L;

	@Column(name="username",updatable=false)
	private String username;
	private String name;
	private String address;
	private String password;
	@Column(name="user_type")
	private String userType;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=Department.class)
	@JoinColumn(name="dept_id")
	private Department department;
	@Column(name="contact_number")
	private String contactNumber;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	

}
