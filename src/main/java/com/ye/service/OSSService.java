package com.ye.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OSSService {
	//访问文件上传目录
	List<Object> getUploadDirectory();
	//上传文件和头像
	int uploadFile(HttpServletRequest request) throws Exception;
	//获取下载文件目录
	List<List<Object>> getDownDirectory(String getKey);
	//下载文件
	void downFile(String getKey, HttpServletResponse response);
	//访问视频
	List<List<Object>> getVideoURL(String key);
	//上传头像接口
	int uploadAvtar(HttpServletRequest request,String email) throws Exception;
}
