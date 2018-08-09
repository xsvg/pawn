package cn.cnplay.demo.vo;

import java.io.Serializable;

public class ResultVo<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private Integer code;
	private String msg;
	private T data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	
	
}
