package com.yueya.customer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.customer.model.Customer;
import com.yueya.customer.model.CustomerLike;
import com.yueya.customer.service.ICustomerLikeService;
import com.yueya.event.service.IEventTypeService;

@Service
public class CustomerLikeServiceImpl extends BaseServiceImpl<CustomerLike>
		implements ICustomerLikeService {
	@Resource
	private IEventTypeService eventTypeService;

	@Override
	public void addCustomerLike(CustomerLike customerLike) {
		String hql = "from CustomerLike where customer.id=? and eventType.id=?";
		if (queryBySingle(hql, new Object[] {
				customerLike.getCustomer().getId(),
				customerLike.getEventType().getId() }) == null) {
			saveEntity(customerLike);
		}
	}

	@Override
	public void delCustomerLike(Integer customerId, Integer eventTypeId) {
		String hql = "from CustomerLike where customer.id=? and eventType.id=?";
		Object[] params = new Object[] { customerId, eventTypeId };
		if (queryBySingle(hql, params) != null) {
			String sql = "delete from CustomerLike where customerId=? and eventTypeId=?";
			executeBySql(sql, params);
		}
	}

	@Override
	public boolean exsits(Integer cusId, Integer eventId) {
		String hql = "from CustomerLike where customer.id=? and eventType.id=?";
		return queryBySingle(hql, new Object[] { cusId, eventId }) != null ? true
				: false;
	}

	@Override
	public void updateCustomerLike(Customer customer, String typeId) {
		// 删除用户取消的兴趣
		StringBuffer sql = new StringBuffer(
				"delete from CustomerLike where customerId=? and eventTypeId not in(");
		String[] ids = typeId.split(",");
		CustomerLike customerLike = null;
		for (String id : ids) {
			customerLike = new CustomerLike();
			customerLike.setCustomer(customer);
			customerLike.setEventType(eventTypeService.getEntityById(Integer
					.valueOf(id)));
			addCustomerLike(customerLike);
			sql.append(id).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		executeBySql(sql.toString(), new Object[] { customer.getId() });
	}

	public void setEventTypeService(IEventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}
}
