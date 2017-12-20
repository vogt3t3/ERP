package com.crm.action;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.crm.model.Bas_dict_t;
import com.crm.model.Cst_activity_t;
import com.crm.model.Cst_customer_t;
import com.crm.model.Cst_indent_t;
import com.crm.model.Cst_linkman_t;
import com.crm.model.Cst_lst_t;
import com.crm.model.Sys_user_t;
import com.crm.model.User;
import com.crm.service.ClientService;
import com.crm.service.DictService;
import com.crm.service.RightService;

public class ClientAction  implements RequestAware,SessionAware{

	
	@Resource(name="ClientService")
	private ClientService clientService;
	
	@Resource(name="RightService")
	private RightService rightService;
	
	@Resource(name="DictService")
	private DictService dictService;
	//客户经理名
	private String manager;
	//地区
	private String area;
	//信誉度
	private String credit;
	
	
	//客户id,根据id查询客户详细信息
	private int id;
	//联系人id
	private int linkManId;
	//交往记录id
	private int activityId;
	//订单记录
	private int identId;
	//暂缓流失
	private int lstId;
	private Map request;
	private	Map session;
	//客户
	private Cst_customer_t cst_customer_t;
	//联系人
	private Cst_linkman_t cst_linkman_t;
	//交往记录
	private Cst_activity_t cst_activity_t ;
	//暂缓流失
	private Cst_lst_t cst_lst_t;
	/**
	 * 添加客户信息
	 * @return 返回结果
	 */
	public String addClient(){
		clientService.saveClient(cst_customer_t);
		request.put("cst_customer_t", cst_customer_t);
		List<Sys_user_t> users=rightService.getUser();
		request.put("userList", users);
		List<Bas_dict_t> bas=dictService.getQuery("from Bas_dict_t b where b.dict_type='企业客户等级'").list();
		request.put("bas", bas);
		return "clientDetail";
	}
	
	/**
	 * 客户信息列表
	 * @return 
	 */
	public String listClient(){
		List<Cst_customer_t> list=clientService.listClient();
		request.put("clientList", list);
		return "clientList";
	}
	
	/**
	 * 客户详细信息
	 * @return
	 */
	public String detailClient(){
		Cst_customer_t c =clientService.find(id, Cst_customer_t.class);
		List<Sys_user_t> users=rightService.getUser();
		request.put("cst_customer_t", c);
		request.put("userList", users);
		List<Bas_dict_t> bas=dictService.getQuery("from Bas_dict_t b where b.dict_type='企业客户等级'").list();
		request.put("bas", bas);
		return "clientDetail";
	}
	
	/**
	 * 打开创建客户界面
	 */
	public String openCreateClient(){
		List<Sys_user_t> users=rightService.getUser();
		request.put("userList", users);
		List<Bas_dict_t> bas=dictService.getQuery("from Bas_dict_t b where b.dict_type='企业客户等级'").list();
		request.put("bas", bas);
		return "clientDetail";
	}
	
	/**
	 * 更新客户信息
	 * @return
	 */
	public String updateClient(){
		clientService.saveOrUpdate(cst_customer_t);
		return "clientUpdate";
	}
	
	/**
	 * 获取联系人
	 * @return
	 */
	public String getLinkMan(){
		Cst_customer_t c =clientService.find(id, Cst_customer_t.class);
		Set<Cst_linkman_t>  cst_linkman_ts=c.getCst_linkman_ts();
	 
		request.put("cst_linkman_ts", cst_linkman_ts);
		request.put("cst_customer_t", c);
		return "linkMans";
	} 
	
	
	/**
	 * 打开创建联系人页面
	 * @return
	 */
	public String openCreateLinkMan(){
		Cst_customer_t c =clientService.find(id, Cst_customer_t.class);
		request.put("cst_customer_t", c);
		return "createLinkMan";
	}
	
	/**
	 * 创建联系人
	 * @return
	 */
	public String addLinkMan(){
		//cst_linkman_t.getCst_customer_t().setCst_id(cst_linkman_t.getLink_cst_id());
		clientService.saveLinkman(cst_linkman_t);
		//c.getCst_linkman_ts().add(cst_linkman_t);
		//clientService.saveClient(c);
		Cst_customer_t c=clientService.find(id, Cst_customer_t.class);
		
		request.put("cst_linkman_ts", c.getCst_linkman_ts());
		request.put("cst_customer_t", c);
		return "linkMans";
	}
	
	/**
	 *  编辑联系人
	 * @return
	 */
	public String editLinkMan(){
		Cst_linkman_t c1 =clientService.find(linkManId, Cst_linkman_t.class);
		Cst_customer_t c2 =clientService.find(id, Cst_customer_t.class);
		request.put("cst_linkman_t", c1);
		request.put("cst_customer_t", c2);
		return "createLinkMan";
	}
	
	/**
	 * 删除联系人
	 * @return
	 */
	public String deleteLinkMan(){
		Cst_linkman_t c =clientService.find(linkManId, Cst_linkman_t.class);
		clientService.delLinkman(c);
		getLinkMan();
		return "linkMans";
	}
	
	/**
	 * 交往记录
	 * @return
	 */
	public String getActivity(){
		Cst_customer_t c =clientService.find(id, Cst_customer_t.class);
		request.put("cst_customer_t", c);
		return "activity";
	}
	
	/**
	 * 打开创建交往记录
	 */
	public String openCreateActivity(){
		Cst_customer_t c =clientService.find(id, Cst_customer_t.class);
		request.put("cst_customer_t", c);
		return "createActivity";
	}
	/**
	 * 交往记录详细信息
	 * @return
	 */
	public String getActivityDetail(){
		Cst_activity_t a=clientService.find(activityId, Cst_activity_t.class);
		request.put("cst_customer_t", a.getCst_customer_t());
		request.put("cst_activity_t", a);
		return "createActivity";
	}
	
