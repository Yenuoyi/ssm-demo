package com.ye.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ye.domain.TwoboardBean;
import com.ye.domain.UserBean;
import com.ye.service.OneboardService;
import com.ye.service.TwoboardService;
import com.ye.service.UserService;

@Controller
public class SearchControll {
	@Autowired
	private UserService userService;
	@Autowired
	private OneboardService oneboardService;
	@Autowired
	private TwoboardService twoboardService;
	
	@RequestMapping("Search")
	public ModelAndView Search(UserBean userbean,TwoboardBean twoboard){
		ModelAndView view = null;
		try{
			String two_title = twoboard.getTwo_title();
			view = new ModelAndView("twoboard_content");
			view.addObject("oneboard",oneboardService.getAllOneboard());
			//返回搜索的课程
			//添加专业课程总数
			view.addObject("one_two_total",1);
			view.addObject("one_two", twoboardService.getTwoboardtitle(two_title));
			if(twoboardService.getTwoboardtitle(twoboard.getTwo_title()).isEmpty()){
				//返回搜索的课程
				view.addObject("search_message", "无此课程，你可以联系老师添加哦！");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			view = new ModelAndView("500page");
		}
		return view;
	}

}
