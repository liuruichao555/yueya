package com.yueya.event.service;

import java.util.List;

import com.yueya.base.IBaseService;
import com.yueya.common.PageBean;
import com.yueya.customer.model.Customer;
import com.yueya.event.model.EventSignup;

public interface IEventSignupService extends IBaseService<EventSignup> {
	/**
	 * 获取参加活动的用户
	 * @param eventId
	 * @param isAll true：表示获取所有报名用户 false：表示只获取前四个
	 * @return
	 */
	List<EventSignup> getSignupCustomer(Integer eventId, boolean isAll);
	/**
	 * 获取用户参加过哪些活动
	 * @param customerId
	 * @param curPage
	 * @return
	 */
	PageBean<EventSignup> getSignupByCustomer(Integer customerId, Integer curPage);
	boolean isSignup(Integer eventId, Integer customerId);
	void delSignup(Integer eventId, Integer customerId);
	/**
	 * 报名参加活动
	 * @param eventId
	 * @param customer
	 * @return 1表示参加成功，2表示人数已满
	 */
	Integer signUp(Integer eventId, Customer customer);
}
