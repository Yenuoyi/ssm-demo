package com.ye.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ye.domain.OneboardBean;
@Repository
public interface OneboardMapper {
	//添加专业
	int addOneboard(OneboardBean oneboard);
	//删除专业
	int deleteOneboardById(int one_id);
	//更新专业
	int updateOneboard(OneboardBean oneboard);
	//模糊查询专业
	List<OneboardBean> getOneboardBytitle(String one_title);
	//专业id查询专业
	OneboardBean getOneboardByid(int one_id);
	//查询所有专业
	List<OneboardBean> getAllOneboard();
}
