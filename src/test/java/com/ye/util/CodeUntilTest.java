package com.ye.util;

import org.junit.Test;

public class CodeUntilTest {

	@Test
	public void test() {
		CodeUtil getcode = CodeUtil.getInstance();
		String a = getcode.code();
		System.out.println(a);
	}

}
