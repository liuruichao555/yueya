package com.yueya.topic.dto;

import java.io.Serializable;

import com.yueya.topic.model.TopicReply;
import com.yueya.util.CustomerLevelUtil;
import com.yueya.util.DateFormatUtil;

public class TopicReplyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String content;
	private String createTime;
	private String cusName;
	private String cusImageUrl;
	private String cusLevel;// 用户称号
	private String cusId;
	private String points;// 积分

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusImageUrl() {
		return cusImageUrl;
	}

	public void setCusImageUrl(String cusImageUrl) {
		this.cusImageUrl = cusImageUrl;
	}

	public String getCusLevel() {
		return cusLevel;
	}

	public void setCusLevel(String cusLevel) {
		this.cusLevel = cusLevel;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	/**
	 * 持久化对象转为DTO对象
	 * 
	 * @param reply
	 */
	public void setTopicReply(TopicReply reply) {
		this.id = reply.getId() + "";
		this.content = reply.getContent();
		this.createTime = DateFormatUtil.dateToStr(reply.getCreateTime());
		this.cusName = reply.getCustomer().getUsername();
		this.cusImageUrl = reply.getCustomer().getImageUrl() == null ? ""
				: reply.getCustomer().getImageUrl();
		this.points = reply.getCustomer().getPoints() + "";
		this.cusLevel = CustomerLevelUtil.getCustomerLevel(reply.getCustomer()
				.getPoints());
		this.cusId = reply.getCustomer().getId() + "";
	}
}
