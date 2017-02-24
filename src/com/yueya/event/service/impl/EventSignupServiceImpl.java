package com.yueya.event.service.impl;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yueya.event.model.Event;
import com.yueya.event.model.EventSignup;
import com.yueya.event.service.IEventService;
import com.yueya.event.service.IEventSignupService;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.common.PageBean;
import com.yueya.customer.model.Customer;
import com.yueya.customer.service.IMessageService;

@Service
public class EventSignupServiceImpl extends BaseServiceImpl<EventSignup>
		implements IEventSignupService {
	@Resource
	private IMessageService messageService;
	@Resource
	private IEventService eventService;
	private Lock lock = new ReentrantLock();

	@Override
	public List<EventSignup> getSignupCustomer(Integer eventId, boolean isAll) {
		String hql = "from EventSignup where event.id=? order by id";
		if (isAll) {
			return queryByHql(hql, new Object[] { eventId });
		}
		return daoSupport.list(hql, new Object[] { eventId }, 0, 4);
	}

	@Override
	public boolean isSignup(Integer eventId, Integer customerId) {
		String hql = "from EventSignup where event.id=? and customer.id=?";
		return queryBySingle(hql, new Object[] { eventId, customerId }) != null ? true
				: false;
	}

	@Override
	public void delSignup(Integer eventId, Integer customerId) {
		String hql = "from EventSignup where event.id=? and customer.id=?";
		EventSignup eventSignup = queryBySingle(hql, new Object[] { eventId,
				customerId });
		if (eventSignup != null)
			daoSupport.delete(eventSignup);
	}

	@Override
	public PageBean<EventSignup> getSignupByCustomer(Integer customerId,
			Integer curPage) {
		String hql = "from EventSignup where customer.id=? order by -id";
		PageBean<EventSignup> pageBean = new PageBean<EventSignup>();
		pageBean.setPageIndex(curPage);
		return queryByPage(hql, new Object[] { customerId }, pageBean);
	}

	@Override
	public Integer signUp(Integer eventId, Customer customer) {
		Event event = null;
		// 同步模块
		lock.lock();
		try {
			event = eventService.getEntityById(eventId);
			// 报名人数达到指定人数 0表示不限报名人数
			if (event.getJoinCount() != 0
					&& event.getJoinCount() >= event.getPersonCount())
				return 2;
			// 增加参加人数
			event.setJoinCount(event.getJoinCount() + 1);
		} finally {
			//解锁
			lock.unlock();
		}

		EventSignup eventSignup = new EventSignup();
		eventSignup.setCustomer(customer);
		eventSignup.setEvent(event);
		saveEntity(eventSignup);
		Customer fromCustomer = eventSignup.getCustomer();
		Customer toCustomer = eventSignup.getEvent().getCustomer();
		messageService.addNotice(fromCustomer, toCustomer,
				fromCustomer.getUsername() + " 参加了您的活动。",
				fromCustomer.getUsername() + "参加了您发布的活动 "
						+ eventSignup.getEvent().getTitle() + "。", 2,
				eventSignup.getEvent().getId());
		return 1;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}
}
