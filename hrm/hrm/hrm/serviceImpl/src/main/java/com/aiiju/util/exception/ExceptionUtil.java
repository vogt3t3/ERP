package com.aiiju.util.exception;

/**
 * @ClassName: ExceptionUtil
 * @Description: 异常处理基础类
 * @author 哪吒
 * @date 2016年9月12日 上午10:02:14
 * 
 */
public class ExceptionUtil extends RuntimeException {

	private static final long serialVersionUID = -7486328726639956900L;

	// 错误编码
	private String errorCode = "errorCode.undefined";
	// 内部错误编码
	private String internalCode = "";
	// 默认错误信息
	protected String defaultMessage = null;

	private String[] args = null;

	public ExceptionUtil() {
		super();
	}

	/**
	 * @param cause
	 *            原始的异常信息
	 */
	public ExceptionUtil(Throwable cause) {
		super(cause);
		if (cause instanceof ExceptionUtil) {
			ExceptionUtil e = (ExceptionUtil) cause;
			this.defaultMessage = e.defaultMessage;
			this.errorCode = e.errorCode;
		}
	}

	/**
	 * @param cause
	 *            原始的异常信息
	 * @param errorCode
	 *            错误编码
	 */
	public ExceptionUtil(Throwable cause, String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	/**
	 * @param cause
	 *            原始的异常信息
	 * @param errorCode
	 *            错误编码
	 * @param defaultMessage
	 *            默认显示信息，配置文件无对应值时显示
	 */
	public ExceptionUtil(Throwable cause, String errorCode,
			String defaultMessage) {
		this(cause, errorCode);
		this.defaultMessage = defaultMessage;
	}

	/**
	 * @param errorCode
	 *            错误编码
	 * @param defaultMessage
	 *            默认显示信息，配置文件无对应值时显示
	 */
	public ExceptionUtil(String errorCode, String defaultMessage) {
		super();
		this.errorCode = errorCode;
		this.defaultMessage = defaultMessage;
	}

	/**
	 * @param errorCode
	 *            错误编码
	 * @param internalCode
	 *            内部错误编码
	 * @param defaultMessage
	 *            默认显示信息，配置文件无对应值时显示
	 */
	public ExceptionUtil(String errorCode, String internalCode,
			String defaultMessage) {
		super();
		this.errorCode = errorCode;
		this.internalCode = internalCode;
		this.defaultMessage = defaultMessage;
	}

	public ExceptionUtil(String errorCode, String[] args) {
		super();
		this.errorCode = errorCode;
		this.args = args;
	}

	@Override
	public String getMessage() {
		return this.defaultMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public String getInternalCode() {
		return internalCode;
	}

	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
	
	
	
	public static void throwExceptionUtil(Exception e) throws Exception{
		if(e instanceof BizException){
			throw e;
		}else {
			throw new DAOException("300", "数据库读取失败");
		}
	}

}
