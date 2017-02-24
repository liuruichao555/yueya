package com.yueya.event.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yueya.base.impl.BaseServiceImpl;
import com.yueya.common.PageBean;
import com.yueya.customer.model.Customer;
import com.yueya.event.model.Event;
import com.yueya.event.service.IEventFavorService;
import com.yueya.event.service.IEventService;

@Service
public class EventServiceImpl extends BaseServiceImpl<Event> implements
		IEventService {
	@Resource
	private IEventFavorService eventFavorService;

	@Override
	public PageBean<Event> list(String typeId, Integer cityId, Integer curPage) {
		PageBean<Event> pageBean = new PageBean<>();
		pageBean.setPageIndex(curPage);
		StringBuffer hql = new StringBuffer(
				"from Event where cityId=? and status>0 and typeId in(");
		String[] ids = typeId.split(",");
		for (String id : ids) {
			hql.append(id).append(",");
		}
		hql.deleteCharAt(hql.length() - 1);
		hql.append(") order by status asc, id desc");
		return queryByPage(hql.toString(), new Object[] { cityId }, pageBean);
	}

	@Override
	public void favor(Integer id, Integer flag, Customer customer) {
		String sql = null;
		if (flag == 1) {
			// 如果没有赞，则赞
			if (!eventFavorService.isFavor(customer.getId(), id)) {
				// 赞
				sql = "update Event set favor=favor+1 where id = ?";
				executeBySql(sql, new Object[] { id });
				sql = "insert into EventFavor values(null,?,?)";
				executeBySql(sql, new Object[] { id, customer.getId() });
			}
		} else {
			// 如果已经赞，则取消赞
			if (eventFavorService.isFavor(customer.getId(), id)) {
				// 取消赞
				sql = "update Event set favor=favor-1 where id = ?";
				executeBySql(sql, new Object[] { id });
				sql = "delete from EventFavor where eventId=? and customerId=?";
				executeBySql(sql, new Object[] { id, customer.getId() });
			}
		}
	}

	public void setEventFavorService(IEventFavorService eventFavorService) {
		this.eventFavorService = eventFavorService;
	}

	@Override
	public PageBean<Event> listByCreateCustomer(Integer customerId,
			Integer curPage) {
		String hql = "from Event where status>0 and customer.id=? order by -id";
		PageBean<Event> pageBean = new PageBean<Event>();
		pageBean.setPageIndex(curPage);
		return queryByPage(hql, new Object[] { customerId }, pageBean);
	}

	@Override
	public void addShare(Integer eventId) {
		String sql = "update Event set shareCount=shareCount+1 where id=?";
		executeBySql(sql, new Object[] { eventId });
	}
}
