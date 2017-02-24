package com.yueya.event.service;

import com.yueya.base.IBaseService;
import com.yueya.common.PageBean;
import com.yueya.event.model.EventComment;

public interface IEventCommentService extends IBaseService<EventComment>{
	Integer getCountByEvent(Integer eventId);
	EventComment addEventComment(Integer eventId, String content, Integer customerId);
	PageBean<EventComment> list(Integer curPage, Integer eventId);
}
