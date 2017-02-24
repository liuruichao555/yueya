package com.yueya.customer.service;


import com.yueya.base.IBaseService;
import com.yueya.common.PageBean;
import com.yueya.customer.model.Customer;
import com.yueya.customer.model.Message;

public interface IMessageService extends IBaseService<Message> {
	/**
	 * 发送消息
	 * @param fromCusId
	 * @param toCusId
	 * @param title
	 * @param content
	 */
	void addNotice(Customer fromCustomer, Customer toCustomer, String title, String content, Integer type, Integer objId);
	/**
	 * 修改通知状态
	 * @param id
	 * @param status
	 */
	void updateStatus(Integer id, Integer status);
	/**
	 * 获得用户通知信息
	 * @param customerId
	 * @return
	 */
	PageBean<Message> getNewMessage(Integer customerId, Integer curPage);
	Integer getNewCountByCustomer(Integer customerId);
}
