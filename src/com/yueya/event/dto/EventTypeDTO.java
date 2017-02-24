package com.yueya.event.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yueya.event.model.EventType;

public class EventTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String parentId;
	private List<EventTypeDTO> childs;
	private String imageUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<EventTypeDTO> getChilds() {
		return childs;
	}

	public void setChilds(List<EventTypeDTO> childs) {
		this.childs = childs;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * EventType 对象转为 DTO 对象
	 * 
	 * @param eventType
	 */
	public void setEventType(EventType eventType) {
		this.id = eventType.getId() + "";
		this.name = eventType.getName();
		this.parentId = eventType.getParent() == null ? "" : eventType
				.getParent().getId() + "";
		this.childs = getEventTypeChild(eventType);
		this.imageUrl = eventType.getImageUrl() == null ? "" : eventType
				.getImageUrl();
	}

	/**
	 * 递归得到子节点
	 * 
	 * @param eventType
	 * @return
	 */
	private List<EventTypeDTO> getEventTypeChild(EventType eventType) {
		List<EventTypeDTO> list = new ArrayList<>();
		if (eventType != null) {
			EventTypeDTO dto = null;
			for (EventType type : eventType.getTypes()) {
				dto = new EventTypeDTO();
				getEventTypeChild(type);
				dto.setEventType(type);
				list.add(dto);
			}
		}
		return list;
	}

}
