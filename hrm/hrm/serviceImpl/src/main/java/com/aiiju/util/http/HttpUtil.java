package com.aiiju.util.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @ClassName: HttpUtils 
 * @Description: HTTP请求封装类
 * @author 哪吒 
 * @date 2016年11月22日 下午2:18:45 
 *
 */

public class HttpUtil {
    private static final int TIMEOUT_IN_MILLIONS = 20000;

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 
     * @Title: doGetAsyn 
     * @Description: 异步的GET请求
     * @param urlStr
     * @param callBack 
     * @throws
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    /**
     * 
     * @Title: doPostAsyn 
     * @Description: 异步的POST请求
     * @param urlStr 请求路径
     * @param params 请求参数，多参数采用name1=value1&name2=value2的形式
     * @param callBack
     * @throws Exception 
     * @throws
     */
    public static void doPostAsyn(final String urlStr, final String params, final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();

    }

    /**
     * 
     * @Title: doGet 
     * @Description: GET请求，获得返回数据
     * @param urlStr 请求路径
     * @return String
     * @throws
     */
    public static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1; 
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 
     * @Title: doPost 
     * @Description: POST请求，获得返回数据
     * @param urlStr 请求路径
     * @param param 请求参数，多参数采用name1=value1&name2=value2的形式
     * @return String
     * @throws
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流(并指定编码为utf-8,不然手机短信中文乱码)
                out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 
     * @Title: main 
     * @Description: 测试
     * @param args 
     * @throws
     */
    public static void main(String[] args) {
        String str = doPost("http://uc.ecbao.cn/ajuc/uc_api", "method=oa_user/find_user&args=%7B\"visit_id\"%3A61%7D&time=1472539292&sign=8af4bb3e72e6a8f0d770dfb261638a6f");
        System.out.println(str);
    }
}
