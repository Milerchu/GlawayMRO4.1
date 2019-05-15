package com.glaway.sddq.tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * http发送post和get请求
 * 
 * @author  public2176
 * @version  [版本号, 2018-6-25]
 * @since  [产品/模块版本]
 */
public class HttpRequestHelper {

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset         
     *             发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public static String sendGet(String url, String param,String charset) throws UnsupportedEncodingException, IOException {
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("Cache-Control", "no-cache");
            //设置超时时间
            conn.setConnectTimeout(2*1000);
            conn.setReadTimeout(2*1000);
            // 建立实际的连接
            conn.connect();
            // 定义 BufferedReader输入流来读取URL的响应,设置接收格式
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 
     * 带认证的get请求
     * @param url
     * @param param
     * @param auth Authorization数据
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException [参数说明]
     *
     */
    public static String sendGet(String url, String param, String auth, String charset) throws UnsupportedEncodingException, IOException {
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Authorization", auth);
            //设置超时时间
            conn.setConnectTimeout(2*1000);
            conn.setReadTimeout(2*1000);
            // 建立实际的连接
            conn.connect();
            // 定义 BufferedReader输入流来读取URL的响应,设置接收格式
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset         
     *             发送和接收的格式       
     * @return 所代表远程资源的响应结果
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public static String sendPost(String url, String param,String charset) throws UnsupportedEncodingException, IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接 
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("Cache-Control", "no-cache");
            //设置超时时间
            conn.setConnectTimeout(15*1000);
            conn.setReadTimeout(15*1000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
        } 
        //使用finally块来关闭输出流、输入流
        finally{
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        }
        return result;
    } 
    
//    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
//    	String url="http://localhost:58000/gwmro/mroservice/rest/bigdata/faultConfirm";
//		String param = "orderID=ZZ-GZ-2018-1002";
//		String user = "glawaymro:glawaymro";//用户名:密码
//		byte[] b = Base64.encodeBase64(user.getBytes(Charset.forName("US-ASCII")));
//		String auth="Basic "+ new String(b);
//    	System.out.println(sendGet(url, param,auth, "UTF-8"));
//	}
}