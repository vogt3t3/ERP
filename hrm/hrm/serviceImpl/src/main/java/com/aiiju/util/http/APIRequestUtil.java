package com.aiiju.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aiiju.util.common.MD5;
import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.common.Result;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: APIRequestUtil 
 * @Description: 请求第三方接口工具类
 * @author 哪吒 
 * @date 2016年11月22日 下午2:20:37 
 *
 */
public class APIRequestUtil {
	
	private static Logger logger = LoggerFactory.getLogger(APIRequestUtil.class);
	/**
	 * 
	 * @Title: getResultFromPhpAPI
	 * @Description: 从第三方接口获取信息
	 * @param methodKey 请求方法的key值（详细请参照apiInfo.properties）
	 * @param object 请求参数对象
	 * @param type 返回类型 1-JSONObject处理返回数据 2-GSON处理返回数据
	 * @param encType sign生成规则
	 * @return 
	 * @throws
	 */
    public static Result getResultFromPhpAPI(String urlName, String methodKey, Object object, String type, String signType) {
    	logger.info("---------------"+"调用第三方接口START"+"----------------");
        // 声明StringBuffer,用于组装数据
        StringBuffer data = new StringBuffer();
        // 获取接口URL
        String url = PropertiesUtil.getPropertyByKey(urlName);
        // 获取method,从apiInfo.properties中获取
        String method = PropertiesUtil.getPropertyByKey(methodKey);
        // TEST
        /*String url = urlName;
        String method = methodKey;*/
        // 获取args，将对象转为json字符串
        Gson gson = new Gson();
        
        try {
        	String args = gson.toJson(object);
		
	        // 获取time
	        String time = String.valueOf(System.currentTimeMillis() / 1000);
	        // 生成sign
	        String sign = "";
	        if (StringUtils.equals(signType, "1")) { // 请求ajuc接口的签名规则
	            sign = MD5.md5(method + args + time + "ecbao");
	            args=URLEncoder.encode(args,"UTF-8");
	            // 组装请求参数
	            data = data.append("method=").append(method).append("&").append("args=").append(args).append("&")
	                    .append("time=").append(time).append("&").append("sign=").append(sign);
	        }
	        if (StringUtils.equals(signType, "2")) { // 请求OA接口的签名规则
	            sign = MD5.md5("method" + method + "param" + args + "ecbao");
	            // 组装请求参数
	            data = data.append("method=").append(method).append("&").append("param=").append(args).append("&")
	                    .append("sign=").append(sign);
	        }
	        logger.info("---------------URL:"+url+"----------------");
	        String resultStr = HttpUtil.doPost(url, data.toString());
	        logger.info("---------------resultStr:"+resultStr+"----------------");
	        Result result = null;
	        if (StringUtils.equals(type, "1")) {
	         // 使用JSONObject.toJavaObject()方法获得的data字段是JSONArray类型，不方便后续的数据处理
	            result = JSONObject.toJavaObject(JSONObject.parseObject(resultStr), Result.class);
	        }else if (StringUtils.equals(type, "2")) {
	         // 使用gson.fromJson()方法获得的data字段是ArrayList<E>类型
	            result = gson.fromJson(resultStr, Result.class);
	        }
	        logger.info("---------------"+"调用第三方接口END"+"----------------");
	        	return result;
        	} catch (UnsupportedEncodingException e) {
        		e.printStackTrace();
        	}
        logger.info("---------------"+"调用第三方接口END"+"----------------");
		return null;
    }
    
