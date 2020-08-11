package com.ye.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ye.domain.TwoboardBean;
import com.ye.service.OneboardService;
import com.ye.service.ThreeboardService;
import com.ye.service.TwoboardService;
import com.ye.service.UserService;


@Controller
public class ThreeboardControll {
	@Autowired
    private UserService userService;
	@Autowired
    private OneboardService oneboardService;
	@Autowired
    private TwoboardService twoboardService;
	@Autowired
    private ThreeboardService threeboardService;

	@RequestMapping("/Threeboard_content")
	public ModelAndView Twoboard_content(TwoboardBean two) throws Exception{
		ModelAndView  view;
		try{
			view = new ModelAndView("threeboard_content");
			//根据课程id查找专业
			TwoboardBean twoo = twoboardService.getTwoboardtwo_id(two.getTwo_id());
			int one_id = twoo.getTwo_one_id();
			//添加所有专业
			view.addObject("oneboard",oneboardService.getAllOneboard());
			//添加专业属性
			view.addObject("one",oneboardService.getOneboardByid(one_id));
			//添加该课程属性
			view.addObject("two",twoboardService.getTwoboardtwo_id(two.getTwo_id()));
			//添加课程热门章节
			view.addObject("topthree", threeboardService.getThreeboardTop_Bytwoid(two.getTwo_id()));
			//添加所属课程的章节
			view.addObject("two_three", threeboardService.getThreeboard_Bytwoid(two.getTwo_id()));
		}catch(Exception e){
			e.printStackTrace();
			view = new ModelAndView("500page");
		}
		return view;
	}
}
