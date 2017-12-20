package com.ajy.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aiiju.util.common.MD5;
import com.aiiju.util.common.ParameterUtil;
import com.aiiju.util.enums.Response;
import com.aiiju.util.http.StringUtils;
import com.ajy.web.vo.RequestParam;
import com.ajy.web.vo.Result;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName: RequestInterceptor 拦截器
 * @Description: 校验参数合法性
 * @author 小飞
 * @date 2016年6月13日 上午11:42:13
 *
 */
public class RequestInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String imgStr = request.getParameter("imgStr");
		if(StringUtils.isNotBlank(imgStr)){
			//由于上传头像新接口的调用方式与老接口调用方式不一样,因此这个接口的调用暂时避开参数签名加密的校验
			return true;
		}else{
			String method = request.getParameter("method");
			// String time = request.getParameter("time");
			String args = request.getParameter("param");
			String sign = request.getParameter("sign");

			Result result = new Result();
			// 2.判断time是否超过5分钟
			// long currentime = System.currentTimeMillis();
			// currentime = currentime / 1000;
			// if (currentime - Long.parseLong(time) > 300) {
			// result.setError_code(Response.TIME_ERROR.getErrorCode());
			// result.setError_msg(Response.TIME_ERROR.getMsg());
			// this.writeResult(result, response);
			// return false;
			// }
			
			/*if (StringUtils.isEmpty(method) || StringUtils.isEmpty(args) || StringUtils.isEmpty(sign)) {
				result.setCode(Response.EMPTY_ERROR.getErrorCode());
				result.setMessage(Response.EMPTY_ERROR.getMsg());
				this.writeResult(result, response);

				return false;
			}*/
			 //3.判断sign有效性
//			String new_sign = "method" + method + "param" + args + "ecbao";
//			String md5_sign = MD5.md5(new_sign);
		/*	if (!md5_sign.equals(sign)) {
				result.setCode(Response.SIGN_ERROR.getErrorCode());
				result.setMessage(Response.SIGN_ERROR.getMsg());
				this.writeResult(result, response);
				return false;
			}*/
			// 4.判断args合法性
			String[] methods = method.split("/");
			String request_type = methods[0];
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			Object bean = webApplicationContext.getBean(request_type + "Service");

			if (bean != null) {
				boolean flag = ParameterUtil.check(args, RequestParam.class);
				if (!flag) {
					result.setCode(Response.PROP_ERROR.getErrorCode());
					result.setMessage(Response.PROP_ERROR.getMsg());

					this.writeResult(result, response);

					return false;
				}
			} else {
				result.setCode(Response.LOGIC_ERROR.getErrorCode());
				result.setMessage(Response.LOGIC_ERROR.getMsg());

				this.writeResult(result, response);
				return false;
			}
		}
		return true;
	}

	/**
	 * 数据写出
	 * 
	 * @param result
	 * @param response
	 */
	private void writeResult(Result result, HttpServletResponse response) {
		try {
			String reslutJSON = JSONObject.toJSONString(result);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(reslutJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

}
