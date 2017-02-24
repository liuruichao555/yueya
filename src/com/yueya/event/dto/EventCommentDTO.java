package com.yueya.event.dto;

import java.io.Serializable;

import com.yueya.event.model.CommentImage;
import com.yueya.event.model.EventComment;
import com.yueya.util.DateFormatUtil;

public class EventCommentDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String commentImage;// 1.jpg 2.jpg 3.jpg
	private String content;
	private String cusName;
	private String cusImageUrl;
	private String createTime;
	private String cusId;

	public String getCommentImage() {
		return commentImage;
	}

	public void setCommentImage(String commentImage) {
		this.commentImage = commentImage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public void setEventComment(EventComment comment) {
		this.id = comment.getId();
		StringBuffer sbu = new StringBuffer();
		if (comment.getImage() != null) {
			for (CommentImage image : comment.getImage()) {
				sbu.append(image.getUrl()).append(",");
			}
			if (sbu.length() > 0)
				sbu.deleteCharAt(sbu.length() - 1);
		}
		this.commentImage = sbu.length() == 0 ? "" : sbu.toString();
		this.content = comment.getContent();
		this.cusName = comment.getCustomer().getUsername();
		this.cusImageUrl = comment.getCustomer().getImageUrl() == null ? ""
				: comment.getCustomer().getImageUrl();
		this.createTime = DateFormatUtil.dateToStr(comment.getCreateTime());
		this.cusId = comment.getCustomer().getId() + "";
	}
}
