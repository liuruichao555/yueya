package com.yueya.customer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.customer.model.Customer;
import com.yueya.customer.model.CustomerLike;
import com.yueya.customer.service.ICustomerLikeService;
import com.yueya.customer.service.ICustomerService;
import com.yueya.event.service.IEventTypeService;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements
		ICustomerService {
	@Resource
	private ICustomerLikeService customerLikeService;
	@Resource
	private IEventTypeService eventTypeService;
	
	@Override
	public Customer getCustomerByOpenId(String openId) {
		String hql = "from Customer where openid=?";
		Object[] params = new Object[] { openId };
		return queryBySingle(hql, params);
	}

	@Override
	public boolean exsitsUsername(String username) {
		String hql = "from Customer where username=?";
		return queryBySingle(hql, new Object[] { username }) != null ? true
				: false;
	}

	@Override
	public Integer getTopicCount(Integer customerId) {
		String hql = "from Topic where customer.id=?";
		return queryByCount(hql, new Object[] { customerId });
	}

	@Override
	public Integer getJoinEventCount(Integer customerId) {
		String hql = "from EventSignup where customer.id=?";
		return queryByCount(hql, new Object[] { customerId });
	}

	@Override
	public Integer getCreateEventCount(Integer customerId) {
		String hql = "from Event where customer.id=?";
		return queryByCount(hql, new Object[] { customerId });
	}

	@Override
	public void addPoints(Integer points, Integer customerId) {
		String sql = "update Customer set points=points+? where id=?";
		executeBySql(sql, new Object[] { points, customerId });
	}

	@Override
	public void updateCustomer(Customer customer, String like) {
		updateEntity(customer);
		if(like != null && like.length() > 0) {
		String[] likes = like.split(",");
		if(likes != null && likes.length > 0) {
			CustomerLike customerLike = null;
			for(String tLike : likes) {
				if(!customerLikeService.exsits(customer.getId(), Integer.valueOf(tLike))) {
					//如果没有添加，则添加
					customerLike = new CustomerLike();
					customerLike.setCustomer(customer);
					customerLike.setEventType(eventTypeService.getEntityById(tLike));
					customerLikeService.saveEntity(customerLike);
				}
			}
		}
		}
	}
	public void setCustomerLikeService(ICustomerLikeService customerLikeService) {
		this.customerLikeService = customerLikeService;
	}

	public void setEventTypeService(IEventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}
}
