package com.aiiju.util.enums;

/**
 * 
 * @ClassName: Response
 * @Description: 返回结果的错误码和信息
 * @author 小飞
 * @date 2016年6月20日 上午11:12:39
 *
 */
public enum Response {

	LOGIN_SUCESS("0","hrm登录成功"),
	LOGIN_FAILURE("400","hrm登录失败"),
	LOGINOUT_SUCESS("0","hrm登出成功"),
	EMPTY_ERROR("001", "参数为空"), 
	TIME_ERROR("002", "时间错误"), 
	SIGN_ERROR("003","签名错误"),
	METHOD_ERROR("004", "方法错误"), 
	PROP_ERROR("005", "属性错误"), 
	LOGIC_ERROR("006", "逻辑错误");

	private String errorCode;
	private String msg;

	private Response(String errorCode, String msg) {
		this.errorCode = errorCode;
		this.msg = msg;
	}

	public static String getMsg(String errorCode) {
		for (Response res : Response.values()) {
			if (res.getErrorCode().equals(errorCode)) {
				return res.getMsg();
			}
		}
		return null;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
