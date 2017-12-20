/**   
 * @Title ResponseParam.java 
 * @Package com.ajy.web.vo 
 * @Description TODO(用一句话描述该文件做什么) 
 * @author 哪吒  
 * @date 2016年9月28日 下午7:08:08 
 * @version V1.0   
 */
package com.ajy.web.vo;

/**
 * @ClassName: ResponseParam
 * @Description: 请求第三方接口的返回参数
 * @author 哪吒
 * @date 2016年9月28日 下午7:08:08
 * 
 */
public class ResponseParam {

	/**
	 * 错误码
	 */
	private String error_code;
	/**
	 * 错误信息
	 */
	private String error_msg;
	/**
	 * 符合条件的数据总量
	 */
	private String total_num;
	/**
	 * 返回数据：对象/数组，当有错误时，返回null
	 */
	private Object data;

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
