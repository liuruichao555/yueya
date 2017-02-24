package com.yueya.topic.service.impl;

import org.springframework.stereotype.Service;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.common.PageBean;
import com.yueya.topic.model.Topic;
import com.yueya.topic.service.ITopicService;

@Service
public class TopicServiceImpl extends BaseServiceImpl<Topic> implements
		ITopicService {
	@Override
	public PageBean<Topic> list(String types, Integer curPage) {
		StringBuffer sbu = new StringBuffer("from Topic where type in(");

		String[] eventType = types.split(",");
		for (String type : eventType) {
			sbu.append(type).append(",");
		}
		sbu.deleteCharAt(sbu.length() - 1);
		sbu.append(") order by lastUpdateTime desc");
		PageBean<Topic> pageBean = new PageBean<Topic>();
		pageBean.setPageIndex(curPage);
		return queryByPage(sbu.toString(), null, pageBean);
	}

	@Override
	public PageBean<Topic> listByCustomer(Integer customerId, Integer curPage) {
		String hql = "from Topic where customer.id=? order by -id";
		PageBean<Topic> pageBean = new PageBean<Topic>();
		pageBean.setPageIndex(curPage);
		return queryByPage(hql, new Object[] { customerId }, pageBean);
	}
}
