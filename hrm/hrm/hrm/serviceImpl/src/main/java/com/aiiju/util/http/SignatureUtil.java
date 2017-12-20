package com.aiiju.util.http;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.aiiju.util.common.MD5;


/** 
 * @ClassName: SignatureUtil 
 * @Description: 签名生成规则工具类（这个是第三方短信平台的签名规则）
 * @author 哪吒 
 * @date 2016年9月27日 上午10:35:28 
 *  
 */
public class SignatureUtil {

    /**
     * 
     * @Title: getSignature 
     * @Description: sign生成规则 
     * @param params
     * @param secret
     * @return 
     * @throws
     */
    public static String getSignature(Map<String, String> params, String secret) {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append(param.getValue());
        }
        basestring.append("keysecret").append(secret);

        String sign = MD5.md5(basestring.toString());
        return sign.toString();
    }
}
