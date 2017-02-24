package com.yueya.event.service;



import com.yueya.base.IBaseService;
import com.yueya.event.model.EventFavor;

public interface IEventFavorService extends IBaseService<EventFavor>{
	boolean isFavor(Integer customerId, Integer eventId);
}
