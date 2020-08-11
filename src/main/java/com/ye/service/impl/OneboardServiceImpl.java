package com.ye.service.impl;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ye.dao.OneboardMapper;
import com.ye.domain.OneboardBean;
import com.ye.redis.RedisOne;
import com.ye.service.OneboardService;

@Service @Transactional
public class OneboardServiceImpl implements OneboardService{
	@Autowired
	public OneboardMapper oneboardMapper;
	@Autowired
	private RedisOne redisOne;
	int result;
	
	//添加专业
	@Override
	public int addOneboard(OneboardBean oneboardBean)  throws Exception{
		result = oneboardMapper.addOneboard(oneboardBean);
		if(redisOne.isOne()){
			redisOne.deleteOne();
		}
		return result;
	}

	//删除专业
	@Override
	public int deleteOneboardById(int one_id) throws Exception {
		result = oneboardMapper.deleteOneboardById(one_id);
		if(redisOne.isOne()){
			redisOne.deleteOne();
		}
		return result;
	}

	//更新专业
	@Override
	public int updateOneboard(OneboardBean oneboard) throws Exception {
		result = oneboardMapper.updateOneboard(oneboard);
		if(redisOne.isOne()){
			redisOne.deleteOne();
		}
		return result;
	}

	//通过专业名获取专业
	@Override
	public List<OneboardBean> getOneboardBytitle(String one_title) throws Exception {
		List<OneboardBean> oneboard = oneboardMapper.getOneboardBytitle(one_title);
		return oneboard;
	}

	//获取所有的专业
	@Override
	public List<OneboardBean> getAllOneboard() throws Exception {
		if(redisOne.isOne()){
			return redisOne.getOne();
		}else{
			List<OneboardBean> oneboard = oneboardMapper.getAllOneboard();
			redisOne.setOne(oneboard);
			return oneboard;
		}
	}

	//通过专业id获取专业
	@Override
	public OneboardBean getOneboardByid(int one_id) throws Exception {
		OneboardBean oneboard = oneboardMapper.getOneboardByid(one_id);
		return oneboard;
	}
	
}

