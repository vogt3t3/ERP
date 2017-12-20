package com.crm.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.crm.model.Sys_role_t;
import com.crm.model.Sys_user_t;
import com.crm.service.UsersService;

public class LoginAction implements RequestAware,SessionAware{

	private Map request;
	private	Map session;
	
	@Resource(name="UsersService")
	private UsersService userService;
	
	private Sys_user_t sys_user_t;
	public String login(){
		Sys_user_t user= userService.findUserByUserName(sys_user_t.getUser_name());
		if(user.getUser_password().equals(sys_user_t.getUser_password()))
		{	
			sys_user_t.setUser_flag(user.getUser_flag());
			sys_user_t.setUser_id(user.getUser_id());
			
			session.put("user", sys_user_t);	
			session.put("role", user.getSys_role_t().getRole_name());
		return "success";
		}
		else
		{
			request.put("msg", "用户名或密码错误");
			return "fail";
		}
	}
	
	public String loginout(){
		
		session.remove("user");
		session.remove("role");
		return "login";
	}
	public Sys_user_t getSys_user_t() {
		return sys_user_t;
	}


	public void setSys_user_t(Sys_user_t sys_user_t) {
		this.sys_user_t = sys_user_t;
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
}
