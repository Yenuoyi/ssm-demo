package com.ye.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import com.ye.service.OSSService;

@Service
public class OSSServiceImpl implements OSSService{
    private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    private static String accessKeyId = "*************";
    private static String accessKeySecret = "**************";
    private static String bucketName1 = "yebing";
    private static String bucketName2 = "yebingavtar";
    
    //访问文件上传目录
    @Override
	public
  	List<Object> getUploadDirectory(){
        String key = "文件资源/";
    	List<Object> folder = new LinkedList<Object>();
    	//设置超时时间以及重新连接次数
    	ClientConfiguration conf = new ClientConfiguration();
    	conf.setConnectionTimeout(5000);
    	conf.setMaxErrorRetry(3);
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret); 
        try {
            if (!ossClient.doesBucketExist(bucketName1)) {
//                System.out.println("Creating bucket " + bucketName1 + "\n");
                ossClient.createBucket(bucketName1);
                CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName1);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
         // 构造ListObjectsRequest请求
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName1);
            // "/" 为文件夹的分隔符
            listObjectsRequest.setDelimiter("/");
            // 列出fun目录下的所有文件和文件夹
            listObjectsRequest.setPrefix(key);
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
            // 遍历所有CommonPrefix
//            System.out.println("\nCommonPrefixs:");
            for (String commonPrefix : listing.getCommonPrefixes()) {
//                System.out.println(commonPrefix);
                folder.add(commonPrefix);
            }   
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
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            ossClient.shutdown();
        }
        return folder;
  	}
	//上传文件
	@Override
	public int uploadFile(HttpServletRequest request) throws Exception{
		int over = 1;
		//上传目的地
		String tokey = null;
		//接收前端表单数据
        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        List<FileItem> items = upload.parseRequest(request);
        Iterator<FileItem> iterator = items.iterator();
        //System.out.println("前端文件接收完毕！");
        while(iterator.hasNext()){
        	FileItem item = (FileItem) iterator.next();
        	if(item.isFormField()){//表单的参数字段
        		tokey = item.getString("UTF-8");
            }else {
        		if(item.getName()!=null&&!item.getName().equals("")){
        			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            			tokey = tokey.concat(item.getName());
            			//文件小于0.5M不需要分片，直接上传返回结果集
            			if(item.getSize()<(512 * 1024L)){
            				ossClient.putObject(bucketName1, tokey, item.getInputStream());
            				ossClient.shutdown();
            				return 1;
            			}
            			//从网页表单中获取的文件流
            			FileInputStream fileIn = null;
            			//文件流的渠道
            			FileChannel fileChannel = null;
        			try {
        				final long partSize = 512 * 1024L;   // 0.5MB
        				long objectSize =item.getSize();
        				//分片上传
        				InitiateMultipartUploadRequest req = new InitiateMultipartUploadRequest(bucketName1, tokey);
        				ObjectMetadata object = new ObjectMetadata();
        				object.setContentType(item.getContentType());
        				req.setObjectMetadata(object);
        				InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(req);
        				String uploadId = result.getUploadId();
        				//上传分片开始
        				//判断文件分几片
        				int partCount = (int) (objectSize / partSize);
        	            if (objectSize % partSize != 0) {
        	                partCount++;
        	            }
        	            if (partCount > 10000) {
        	                throw new RuntimeException("Total parts count should not exceed 10000");
        	            } else {                
        	                System.out.println("Total parts count " + partCount + "\n");
        	            }
        	            //用于存储文件流转换成字节数组的list
        	            List<byte[]> list =new ArrayList<byte[]>();
        	            //获得文件流
        	            fileIn = (FileInputStream)item.getInputStream();
        	            fileChannel = fileIn.getChannel();
        	            ByteBuffer buffer = ByteBuffer.allocate((int)partSize);
        	         // 读取数据到缓冲区  
        	            int x = fileChannel.read(buffer);
        	            while(x!=-1){
            	            // 重设buffer，将limit设置为position，position设置为0  
            	            buffer.flip();
        	            	int y = buffer.remaining();
        	                byte[] b = new byte[y];
        	                buffer.get(b);
        	                list.add(b);
        	            	buffer.clear();
        	            	x = fileChannel.read(buffer);
        	            }
        	            //分片存储过程
        				List<PartETag> partETags = new ArrayList<PartETag>();
        				//使用线程池上传分片
    	                ExecutorService executors ;
    	                if(partCount>5){
    	                	executors =Executors.newFixedThreadPool(8);
    	                }else{
    	                	executors = Executors.newCachedThreadPool();
    	                }
    	                CountDownLatch countDownLatch = new CountDownLatch(partCount);
    	                //开始分片
        				for (int i = 0; i < partCount; i++) {
        					long startPos = i * partSize;
        					long curPartSize;
        					if(partCount!=1){
        						 curPartSize = (i+1 == partCount) ? (objectSize - startPos) : partSize;
        					}else{
            	                curPartSize = objectSize;
        					}
        					//将字节数组转换成字节流
        					InputStream instream = new ByteArrayInputStream(list.get(i));
                			// 设置分片号，范围是1~10000，
            				UploadPartRequest uploadPartRequest = new UploadPartRequest();
            				uploadPartRequest.setBucketName(bucketName1);
            				uploadPartRequest.setKey(tokey);
                			uploadPartRequest.setPartNumber(i+1);
                			executors.execute(new Runnable(){
								@Override
								public void run() {
									try{
		            				uploadPartRequest.setUploadId(uploadId);
		            				uploadPartRequest.setInputStream(instream);
		        					uploadPartRequest.setPartSize(curPartSize);
									UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
									//上锁，保证线程安全
									synchronized(partETags){
										partETags.add(uploadPartResult.getPartETag());
									}
									countDownLatch.countDown();
									}catch(ClientException ce){
										ce.printStackTrace();
									}finally{
										try {
											instream.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}
                			});	
        				}
        				//等待子线程结束
        				countDownLatch.await();
//        				System.out.println("上传完成！");
        				//关闭线程池，表示不再接受线程
        				executors.shutdown();
        				//完成分片上传，开始整合整个文件
        				Collections.sort(partETags, new Comparator<PartETag>() {
        				    @Override
        				    public int compare(PartETag p1, PartETag p2) {
        				        return p1.getPartNumber() - p2.getPartNumber();
        				    }
        				});
        				CompleteMultipartUploadRequest completeMultipartUploadRequest = 
        				        new CompleteMultipartUploadRequest(bucketName1, tokey, uploadId, partETags);
        				ossClient.completeMultipartUpload(completeMultipartUploadRequest);
        	            
        	        } catch (OSSException oe) {
        	        	over = 0;
        	            System.out.println("Error Message: " + oe.getErrorCode());
        	            System.out.println("Error Code:       " + oe.getErrorCode());
        	            System.out.println("Request ID:      " + oe.getRequestId());
        	            System.out.println("Host ID:           " + oe.getHostId());
        	        } catch (ClientException ce) {
        	        	over = 0;
        	            System.out.println("Error Message: " + ce.getMessage());
        	            System.out.println("Error Code: " + ce.getErrorCode());
        	        } finally {
        	        	if(fileIn!=null){
        	        		fileIn.close();
        	        	}
        	        	if(fileChannel!=null){
        	        		fileChannel.close();
        	        	}
        	        	ossClient.shutdown();
        	        }
        		}else{
        			over = 0;
        		}
        	}
        }
		return over;
	}

	//获取下载文件目录
	public List<List<Object>> getDownDirectory(String getKey){
		List<List<Object>> list = new ArrayList<List<Object>>(2);
		List<Object> board = new LinkedList<Object>();
    	List<Object> document = new LinkedList<Object>();
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret); 
        try {
            if (!ossClient.doesBucketExist(bucketName1)) {
//                System.out.println("Creating bucket " + bucketName1 + "\n");
                ossClient.createBucket(bucketName1);
                CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName1);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
         // 构造ListObjectsRequest请求
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName1);
            // "/" 为文件夹的分隔符
            listObjectsRequest.setDelimiter("/");
            // 列出fun目录下的所有文件和文件夹
            listObjectsRequest.setPrefix(getKey);
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
            // 遍历所有Object
//            System.out.println("Objects:");
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                document.add(objectSummary.getKey());
            }
            list.add(document);
            // 遍历所有CommonPrefix
//            System.out.println("\nCommonPrefixs:");
            for (String commonPrefix : listing.getCommonPrefixes()) {
//                System.out.println(commonPrefix);
                board.add(commonPrefix);
            }
            list.add(board);
            
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
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            ossClient.shutdown();
        }
        return list;
	}
	//下载文件
	@Override
	public void downFile(String getKey, HttpServletResponse response) {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret); 
        try {
            /*
             * Download an object from your bucket
             */
//            System.out.println("Downloading an object");
            OSSObject object = ossClient.getObject(new GetObjectRequest(bucketName1, getKey));
            response.setContentType("multipart/form-data");
          //转码，免得文件名中文乱码  
            String[] result = getKey.split("/");
            getKey = result[result.length-1];
            try {
            	getKey = URLEncoder.encode(getKey,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}  
            response.addHeader("Content-Disposition", "attachment;filename=" + getKey);  
            InputStream in = null;
            ServletOutputStream out  = null;
            try{
            	in = object.getObjectContent();
            	out = response.getOutputStream();
            	int b;
            	while((b = in.read()) != -1){
            		out.write(b);
            	}
            	out.flush();
            }catch(IOException e){
            	e.printStackTrace();
            }finally{
            	try {
            		if(in!=null){
            			in.close();
            		}
					if(out!=null){
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }  
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
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            ossClient.shutdown();
        }
	}
	//访问视频
	public List<List<Object>> getVideoURL(String key){
		List<List<Object>> list = new ArrayList<List<Object>>(2);
		List<Object> board = new LinkedList<Object>();
    	List<Object> document = new LinkedList<Object>();
//		String key = "video/";
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret); 
        try {
            if (!ossClient.doesBucketExist(bucketName2)) {
                System.out.println("Creating bucket " + bucketName2 + "\n");
                ossClient.createBucket(bucketName2);
                CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName2);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
         // 构造ListObjectsRequest请求
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName2);
            // "/" 为文件夹的分隔符
            listObjectsRequest.setDelimiter("/");
         // 列出fun目录下的所有文件和文件夹
            listObjectsRequest.setPrefix(key);
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
            // 遍历所有Object
//            System.out.println("Objects:");
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                document.add(objectSummary.getKey());
//                System.out.println(objectSummary.getKey());
            }
            if(!document.isEmpty()){
            	document.remove(0);
            }
            list.add(document);
            // 遍历所有CommonPrefix
//            System.out.println("\nCommonPrefixs:");
            for (String commonPrefix : listing.getCommonPrefixes()) {
//                System.out.println(commonPrefix);
                board.add(commonPrefix);
            }
            list.add(board);
            
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
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            ossClient.shutdown();
        }
        return list;
    }
	//上传头像接口
	public int uploadAvtar(HttpServletRequest request,String email) throws Exception{
		int over = 1;
		String tokey = null;
		//接收前端表单数据
		//1、创建一个DiskFileItemFactory工厂
        DiskFileItemFactory fac = new DiskFileItemFactory();
        //2、创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(fac);
        //3、解决上传文件名的中文乱码
        upload.setHeaderEncoding("UTF-8"); 
        List<FileItem> items = upload.parseRequest(request);
        Iterator<FileItem> iterator = items.iterator();
        while(iterator.hasNext()){
        	FileItem item = (FileItem) iterator.next();
        	if(item.isFormField()){ // 表单的参数字段
                tokey = item.getString("UTF-8");
            }else {
            	//头像命名
            	tokey = tokey.concat(email+".jpg");
            	//文件大于2M不给上传
        		if(item.getSize()<(2*1024*1024)){
        			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        			//文件小于0.5M不需要分片，直接上传
        			if(item.getSize()<(512 * 1024L)){
        				ossClient.putObject(bucketName2, tokey, item.getInputStream());
        				ossClient.shutdown();
        				return 1;
        			}
        			//从网页表单中获取的文件流
        			FileInputStream fileIn = null;
        			//文件流的渠道
        			FileChannel fileChannel = null;
    			try {
    				final long partSize = 512 * 1024L;   // 0.5MB
    				long objectSize =item.getSize();	
    				//分片上传初始化
    				InitiateMultipartUploadRequest req = new InitiateMultipartUploadRequest(bucketName2, tokey);
    				//设置上传文件属性
    				ObjectMetadata object = new ObjectMetadata();
    				object.setContentType(item.getContentType());
    				req.setObjectMetadata(object);
    				InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(req);
    				String uploadId = result.getUploadId();
    				//上传分片开始
    				//判断文件分几片
    				int partCount = (int) (objectSize / partSize);
    	            if (objectSize % partSize != 0) {
    	                partCount++;
    	            }
    	            if (partCount > 10000) {
    	                throw new RuntimeException("Total parts count should not exceed 10000");
    	            } else {                
//    	                System.out.println("Total parts count " + partCount + "\n");
    	            }
    	            //用于存储文件流转换成字节数组的list
    	            List<byte[]> list =new ArrayList<byte[]>();
    	            //获得文件流
    	            fileIn = (FileInputStream)item.getInputStream();
    	            fileChannel = fileIn.getChannel();
    	            ByteBuffer buffer = ByteBuffer.allocate((int)partSize);
    	         // 读取数据到缓冲区  
    	            int x = fileChannel.read(buffer);
    	            while(x!=-1){
        	            // 重设buffer，将limit设置为position，position设置为0  
        	            buffer.flip();
    	            	int y = buffer.remaining();
    	                byte[] b = new byte[y];
    	                buffer.get(b);
    	                list.add(b);
    	            	buffer.clear();
    	            	x = fileChannel.read(buffer);
    	            }
//    	            System.out.println(list.size());
    	            //分片存储过程
    				List<PartETag> partETags = new ArrayList<PartETag>();
    				//使用线程池上传分片
	                ExecutorService executors ;
	                if(partCount>5){
	                	executors =Executors.newFixedThreadPool(8);
	                }else{
	                	executors = Executors.newCachedThreadPool();
	                }
	                CountDownLatch countDownLatch = new CountDownLatch(partCount);
	                //开始分片
    				for (int i = 0; i < partCount; i++) {
    					long startPos = i * partSize;
    					long curPartSize;
    					if(partCount!=1){
    						 curPartSize = (i+1 == partCount) ? (objectSize - startPos) : partSize;
    					}else{
        	                curPartSize = objectSize;
    					}
    					//将字节数组转换成字节流
    					InputStream instream = new ByteArrayInputStream(list.get(i));
            			// 设置分片号，范围是1~10000，
        				UploadPartRequest uploadPartRequest = new UploadPartRequest();
        				uploadPartRequest.setBucketName(bucketName2);
        				uploadPartRequest.setKey(tokey);
            			uploadPartRequest.setPartNumber(i+1);
            			executors.execute(new Runnable(){
							@Override
							public void run() {
								try{
	            				uploadPartRequest.setUploadId(uploadId);
	            				uploadPartRequest.setInputStream(instream);
	        					uploadPartRequest.setPartSize(curPartSize);
								UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
								//上锁
								synchronized(partETags){
									partETags.add(uploadPartResult.getPartETag());
								}
								countDownLatch.countDown();
								}catch(ClientException ce){
									ce.printStackTrace();
								}finally{
									try {
										instream.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
            			});	
    				}
    				//等待子线程结束
    				countDownLatch.await();
//    				System.out.println("上传完成！");
    				//关闭线程池，表示不再接受线程
    				executors.shutdown();
    				//完成分片上传，开始整合整个文件
    				Collections.sort(partETags, new Comparator<PartETag>() {
    				    @Override
    				    public int compare(PartETag p1, PartETag p2) {
    				        return p1.getPartNumber() - p2.getPartNumber();
    				    }
    				});
    				CompleteMultipartUploadRequest completeMultipartUploadRequest = 
    				        new CompleteMultipartUploadRequest(bucketName2, tokey, uploadId, partETags);
    				ossClient.completeMultipartUpload(completeMultipartUploadRequest);
    	            
    	        } catch (OSSException oe) {
    	        	over = 0;
    	            System.out.println("Error Message: " + oe.getErrorCode());
    	            System.out.println("Error Code:       " + oe.getErrorCode());
    	            System.out.println("Request ID:      " + oe.getRequestId());
    	            System.out.println("Host ID:           " + oe.getHostId());
    	        } catch (ClientException ce) {
    	        	over = 0;
    	            System.out.println("Error Message: " + ce.getMessage());
    	            System.out.println("Error Code: " + ce.getErrorCode());
    	        } finally {
    	        	if(fileChannel!=null){
    	        		fileChannel.close();
    	        	}
    	        	if(fileIn!=null){
    	        		fileIn.close();
    	        	}
    	        	ossClient.shutdown();
    	        }
        		}else{
        			over = 0;
        		}
        		
        	}
        }
    	return over;
    }
}
