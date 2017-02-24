package com.yueya.base;

import java.io.Serializable;
import java.util.List;

public interface IDaoSupport<E> {
	void save(E entity);
	void delete(E entity);
	void update(E entity);
	E getById(Class<E> e, Serializable id);
	void executeBySql(String sql, Object[] params);
	void executeByHql(String hql, Object[] params);
	List<E> list(String hql, Object[] params);
	Object singleResult(String hql, Object[] params);
	List<E> list(String hql, Object[] params, int startindex, int maxresult);
}
