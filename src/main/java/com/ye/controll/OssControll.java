package com.ye.controll;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ye.service.OSSService;
import com.ye.service.OneboardService;
import com.ye.service.UserService;

@Controller
public class OssControll {
	@Autowired
    private UserService userService;
	@Autowired
    private OneboardService oneboardService;
	@Autowired
	public OSSService ossService;
    //访问上传文件页面
    @RequestMapping("/Admin/Upload")
    public String upload(Model model,HttpServletRequest request){
    	
    	model.addAttribute("folder", ossService.getUploadDirectory());
    	return "admin/upload";
    }
	//管理员上传文件
	@RequestMapping(value="/Admin/Uploadfile",method = RequestMethod.POST)
    public String uploadfile(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		if(ossService.uploadFile(request)==1){
	        request.setAttribute("message", "文件上传成功！");
	        model.addAttribute("folder", ossService.getUploadDirectory());
	    	return "admin/upload";
		}else{
			request.setAttribute("message", "文件上传失败，文件名不能为空！！");
			model.addAttribute("folder", ossService.getUploadDirectory());
	    	return "admin/upload";
		}
    }
	//访问下载时文件目录
	@RequestMapping("/Download")
    public String download(Model model,HttpServletRequest request) throws Exception{
		String getKey = request.getParameter("board");
        model.addAttribute("oneboard",oneboardService.getAllOneboard());
        model.addAttribute("document", ossService.getDownDirectory(getKey).get(0));
    	model.addAttribute("board", ossService.getDownDirectory(getKey).get(1));
    	return "downprofessionview";
    }
	//下载文件
    @RequestMapping("/DownloadFile")
    public void DownloadFile(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
    	String getKey = request.getParameter("document");
    	ossService.downFile(getKey, response);
    }
	//访问视频页面
	@RequestMapping("/Video")
	public ModelAndView Video(HttpServletRequest request) throws Exception{
		ModelAndView  model= new ModelAndView("videomenu");
		String key = request.getParameter("board");
		//添加所有专业
		model.addObject("oneboard", oneboardService.getAllOneboard());
		model.addObject("document", ossService.getVideoURL(key).get(0));
    	model.addObject("board", ossService.getVideoURL(key).get(1));
		return model;
	}
	//访问上传头像页面
	@RequestMapping("/uploadAvtarView")
	public ModelAndView uploadAvtarView(HttpServletResponse response) throws Exception{
		ModelAndView  view= new ModelAndView("uploadavtar");
		//添加所有专业
		view.addObject("oneboard", oneboardService.getAllOneboard());
		response.setHeader("Cache-Control", "no-cache");
		return view;
	}
	
	//访问上传证件照页面
	@RequestMapping("/uploadFaceView")
	public ModelAndView uploadFaceView() throws Exception{
		ModelAndView  view= new ModelAndView("face");
		//添加所有专业
		view.addObject("oneboard", oneboardService.getAllOneboard());
		return view;
	}
	//上传头像接口
	@RequestMapping(value="/UploadAvtar",method = RequestMethod.POST)
	@ResponseBody
    public int uploadAvtar(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		int result;
		//获取session中用户信息
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String headpicurl = session.getAttribute("headpicurl").toString();
		String email = session.getAttribute("email").toString();
		if(ossService.uploadAvtar(request, email)==1){
			result = 1;
		}else{
			return 0;
		}
		if(headpicurl.equals("http://yebingavtar.oss-cn-shenzhen.aliyuncs.com/Avtar/avtar.png")){
			headpicurl = "http://yebingavtar.oss-cn-shenzhen.aliyuncs.com/Avtar/"+email+".jpg";
			userService.updateUser_headpicurl(headpicurl, email);
			
		}
    	return result;
	}
}
