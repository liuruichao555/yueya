package com.yueya.customer.model;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Entity;

/**
 * 通知实体
 * @author liuruichao
 *
 */
@Entity
@Table(name = "Message")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fromCusId", nullable = false)
	private Customer fromCustomer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toCusId", nullable = false)
	private Customer toCustomer;
	private String title;
	private String content;
	private Integer status;//0 代表未读 1 代表已读
	private Integer type;//1 代表主题通知 2 代表活动通知 3 代表系统通知
	private Integer objId;//用户跳转到详情页的活动或者话题id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Customer getFromCustomer() {
		return fromCustomer;
	}
	public void setFromCustomer(Customer fromCustomer) {
		this.fromCustomer = fromCustomer;
	}
	public Customer getToCustomer() {
		return toCustomer;
	}
	public void setToCustomer(Customer toCustomer) {
		this.toCustomer = toCustomer;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getObjId() {
		return objId;
	}
	public void setObjId(Integer objId) {
		this.objId = objId;
	}
}
