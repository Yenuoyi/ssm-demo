package com.ye.util;

import java.io.UnsupportedEncodingException;
import org.junit.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ye.domain.UserBean;

public class TokenUtilTest {

	@Test
	public void test() throws UnsupportedEncodingException {
		UserBean userbean = new UserBean();
		userbean.setEmail("19689405@qq.com");
		userbean.setCode("21dd");
		TokenUtil token = new TokenUtil();
		String aaa = token.CreateTokenNoTime(userbean.toString());
		System.out.println(aaa);
		String bbb = token.Vertify(aaa, 3600);
		System.out.println(bbb);
		JsonObject json = new JsonParser().parse(bbb).getAsJsonObject();
		System.out.println(json.get("code").getAsString());
	}
}
