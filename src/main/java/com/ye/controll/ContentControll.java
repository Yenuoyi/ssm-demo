package com.ye.controll;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ye.domain.OneboardBean;
import com.ye.domain.ThreeboardBean;
import com.ye.domain.TwoboardBean;
import com.ye.domain.UserBean;
import com.ye.service.CommentService;
import com.ye.service.OneboardService;
import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;
import com.ye.service.UserService;

@Controller
public class ContentControll {
	@Autowired
    private UserService userService;
	@Autowired
    private OneboardService oneboardService;
	@Autowired
    private TwoboardService twoboardService;
	@Autowired
    private ThreeboardService threeboardService;
	@Autowired
    private CommentService commentService;

	@RequestMapping("/Content")
	public ModelAndView Twoboard_content(UserBean userbean,OneboardBean one,TwoboardBean two,ThreeboardBean three) throws Exception{
		ModelAndView  view;
		try{
			two = twoboardService.getTwoboardtwo_id(two.getTwo_id());
			if(one.getOne_id()==0){
				one = oneboardService.getOneboardByid(two.getTwo_one_id());
			}
			view = new ModelAndView("content_comment");
			//添加所有专业
			view.addObject("oneboard",oneboardService.getAllOneboard());
			//添加专业属性
			view.addObject("one",oneboardService.getOneboardByid(one.getOne_id()));
			//添加该课程属性
			view.addObject("two",two);
			//添加所属课程的章节
			view.addObject("two_three", threeboardService.getThreeboard_Bytwoid(two.getTwo_id()));
			//添加该章节属性
			view .addObject("three", threeboardService.getThreeboardthree_id(three.getThree_id()));
			//添加该章节的评论
			view .addObject("three_content", commentService.getComment(three.getThree_id(),0,10));
			//添加该章节评论总数
			view.addObject("threeContentTotal",commentService.getTotal(three.getThree_id()));
		}catch(Exception e){
			e.printStackTrace();
			view = new ModelAndView("500page");
		}
		return view;
	}
	@RequestMapping(value="/ContentPagination",produces="text/html;charset=UTF-8;")
	@ResponseBody
	public String contentPagination(@RequestBody String pagination, HttpServletRequest request) throws Exception{
		JSONObject object = JSONObject.parseObject(pagination);
		int comment_three_id = object.getIntValue("comment_three_id");
		int curpage = object.getIntValue("curpage");
		int start = curpage*10-10;
		int end = 10;
		String result = commentService.getComment(comment_three_id,start,end);
		return result;
	}
}