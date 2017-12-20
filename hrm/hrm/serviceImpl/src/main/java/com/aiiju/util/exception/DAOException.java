package com.aiiju.util.exception;


/**
 * 
 * @ClassName: DAOException 
 * @Description: 数据库异常 
 * @author 兵长
 * @date 2016年9月12日 下午2:21:29 
 *
 */

public class DAOException extends ExceptionUtil {

    private static final long serialVersionUID = -6828905987297675774L;
  
    public DAOException() {
        super ();
    }

    public DAOException(String errorCode) {
        this (errorCode, "");
        String message = BizExceptionMessage.getInstance ().getExpInfo (this.getErrorCode ());
        for (int i = 0; this.getArgs () != null && i < this.getArgs ().length; i++) {
            message = message.replaceFirst("\\{\\d{1}\\}", this.getArgs ()[i]);
        }
        this.setDefaultMessage(message);
    }

    public DAOException(String errorCode, String defaultMessage) {
        super (errorCode, defaultMessage);
    }

    public DAOException(String errorCode, String internalCode, String defaultMessage) {
        super (errorCode, internalCode, defaultMessage);
    }

    public DAOException(Throwable cause, String errorCode, String defaultMessage) {
        super (cause, errorCode, defaultMessage);
    }

    public DAOException(Throwable cause, String errorCode) {
        super (cause, errorCode);
    }

    public DAOException(String errorCode, String[] args) {
        super (errorCode, args);
    }

    @Override
    public String getMessage(){
        if (null != this.defaultMessage && 0 != defaultMessage.length ()) { 
            return this.defaultMessage;
        }
        String message = DAOExceptionMessage.getInstance ().getExpInfo (this.getErrorCode ());
        for (int i = 0; this.getArgs () != null && i < this.getArgs ().length; i++) {
            message = message.replaceFirst("\\{\\d{1}\\}", this.getArgs ()[i]);
        }
        return message;
    }
}
