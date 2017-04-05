package com.ai.sample.db.dao.common;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {

	@Autowired
	private SessionFactory sessionFactory;

	protected  Session getSession() throws PSQLException {
		return sessionFactory.getCurrentSession();
	}

	public void persist(Object entity) throws PSQLException{
		getSession().persist(entity);
	}

	public void delete(Object entity) throws PSQLException{
		getSession().delete(entity);
	}
	
	public void saveOrUpdate(Object entity)throws PSQLException
	{
		getSession().saveOrUpdate(entity);
	}
	
	public void evictAll() throws PSQLException{
		    Cache cache = sessionFactory.getCache();
		    cache.evictQueryRegions();
		    cache.evictDefaultQueryRegion();
		    cache.evictCollectionRegions();
		    cache.evictEntityRegions();
		}
	
	public void merge(Object entity)throws PSQLException
	{
		getSession().merge(entity);
	}

}
