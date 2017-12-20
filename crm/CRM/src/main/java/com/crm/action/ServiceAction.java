package com.crm.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.crm.model.Cst_service_t;
import com.crm.model.User;
import com.crm.service.ServiceService;

public class ServiceAction implements RequestAware,SessionAware{
	@Resource(name="ServiceService")
	private ServiceService serviceService;
	
	
	private int id;
	
	private String cstname;
	private String title;
	//服务状态
	private String status;
	//服务类型
	private String type;
	//负责人
	private String due_to;
	private Map<String, Object> request;
	private	Map<String, Object> session;
	private Cst_service_t cst_service_t;
	
	/**
	 * 保存服务信息
	 * @return 返回结果
	 */
	public String addService(){
		System.out.println(cst_service_t.getSvc_status());
		serviceService.saveService(cst_service_t); 
		request.put("cst_service_t", cst_service_t);
		
		return "serviceDetail";
	}
	/**
	 * 保存服务处理信息
	 * @return 返回结果
	 */
	public String addresolveService(){
		serviceService.saveService(cst_service_t);
		request.put("cst_service_t", cst_service_t);
		
		return "serviceDetailresolve";
	}
	/**
	 * 保存服务反馈信息
	 * @return 返回结果
	 */
	public String addbackService(){
		serviceService.saveService(cst_service_t);
		request.put("cst_service_t", cst_service_t);
		
		return "serviceDetailback";
	}
	
	/**
	 * 打开服务创建界面
	 */
	public String openCreateService(){
		return "serviceDetail";
	}
	/**
	 * 打开服务分配界面
	 */
	public String openAssignService(){
		List<Cst_service_t> list=serviceService.listService();
		List<User>  users=serviceService.getQuery("from User u").list();
		request.put("users", users);
		request.put("serviceList", list);
		return "serviceAssign";
	}
	/**
	 * 打开服务处理界面
	 */
	public String openResolveService(){
		List<Cst_service_t> list=serviceService.listService();
		request.put("serviceList", list);
		return "serviceResolve";
	}
	/**
	 * 打开服务反馈界面
	 */
	public String openBackeService(){
		List<Cst_service_t> list=serviceService.listService();
		request.put("serviceList", list);
		return "serviceBack";
	}
	/**
	 * 打开服务归档界面
	 */
	public String openStoreService(){
		List<Cst_service_t> list=serviceService.listService();
		request.put("serviceList", list);
		return "serviceStore";
	}
	
	/**
	 * 服务分配
	 * @return
	 */
	public String assignService(){
		Cst_service_t c =serviceService.find(id, Cst_service_t.class);
		c.setSvc_due_date(new Date());
		c.setSvc_due_to(due_to);
		c.setSvc_status("已分配");
		serviceService.saveService(c);
		openAssignService();
		return "serviceAssign";
	}
	
	
	/**
	 *删除服务分配 
	 */
	public String deleteService(){
			Cst_service_t c =serviceService.find(id, Cst_service_t.class);
			serviceService.delete(c);
			openAssignService();
			return "serviceAssign";
	}
	
	
	/**
	 * 编辑服务处理信息
	 * @return
	 */
	public String detailresolveService(){
		Cst_service_t c =serviceService.find(id, Cst_service_t.class);
		request.put("cst_service_t", c);
		
		return "serviceResolve1";
	}
	/**
	 * 编辑服务反馈信息
	 * @return
	 */
	public String detailbackService(){
		Cst_service_t c =serviceService.find(id, Cst_service_t.class);
		request.put("cst_service_t", c);
		
		return "serviceBack2";
	}
	/**
	 * 编辑服务归档信息
	 * @return
	 */
	public String detailstoreService(){
		Cst_service_t c =serviceService.find(id, Cst_service_t.class);
		request.put("cst_service_t", c);
		
		return "serviceStore2";
	}
	
	
	/**
	 * 查询服务分配信息
	 * @return
	 */
	public String searchService(){
		List<Cst_service_t> list=serviceService.showService(cstname, title,type,status);
		request.put("serviceList", list);
		List<User>  users=serviceService.getQuery("from User u").list();
		request.put("users", users);
		return "serviceAssign";
	}
	/**
	 * 查询服务处理信息
	 * @return
	 */
	public String searchresolveService(){
		List<Cst_service_t> list=serviceService.showService(cstname, title,type,status);
		request.put("serviceList", list);
		List<User>  users=serviceService.getQuery("from User u").list();
		request.put("users", users);
		return "serviceResolve";
	}
	/**
	 * 查询服务反馈信息
	 * @return
	 */
	public String searchbackService(){
		List<Cst_service_t> list=serviceService.showService(cstname, title,type,status);
		request.put("serviceList", list);
		List<User>  users=serviceService.getQuery("from User u").list();
		request.put("users", users);
		return "serviceBack";
	}
	/**
	 * 查询服务归档信息
	 * @return
	 */
	public String searchstoreService(){
		List<Cst_service_t> list=serviceService.showService(cstname, title,type,status);
		request.put("serviceList", list);
		List<User>  users=serviceService.getQuery("from User u").list();
		request.put("users", users);
		return "serviceStore";
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

	public Cst_service_t getCst_service_t() {
		return cst_service_t;
	}

	public void setCst_service_t(Cst_service_t cst_service_t) {
		this.cst_service_t = cst_service_t;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getCstname() {
		return cstname;
	}


	public void setCstname(String cstname) {
		this.cstname = cstname;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	
	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}
	public String getDue_to() {
		return due_to;
	}
	public void setDue_to(String due_to) {
		this.due_to = due_to;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
