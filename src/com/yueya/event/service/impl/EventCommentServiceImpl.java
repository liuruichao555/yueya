package com.yueya.event.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.common.PageBean;
import com.yueya.customer.model.Customer;
import com.yueya.customer.service.ICustomerService;
import com.yueya.customer.service.IMessageService;
import com.yueya.event.model.Event;
import com.yueya.event.model.EventComment;
import com.yueya.event.service.IEventCommentService;
import com.yueya.event.service.IEventService;

@Service
public class EventCommentServiceImpl extends BaseServiceImpl<EventComment>
		implements IEventCommentService {
	@Resource
	private IEventService eventService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private IMessageService messageService;

	@Override
	public Integer getCountByEvent(Integer eventId) {
		String hql = "from EventComment where isDelete = 0 and event.id=?";
		return queryByCount(hql, new Object[] { eventId });
	}

	@Override
	public EventComment addEventComment(Integer eventId, String content,
			Integer customerId) {
		Customer fromCustomer = customerService.getEntityById(customerId);
		Event event = eventService.getEntityById(eventId);

		EventComment comment = new EventComment();
		comment.setContent(content);
		comment.setCustomer(fromCustomer);
		comment.setEvent(event);
		comment.setIsDelete(false);
		comment.setCreateTime(new Date());
		saveEntity(comment);
		// 发送通知
		messageService.addNotice(fromCustomer, event.getCustomer(),
				fromCustomer.getUsername() + " 评论了您的活动 " + event.getTitle() + "",
				content, 2, event.getId());
		return comment;
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public PageBean<EventComment> list(Integer curPage, Integer eventId) {
		PageBean<EventComment> pageBean = new PageBean<>();
		pageBean.setPageIndex(curPage);
		return queryByPage("from EventComment where event.id=? order by id",
				new Object[] { eventId }, pageBean);
	}
}
