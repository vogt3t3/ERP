package com.aiiju.util.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aiiju.util.common.MD5;
import com.aiiju.util.common.PropertiesUtil;

/**
 * 
 * @Description:远程调用接口工具类(通过httpclient方式调用AJUC的新接口)
 * @author 小辉
 * @date 2016年4月12日  15:02:26 
 *
 */
public class HrmHttpClientUtil extends PostMethod {

	public final static String JSONObject = "JSONObject";
	public final static String JSONArray = "JSONArray";
	
	private static Logger logger = LoggerFactory.getLogger(HrmHttpClientUtil.class);
	//自定义请求头信息
	private Map<String,String> head = new LinkedHashMap<String,String>();
	private int timeOut = 60000 ;//默认超时时间(1分钟)
	
	/**
	 * 目前调用AJUC的新接口暂时用工具类里的这个方法(其他方法对PHP目前定义的新接口暂时无法调通)
	 * @param url AJUC的接口地址 http://uc.ecbao.cn/ajuc/gateway
	 * @param className AJUC的接口类名
	 * @param methodName AJUC的接口方法名
	 * @param paramArray AJUC所需要的参数数组
	 * @return
	 * @throws Exception
	 */
	public static String getDataFromNewAjuc(String url,String className,String methodName,List<Object> paramArray) throws Exception{
		
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = null;
		String response = null;
		JSONObject jsonParams = null;
		try {
			jsonParams = new JSONObject();
			jsonParams.put("class", className);
			jsonParams.put("method", methodName);
			jsonParams.put("arguments", paramArray);
			//获取自定义的PostMethod
			postMethod = getPostMethod(url,jsonParams);
			//执行方法
			int status = httpClient.executeMethod(postMethod);
			if(status==HttpStatus.SC_OK){//200
				if(logger.isInfoEnabled()){
					logger.info("httpclient远程调AJUC接口请求成功！"+status);
				}
				response = postMethod.getResponseBodyAsString();
				
			}else if(status==HttpStatus.SC_FORBIDDEN){//403
				logger.error("httpclient远程调AJUC接口请求失败！"+"状态码："+status+"原因：签名异常或调用超时");
			}else if(status==HttpStatus.SC_INTERNAL_SERVER_ERROR){//500
				logger.error("httpclient远程调AJUC接口请求失败！"+"状态码："+status+"原因：服务器内部错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常请求返回,异常信息如下: "+ e.getMessage(),e);
		}finally{
			//释放链接
			postMethod.releaseConnection();
		}
	   return response;
	}
	
	/**
	 * 返回自定义的PostMethod
	 * @param url 请求地址
	 * @param jsonParams 请求参数
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private static PostMethod getPostMethod(String url,JSONObject jsonParams) throws IOException{
		PostMethod post = new PostMethod(url);
		//签名时间
		String signTime = String.valueOf(System.currentTimeMillis() / 1000);
		//签名加密规则
		String signBefore = "ecbao_rpc_secret"+jsonParams+signTime+"ecbao_rpc_secret";
		//对签名进行md5加密
		String sign = MD5.md5(signBefore);
		//设置请求头信息
		post.setRequestHeader("Sign",sign);
		post.setRequestHeader("Sign-Time",signTime);
		post.setRequestHeader("Content-Type","application/json");
		//将参数设置到请求体
		
		ByteArrayInputStream input = new ByteArrayInputStream(jsonParams.toString().getBytes("utf-8"));
		post.setRequestBody(input);
		
		
		return post;
	}
	
	/**
	 * 直接返回对应类型
	 * @param <T>
	 * @param requiredType  支持[net.sf.json.JSONArray,net.sf.json.JSONObject,String]
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T executeMethod(Class<T> requiredType) {
		if (null == requiredType) {
			return null;
		}
		if ("net.sf.json.JSONArray".equals(requiredType.getCanonicalName())) {
			return (T)executeMethod(JSONArray);
		}
		if ("net.sf.json.JSONObject".equals(requiredType.getCanonicalName())) {
			return (T)executeMethod(JSONObject);
		}
		return (T) (executeMethod());
	}
	/**
	 * 执行方法   type  可以不传，默认返回String
	 * @param type [JSONObject,JSONArray]
	 * @return
	 */
	public Object executeMethod(String... type) {
		try {
			HttpClient client = new HttpClient();
			HttpClientParams params = client.getParams();
			//新版AJUC接口地址http://uc.ecbao.cn/ajuc/gateway
			String domain = PropertiesUtil.getPropertyByKey("ajuc.new");
			params.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			/**
			 * 设置请求来源如果没有配置domain 那么就直接把domain换成你自己的域名即可
			 */
			params.setParameter("Referer",domain);
			params.setParameter("Connection","close");
			
			/**
			 * 请求超时时间
			 * 如果需要调整，请setTimeOut(自定义时间(单位毫秒))
			 */
			client.getHttpConnectionManager().getParams().setConnectionTimeout(getTimeOut());
			//加入额外的请求头信息
			for (String key : head.keySet()) {
				if(StringUtils.isNotBlank(key,head.get(key))){
					params.setParameter(key,head.get(key));
				}
			}
			int status = 0;
			String result = null;
			String url = getPath();
			try {
				status = client.executeMethod(this);
			} catch (Exception e) {
				logger.error("请求[" + url + "]超时或错误,message : "+e.getMessage(), e);
				e.printStackTrace();
			}
	
			if (status == HttpStatus.SC_OK) {
				try {
					result = this.getResponseBodyAsString();
					System.out.println(result);
				} catch (IOException e) {
					logger.error("Abnormal request returns! Exception information as follows: "+ e.getMessage(), e);
					e.printStackTrace();
				}
			} else {
				logger.error("HTTP请求错误，请求地址为：[" + url + "],状态为：["+ status + "]");
			}
			if (StringUtils.indexOf(JSONObject, type) > 0) {
				return net.sf.json.JSONObject.fromObject(result);
			}
	
			if (StringUtils.indexOf(JSONArray, type) > 0) {
				return net.sf.json.JSONArray.fromObject(result);
			}
			return result;
		}finally{
			this.releaseConnection();
		}
	}

	/**
	 * 设置参数List<Map<String, Object>>
	 * 
	 * @param parameter
	 */
	public void setParameter(List<Map<String, Object>> parameter) {
		for (Map<String, Object> map : parameter) {
			setParameter(map);
		}
	}

	/**
	 * 设置参数JSONObject
	 * 
	 * @param parameter
	 */
	public void setJSONParameter(JSONObject parameter) {
		for (Iterator<?> iter = parameter.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			Object str = parameter.get(key);
			String value = null == str ? "" : StringUtils.trimToEmpty(str
					.toString());
			this.addParameter(key,value);
		}
	}

	/**
	 * 设置参数JSONArray
	 * 
	 * @param parameter
	 */
	public void setJSONArrayParameter(JSONArray parameter) {
		for (Object object : parameter) {
			JSONObject jsonObject = (JSONObject) object;
			this.setJSONParameter(jsonObject);
		}
	}

	/**
	 * 设置参数Map<String, Object>
	 * 
	 * @param parameter
	 */
	public void setParameter(Map<String, Object> parameter) {
		for (String key : parameter.keySet()) {
			Object str = parameter.get(key);
			String value = null == str ? "" : StringUtils.trimToEmpty(str.toString());
			this.addParameter(key, value);
		}
	}

	/**
	 * 构造方法begin
	 */
	public HrmHttpClientUtil(String url) {
		super(url);
	}

	public HrmHttpClientUtil(List<Map<String, Object>> parameter) {
		super();
		this.setParameter(parameter);
	}

	public HrmHttpClientUtil(String url, List<Map<String, Object>> parameter) {
		super(url);
		this.setParameter(parameter);
	}

	public HrmHttpClientUtil(JSONObject parameter) {
		super();
		this.setJSONParameter(parameter);
	}

	public HrmHttpClientUtil(Map<String, Object> parameter) {
		super();
		this.setParameter(parameter);
	}

	public HrmHttpClientUtil(String url, Map<String, Object> parameter) {
		super(url);
		this.setParameter(parameter);
	}

	public HrmHttpClientUtil(String url, JSONObject parameter) {
		super(url);
		this.setJSONParameter(parameter);
	}

	public HrmHttpClientUtil(JSONArray parameter) {
		super();
		this.setJSONArrayParameter(parameter);
	}

	public HrmHttpClientUtil(String url, JSONArray parameter) {
		super(url);
		this.setJSONArrayParameter(parameter);
	}
	
	/**
	 * 构造方法end
	 */

	public Map<String, String> getHead() {
		return head;
	}

	public void setHead(Map<String, String> head) {
		this.head = head;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	public JsonConfig getConfig() {
		JsonConfig config = new JsonConfig();
		// 实现属性过滤器接口并重写apply()方法
		PropertyFilter pf = new PropertyFilter() {
			@Override
			// 返回true则跳过此属性，返回false则正常转换
			public boolean apply(Object source, String name, Object value) {
				if (StringUtils.isBlank(value)) {
					return true;
				}
				return false;
			}
		};
		// 将过滤器放入json-config中
		config.setJsonPropertyFilter(pf);
		return config;
	}

}
