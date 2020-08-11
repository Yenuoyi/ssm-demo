package com.ye.emailstatistics;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.ye.dao.StatisticsMapper;
import com.ye.domain.CommentBean;
import com.ye.domain.ThreeboardBean;
import com.ye.domain.TwoboardBean;
import com.ye.domain.UserBean;

public class SendEmailStatis {
	@Autowired
	private StatisticsMapper statisticsMapper;
//	@Scheduled(cron = "0/2 * * * * *")
//	注解方式启动定时器
	public void autoSmsTime() throws GeneralSecurityException, IOException {
		int commentCount = statisticsMapper.commentCount();
		List<TwoboardBean> two = statisticsMapper.twoCount();
		List<ThreeboardBean> three = statisticsMapper.threeCount();
		List<CommentBean> comment = statisticsMapper.teacherCountByCount();
		List<Integer> userid = new ArrayList<Integer>();
		for(int i=0;i<comment.size();i++){
			userid.add(comment.get(i).getComment_name_id());
		}
		List<UserBean> user = statisticsMapper.teacherCountBDetail(userid);
		SendEmail send = new SendEmail();
		send.send("19689405@qq.com",commentCount,two,three,comment,user);
	}

}
