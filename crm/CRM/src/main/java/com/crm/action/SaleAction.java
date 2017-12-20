package com.crm.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.crm.model.Sal_chance_t;
import com.crm.model.Sal_plan_t;
import com.crm.model.User;
import com.crm.service.SalService;
public class SaleAction implements RequestAware,SessionAware{
	
	@Resource(name="SalService")
	private SalService salService;
	//客户名字
	private String name;
	//联系人
	private String linkman;
	//概要
	private String title;
	//潜在客户id
	private  int cid;
	//开发计划id
	private int pid;
	//执行计划
	private String todo;
	//执行结果
	private String result;
	//客户
	private Sal_chance_t sal_chance_t;
	private Sal_plan_t sal_plan_t;
	private Map request;
	private	Map session;
	/**
	 * 添加销售机会
	 * @return
	 */
	public String addChance(){
		salService.saveOrUpdateChance(sal_chance_t);
		listChance();
		return "chanceList";
	}
	
	/**
	 * 指派销售机会
	 */
	public String addselectChance(){
		salService.saveOrUpdateChance(sal_chance_t);
		listChance();
		return "planList";
	}
	
	/**
	 * 营销机会表
	 */
	public String listChance(){

		List<Sal_chance_t> list=salService.listchance();
		request.put("chanceList", list);
		return "chanceList";
		}
	/**
	 * 营销机会详细信息
	 * @return
	 */
	public String detailChance(){
		Sal_chance_t c =salService.find(cid, Sal_chance_t.class);
		List<User> users=salService.getQuery("from User u ").list();
		request.put("users", users);
		request.put("sal_chance_t", c);
		return "chanceDetail";
	}
	
	/**
	 * 计划开发指派人选
	 * @return
	 */
	public String selectChance(){
		Sal_chance_t c =salService.find(cid, Sal_chance_t.class);
		List<User> users=salService.getQuery("from User u ").list();
		request.put("users", users);
		request.put("sal_chance_t", c);
		return "chanceSelect";
	}
	
	/**
	 * 打开创建销售机会界面
	 */

	public String openCreateChance(){
		List<User> users=salService.getQuery("from User u ").list();
		request.put("users", users);
		return "chanceDetail";
	}

	
	
	/**
	 * 更新销售机会信息
	 * @return
	 */
	public String updateChance(){
		salService.saveOrUpdate(sal_chance_t);
		return "chanceUpdate";
	}
	
	/**
	 * 查找销售机会
	 */
	public String searchChance(){
		List<Sal_chance_t> list=salService.searchChance(title, name, linkman);
		request.put("chanceList", list);
		return "chanceList";
	}
	
	/**
	 * 打开制定计划信息
	 * @return
	 */
	public String detailPlan(){
		Sal_chance_t c =salService.find(cid, Sal_chance_t.class);
		List<Sal_plan_t> list=salService.listplan(cid);
		List<User> users=salService.getQuery("from User u ").list();
		request.put("users", users);
		request.put("sal_chance_t", c);
		request.put("sal_plan_ts",list);
		return "planDetail";
	}
	
	
	/**
	 * 开发计划列表
	 */
	public String listPlan(){

		List<Sal_chance_t> list=salService.listchance();
		request.put("chanceList", list);
		return "planList";
	}
	
	
	/**
	 * 添加制定开发计划
	 * @return
	 */
	public String addPlan(){
		sal_plan_t.setPlan_chc_id(cid);
		salService.saveOrUpdatePlan(sal_plan_t);
		
		Sal_chance_t c =salService.find(cid, Sal_chance_t.class);
		List<Sal_plan_t> list=salService.listplan(cid);
		List<User> users=salService.getQuery("from User u ").list();
		request.put("users", users);
		request.put("sal_chance_t", c);
		request.put("sal_plan_ts",list);
		
        return "planDetail";
	}
	
	/**
	 * 更新制定开发计划
	 * @return
	 */
	public String modPlan(){
		
		Sal_plan_t plan=salService.find(pid,Sal_plan_t.class);
		plan.setPlan_todo(todo);
		salService.saveOrUpdatePlan(plan);
		Sal_chance_t c =salService.find(plan.getPlan_chc_id(),Sal_chance_t.class);
		List<Sal_plan_t> list=salService.listplan(plan.getPlan_chc_id());
		List<User> users=salService.getQuery("from User u ").list();
		request.put("users", users);
		request.put("sal_chance_t", c);
		request.put("sal_plan_ts",list);
		return "planDetail";
	}
	
	/**
	 * 删除制定开发计划
	 * @return
	 */
	public String delPlan(){
		
		Sal_plan_t plan=salService.find(pid,Sal_plan_t.class);
		salService.delete(plan);
		Sal_chance_t c =salService.find(plan.getPlan_chc_id(),Sal_chance_t.class);
		List<Sal_plan_t> list=salService.listplan(plan.getPlan_chc_id());
		List<User> users=salService.getQuery("from User u ").list();
		request.put("users", users);
		request.put("sal_chance_t", c);
		request.put("sal_plan_ts",list);
		return "planDetail";
	}
	
	
	 /**
	   *  打开执行开发计划
	   *  
	 */
	public String  executePlan(){
		
		List<Sal_plan_t> list=salService.listplan(sal_chance_t.getChc_id());
		List<User> users=salService.getQuery("from User u ").list();
		request.put("users", users);
		request.put("sal_chance_t", sal_chance_t);
		request.put("sal_plan_ts",list);
		
		return "planExecute";
		
	}
	
	
	/**
	 * 写下开发计划结果
	 */
	public String modexecutePlan(){
		
		Sal_plan_t plan=salService.find(pid,Sal_plan_t.class);
		System.out.println(result);
		plan.setPlan_result(result);
		salService.saveOrUpdatePlan(plan);
		Sal_chance_t c =salService.find(plan.getPlan_chc_id(),Sal_chance_t.class);
		List<Sal_plan_t> list=salService.listplan(plan.getPlan_chc_id());
		request.put("sal_chance_t", c);
		request.put("sal_plan_ts",list);
		return "planExecute";
	}
	
	/**
	 * 开发成功
	 */
	public String planSuccess(){
		Sal_chance_t c =salService.find(cid,Sal_chance_t.class);
		c.setChc_status("开发成功");
		salService.saveOrUpdateChance(c);
		listChance();
		return "chanceList";
	}
	
	/**
	 * 开发失败
	 */
	public String planFail(){
		Sal_chance_t c =salService.find(cid,Sal_chance_t.class);
		c.setChc_status("开发失败");
		salService.saveOrUpdateChance(c);
		listChance();
		return "chanceList";
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
	public SalService getSalService() {
		return salService;
	}
	public void setSalService(SalService salService) {
		this.salService = salService;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public Sal_chance_t getSal_chance_t() {
		return sal_chance_t;
	}
	public void setSal_chance_t(Sal_chance_t sal_chance_t) {
		this.sal_chance_t = sal_chance_t;
	}
	public Sal_plan_t getSal_plan_t() {
		return sal_plan_t;
	}
	public void setSal_plan_t(Sal_plan_t sal_plan_t) {
		this.sal_plan_t = sal_plan_t;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

}
