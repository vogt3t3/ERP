package com.crm.service;

import org.hibernate.Query;


public interface BaseService {
	<T> void saveOrUpdate(Object object);
	<T> void delete(Object object);
	<T> Query getQuery(String hql);
	<T> T find(Object id,Class<T> clazz);
}
