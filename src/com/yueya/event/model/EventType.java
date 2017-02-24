package com.yueya.event.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "EventType")
public class EventType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private EventType parent;
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("id")
	private Set<EventType> types;
	private String imageUrl;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EventType getParent() {
		return parent;
	}
	public void setParent(EventType parent) {
		this.parent = parent;
	}
	public Set<EventType> getTypes() {
		return types;
	}
	public void setTypes(Set<EventType> types) {
		this.types = types;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
