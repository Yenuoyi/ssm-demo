package com.ye.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.Random;

public class CodeUtil {
	private static CodeUtil codeUtil;
	private BufferedImage image; // 图像
    private String str; // 验证码
    // 获取图片
    public RenderedImage getImage() {
        return this.image;
    }
    
    // 获取6位随机验证码
    public String getStr() {
        return this.str;
    }
	private CodeUtil(){
	}
	public static CodeUtil getInstance(){
		if(codeUtil==null){
			codeUtil = new CodeUtil();
		}
		return codeUtil;
	}
	//注册后台数据库使用验证码
	public String code(){
		/** 
	     * 生成四位数字算法
	     *  
	     * @return rands 
	     */ 
	        String chars = "abcdefghijklmmopqrstuvwxyz1234567890"; 
	        char[] rands = new char[4]; 
	        for (int i = 0; i < 4; i++) 
	        { 
	            int rand = (int) (Math.random() * 36); 
	            rands[i] = chars.charAt(rand); 
	        } 
	        StringBuffer sb = new StringBuffer();
	        for(int j = 0; j < rands.length; j++){
	        	 sb. append(rands[j]);
	        	}
	        String rand = sb.toString();
	        return rand; 
	    }
	
	//注册前端使用验证码
	 public synchronized void init() {
	        // 在内存中创建图象，设置图像的宽高
	        int width = 120, height = 30;
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        // 获取图形上下文
	        Graphics g = image.getGraphics();
	        // 生成随机类
	        Random random = new Random();
	        // 设定背景色
	        g.setColor(getRandColor(200, 250));
	        g.fillRect(0, 0, width, height);
	        // 设定字体
	        g.setFont(new Font("宋体", Font.PLAIN, height - 4));
	        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
	        g.setColor(getRandColor(160, 200));
	        for (int i = 0; i < 155; i++) {
	            int x = random.nextInt(width);
	            int y = random.nextInt(height);
	            int xl = random.nextInt(12);
	            int yl = random.nextInt(12);
	            g.drawLine(x, y, x + xl, y + yl);
	        }
	        // 取随机产生的认证码(4位数字)
	        String sRand = "";
	        for (int i = 0; i < 4; i++) {
	            String rand = String.valueOf(random.nextInt(10));
	            sRand += rand;
	            // 将认证码显示到图象中
	            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
	            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
	            g.drawString(rand, 13 * i + 26, 25);
	        }
	        // 赋值验证码
	        this.str = sRand;
	        // 图象生效
	        g.dispose();
	        this.image = image;
	    }

	    // 给定范围获得随机颜色
	    private Color getRandColor(int fc, int bc) {
	        Random random = new Random();
	        if (fc > 255)
	            fc = 255;
	        if (bc > 255)
	            bc = 255;
	        int r = fc + random.nextInt(bc - fc);
	        int g = fc + random.nextInt(bc - fc);
	        int b = fc + random.nextInt(bc - fc);
	        return new Color(r, g, b);
	    }
}
