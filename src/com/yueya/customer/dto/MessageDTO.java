package com.yueya.customer.dto;

import java.io.Serializable;

import com.yueya.customer.model.Message;

public class MessageDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String content;
	private String type;
	private String objId;
	private String status;// 0 表示未读 1表示已读

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getType() {
		return type;
	}

	public String getObjId() {
		return objId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 将持久化对象转换为DTO对象
	 * 
	 * @param message
	 */
	public void setMessage(Message message) {
		this.id = message.getId() + "";
		this.title = message.getTitle();
		this.content = message.getContent();
		this.type = message.getType() + "";
		this.objId = message.getObjId() + "";
		this.status = message.getStatus() + "";
	}
}
