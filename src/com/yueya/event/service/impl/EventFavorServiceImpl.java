package com.yueya.event.service.impl;


import org.springframework.stereotype.Service;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.event.model.EventFavor;
import com.yueya.event.service.IEventFavorService;

@Service
public class EventFavorServiceImpl extends BaseServiceImpl<EventFavor>
		implements IEventFavorService {
	@Override
	public boolean isFavor(Integer customerId, Integer eventId) {
		String hql = "from EventFavor where eventId=? and customerId=?";
		Integer count = queryByCount(hql, new Object[] { eventId, customerId });
		return count == 0 ? false : true;
	}
}
