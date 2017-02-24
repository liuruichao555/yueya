package com.yueya.event.service;

import com.yueya.base.IBaseService;
import com.yueya.common.PageBean;
import com.yueya.customer.model.Customer;
import com.yueya.event.model.Event;

public interface IEventService extends IBaseService<Event>{
	PageBean<Event> list(String typeId, Integer cityId, Integer curPage);
	PageBean<Event> listByCreateCustomer(Integer customerId, Integer curPage);
	void favor(Integer id, Integer flag, Customer customer);
	void addShare(Integer eventId);
}
