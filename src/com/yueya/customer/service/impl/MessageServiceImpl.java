package com.yueya.customer.service.impl;


import org.springframework.stereotype.Service;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.common.PageBean;
import com.yueya.customer.model.Customer;
import com.yueya.customer.model.Message;
import com.yueya.customer.service.IMessageService;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements
		IMessageService {
	@Override
	public void addNotice(Customer fromCustomer, Customer toCustomer,
			String title, String content, Integer type, Integer objId) {
		Message message = new Message();
		message.setFromCustomer(fromCustomer);
		message.setToCustomer(toCustomer);
		message.setTitle(title);
		message.setContent(content);
		message.setStatus(0);// 标记为未读
		message.setType(type);// 设置通知类型
		message.setObjId(objId);
		saveEntity(message);
	}

	@Override
	public void updateStatus(Integer id, Integer status) {
		Message message = getEntityById(id);
		message.setStatus(status);
		updateEntity(message);
	}

	@Override
	public PageBean<Message> getNewMessage(Integer customerId, Integer curPage) {
		String hql = "from Message where toCustomer.id=? order by -id";
		PageBean<Message> pageBean = new PageBean<Message>();
		pageBean.setPageIndex(curPage);
		return queryByPage(hql, new Object[] { customerId }, pageBean);
	}

	@Override
	public Integer getNewCountByCustomer(Integer customerId) {
		String hql = "from Message where toCustomer.id=? and status=0";
		return queryByCount(hql, new Object[] { customerId });
	}
}