    /**
     * 访问长链接更换为短链接接口
     * @param object
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Result getResultOfShortURL(Object object, String type) {
        // 声明StringBuffer,用于组装数据
        StringBuilder data = new StringBuilder();
        // 获取接口URL
        String url = PropertiesUtil.getPropertyByKey("crm.short.url");
        // 获取appkey
        String appkey = PropertiesUtil.getPropertyByKey("crm.sms.appkey");
        // 获取密钥
        String keysecret = PropertiesUtil.getPropertyByKey("crm.sms.keysecret");
        // 获取当前请求时间
        String timestamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        
        Map<String, Object> requestParams = (Map<String, Object>) object;
        
        // 生成sign(业务参数不参与生成规则)
        Map<String, String> signParams = new HashMap<String, String>();
        signParams.put("appkey", appkey);
        signParams.put("timestamp", timestamp);
        String sign = SignatureUtil.getSignature(signParams, keysecret);
        // 组装请求参数
        data = data.append("appkey=").append(appkey).append("&").append("timestamp=").append(timestamp).append("&")
                .append("sign=").append(sign).append("&").append("url=").append(requestParams.get("url"));
        
        String resultStr = HttpUtil.doPost(url, data.toString());
        Result result = null;
        Gson gson = new Gson();
        if (StringUtils.equals(type, "1")) {
         // 使用JSONObject.toJavaObject()方法获得的data字段是JSONArray类型，不方便后续的数据处理
            result = JSONObject.toJavaObject(JSONObject.parseObject(resultStr), Result.class);
        }else if (StringUtils.equals(type, "2")) {
         // 使用gson.fromJson()方法获得的data字段是ArrayList<E>类型
            result = gson.fromJson(resultStr, Result.class);
        }
        return result;
    }
    
    /**
     * 访问短信平台接口
     * @param object
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Result getResultOfSMS(Object object, String type) {
        // 声明StringBuffer,用于组装数据
        StringBuilder data = new StringBuilder();
        // 获取接口URL
        String url = PropertiesUtil.getPropertyByKey("crm.sms.url");
        // 获取appkey
        String appkey = PropertiesUtil.getPropertyByKey("crm.sms.appkey");
        // 获取密钥
        String keysecret = PropertiesUtil.getPropertyByKey("crm.sms.keysecret");
        // 获取msg_sign
        String msgSign = PropertiesUtil.getPropertyByKey("sms.msg.salary.sign");
        // 获取当前请求时间
        String timestamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        
        Map<String, Object> requestParams = (Map<String, Object>) object;
        
        // 生成sign
        Map<String, String> signParams = new HashMap<String, String>();
        signParams.put("appkey", appkey);
        signParams.put("timestamp", timestamp);
        //新的接口把所有参数作为签名规则进行加密(新接口的签名规则要求业务参数也进行签名加密)
        signParams.put("phone", requestParams.get("phone").toString());
        signParams.put("message", requestParams.get("message").toString());
        signParams.put("type", requestParams.get("type").toString());
        signParams.put("msg_sign", msgSign);
        
        String sign = SignatureUtil.getSignature(signParams, keysecret);
        // 组装请求参数
        data = data.append("appkey=").append(appkey).append("&").append("timestamp=").append(timestamp).append("&")
                .append("sign=").append(sign).append("&").append("phone=").append(requestParams.get("phone"))
                .append("&").append("message=").append(requestParams.get("message")).append("&").append("msg_sign=")
                .append(msgSign).append("&").append("type=").append(requestParams.get("type"));
        
        String resultStr = HttpUtil.doPost(url, data.toString());
        Result result = null;
        Gson gson = new Gson();
        if (StringUtils.equals(type, "1")) {
         // 使用JSONObject.toJavaObject()方法获得的data字段是JSONArray类型，不方便后续的数据处理
            result = JSONObject.toJavaObject(JSONObject.parseObject(resultStr), Result.class);
        }else if (StringUtils.equals(type, "2")) {
         // 使用gson.fromJson()方法获得的data字段是ArrayList<E>类型
            result = gson.fromJson(resultStr, Result.class);
        }
        return result;
    }
    
    public static void main(String[] args) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("visit_id", 61);
    	params.put("name", "测试1214");
    	params.put("phone", "18367161833");
    	getResultFromPhpAPI("http://121.199.182.2/ajuc_test/uc_api", "oa_user/addUserWithNotification", params, "2", "1");
    }
}
