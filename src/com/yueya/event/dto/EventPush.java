package com.yueya.event.dto;

import java.io.Serializable;

import com.yueya.event.model.Event;

/**
 * 活动推送推送
 * @author liuruichao
 *
 */
public class EventPush implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String content;
	private String cityId;
	private String typeId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * 持久化对象转化为推送对象
	 * @param event
	 */
	public void setEvent(Event event) {
		this.id = event.getId() + "";
		this.title = event.getTitle();
		this.content = event.getContent();
		this.cityId = event.getCityId() + "";
		this.typeId = event.getTypeId() + "";
	}
}
