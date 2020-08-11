package com.ye.ai;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

public class FaceRecognition {
	public static int face(String imgstr, String imgStr2) throws IOException{
		int result = 0;
		String ossimgStr2 = GetOssAvtar.getInstance().downFile(imgStr2);
		if(ossimgStr2==null||ossimgStr2.equals("")){
			return 0;
		}
		String face = FaceMatch.match(imgstr, ossimgStr2);
		if(face!=null){
			JSONObject jsonObject = JSONObject.parseObject(face);
			double score = 0.0;
			if(!jsonObject.getString("result").equals("[]")){
				score = jsonObject.getJSONArray("result").getJSONObject(0).getDouble("score");
			}
			if(score>80){
				result = 1;
			}
		}
		return result;
	}
}
