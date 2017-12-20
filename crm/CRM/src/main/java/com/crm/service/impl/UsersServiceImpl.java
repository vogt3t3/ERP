package com.crm.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.crm.model.Sys_user_t;
import com.crm.service.UsersService;
/**
 * 系统用户信息实现类
 * @author yuduan
 *
 */
@Service("UsersService")
public class UsersServiceImpl extends BaseServiceImpl implements UsersService {

	/**
	 * 系统用户-保存更新
	 * @param u 用户
	 */
	@Override
	public void saveUser(Sys_user_t u) {
		// TODO Auto-generated method stub

		saveOrUpdate(u);
	}	
	/**
	 * 系统用户-删除
	 * @param u 用户
	 */
	@Override
	public void delUser(Sys_user_t u) {
		// TODO Auto-generated method stub
		delete(u);
	}
	/**
	 * 系统用户-查询
	 */
	@Override
	public List<Sys_user_t> showUser(int id) {
		// TODO Auto-generated method stub
		Query query = getQuery("from Sys_user_t u where u.user_id="+id);
		return query.list();
	}
	
	public Sys_user_t findUserByUserName(String username){
		List<Sys_user_t> list=getQuery("from Sys_user_t s where s.user_name='"+username+"'").list();
		if(list!=null&&list.size()>0)
			return list.get(0);
		else return null;
	}
}
