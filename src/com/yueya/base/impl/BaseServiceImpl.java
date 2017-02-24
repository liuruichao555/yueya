package com.yueya.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;


import com.yueya.base.IBaseService;
import com.yueya.base.IDaoSupport;
import com.yueya.common.PageBean;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseServiceImpl<E> implements IBaseService<E> {
	@Resource
	protected IDaoSupport<E> daoSupport;
	protected Class<E> e;

	public BaseServiceImpl() {
		this.e = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.e = (Class<E>) p[0];
		}
	}

	@Override
	public E getEntityById(Serializable id) {
		return daoSupport.getById(e, id);
	}

	@Override
	public void updateEntity(E entity) {
		daoSupport.update(entity);
	}

	@Override
	public void saveEntity(E entity) {
		daoSupport.save(entity);
	}

	@Override
	public void delEntityById(Serializable id) {
		String hql = "delete from " + e.getSimpleName() + " where id=?";
		daoSupport.executeByHql(hql, new Object[] { id });
	}

	@Override
	public PageBean<E> queryByPage(String hql, Object[] params,
			PageBean<E> pageBean) {
		pageBean.setTotalCount(queryByCount(hql, params));
		pageBean.setData(daoSupport.list(hql, params,
				(pageBean.getPageIndex() - 1) * pageBean.getPageSize(),
				pageBean.getPageSize()));
		return pageBean;
	}

	@Override
	public Integer queryByCount(String hql, Object[] params) {
		int index = hql.indexOf("from");
		String thql = "select count(*) " + hql.substring(index);
		Long count = (Long) daoSupport.singleResult(thql, params);
		return count.intValue();
	}
	
	@Override
	public void delEntityById(String ids) {
		String[] strs = ids.split(",");
		StringBuffer sbu = new StringBuffer();
		sbu.append("delete from").append(" ").append(e.getSimpleName()).append(" where id in(");
		for(String str : strs) {
			sbu.append(str).append(",");
		}
		sbu.deleteCharAt(sbu.length() - 1);
		sbu.append(")");
		daoSupport.executeByHql(sbu.toString(), null);
	}

	public void setDaoSupport(IDaoSupport<E> daoSupport) {
		this.daoSupport = daoSupport;
	}

	@Override
	public E queryBySingle(String hql, Object[] params) {
		return (E) daoSupport.singleResult(hql, params);
	}

	@Override
	public void executeByHql(String hql, Object[] params) {
		daoSupport.executeByHql(hql, params);
	}

	@Override
	public void executeBySql(String sql, Object[] params) {
		daoSupport.executeBySql(sql, params);
	}

	@Override
	public List<E> queryByHql(String hql, Object[] params) {
		return daoSupport.list(hql, params);
	}
}
