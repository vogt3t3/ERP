package com.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.crm.model.Bas_dict_t;
import com.crm.model.Bas_product_t;
import com.crm.service.DictService;
/**
 * 数据字典实现类
 * @author yuduan
 *
 */
@Service("DictService")
public class DictServiceImpl extends BaseServiceImpl implements DictService {

	/**
	 * 基础数据字典-保存更新
	 * @param b 基础数据字典
	 */
	@Override
	public void saveDict(Bas_dict_t b) {
		// TODO Auto-generated method stub
		saveOrUpdate(b);
	}
	/**
	 * 基础数据字典-删除
	 * @param b 基础数据字典
	 */
	@Override
	public void delDict(Bas_dict_t b) {
		// TODO Auto-generated method stub
		delete(b);
	}
	/**
	 * 基础数据字典-查询
	 * @param b 基础数据字典
	 */
	@Override
	public List<Bas_dict_t> showDict(String type,String name,String value) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer();
		boolean isFirst=true;
		hql.append("from Bas_dict_t d ");
		if(!StringUtils.isEmpty(type))
			if(isFirst)
				{hql.append("where d.dict_type='"+type+"'");isFirst=false;}
			else hql.append("and d.dict_type='"+type+"'");
		if(!StringUtils.isEmpty(name))
			if(isFirst)
			{hql.append("where d.dict_item='"+name+"'");isFirst=false;}
		else hql.append("and d.dict_item='"+name+"'");
		if(!StringUtils.isEmpty(value))
			if(isFirst)
			hql.append("where d.dict_value='"+value+"'");
		else hql.append("and d.dict_value='"+value+"'");
		return getQuery(hql.toString()).list();
	}
	/**
	 * 基础数据字典-查询 id
	 */
	@Override
	public List<Bas_dict_t> showDict(int id) {
		// TODO Auto-generated method stub
		Query query = getQuery("from Bac_dict_t c where c.dict_id="+id);
	//	System.out.println("from Bac_dict_t c where c.dict_id="+id);
		return query.list();
	}
	/**
	 * 产品数据查询-显示产品信息
	 */
	@Override
	public List<Bas_product_t> showProduct(String pname,String ptype,String prank) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer();
		boolean isFirst=true;
		hql.append("from Bas_product_t d ");
		if(!StringUtils.isEmpty(pname))
			if(isFirst)
				{hql.append("where d.pro_name='"+pname+"'");isFirst=false;}
			else hql.append("and d.pro_name='"+pname+"'");
		if(!StringUtils.isEmpty(ptype))
			if(isFirst)
			{hql.append("where d.pro_type='"+ptype+"'");isFirst=false;}
		else hql.append("and d.pro_type='"+ptype+"'");
		if(!StringUtils.isEmpty(prank))
			if(isFirst)
			hql.append("where d.pro_rank='"+prank+"'");
		else hql.append("and d.pro_rank='"+prank+"'");
		System.out.println(hql.toString());
		return getQuery(hql.toString()).list();
	}
	/**
	 * 库存查询-得到库存信息
	 */

	@Override
	public List<Bas_product_t> showStock(String pro, String depot) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer();
		boolean isFirst=true;
		hql.append("from Bas_product_t d ");
		if(!StringUtils.isEmpty(pro))
			if(isFirst)
				{hql.append("where d.pro_name='"+pro+"'");isFirst=false;}
			else hql.append("and d.pro_pro='"+pro+"'");
		if(!StringUtils.isEmpty(depot))
			if(isFirst)
			hql.append("where d.pro_depot='"+depot+"'");
		else hql.append("and d.pro_depot='"+depot+"'");
		//System.out.println(hql.toString()+pro+depot);
		return getQuery(hql.toString()).list();
	}
	/**
	 * 产品、库存-保存更新
	 */
	@Override
	public void saveProduct(Bas_product_t b) {
		// TODO Auto-generated method stub
		saveOrUpdate(b);
	}

	

	


}


