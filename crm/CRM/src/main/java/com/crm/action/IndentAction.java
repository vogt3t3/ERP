package com.crm.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.crm.model.Cst_customer_t;
import com.crm.model.Cst_indent_t;
import com.crm.service.ClientService;
import com.crm.service.IndentService;

public class IndentAction implements RequestAware,SessionAware{
	
	@Resource(name="IndentService")
	private IndentService indentService;
	
	@Resource(name="ClientService")
	private ClientService clientService;
	private int id;

	private String iname;
	private String cname;

	private Cst_indent_t cst_indent_t;
	private Cst_customer_t cst_customer_t;
	public Cst_customer_t getCst_customer_t() {
		return cst_customer_t;
	}

	public void setCst_customer_t(Cst_customer_t cst_customer_t) {
		this.cst_customer_t = cst_customer_t;
	}
	private Map request;
	private	Map session;
	/**
	 * 保存订单
	 * @return
	 */
	public String addIndent(){
		indentService.saveOrUpdateIndent(cst_indent_t); 
		request.put("cst_indent_t", cst_indent_t);
		listIndent();
		return "indentList";
	}
	
	/**
	 * 订单列表
	 * @return
	 */
	public String listIndent(){

		List<Cst_indent_t> list=indentService.listIndent();
		request.put("indentList", list);
		return "indentList";
	}     
	/**
	 * 编辑订单信息
	 * @return
	 */
	public String detailIndent(){
		Cst_indent_t c =indentService.find(id, Cst_indent_t.class);
		List<Cst_customer_t> clients=clientService.listClient();
		request.put("cst_indent_t", c);
		request.put("client", clients);
		return "indentDetail";
	}
	
	/**
	 * 打开添加订单
	 */
	public String openaddIndent(){
			
			List<Cst_customer_t> clients=clientService.listClient();
		
			request.put("client", clients);
			return "indentDetail";
		}
	
	
	/**
	 * 查询订单信息表
	 */
	public String searchIndent(){
		List<Cst_indent_t> list=indentService.showIndent(iname, cname);
		request.put("indentList", list);
		//System.out.println(iname+cname);
		return "indentList";
	}
	public Map getRequest() {
		return request;
	}



	public Map getSession() {
		return session;
	}

	

	/**
	 * 打开订单界面
	 */

	public String openCreateIndent(){
		listIndent();
		return "indentList";
	}

	
	
	/**
	 * 更新订单
	 * @return
	 */
	public String updateIndent(){
		indentService.saveOrUpdate(cst_indent_t);
		return "indentUpdate";
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
	
	public IndentService getIndentService() {
		return indentService;
	}
	public void setIndentService(IndentService indentService) {
		this.indentService = indentService;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIname() {
		return iname;
	}
	public void setIname(String iname) {
		this.iname = iname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Cst_indent_t getCst_indent_t() {
		return cst_indent_t;
	}
	public void setCst_indent_t(Cst_indent_t cst_indent_t) {
		this.cst_indent_t = cst_indent_t;
	}
	
	

}
