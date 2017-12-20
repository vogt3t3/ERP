package com.ajy.web.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aiiju.service.IPermissionManageService;
import com.aiiju.util.common.ParameterUtil;

public class HrmPermissionInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	private IPermissionManageService permitService;
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		/*String args = request.getParameter("param");
		Map<String, Object> params = (Map<String, Object>) ParameterUtil.parseObj(args, Map.class);
		Map<String, Object> userPermission = permitService.getUserPermissionByAjuc(params);
		if(userPermission!=null&&userPermission.size()>0){
			//1.判断是否登录
			//2.判断用户的角色,是否需要插入U盾
			//3.如果需要插入U盾,则调用Java接口判断是否插入U盾(接口暂未开发)
			//4.需要U盾,判断当前用户是否具有该权限,如果有则且插入成功则放行,无则拦截
			//5.不需要U盾,判断当前用户是否具有该权限,如果有则放行,无则拦截
			if(true){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}*/
		return true;
	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	/**
	 * 从request请求中获得参数ParameterMap,并返回可读的Map
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })  
	public  Map<String,Object> getParameterMap(HttpServletRequest request) {  
	    // 参数Map  
	    Map<String,Object> properties = request.getParameterMap();  
	    // 返回值Map  
	    Map<String,Object> returnMap = new HashMap<String,Object>();  
	    Iterator entries = properties.entrySet().iterator();  
	    Map.Entry entry;  
	    String name = "";  
	    String value = "";  
	    while (entries.hasNext()) {  
	        entry = (Map.Entry) entries.next();  
	        name = (String) entry.getKey();  
	        Object valueObj = entry.getValue();  
	        if(null == valueObj){  
	            value = "";  
	        }else if(valueObj instanceof String[]){  
	            String[] values = (String[])valueObj;  
	            for(int i=0;i<values.length;i++){  
	                value = values[i] + ",";  
	            }  
	            value = value.substring(0, value.length()-1);  
	        }else{  
	            value = valueObj.toString();  
	        }  
	        returnMap.put(name, value);  
	    }  
	    return returnMap;  
	}  
}
