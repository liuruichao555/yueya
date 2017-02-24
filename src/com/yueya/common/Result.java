package com.yueya.common;

import java.io.Serializable;

/**
 * json 数据实体类
 * 
 * @author liuruichao
 * 
 */
public final class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer status;
//	private String message;
	private T data;
	
	public Result(Integer status, String message, T data) {
		this.status = status;
//		this.message = message;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
