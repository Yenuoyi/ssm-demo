package com.ye.controll;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatControll {
	@RequestMapping("/ChatView")
	public String Login(HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		return "Chat/Chat";
	}
}
