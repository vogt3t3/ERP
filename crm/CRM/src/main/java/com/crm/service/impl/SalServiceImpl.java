package com.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.crm.model.Sal_chance_t;
import com.crm.model.Sal_plan_t;
import com.crm.service.SalService;
/**
 * 营销实现类
 * @author yuduan
 *
 *
 */
@Service("SalService")
public class SalServiceImpl extends BaseServiceImpl implements SalService {

	/**
	 * 创建指定开发计划-保存、更新
	 * @param p 计划
	 */
	@Override
	public void saveOrUpdatePlan(Sal_plan_t p) {
		// TODO Auto-generated method stub
		saveOrUpdate(p);
	}
	
	/**
	 * 删除开发计划
	 * 
	 */
	@Override
	public void delPlan(Sal_plan_t p) {
		// TODO Auto-generated method stub
		delete(p);
	}
	/**
	 * 查询开发计划
	 * @param p 计划序号
	 */

	public List<Sal_plan_t> showPlan(String p) {
		// TODO Auto-generated method stub
		Query query = getQuery("from Bas_plan_t d where d.plan_id="+p);
		return query.list();
	}

	/**
	 * 执行开发计划
	 * 按计划执行并记录执行结果
	 */
	//计划执行阶段
	@Override
	public void executePlan(Sal_plan_t p) {
		// TODO Auto-generated method stub
		saveOrUpdate(p);
	}

	
	/**
	 * 记录开发结果
	 * 记录开发计划最终的执行结果
	 * @Return ： success成功  ， false失败
	 */
	@Override
	public void recordResult(Sal_plan_t p) {
		// TODO Auto-generated method stub
		saveOrUpdate(p);
	}
	
	
	/**
	 * 创建、更新销售机会
	 * 发现销售机会并将销售机会录入系统
	 */
	@Override
	public void saveOrUpdateChance(Sal_chance_t c) {
		// TODO Auto-generated method stub
		saveOrUpdate(c);
	}
	
	/**
	 * 销售机会列表
	 */
	public List<Sal_chance_t> listchance(){
		List<Sal_chance_t> list=getQuery("from Sal_chance_t s ").list();
		return list;
	}
	
	/**
	 * 删除销售机会
	 * 对不可为的销售机会进行删除
	 */
	@Override
	public void delChance(Sal_chance_t c) {
		// TODO Auto-generated method stub
		delete(c);
	}


	/**
	 * 指派销售机会
	 * 将销售机会指派给指定的客户经理
	 * 主要修改chc_due_to责任人，chc_due_date分配日期，chc_status分配状态（已分配，未分配）
	 */
	
	@Override
	public void chanceCharger(Sal_chance_t c) {
		// TODO Auto-generated method stub
		saveOrUpdate(c);
	}
	/**
	 * 销售机会的查询
	 * @param d 负责人
	 */

	public List<Sal_chance_t> showChance(String d) {
		// TODO Auto-generated method stub
		Query query = getQuery("from Bas_chance_t d where d.svc_due_to="+d);
		return query.list();
	}


	public List<Sal_plan_t> listplan(int cid){
		return getQuery("from Sal_plan_t s where s.plan_chc_id="+cid).list();
	}
	
	/**
	 * 营销机会信息查询
	 * @param manager
	 * @param area
	 * @param credit
	 * @return
	 */
	public List<Sal_chance_t> searchChance(String title,String name,String linkman) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer();
		boolean isFirst=true;
		hql.append("from Sal_chance_t d ");
		if(!StringUtils.isEmpty(title))
			if(isFirst)
				{hql.append("where d.chc_title like '%"+title+"%'");isFirst=false;}
			else hql.append("and d.chc_title like '%"+title+"%'");
		if(!StringUtils.isEmpty(name))
			if(isFirst)
			{hql.append("where d.chc_cust_name like '%"+name+"%'");isFirst=false;}
		else hql.append("and d.chc_cust_name like '%"+name+"%'");
		if(!StringUtils.isEmpty(linkman))
			if(isFirst)
			hql.append("where d.chc_linkman like '%"+linkman+"%'");
		else hql.append("and d.chc_linkman like '%"+linkman+"%'");
		System.out.println(hql);
		return getQuery(hql.toString()).list();
	}
	
}
