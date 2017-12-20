package com.ajy.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.aiiju.service.IBasicEnumService;

/**
 * 加载基础数据
 * @author qiqi
 * @date 2016-11-22 11:11:11
 */
@WebServlet("/BasicDataServlet")
public class BasicDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private IBasicEnumService basicEnumService;
	
	/**
	 * 将基本数据存储到redis
	 * @return
	 */
    public BasicDataServlet() {
    	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
    	IBasicEnumService basicEnumService=(IBasicEnumService)webApplicationContext.getBean("basicEnumService");
    	basicEnumService.storeBasicEnums(new HashMap<String, Object>());
    	
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
