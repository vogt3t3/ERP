package com.aiiju.util.exception;


/** 
 * @ClassName: BizException 
 * @Description: 业务异常
 * @author 哪吒 
 * @date 2016年9月12日 上午10:54:30 
 *  
 */
public class BizException extends ExceptionUtil {

    private static final long serialVersionUID = 6784166734277508309L;

    public BizException() {
        super ();
    }

    public BizException(String errorCode) {
        this (errorCode, "");
        String message = BizExceptionMessage.getInstance ().getExpInfo (this.getErrorCode ());
        for (int i = 0; this.getArgs () != null && i < this.getArgs ().length; i++) {
            message = message.replaceFirst("\\{\\d{1}\\}", this.getArgs ()[i]);
        }
        this.setDefaultMessage(message);
    }

    public BizException(String errorCode, String defaultMessage) {
        super (errorCode, defaultMessage);
    }

    public BizException(String errorCode, String internalCode, String defaultMessage) {
        super (errorCode, internalCode, defaultMessage);
    }

    public BizException(Throwable cause, String errorCode, String defaultMessage) {
        super (cause, errorCode, defaultMessage);
    }

    public BizException(Throwable cause, String errorCode) {
        super (cause, errorCode);
    }

    public BizException(String errorCode, String[] args) {
        super (errorCode, args);
    }

    @Override
    public String getMessage(){
        if (null != this.defaultMessage && 0 != defaultMessage.length ()) { 
            return this.defaultMessage;
        }
        String message = BizExceptionMessage.getInstance ().getExpInfo (this.getErrorCode ());
        for (int i = 0; this.getArgs () != null && i < this.getArgs ().length; i++) {
            message = message.replaceFirst("\\{\\d{1}\\}", this.getArgs ()[i]);
        }
        return message;
    }
}
