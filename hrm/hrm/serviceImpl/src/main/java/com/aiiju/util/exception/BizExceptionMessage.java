package com.aiiju.util.exception;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: BizExceptionMessage 
 * @Description: 业务异常编码
 *                  系统保留 0-100
 * @author 哪吒 
 * @date 2016年9月12日 上午10:31:53 
 *  
 */
public class BizExceptionMessage {

    @ExceptionAnnotation(key = "0", value = "运行正确。")
    public static final String SUCCESS = "0";
    @ExceptionAnnotation(key = "1", value = "执行失败")
    public static final String FAILURE = "1";
    @ExceptionAnnotation(key = "001", value = "参数为空。")
    public static final String EMPTY_ERROR = "001";
    @ExceptionAnnotation(key = "002", value = "时间错误。")
    public static final String TIME_ERROR = "002";
    @ExceptionAnnotation(key = "003", value = "签名错误。")
    public static final String SIGN_ERROR = "003";
    @ExceptionAnnotation(key = "004", value = "方法错误。")
    public static final String METHOD_ERROR = "004";
    @ExceptionAnnotation(key = "005", value = "属性错误。")
    public static final String PROP_ERROR = "005";
    @ExceptionAnnotation(key = "006", value = "逻辑错误。")
    public static final String LOGIC_ERROR = "006";
    @ExceptionAnnotation(key = "007", value = "数据库异常。")
    public static final String DB_ERROR = "007";
    @ExceptionAnnotation(key = "008", value = "系统繁忙。")
    public static final String SYS_ERROR = "008";
    @ExceptionAnnotation(key = "009", value = "参数错误。")
    public static final String PARAM_ERROR = "009";
    @ExceptionAnnotation(key = "010", value = "读取文件失败。")
    public static final String READ_FILE_FAIL = "010";
    @ExceptionAnnotation(key = "011", value = "模版格式错误。")
    public static final String EXCEL_MODEL_ERROR = "011";
    @ExceptionAnnotation(key = "012", value = "模版不存在。")
    public static final String EXCEL_NOT_EXIST = "012";
    @ExceptionAnnotation(key = "013", value = "验证码错误。")
    public static final String CHECKCODE_ERROR = "013";
    @ExceptionAnnotation(key = "014", value = "验证码已失效或不存在。")
    public static final String VCODE_IS_DISABLE = "014";
    @ExceptionAnnotation(key = "015", value = "验证码发送失败，请检查后重试 。")
    public static final String GET_VCODE_ERROR = "015";
    @ExceptionAnnotation(key = "016", value = "手机号未注册。")
    public static final String PHONE_IS_NONE = "016";
    @ExceptionAnnotation(key = "017", value = "待办事项数目超出上限。")
    public static final String OVER_SCHEDULE_LIMIT = "017";
    @ExceptionAnnotation(key = "018", value = "手机号已注册。")
    public static final String PHONE_IS_REGISTER = "018";
    @ExceptionAnnotation(key = "019", value = "该手机号与当前绑定的手机号相同。")
    public static final String PHONE_IS_REPEATED  = "019";
    @ExceptionAnnotation(key = "10020", value = "未绑定。")
    public static final String OAUTH_INFO_UNBIND = "10020";
    @ExceptionAnnotation(key = "10011", value = "无申请数据。")
    public static final String ENTER_SEARCH_PAGE = "10011";
    @ExceptionAnnotation(key = "20001", value = "公司名已存在。")
    public static final String COMPANY_NAME_EXIST = "20001";
    @ExceptionAnnotation(key = "20011", value = "部门不存在。")
    public static final String DETY_NAME_EXIST = "20011";
    @ExceptionAnnotation(key = "20011", value = "部门没有人员。")
    public static final String EMP_NAME_EXIST = "20012";
    @ExceptionAnnotation(key = "20002", value = "创建公司失败。")
    public static final String COMPANY_ADD_ERROR = "20002";
    @ExceptionAnnotation(key = "20003", value = "获取用户信息失败。")
    public static final String USERINFO_RETURN_ERROR = "20003";
    @ExceptionAnnotation(key = "20004", value = "获取公司列表失败。")
    public static final String SEARCH_COMPANY_ERROR = "20004";
    @ExceptionAnnotation(key = "20005", value = "用户申请已存在。")
    public static final String APPLICATION_IS_EXIST = "20005";
    @ExceptionAnnotation(key = "20006", value = "插入时间段已存在。")
    public static final String DATE_IS_EXIST = "20006";
    @ExceptionAnnotation(key = "A001", value = "手机格式错误。")
    public static final String PHONE_IS_EXIST = "A001";
    @ExceptionAnnotation(key = "A002", value = "邮箱格式错误。")
    public static final String EMIAL_IS_EXIST = "A002";
    @ExceptionAnnotation(key = "A003", value = "语言已存在。")
    public static final String Language_IS_EXIST = "A003";
    @ExceptionAnnotation(key = "A004", value = "员工号已存在。")
    public static final String USERNO_IS_EXIST = "A004";
    @ExceptionAnnotation(key = "A007", value = "任务逻辑错误。")
    public static final String TASK_IS_EXIST = "A007";
    @ExceptionAnnotation(key = "A007", value = "没有数据")
    public static final String TASK_IS_NULL = "A009";
 
    // 异常Map
    Map<String, String> exps = new HashMap<String, String>();
    
    // 单例实现
    private static BizExceptionMessage bem = null;
    
    private BizExceptionMessage() {
        
    }
    
    public synchronized static BizExceptionMessage getInstance() {
        if (null == bem) {
            bem = new BizExceptionMessage();
            for (Field f: BizExceptionMessage.class.getDeclaredFields()) {
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
        System.out.println(BizExceptionMessage.getInstance().exps.toString().replace("{", "").replace("}", "")
                .replaceAll(", ", "\r\n"));
    }
}
