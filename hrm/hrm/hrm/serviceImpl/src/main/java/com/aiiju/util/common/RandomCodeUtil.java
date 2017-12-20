package com.aiiju.util.common;

import java.util.Arrays;

import com.aiiju.util.exception.BizException;
import com.aiiju.util.exception.BizExceptionMessage;

/** 
 * @ClassName: RandomCodeUtil 
 * @Description: 验证码规则类
 * @author 哪吒 
 * @date 2016年9月24日 上午9:09:01 
 *  
 */
public class RandomCodeUtil {

    /**
     * 验证码难度级别。SIMPLE只包含数字，MEDIUM包含数字和小写英文，HARD包含数字和大小写英文
     */
    public enum RandomCodeLevel {
        SIMPLE, MEDIUM, HARD
    }

    /**
     * 字符集合
     */
    private final static char[] CHAR_CODE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z' };

    /**
     * 生成默认验证码，4位简单难度
     */
    public static String getRandomCode() {
        return getRandomCode(4, RandomCodeLevel.SIMPLE, false);
    }

    /**
     * 获取验证码。指定长度、难度、是否允许重复字符
     * @param length 长度
     * @param level 难度
     * @param isCanRepeat 是否允许重复字符
     * @return
     */
    public static String getRandomCode(int length, RandomCodeLevel level, boolean isCanRepeat) {
        // 随机抽取len个字符
        int len = length;
        // 存储截取后的字符数组
        char[] codes;
        // 根据不同的难度截取字符数组
        switch (level) {
        case SIMPLE:
            codes = Arrays.copyOfRange(CHAR_CODE, 0, 10);
            break;
        case MEDIUM:
            codes = Arrays.copyOfRange(CHAR_CODE, 0, 36);
            break;
        case HARD:
            codes = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
            break;
        default:
            codes = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
        }

        // 字符集合长度
        int n = codes.length;
        // 对请求参数做校验
        if (len > n && isCanRepeat == false) {
            throw new RuntimeException(String.format(
                    "调用SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)出现异常，" + "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s", len,
                    level, isCanRepeat, n));
        }
        
        // 存放抽取出来的字符
        char[] result = new char[len];
        // 判断是否允许重复字符
        if (isCanRepeat) {
            for (int i = 0; i < result.length; i++) {
                int r = (int) (Math.random() * n);
                // 将result中的第i个元素设置为codes[r]存放的值
                result[i] = codes[r];
            }
        }else {
            for (int i = 0; i < result.length; i++) {
                int r = (int) (Math.random() * n);
                result[i] = codes[r];
                // 必须确保不会再次抽取到那个字符，因为所有抽取的字符必须不相同。
                // 因此，这里用数组中的最后一个字符改写codes[r]，并将n减1
                codes[r] = codes[n - 1];
                n--;
            }
        }
        return String.valueOf(result);
    }
    
    /**
     * 
     * @Title: getValidationCode 
     * @Description: 生成n位数随机码
     * @return 
     * @throws
     */
    public static String getValidationCode(int num) {
        
        String validationCode = "";
        try {
            // 默认生成n位的数字短信验证码
            validationCode = getRandomCode(num, RandomCodeLevel.SIMPLE, false);
        }catch (Exception e) {
            throw new BizException(BizExceptionMessage.SYS_ERROR);
        }
        
        return validationCode;
    }
}
