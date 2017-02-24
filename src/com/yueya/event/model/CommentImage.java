package com.yueya.event.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 评论图片
 * @author liuruichao
 *
 */
@Entity
@Table(name = "CommentImage")
public class CommentImage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	private String url;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commentId", nullable = false)
	private EventComment comment;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public EventComment getComment() {
		return comment;
	}
	public void setComment(EventComment comment) {
		this.comment = comment;
	}
}
