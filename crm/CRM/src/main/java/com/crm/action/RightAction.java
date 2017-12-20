package com.crm.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.crm.model.Sys_right_t;
import com.crm.model.Sys_role_t;
import com.crm.model.Sys_user_t;
import com.crm.service.RightService;

public class RightAction   implements RequestAware,SessionAware{

	private Map request;
	private	Map session;
	
	//授予权限的权限Id集合
	private String[] rightsId;
	//权限
	private Sys_right_t sys_right_t;

	//角色
	private Sys_role_t sys_role_t;
	
	//用户
	private Sys_user_t sys_user_t;
	
	//权限id
	private int right_id;
	
	//角色Id
	private int role_id;
	//用户id
	private int user_id;
	
	
	//更新权限属性
	private int parent_id;
	
	private String right_type;
	
	private String right_text;
	
	private String rigth_url;
	
	private String right_tip;
	
	
	
	@Resource(name="RightService")
	private RightService rightService;
	
	/**
	 * 打开权限
	 */
	public  String openRight(){
		List<Sys_right_t> topright=rightService.getTopSysRight();
		request.put("parentRight", topright);
		return "right";
	}
	
	/**
	 * 添加权限
	 * @return
	 */
	public String addRight(){
		if(parent_id!=0)
		{	Sys_right_t parent =rightService.find(parent_id, Sys_right_t.class);
		parent.getRights().add(sys_right_t);
		rightService.saveSysRight(parent);
		}
		else rightService.saveSysRight(sys_right_t);
		System.out.println(parent_id);
		getRight();
		return "rightEdit";
	}
	
	/**
	 * 获取权限
	 * @return
	 */
	public String getRight(){
		List<Sys_right_t> rights=rightService.getSysRight();
		List<Sys_right_t> topright=rightService.getTopSysRight();
		request.put("rights", rights);
		request.put("parentRight", topright);
		return "rightEdit";
	}
	
	
	/**
	 * 修改权限
	 * @return
	 */
	public String modRight(){
		Sys_right_t parent =rightService.find(parent_id, Sys_right_t.class);
		Sys_right_t right=new Sys_right_t();
		right.setRight_id(right_id);
		right.setRight_text(right_text);
		right.setRight_tip(right_tip);
		right.setRight_type(right_type);
		right.setRigth_url(rigth_url);
		
		parent.getRights().add(right);
		rightService.saveSysRight(parent);
		
		getRight();
		return "rightEdit";
	}
	
	
	/**
	 * 打开角色
	 */
	public  String openRole(){
		List<Sys_role_t> topright=rightService.getRole();
		request.put("roleList", topright);
		return "role";
	}
	
	/**
	 * 添加角色
	 * @return
	 */
	public String addRole(){
		rightService.saveRole(sys_role_t);
		getRole();
		return "role";
	}
	
	/**
	 * 获取角色
	 * @return
	 */
	public String getRole(){
		List<Sys_role_t> topright=rightService.getRole();
		request.put("roleList", topright);
		return "roleRight";
	}
	
	/**
	 * 打开权限授予
	 */
	public String opengiveRole(){
		
		Sys_role_t s=rightService.find(role_id, Sys_role_t.class);
		List<Sys_right_t> topright=rightService.getTopSysRight();
		request.put("rights", topright);
		request.put("sys_role_t",s);
		return "roleRight";
	}
	
	
	/**
	 * 权限授予
	 */
	public String giveRole(){
		Sys_role_t s=rightService.find(role_id, Sys_role_t.class);
		for(int i=0;i<rightsId.length;i++)
		{
			Sys_right_t right=rightService.find(rightsId[i], Sys_right_t.class);
			s.getSys_right_t().add(right);
			right.getSys_role_t().add(s);
			rightService.saveSysRight(right);
		}
		
		openRole();
		return "role";
	}
	
	/**
	 * 查看拥有的权限
	 */
	public String openhasRole(){
		
		Sys_role_t s=rightService.find(role_id, Sys_role_t.class);
		request.put("sys_role_t",s);
		return "checkHasRight";
	}
	
	/**
	 * 修改角色
	 * @return
	 */
	public String modRole(){
		Sys_right_t right=new Sys_right_t();
		right.setRight_id(right_id);
		right.setRight_text(right_text);
		right.setRight_tip(right_tip);
		right.setRight_type(right_type);
		right.setRigth_url(rigth_url);

		rightService.saveSysRight(right);
		
		getRight();
		return "rightEdit";
	}
	

	/**
	 * 添加用户
	 * @return
	 */
	public String addUser(){
		rightService.saveUser(sys_user_t);
		
		openUser();
		return "users";
	}
	
	
	/**
	 * 打开用户列表
	 */
	public String openUser(){
		List<Sys_user_t> users=rightService.getUser();
		request.put("users", users);
		return "users";
	}
	
	/**
	 * 打开角色授予列表
	 */
	public String opengiveUser(){
		Sys_user_t s=rightService.find(user_id, Sys_user_t.class);
		List<Sys_role_t> r=rightService.getRole();
	
		request.put("roles", r);
		request.put("user", s);
		return "userRole";
	}
	
	/**
	 * 角色授予
	 */
	public String giveUser(){
		Sys_user_t s=rightService.find(user_id, Sys_user_t.class);
		Sys_role_t r=rightService.find(role_id, Sys_role_t.class);
		s.setSys_role_t(r);
		r.getSys_user_t().add(s);
		rightService.saveUser(s);
		openUser();
		return "users";
	}
	
	/**
	 * 用户修改
	 */
	public String openmodUser(){
		Sys_user_t user = rightService.find(user_id,Sys_user_t.class);
		request.put("user",user);
		return "moduser";
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
	public Sys_right_t getSys_right_t() {
		return sys_right_t;
	}

	public void setSys_right_t(Sys_right_t sys_right_t) {
		this.sys_right_t = sys_right_t;
	}



	public int getRight_id() {
		return right_id;
	}

	public void setRight_id(int right_id) {
		this.right_id = right_id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getRight_type() {
		return right_type;
	}

	public void setRight_type(String right_type) {
		this.right_type = right_type;
	}

	public String getRight_text() {
		return right_text;
	}

	public void setRight_text(String right_text) {
		this.right_text = right_text;
	}

	public String getRigth_url() {
		return rigth_url;
	}

	public void setRigth_url(String rigth_url) {
		this.rigth_url = rigth_url;
	}

	public String getRight_tip() {
		return right_tip;
	}

	public void setRight_tip(String right_tip) {
		this.right_tip = right_tip;
	}

	public Sys_role_t getSys_role_t() {
		return sys_role_t;
	}

	public void setSys_role_t(Sys_role_t sys_role_t) {
		this.sys_role_t = sys_role_t;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String[] getRightsId() {
		return rightsId;
	}

	public void setRightsId(String[] rightsId) {
		this.rightsId = rightsId;
	}



	public Sys_user_t getSys_user_t() {
		return sys_user_t;
	}

	public void setSys_user_t(Sys_user_t sys_user_t) {
		this.sys_user_t = sys_user_t;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	
	
	
	
}
