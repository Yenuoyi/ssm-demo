package com.ye.ai;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.baidu.aip.util.Base64Util;

public class GetOssAvtar {
<<<<<<< .merge_file_a18676
	private static String endpoint = "*****";
=======
	private static String endpoint = "http";
>>>>>>> .merge_file_a17620
    private static String accessKeyId = "*****";
    private static String accessKeySecret = "****";
    private static String bucketName = "*****";
    private static GetOssAvtar getOssAvtar = null;
    private GetOssAvtar(){
    }
    public static GetOssAvtar getInstance(){
    	if(getOssAvtar==null){
    		getOssAvtar = new GetOssAvtar();
    	}
    	return getOssAvtar;
    }
	//下载文件
    public String downFile(String getKey) throws IOException {
    	getKey = "Face/"+getKey+".jpg";
    	String result = null;
    	InputStream in = null;
    	ByteArrayOutputStream output = null;
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret); 
    	try {
    		
    		//获得图片
    		OSSObject object = ossClient.getObject(new GetObjectRequest(bucketName, getKey));
    		//字节数组流，用于转换byte数组
    		output = new ByteArrayOutputStream();
    		in = object.getObjectContent();
    		int b;
        	while((b = in.read()) != -1){
        		output.write(b);
        	}
    		result = Base64Util.encode(output.toByteArray());
	        } catch (OSSException oe) {
	            System.out.println("Caught an OSSException, which means your request made it to OSS, "
	                    + "but was rejected with an error response for some reason.");
	            System.out.println("Error Message: " + oe.getErrorCode());
	            System.out.println("Error Code:       " + oe.getErrorCode());
	            System.out.println("Request ID:      " + oe.getRequestId());
	            System.out.println("Host ID:           " + oe.getHostId());
	        } catch (ClientException ce) {
	            System.out.println("Caught an ClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with OSS, "
	                    + "such as not being able to access the network.");
	            System.out.println("Error Message: " + ce.getMessage());
	        } finally {
	        	if(output!=null){
	        		output.close();
	        	}if(in!=null){
	        		in.close();
	        	}
	            ossClient.shutdown();
	        }
    	return result;
		}
}
