package com.aiiju.util.exception;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: BizExceptionMessage 
 * @Description: 数据库异常编码
 *                  系统保留 0-100
 * @author 哪吒 
 * @date 2016年9月12日 上午10:31:53 
 *  
 */
public class DAOExceptionMessage {

    @ExceptionAnnotation(key = "007", value = "数据库异常。")
    public static final String DB_ERROR = "007";

    // 异常Map
    Map<String, String> exps = new HashMap<String, String>();
    
    // 单例实现
    private static DAOExceptionMessage bem = null;
    
    private DAOExceptionMessage() {
        
    }
    
    public synchronized static DAOExceptionMessage getInstance() {
        if (null == bem) {
            bem = new DAOExceptionMessage();
            for (Field f: DAOExceptionMessage.class.getDeclaredFields()) {
                ExceptionAnnotation expInfo = f.getAnnotation(ExceptionAnnotation.class);
                if (null == expInfo) {
                    continue;
                }
                bem.exps.put(expInfo.key(), expInfo.value());
            }
        }
        return bem;
    }
    
    /**
     * 
     * @Title: getExpInfo 
     * @Description: 根据key获取异常信息
     * @param key
     * @return 
     * @throws
     */
    public String getExpInfo(String key) {
        return exps.get(key);
    }
    
    /**
     * 
     * @Title: main 
     * @Description: 打印测试
     * @param args 
     * @throws
     */
    public static void main(String[] args) {
        System.out.println(DAOExceptionMessage.getInstance().exps.toString().replace("{", "").replace("}", "")
                .replaceAll(", ", "\r\n"));
    }
}
