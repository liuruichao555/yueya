package com.yueya.event.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yueya.event.model.EventType;
import com.yueya.event.service.IEventTypeService;

import com.yueya.base.impl.BaseServiceImpl;

@Service
public class EventTypeServiceImpl extends BaseServiceImpl<EventType> implements IEventTypeService {
	@Override
	public List<EventType> getRootType() {
		String hql = "from EventType where parent=null";
		return queryByHql(hql, null);
	}

	@Override
	public List<EventType> getEventTypeImage() {
		String hql = "from EventType where imageUrl is not null";
		return queryByHql(hql, null);
	}
}
