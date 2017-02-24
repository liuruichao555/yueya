package com.yueya.topic.dto;

import java.io.Serializable;
import java.util.List;

import com.yueya.topic.model.Topic;
import com.yueya.topic.model.TopicImage;
import com.yueya.util.CustomerLevelUtil;
import com.yueya.util.DateFormatUtil;

public class TopicDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String content;
	private String createTime;
	private String cusName;
	private String cusImageUrl;
	private String cusLevel;// 用户称号
	private String cusId;
	private String points;// 积分
	private String replyCount;
	private String lastUpdateTime;
	private List<TopicReplyDTO> replys;// 评论
	private String images;

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

	public String getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public List<TopicReplyDTO> getReplys() {
		return replys;
	}

	public void setReplys(List<TopicReplyDTO> replys) {
		this.replys = replys;
	}

	public void setCusLevel(String cusLevel) {
		this.cusLevel = cusLevel;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getCusLevel() {
		return cusLevel;
	}

	public String getPoints() {
		return points;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	/***
	 * 列表页
	 * 
	 * @param topic
	 */
	public void setTopicByList(Topic topic) {
		this.id = topic.getId() + "";
		this.title = topic.getTitle();
		this.content = topic.getContent();
		this.createTime = DateFormatUtil.dateToStr(topic.getCreateTime());
		this.cusName = topic.getCustomer().getUsername();
		this.cusImageUrl = topic.getCustomer().getImageUrl() == null ? ""
				: topic.getCustomer().getImageUrl();
		this.replyCount = topic.getReplyCount() + "";
		this.lastUpdateTime = DateFormatUtil.dateToStr(topic
				.getLastUpdateTime());
		this.points = topic.getCustomer().getPoints() + "";
		this.cusLevel = CustomerLevelUtil.getCustomerLevel(topic.getCustomer()
				.getPoints());
		this.cusId = topic.getCustomer().getId() + "";
		if(topic.getImages() != null && topic.getImages().size() > 0) {
			StringBuffer sbu = new StringBuffer();
			
			for(TopicImage image : topic.getImages()) {
				sbu.append(image.getUrl()).append(",");
			}
			
			sbu.deleteCharAt(sbu.length() - 1);
			this.images = sbu.toString();
		} else 
			this.images = "";
	}

	/***
	 * 详情页
	 * 
	 * @param topic
	 */
	public void setTopicByDetail(Topic topic) {
		this.id = topic.getId() + "";
		this.title = topic.getTitle();
		this.content = topic.getContent();
		this.createTime = DateFormatUtil.dateToStr(topic.getCreateTime());
		this.cusName = topic.getCustomer().getUsername();
		this.cusImageUrl = topic.getCustomer().getImageUrl() == null ? ""
				: topic.getCustomer().getImageUrl();
		this.replyCount = topic.getReplyCount() + "";
		this.lastUpdateTime = DateFormatUtil.dateToStr(topic
				.getLastUpdateTime());
		this.points = topic.getCustomer().getPoints() + "";
		this.cusLevel = CustomerLevelUtil.getCustomerLevel(topic.getCustomer()
				.getPoints());
		this.cusId = topic.getCustomer().getId() + "";
		if(topic.getImages() != null && topic.getImages().size() > 0) {
			StringBuffer sbu = new StringBuffer();
			
			for(TopicImage image : topic.getImages()) {
				sbu.append(image.getUrl()).append(",");
			}
			
			sbu.deleteCharAt(sbu.length() - 1);
			this.images = sbu.toString();
		} else 
			this.images = "";
	}
}
