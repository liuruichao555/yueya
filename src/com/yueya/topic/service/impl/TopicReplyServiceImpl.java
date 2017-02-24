package com.yueya.topic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.common.PageBean;
import com.yueya.customer.model.Customer;
import com.yueya.customer.service.IMessageService;
import com.yueya.topic.model.TopicReply;
import com.yueya.topic.service.ITopicReplyService;

@Service
public class TopicReplyServiceImpl extends BaseServiceImpl<TopicReply>
		implements ITopicReplyService {
	@Resource
	private IMessageService messageService;

	@Override
	public List<TopicReply> getReplyByTop(Integer topicId, int max) {
		String hql = "from TopicReply where topic.id=?";
		return daoSupport.list(hql, new Object[] { topicId }, 0, max);
	}

	@Override
	public PageBean<TopicReply> list(Integer topicId, Integer curPage) {
		String hql = "from TopicReply where topic.id=?";
		PageBean<TopicReply> pageBean = new PageBean<TopicReply>();
		pageBean.setPageIndex(curPage);
		return queryByPage(hql, new Object[] { topicId }, pageBean);
	}

	@Override
	public Integer getCountByTopic(Integer topicId) {
		String hql = "from TopicReply where topic.id=?";
		return queryByCount(hql, new Object[] { topicId });
	}

	@Override
	public void addTopic(TopicReply reply) {
		Customer fromCustomer = reply.getCustomer();
		Customer toCustomer = reply.getTopic().getCustomer();
		messageService.addNotice(fromCustomer, toCustomer,
				fromCustomer.getUsername() + " 评论了您的话题 "
						+ reply.getTopic().getTitle(), reply.getContent(), 1,
				reply.getTopic().getId());
		saveEntity(reply);
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
}
