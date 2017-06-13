package com.favorites;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Sc_httpConnPost_util {
	
	/**
	 * TODO 用HttpURLConnection中的post方式获取数据
	 * @param params 传入的Map<String,String>形式参数集合
	 * @param url 请求链接
	 * @return String 返回数据字符串
	 */
	@SuppressWarnings("rawtypes")
	public static String connPost(String url,Map<String, String> params){
		PrintWriter printWriter = null;  
        BufferedReader bufferedReader = null;  
        StringBuffer param = new StringBuffer();  
        HttpURLConnection httpURLConnection = null;  
        StringBuffer result = new StringBuffer();;
        Iterator it = params.entrySet().iterator();  
        while (it.hasNext()) {  
            Map.Entry element = (Map.Entry) it.next();  
            param.append(element.getKey());  
            param.append("=");  
            param.append(element.getValue());  
            param.append("&");  
        }  
        if (param.length() > 0) {  
        	param.deleteCharAt(param.length() - 1);  
        }  
        try {  
            URL realUrl = new URL(url);  
            httpURLConnection = (HttpURLConnection) realUrl.openConnection();  
            
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setRequestProperty("contentType", "utf-8");
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (compatible) ");
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			
            httpURLConnection.setRequestProperty("accept", "*/*");  
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");  
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));  
            httpURLConnection.setDoOutput(true);  
            httpURLConnection.setDoInput(true); 
            httpURLConnection.setConnectTimeout(1000*10);  //设置连接超时为10s  
            httpURLConnection.setReadTimeout(1000*10);     //读取数据超时也是10s
            httpURLConnection.setRequestMethod("POST");
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());  
            printWriter.write(param.toString());  
            printWriter.flush();  
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));  
            String line;  
            while (bufferedReader != null && (line = bufferedReader.readLine()) != null) {  
            	result.append(line);  
            }  
        } catch (IOException e) {  
        	e.printStackTrace();
        } finally {  
            httpURLConnection.disconnect();  
            try {  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
		return result.toString();
	}
	public static void main(String[] args) {
		String url = "http://192.168.1.173:8080/AllLogin/service/service!getSysDetails.action";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", "shaochong");//用户登录名（账号）
		
		params.put("accessType", "1");//1：系统主要权限
		//params.put("accessType", "2");//2：实时浏览设备
		//params.put("accessType", "3");//3：录像回放设备
		//params.put("accessType", "41");//41：电视墙(管理)
		//params.put("accessType", "42");//42：电视墙(使用)
		
		params.put("sysType", "1");//监控系统
		String result = connPost(url,params);
		System.err.println(result);
		
		params.put("sysType", "2");//谈话系统
		result = connPost(url,params);
		System.err.println(result);
	}
}
