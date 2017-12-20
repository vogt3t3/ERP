package com.crm.service.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.crm.model.Cst_activity_t;
import com.crm.model.Cst_customer_t;
import com.crm.model.Cst_indent_t;
import com.crm.model.Cst_linkman_t;
import com.crm.model.Cst_lst_t;
import com.crm.service.ClientService;

/**
 * 
类名称：ClientService   
* 类描述：   客户信息、客户服务的业务层
* 创建人：胡杰雄   
* 创建时间：2014-9-4 下午5:06:22
 */
@Service("ClientService")
/**
 * 客户信息实现类
 * @author yuduan
 *
 */
public class ClientServiceImpl extends BaseServiceImpl implements ClientService {


	@Override
	/**
	 * 客户信息-保存更新
	 * @param c 客户信息
	 */
	public void saveClient(Cst_customer_t c) {
		// TODO Auto-generated method stub
		saveOrUpdate(c);
	}
	
	/**
	 * 客户信息-删除
	 * @param c 客户信息
	 */
	public void delClient(Cst_customer_t c) {
		// TODO Auto-generated method stub
		delete(c);
	}
	
	
	/**
	 * 客户联系人-保存更新
	 * @param l 联系人
	 */
	@Override
	public void saveLinkman(Cst_linkman_t l) {
		// TODO Auto-generated method stub
		saveOrUpdate(l);
	}
	
	/**
	 * 客户联系人-删除
	 * @param l 联系人
	 */
	@Override
	public void delLinkman(Cst_linkman_t l) {
		// TODO Auto-generated method stub
		delete(l);
	}

	/**
	 * 客户信息列表
	 */
	public List<Cst_customer_t> listClient(){
		Query query=getQuery("from Cst_customer_t c");
		return query.list();
	}
	
	
	/**
	 * 查询联系人
	 */
	public List<Cst_linkman_t> getLinkman(int clientId){
		Query query=getQuery("from Cst_linkman_t c where c.link_cst_id="+clientId);
		return query.list();
	}
	
	/**
	 * 交往记录-保存更新
	 * @param a 交往记录
	 */
	@Override
	public void saveActivity(Cst_activity_t a) {
		// TODO Auto-generated method stub
		saveOrUpdate(a);
	}
	
	/**
	 * 交往记录-删除
	 * @param a 交往记录
	 */
	@Override
	public void delActivity(Cst_activity_t a) {
		// TODO Auto-generated method stub
		delete(a);
	}
	
	
	/**
	 * 历史订单-显示客户的历史订单信息
	 * 显示订单明细
	 */

	public List<Cst_activity_t> showActivityHistory(int id) {
		// TODO Auto-generated method stub
		Query query = getQuery("from Cst_activity_t c where c.atv_cst_id="+id);
		return query.list();
	}
	
	/**
	 * 客户流失预警
	 * 在固定的时间点对超过6个月没有下单的客户 添加 流失预警记录
	 * 
	 */
	//应该修改一下。 加个循环把查到的数据添加到Cst_lst_t这个表里。。
	@Override
	public List<Cst_indent_t> LstList() {
		// TODO Auto-generated method stub
		SimpleDateFormat df=new SimpleDateFormat("yyy-MM-dd");
		Query query=getQuery("from Cst_indent_t b where b.indent_id=( select  max(a.indent_id) from Cst_indent_t a where a.cst_customer_t.cst_id=b.cst_customer_t.cst_id)");
		Date d=new Date();
		String today=df.format(d); 
		List<Cst_indent_t> list= query.list();
		for(int i=0;i<list.size();i++)
		{
			double temp=(d.getTime()-list.get(i).getIndent_date().getTime())/(24*60*60*1000) ;
			if(temp<180){
				list.remove(i);
				i--;
			}
		}
		return list;
	}
	
	/**
	 * 暂缓流失列表
	 */
	public List<Cst_lst_t> lstList(){
		return getQuery("from Cst_lst_t c").list();
	}
	
	/**
	 * 客户流失预警记录-显示记录
	 * 暂缓客户流失-对预警客户采取暂缓流失并填写预计采取的措施
	 * 客户流失-确认流失时的删除
	 * @param l 流失表，主要修改lst_delay暂缓措施，lst_reason流失原因，lst_status状态
	 */
	//修改lst_delay暂缓措施，lst_status状态
	@Override
	public void burreyLst(Cst_lst_t l) {
		// TODO Auto-generated method stub
		saveOrUpdate(l);
	}
	
	
	//修改st_reason流失原因，lst_status状态
	@Override
	public void delLst(Cst_lst_t l) {
		// TODO Auto-generated method stub
		saveOrUpdate(l);//
	}


	/**
	 * 查询客户信息
	 * @param manager 客户经理名
	 * @param area	     地区
	 * @param credit  客户信誉度
	 * @return
	 */
	public List<Cst_customer_t> searchClient(String manager,String area,String credit) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer();
		boolean isFirst=true;
		hql.append("from Cst_customer_t d ");
		if(!StringUtils.isEmpty(manager))
			if(isFirst)
				{hql.append("where d.cust_manager_name like '%"+manager+"%'");isFirst=false;}
			else hql.append("and d.cust_manager_name like '%"+manager+"%'");
		if(!StringUtils.isEmpty(area))
			if(isFirst)
			{hql.append("where d.cst_region = '"+area+"'");isFirst=false;}
		else hql.append("and d.cst_region = '"+area+"'");
		if(!StringUtils.isEmpty(credit))
			if(isFirst)
			hql.append("where d.cst_credit = '"+credit+"'");
		else hql.append("and d.cst_credit = '"+credit+"'");
		System.out.println(hql);
		return getQuery(hql.toString()).list();
	}
	
	
	
}
