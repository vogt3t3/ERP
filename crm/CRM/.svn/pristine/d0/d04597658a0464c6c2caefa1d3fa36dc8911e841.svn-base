package com.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.crm.model.Bas_dict_t;
import com.crm.model.Cst_service_t;
import com.crm.service.ServiceService;
@Service("ServiceService")
public class ServiceServiceImpl extends BaseServiceImpl implements ServiceService {



	/**
	 * 服务创建
	 * 完成为客户创建服务的操作
	 */
	@Override
	public void saveService(Cst_service_t s) {
		// TODO Auto-generated method stub
		saveOrUpdate(s);
	}

	/**
	 * 服务分配
	 * 将已有的服务分配给专门的客户经理进行服务
	 * svc_due_id分配编号，svc_due_to责任人，svc_due_date分配日期

	 */
	@Override
	public void distService(Cst_service_t s) {
		// TODO Auto-generated method stub
		saveOrUpdate(s);
	
	}
	
	/**
	 * 服务分配列表
	 */
	public List<Cst_service_t> listService(){
		return getQuery("from Cst_service_t c").list();
	}
	
	/**
	 * 服务处理
	 * 添加服务的处理结果svc_result
	 */
	@Override
	public void disposeService(Cst_service_t s) {
		// TODO Auto-generated method stub
		saveOrUpdate(s);
	}
	/**
	 * 服务反馈
	 * 录入客户对服务的结果满意度svc_satisfy
	 */
	@Override
	public void feedbackService(Cst_service_t s) {
		// TODO Auto-generated method stub
		saveOrUpdate(s);
	}

	/**
	 * 服务归档
	 * 如果客户反馈的满意度高于3则服务完成并进行归档否则重新处理
	 * svc_status服务状态
	 */
	@Override
	public void fileService(Cst_service_t s) {
		// TODO Auto-generated method stub
		saveOrUpdate(s);
	}
	
	@Override
	public List<Cst_service_t> showService(String cstname,String title,String  type,String status) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer();
		boolean isFirst=true;
		hql.append("from Cst_service_t s ");
		if(!StringUtils.isEmpty(cstname))
			if(isFirst)
				{hql.append("where s.svc_cst_name='"+cstname+"'");isFirst=false;}
			else hql.append(" and s.svc_cst_name='"+cstname+"'");
		if(!StringUtils.isEmpty(title))
			if(isFirst)
			{hql.append("where s.svc_title='"+title+"'");isFirst=false;}
			else hql.append(" and s.svc_title='"+title+"'");
		if(!StringUtils.isEmpty(type))
			if(isFirst)
			{hql.append("where s.svc_type='"+type+"'");isFirst=false;}
			else hql.append(" and s.svc_type='"+type+"'");
		if(!StringUtils.isEmpty(status))
			if(isFirst)
			{hql.append("where s.svc_status='"+status+"'");}
			else hql.append(" and s.svc_status='"+status+"'");
		
		System.out.println(hql.toString());
		return getQuery(hql.toString()).list();
	}
}
