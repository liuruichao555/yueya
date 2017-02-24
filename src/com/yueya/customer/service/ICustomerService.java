package com.yueya.customer.service;

import com.yueya.base.IBaseService;
import com.yueya.customer.model.Customer;

public interface ICustomerService extends IBaseService<Customer>{
	Customer getCustomerByOpenId(String openId);
	boolean exsitsUsername(String username);
	/**
	 * 用户发布了多少话题
	 * @return
	 */
	Integer getTopicCount(Integer customerId);
	/**
	 * 用户参加过多少活动
	 * @param customerId
	 * @return
	 */
	Integer getJoinEventCount(Integer customerId);
	/**
	 * 用户组织过多少活动
	 * @param customerId
	 * @return
	 */
	Integer getCreateEventCount(Integer customerId);
	/**
	 * 用户增加积分
	 * @param points
	 */
	void addPoints(Integer points, Integer customerId);
	void updateCustomer(Customer customer, String like);
}
