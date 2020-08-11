package com.ye.util;

import java.security.GeneralSecurityException;

import org.junit.Test;

public class SendEmailTest {

	@Test
	public void test() throws GeneralSecurityException {
		SendEmail sendd = new SendEmail();
		sendd.send("199382173@qq.com","199382173@qq.com",1);
	}

}
