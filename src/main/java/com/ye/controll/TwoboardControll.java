package com.ye.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ye.domain.OneboardBean;
import com.ye.service.OneboardService;
import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;
import com.ye.service.UserService;

@Controller
public class TwoboardControll {
	@Autowired
    private UserService userService;
	@Autowired
    private OneboardService oneboardService;
	@Autowired
    private TwoboardService twoboardService;
	@Autowired
    private ThreeboardService threeboardService;

	@RequestMapping(value="/Twoboard_content",produces="text/html;charset=UTF-8;")
	public ModelAndView Twoboard_content(OneboardBean one) throws Exception{
		ModelAndView  view;
		try{
			view = new ModelAndView("twoboard_content");
			//添加所有专业
			view.addObject("oneboard",oneboardService.getAllOneboard());
			//添加所属专业的课程
			view.addObject("one_two",twoboardService.getTwoboardone_id(one.getOne_id()));
			//添加专业属性
			view.addObject("one",oneboardService.getOneboardByid(one.getOne_id()));
			//添加专业课程总数
			view.addObject("one_two_total",twoboardService.getTwoIdTotalByOneId(one.getOne_id()));
		}catch(Exception e){
			view = new ModelAndView("500page");
		}
		return view;
	}
	@RequestMapping(value="/TwoboardPage",produces="text/html;charset=UTF-8;")
	@ResponseBody
	public String getTwoboardPage(@RequestBody String pagination){
		JSONObject object = JSONObject.parseObject(pagination);
		int twoOneId = object.getIntValue("twoOneId");
		int curpage = object.getIntValue("curpage");
		int start = curpage*10-10;
		int end = 10;
		return twoboardService.getTwoboardPage(twoOneId, start, end);
	}
}
