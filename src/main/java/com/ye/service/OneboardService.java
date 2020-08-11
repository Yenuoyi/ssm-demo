package com.ye.service;

import java.util.List;

import com.ye.domain.OneboardBean;

public interface OneboardService {

	//添加专业
	int addOneboard(OneboardBean oneboard) throws Exception;
	//删除专业
	int deleteOneboardById(int one_id) throws Exception;
	//更新专业
	int updateOneboard(OneboardBean oneboard) throws Exception;
	//专业名模糊查询专业
	List<OneboardBean> getOneboardBytitle(String one_title) throws Exception;
	//专业ID查询专业
	OneboardBean getOneboardByid(int one_id) throws Exception;
	//查询所有专业
	List<OneboardBean> getAllOneboard() throws Exception;
}
