package com.crm.service.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.crm.dao.BaseDao;
import com.crm.service.BaseService;
@Service("BaseService")
public class BaseServiceImpl implements BaseService{

		@Resource(name="baseDao")
		private BaseDao baseDao;
		
		/**
		 * 保存或更新
		 */
		@Override
		public <T> void saveOrUpdate(Object object) {
			// TODO Auto-generated method stub
			baseDao.saveOrUpdate(object);
		}
		
		/**
		 * 删除操作
		 */
		@Override
		public  void delete(Object object) {
			// TODO Auto-generated method stub
			baseDao.delete(object);
		}

		/**
		 * 获取Query对象
		 */
		public <T> Query getQuery(String hql) {
			return baseDao.getQuery(hql);
			
		}
		
		/**
		 * 根据id查找特定对象
		 */
		public <T> T find(Object id,Class<T> clazz){
			return baseDao.find(clazz, id);
		}
		
}
