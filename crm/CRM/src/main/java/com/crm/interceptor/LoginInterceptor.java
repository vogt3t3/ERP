package com.crm.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
  
public class LoginInterceptor extends AbstractInterceptor {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
     * 这个拦截器的作用是如果是登录，即请求的是LoginAction，那么就不拦截这个请求，让其通过拦截器，进行登录 
     * 如果请求的是别的Action，若在没有进行登录的情况下，那么拦截器就会起作用，跳转到登录页面，进行登录， 
     * 若是在登录的情况下，则可以通过拦截器，继续下面操作。 
     */  
    @Override  
    public String intercept(ActionInvocation invocation) throws Exception {  
        /** 
         * invocation.getAction()是得到当前访问的Action 
         */  
    	
        if(invocation.getAction().getClass().getName().contains("LoginAction")) 
        {  
            return invocation.invoke();  
        }  
          
        Map map=invocation.getInvocationContext().getSession();//获得session  
          
        if(null==map.get("user")) 
        {  
           return Action.LOGIN; 
        }  
          
        return invocation.invoke();  
    }  
  
}  