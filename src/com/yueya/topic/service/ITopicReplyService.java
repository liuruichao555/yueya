package com.yueya.topic.service;

import java.util.List;

import com.yueya.base.IBaseService;
import com.yueya.common.PageBean;
import com.yueya.topic.model.TopicReply;

public interface ITopicReplyService extends IBaseService<TopicReply> {
	List<TopicReply> getReplyByTop(Integer topicId, int max);
	PageBean<TopicReply> list(Integer topicId, Integer curPage);
	Integer getCountByTopic(Integer topicId);
	void addTopic(TopicReply reply);
}
