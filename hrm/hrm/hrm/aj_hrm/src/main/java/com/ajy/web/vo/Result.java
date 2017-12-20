package com.ajy.web.vo;

/**
 * 
 * @ClassName: Result
 * @Description: 返回消息
 * @author 小飞
 * @date 2016年6月20日 上午11:06:23
 *
 */
public class Result {
	/**
	 * 错误码
	 */
	private String code = "0";
	/**
	 * 错误信息
	 */
	private String message = "操作成功";
	/**
	 * 符合条件的数据总量
	 */
	private String total_num;
	/**
	 * 返回数据：对象/数组，当有错误时，返回null
	 */
	private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
