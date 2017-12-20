package com.crm.aop;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.crm.model.Sys_right_t;
import com.crm.model.Sys_user_t;
import com.crm.service.UsersService;

/**
类名称：PermissionCheckAdvice   
* 类描述：   AOP实现权限管理的核心
* 创建人：胡杰雄   
* 创建时间：2014-4-23 下午4:03:45   
*/


@Aspect
@Component
public class PermissionCheckAdvice{

	@Resource(name="UsersService")
	private UsersService userService;
	private Logger log=Logger.getLogger(PermissionCheckAdvice.class);
	
	//切入点集合
	@Pointcut("execution(* com.crm.action.*.*(..))&& " +   
            "!execution(* com.crm.action.*.set*(..)) && " +   
            "!execution(* com.crm.action.*.get*(..)) && !execution(* com.crm.action.*.login*(..))")  
	public void testPointCut(){
		log.debug("权限检测完成。。");
	}
	//通知
	//在切点方法集合执行前，执行前置通知
	@Around("com.crm.aop.PermissionCheckAdvice.testPointCut()")
	public Object before(ProceedingJoinPoint joinPoint) throws Throwable {  
		// TODO Auto-generated method stub
		//拦截的实体类
		String target = joinPoint.getTarget().getClass().getName();
		//拦截的方法名称
		String methodName = joinPoint.getSignature().getName(); 

		HttpServletRequest request=ServletActionContext.getRequest();
		Sys_user_t user=(Sys_user_t)request.getSession().getAttribute("user");
		//判断是否有权限
		boolean flag=false;
		 if(user!=null)
		 {
			 Sys_user_t u = userService.findUserByUserName(user.getUser_name());
			 Set<Sys_right_t> prilivige= u.getSys_role_t().getSys_right_t();
			 Iterator iterator=prilivige.iterator();
			 for(;iterator.hasNext();)
			 {
				 Sys_right_t right= (Sys_right_t) iterator.next();
				 String url=sortUrl.getUrl(target, methodName);
				 System.out.println(url);
				 if(right.getRigth_url().equals(url))
					 {flag=true;break;}
			 }
			 if(!flag)
			 {	 log.debug("没有权限");System.out.println("没有权限");return "fail";}
		 }
			 
         log.debug("权限检测中。。。。。。");
         return joinPoint.proceed();
	}


}
