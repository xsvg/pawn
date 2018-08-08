package cn.cnplay.demo.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@MappedSuperclass
public abstract class IDEntity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String randomID()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	@Id()
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id = randomID();

	@JsonIgnore
	@Transient
	private transient Page page;

	@Transient
	public void setPageNumber(int pageNumber)
	{
		if (page == null)
		{
			page = new Page();
		}
		page.setPageNumber(pageNumber);
	}

	@Transient
	public void setPageSize(int pageSize)
	{
		if (page == null)
		{
			page = new Page();
		}
		page.setPageSize(pageSize);
	}

	@JsonIgnore
	public int getFirstResult()
	{
		if (page == null)
		{
			page = new Page();
		}
		return page.getFirstResult();
	}

	@JsonIgnore
	public int getMaxResults()
	{
		if (page == null)
		{
			page = new Page();
		}
		return page.getMaxResults();
	}

	@JsonIgnore
	public void setMaxResults(int maxResults)
	{
		if (page == null)
		{
			page = new Page();
		}
		page.setPageNumber(0);
		page.setPageSize(maxResults);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Page getPage()
	{
		return page;
	}

	public void setPage(Page page)
	{
		this.page = page;
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
		return (id != null ? id.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return getClass().getName() + "@id=" + this.id;
	}

}
