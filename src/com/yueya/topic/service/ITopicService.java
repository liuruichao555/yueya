package com.yueya.topic.service;

import com.yueya.base.IBaseService;
import com.yueya.common.PageBean;
import com.yueya.topic.model.Topic;

public interface ITopicService extends IBaseService<Topic>{
	PageBean<Topic> list(String types, Integer curPage);
	PageBean<Topic> listByCustomer(Integer customerId, Integer curPage);
}
