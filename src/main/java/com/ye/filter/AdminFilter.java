package com.ye.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ye.util.TokenUtil;

public class AdminFilter implements Filter {
	public void init(FilterConfig fConfig) throws ServletException {
	}


    public static boolean isContains(String container, String[] regx) {
        boolean result = false;

        for (int i = 0; i < regx.length; i++) {
            if (container.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return result;
    }
    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		//String uri = req.getRequestURI();
		//String[] loginList = {"/BBS/Upload","/BBS/uploadfile"};
        	String user = session.getAttribute("user").toString();
        	try{
        	//解锁token
			TokenUtil token = new TokenUtil();
			String unsign = token.Vertify(user);
			//将token转换成json格式
			JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
			//json以string格式输出
			int roleid = json.get("roleid").getAsInt();
			if(roleid==0||roleid==1){
				chain.doFilter(request, response);
			}else{
				res.sendRedirect("/BBS/Hello");
				return;
			}
			}catch(Exception e){
				e.printStackTrace();
				res.addHeader("expired", "密码过期请重新登录，谢谢！");
				res.sendRedirect("/BBS/Admin");
			}
	}
	
	public void destroy() {
	}

}

