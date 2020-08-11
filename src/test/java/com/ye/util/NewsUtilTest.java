package com.ye.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class NewsUtilTest {

	@Test
	public void test() {
		long startTime = System.currentTimeMillis();
		int result;
		NewsUtil news = NewsUtil.getInstance();
		if(!news.getnews().isEmpty()&&news.getnews().size()<6){
			result = 1;
		}else{
			result = 0;
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime);
		assertEquals(1,result);
	}

}
