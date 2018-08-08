package cn.cnplay.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cn.cnplay.demo.annotation.Note;

@JsonInclude(Include.NON_NULL)
public class Json<T>
{
	@Note("请求者应当继续提出请求")
	public final static int StatusIng = 100;

	@Note("请求响应正确")
	public final static int StatusOK = 200;

	@Note("（未登录） 请求要求身份验证")
	public final static int StatusNotLogin = 401;

	@Note("需要授权")
	public final static int StatusNotAuth = 407;

	@Note("服务器内部错误")
	public final static int StatusError = 500;

	private int status = StatusOK;

	private String msg = "成功";

	private T data;

	private Page page;

	public Json()
	{
		this(null);
	}

	public Json(T data)
	{
		this.setData(data);
	}

	public Json(T data, String msg)
	{
		this(data, msg, StatusOK);
	}

	public Json(T data, String msg, int status)
	{
		this.setStatus(status);
		this.setMsg(msg);
		this.setData(data);
	}

	public void setPageNumber(int pageNumber)
	{
		if (page == null)
		{
			page = new Page();
		}
		page.setPageNumber(pageNumber);
	}

	public void setPageSize(int pageSize)
	{
		if (page == null)
		{
			page = new Page();
		}
		page.setPageSize(pageSize);
	}

	/***** 以下自动生成 *****/

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

	public Page getPage()
	{
		return page;
	}

	public void setPage(Page page)
	{
		this.page = page;
	}

}
