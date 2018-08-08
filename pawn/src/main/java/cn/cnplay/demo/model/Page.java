package cn.cnplay.demo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cn.cnplay.demo.annotation.Note;

@JsonInclude(Include.NON_NULL)
public class Page implements Serializable
{

	/**
	 * 分页类
	 */
	private static final long serialVersionUID = -6518359964486465431L;

	@Note("当前页")
	private int pageNumber = 1;
	@Note("每页行数")
	private int pageSize = 25;
	@Note("总页数")
	private long pageCount;
	@Note("总行数")
	private long total;

	public void setPageNumber(int pageNumber)
	{
		if (pageNumber < 1)
		{
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public void setTotal(long total)
	{
		this.total = total;
		pageCount = (total + pageSize - 1) / pageSize;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public long getPageCount()
	{
		return this.pageCount;
	}

	public int getFirstResult()
	{
		return pageSize * (pageNumber - 1);
	}

	public int getMaxResults()
	{
		return pageSize * pageNumber;
	}

	public long getTotal()
	{
		return total;
	}

}
