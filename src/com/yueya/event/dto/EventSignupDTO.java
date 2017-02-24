package com.yueya.event.dto;

import java.io.Serializable;

import com.yueya.event.model.EventSignup;
import com.yueya.util.CustomerLevelUtil;

public class EventSignupDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cusId;
	private String cusName;
	private String imageUrl;
	private String phone;
	private String points;
	private String cusLevel;

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getCusLevel() {
		return cusLevel;
	}

	public void setCusLevel(String cusLevel) {
		this.cusLevel = cusLevel;
	}

	/**
	 * 持久化对象转为DTO对象
	 * 
	 * @param eventSignup
	 */
	public void setEventSignup(EventSignup eventSignup) {
		this.cusId = eventSignup.getCustomer().getId() + "";
		this.cusName = eventSignup.getCustomer().getUsername() + "";
		this.imageUrl = eventSignup.getCustomer().getImageUrl();
		this.phone = eventSignup.getPhone();
		this.points = eventSignup.getCustomer().getPoints() + "";
		this.cusLevel = CustomerLevelUtil.getCustomerLevel(eventSignup
				.getCustomer().getPoints());
	}
}
