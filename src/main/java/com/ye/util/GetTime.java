package com.ye.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetTime {
	public String getTime() throws Exception{
		URL u=new URL("http://api.k780.com:88/?app=life.time&appkey=***&sign=*&format=json");
		//URL u=new URL("http://api.k780.com:88/?app=life.time&appkey=28411&sign=4cb6fdd3c75979b50ad76b0f5e08266c&format=json");
		InputStream in=u.openStream();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            byte buf[]=new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        }  finally {
            if (in != null) {
                in.close();
            }
        }
        byte b[]=out.toByteArray( );
        String a=new String(b,"utf-8");
        JsonObject resultJson = new JsonParser().parse(a).getAsJsonObject();
        String aa=resultJson.get("result").getAsJsonObject().get("datetime_1").getAsString();
	    return aa;
	}
}
