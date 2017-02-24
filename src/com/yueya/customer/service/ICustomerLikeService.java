package com.yueya.customer.service;

import com.yueya.base.IBaseService;
import com.yueya.customer.model.Customer;
import com.yueya.customer.model.CustomerLike;


public interface ICustomerLikeService extends IBaseService<CustomerLike> {
	void addCustomerLike(CustomerLike customerLike);
	void delCustomerLike(Integer customerId, Integer eventTypeId);
	boolean exsits(Integer cusId, Integer eventId);
	void updateCustomerLike(Customer customer, String typeId);
}