	/**
	 * 保存交往记录
	 * @return
	 */
	public String addActivity(){
		Cst_customer_t c =clientService.find(id,  Cst_customer_t.class);
		clientService.saveActivity(cst_activity_t);
		request.put("cst_customer_t", c);
		return "activity";
	}
	
	/**
	 * 修改交往记录
	 */
	public String modActivity(){
		Cst_activity_t a=clientService.find(cst_activity_t.getAtv_id(), Cst_activity_t.class);
		a.setAtv_date(cst_activity_t.getAtv_date());
		a.setAtv_desc(cst_activity_t.getAtv_desc());
		a.setAtv_detail(cst_activity_t.getAtv_detail());
		a.setAtv_place(cst_activity_t.getAtv_place());
		a.setAtv_title(cst_activity_t.getAtv_title());
		clientService.saveActivity(a);
		Cst_customer_t c =clientService.find(cst_activity_t.getCst_customer_t().getCst_id(),  Cst_customer_t.class);
		request.put("cst_customer_t", c);
		return "activity";
	}
	
	/**
	 * 获取历史订单
	 * @return
	 */
	public String getIndent(){
		Cst_customer_t c =clientService.find(id, Cst_customer_t.class);
		request.put("cst_customer_t", c);
		return "indent";
	}
	
	/**
	 * 历史订单详细信息
	 * @return
	 */
	public String getIndentDetail(){
		Cst_indent_t c =clientService.find(identId, Cst_indent_t.class);
		request.put("cst_indent_t", c);
		return "indentDetail";
	}
	
	/**
	 * 删除历史订单
	 * @return
	 */
	public String deleteIndentDetail(){
		Cst_indent_t indent =clientService.find(identId, Cst_indent_t.class);
		Cst_customer_t c=clientService.find(indent.getCst_customer_t().getCst_id(), Cst_customer_t.class);
		clientService.delete(indent);
		request.put("cst_customer_t", c);
		return "indent";
	}
	
	/**
	 * 流失预警客户名单（自动计算）
	 * @return
	 */
	public void addLst(){
		List<Cst_indent_t> list=clientService.LstList();
		for(Cst_indent_t c:list)
		{
			Cst_lst_t lst=new Cst_lst_t();
			lst.setLst_cst_manager_id(c.getCst_customer_t().getCst_manager_id());
			lst.setLst_cst_manager_name("");
			lst.setLst_cst_name(c.getCst_customer_t().getCst_name());
			lst.setLst_last_order_date(c.getIndent_date());
			lst.setLst_status("流失预警");
			clientService.saveOrUpdate(lst);
		}
	}
	
	/**
	 * 
	 * 暂缓流失列表
	 * @return
	 */
	public String listLst(){
		List<Cst_lst_t> list=clientService.lstList();
		request.put("lstList", list);
		return "lstList";
	}
	
	/**
	 * 打开暂缓流失
	 * @return
	 */
	public String openLst(){
		Cst_lst_t c=clientService.find(lstId, Cst_lst_t.class);
		request.put("lst", c);
		return "lstDetail";
	}
	
	
	
	/**
	 * 修改暂缓流失
	 * @return
	 */
	public String modLst(){
		clientService.saveOrUpdate(cst_lst_t);
		listLst();
		return "lstList";
	}
	
	/**
	 * 打开确认流失
	 */
	public String opensureLst(){
		Cst_lst_t c=clientService.find(lstId, Cst_lst_t.class);
		request.put("lst", c);
		return "sureLst";
	}
	
	/**
	 * 确认流失
	 * @return
	 */
	public String sureLst(){
		cst_lst_t.setLst_status("确认流失");
		cst_lst_t.setSureLstDate(new Date());
		clientService.saveOrUpdate(cst_lst_t);
		listLst();
		return "lstList";
	}
	
	
	/**
	 * 客户信息查找
	 * @return
	 */
	public String searchClient(){
		List<Cst_customer_t> list=clientService.searchClient(manager, area, credit);
		request.put("clientList", list);
		return "clientList";
	}
	public Cst_customer_t getCst_customer_t() {
		return cst_customer_t;
	}

	public void setCst_customer_t(Cst_customer_t cst_customer_t) {
		this.cst_customer_t = cst_customer_t;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		session=arg0;
	}

	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		request=arg0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cst_linkman_t getCst_linkman_t() {
		return cst_linkman_t;
	}

	public void setCst_linkman_t(Cst_linkman_t cst_linkman_t) {
		this.cst_linkman_t = cst_linkman_t;
	}

	public int getLinkManId() {
		return linkManId;
	}

	public void setLinkManId(int linkManId) {
		this.linkManId = linkManId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public Cst_activity_t getCst_activity_t() {
		return cst_activity_t;
	}

	public void setCst_activity_t(Cst_activity_t cst_activity_t) {
		this.cst_activity_t = cst_activity_t;
	}

	public int getIdentId() {
		return identId;
	}

	public void setIdentId(int identId) {
		this.identId = identId;
	}

	public int getLstId() {
		return lstId;
	}

	public void setLstId(int lstId) {
		this.lstId = lstId;
	}

	public Cst_lst_t getCst_lst_t() {
		return cst_lst_t;
	}

	public void setCst_lst_t(Cst_lst_t cst_lst_t) {
		this.cst_lst_t = cst_lst_t;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	
}
