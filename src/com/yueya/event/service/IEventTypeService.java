package com.yueya.event.service;


import java.util.List;

import com.yueya.base.IBaseService;
import com.yueya.event.model.EventType;

public interface IEventTypeService extends IBaseService<EventType>{
	List<EventType> getRootType();
	List<EventType> getEventTypeImage();
}
