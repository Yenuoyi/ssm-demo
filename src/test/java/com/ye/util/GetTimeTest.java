package com.ye.util;

import org.junit.Test;

public class GetTimeTest {
	@Test
	public void time() throws Exception{
		GetTime time = new GetTime();
		String a =time.getTime();
		System.out.println(a);
	}
}
