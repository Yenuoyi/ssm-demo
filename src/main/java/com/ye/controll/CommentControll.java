package com.ye.controll;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ye.domain.CommentBean;
import com.ye.domain.ReplyBean;
import com.ye.service.CommentService;

@Controller
public class CommentControll {
	@Autowired
    private CommentService commentService;

	@RequestMapping(value="/Comment",method = RequestMethod.POST)
	@ResponseBody
	public int comment(@RequestBody CommentBean commentbean,HttpServletRequest request) throws Exception{
		int result = commentService.addComment(commentbean,request);
		return result;
	}
	
	@RequestMapping(value="/Reply",method = RequestMethod.POST)
	@ResponseBody
	public int reply(@RequestBody ReplyBean replybean,HttpServletRequest request) throws Exception{
		int result = commentService.addReply(replybean, request);
		return result;
	}
}
