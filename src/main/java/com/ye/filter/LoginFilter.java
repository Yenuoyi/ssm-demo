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

public class LoginFilter implements Filter {
	String paramValue = null;
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
		String uri = req.getRequestURI();
		String[] loginList = {"/BBS/css/","/BBS/js/","/BBS/login",
				"/BBS/Face","/BBS/FaceLogin","/BBS/Hello","/BBS/Welcome","/BBS/admin/adminlogin",
				"/BBS/AdminHello","/BBS/AdminLogin","/BBS/logout","BBS/Register/","BBS/Register/Code","/BBS/LostPassword","/BBS/SendPassword"};

        if (isContains(uri, loginList)) {// 对登录页面不进行过滤
            chain.doFilter(request, response);
            return;
        }
      //如果用户没有登录，存储当前访问的页面路径后跳转至登录页面、
		if(session.getAttribute("user")==null){
			res.sendRedirect("/BBS/Hello");
		}
		else{
		 //他的作用是将请求转发给过滤器链上下一个对象。这里的下一个指的是下一个filter，如果没有filter那就是你请求的资源。 一般filter都是一个链,web.xml 里面配置了几个就有几个。一个一个的连在一起 
         //request -> filter1 -> filter2 ->filter3 -> .... -> request resource.
			chain.doFilter(request, response);
		}
	}
	
	public void destroy() {
	}

}
