package cn.cnplay.demo.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * 实体表基类
 * 
 * @author peixere@qq.com
 * 
 * 
 */
@JsonInclude(Include.NON_NULL)
@MappedSuperclass
public abstract class BaseEntity extends IDEntity
{
	private static final long serialVersionUID = 1L;

	@Column(name = "time_create", updatable = false)
	@OrderBy("desc")
	private Long timeCreate = System.currentTimeMillis();

	@Column(name = "time_update")
	private Long timeUpdate = System.currentTimeMillis();

	@Column(name = "memo", length = 512, nullable = true)
	private String memo;

	public Long getTimeCreate()
	{
		return timeCreate;
	}

	public void setTimeCreate(Long timeCreate)
	{
		this.timeCreate = timeCreate;
	}

	public Long getTimeUpdate()
	{
		return timeUpdate;
	}

	public void setTimeUpdate(Long timeUpdate)
	{
		this.timeUpdate = timeUpdate;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

}
