package com.yueya.base.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.yueya.base.IDaoSupport;

@SuppressWarnings("unchecked")
@Component
public class DaoSupportImpl<E> implements IDaoSupport<E> {

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public void save(E entity) {
		getCurSession().save(entity);
	}
	@Override
	public void delete(E entity) {
		getCurSession().delete(entity);
	}
	@Override
	public void update(E entity) {
		getCurSession().update(entity);
	}
	@Override
	public E getById(Class<E> e, Serializable id) {
		return (E) getCurSession().get(e, id);
	}
	@Override
	public void executeBySql(String sql, Object[] params) {
		Session session = getCurSession();
		Query query = session.createSQLQuery(sql);
		setParameters(query, params);
		query.executeUpdate();
	}
	@Override
	public void executeByHql(String hql, Object[] params) {
		Session session = getCurSession();
		Query query = session.createQuery(hql);
		setParameters(query, params);
		query.executeUpdate();
	}
	@Override
	public List<E> list(String hql, Object[] params) {
		Query query = getCurSession().createQuery(hql);
		setParameters(query, params);
		return query.list();
	}
	@Override
	public Object singleResult(String hql, Object[] params) {
		Query query = getCurSession().createQuery(hql);
		setParameters(query, params);
		return query.uniqueResult();
	}
	@Override
	public List<E> list(String hql, Object[] params, int startindex,
			int maxresult) {
		Query query = getCurSession().createQuery(hql);
		query.setFirstResult(startindex);
		query.setMaxResults(maxresult);
		setParameters(query, params);
		return query.list();
	}

	/**
	 * 获取当前session
	 * 
	 * @return Session
	 */
	protected Session getCurSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 注入参数
	 * 
	 * @param query
	 * @param params
	 */
	private void setParameters(Query query, Object[] params) {
		if (query != null && params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
