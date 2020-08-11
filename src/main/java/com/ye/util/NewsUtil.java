package com.ye.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NewsUtil {
	private static NewsUtil newsUtil;
	private NewsUtil(){
	}
	public static NewsUtil getInstance(){
		if(newsUtil!=null){
			return newsUtil;
		}else{
			newsUtil = new NewsUtil();
			return newsUtil;
		}
	}
	public List<Map<String, String>> getnews() {
	    String host = "http://toutiao-ali.juheapi.com";
	    String path = "/toutiao/index";
	    String method = "GET";
	    String appcode = "1b13d25b0a0b469b9f221f8c4f0c34d9";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("type", "type");
    	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	String news = EntityUtils.toString(response.getEntity());
	    	JsonObject resultJson = new JsonParser().parse(news).getAsJsonObject();
	    	JsonArray array = resultJson.get("result").getAsJsonObject().get("data").getAsJsonArray();
	    	for (int i = 0; i < 5; i++) {
	    		Map<String,String> map = new HashMap<String,String>(3);
                JsonObject subObject = array.get(i).getAsJsonObject();
                map.put("title", subObject.get("title").getAsString());
                //map.put("date", subObject.get("date").getAsString());
                map.put("url", subObject.get("url").getAsString());
                list.add(map);
            }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return list;
	}
}
