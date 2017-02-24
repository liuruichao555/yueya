package com.yueya.customer.dto;

import java.io.Serializable;

import com.yueya.customer.model.Customer;
import com.yueya.customer.model.CustomerLike;
import com.yueya.util.CustomerLevelUtil;

public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String cusName;
	private String imageUrl;
	private String points;
	private String cusLevel;
	private String topicCount;
	private String joinEventCount;
	private String createEventCount;
	private String msgCount;
	private String realName;
	private String phone;
	private String like;
	private String age;
	private String gender;

	public CustomerDTO() {
	}

	public CustomerDTO(String id, String cusName, String imageUrl) {
		super();
		this.id = id;
		this.cusName = cusName;
		this.imageUrl = imageUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(String topicCount) {
		this.topicCount = topicCount;
	}

	public String getJoinEventCount() {
		return joinEventCount;
	}

	public void setJoinEventCount(String joinEventCount) {
		this.joinEventCount = joinEventCount;
	}

	public String getCreateEventCount() {
		return createEventCount;
	}

	public void setCreateEventCount(String createEventCount) {
		this.createEventCount = createEventCount;
	}

	public String getCusLevel() {
		return cusLevel;
	}

	public void setCusLevel(String cusLevel) {
		this.cusLevel = cusLevel;
	}

	public String getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(String msgCount) {
		this.msgCount = msgCount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * 持久化对象转换为DTO对象
	 * 
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.id = customer.getId() + "";
		this.cusName = customer.getUsername() == null ? "" : customer
				.getUsername();
		this.imageUrl = customer.getImageUrl() == null ? "" : customer
				.getImageUrl();
		this.points = customer.getPoints() + "";
		this.cusLevel = CustomerLevelUtil
				.getCustomerLevel(customer.getPoints());
		this.realName = customer.getRealname() == null ? "" : customer
				.getRealname();
		this.phone = customer.getPhone() == null ? "" : customer.getPhone();
		this.age = customer.getAge() + "";
		this.gender = customer.getGender() + "";
		if (customer.getLikes() != null && customer.getLikes().size() > 0) {
			StringBuffer sbu = new StringBuffer();
			for (CustomerLike like : customer.getLikes()) {
				sbu.append(like.getEventType().getName()).append(",");
			}
			sbu.deleteCharAt(sbu.length() - 1);
			this.like = sbu.toString();
		} else
			this.like = "";
	}
}
