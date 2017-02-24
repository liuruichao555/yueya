package com.yueya.base;

import java.io.Serializable;
import java.util.List;

import com.yueya.common.PageBean;

public interface IBaseService<E> {
	E getEntityById(Serializable id);
	void updateEntity(E entity);
	void saveEntity(E entity);
	void delEntityById(Serializable id);
	void delEntityById(String ids);
	void executeByHql(String hql, Object[] params);
	void executeBySql(String sql, Object[] params);
	PageBean<E> queryByPage(String hql, Object[] params, PageBean<E> pageBean);
	Integer queryByCount(String hql, Object[] params);
	E queryBySingle(String hql, Object[] params);
	List<E> queryByHql(String hql, Object[] params);
}
