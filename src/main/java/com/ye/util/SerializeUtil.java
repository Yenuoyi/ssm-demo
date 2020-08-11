package com.ye.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.log4j.Logger;

public class SerializeUtil {
	 static Logger log = Logger.getLogger(SerializeUtil.class);  
	    /** 
	     *  
	     * <p>Title: ObjTOSerialize</p> 
	     * <p>Description: 序列化一个对象</p> 
	     * @param obj 
	     * @return 
	     * @author yebing
	     */  
	    public static byte[] ObjTOSerialize(Object obj){  
	        ObjectOutputStream oos = null;  
	        ByteArrayOutputStream byteOut = null;  
	        try{  
	            byteOut = new ByteArrayOutputStream();  
	            oos = new ObjectOutputStream(byteOut);  
	            oos.writeObject(obj);  
	            byte[] bytes = byteOut.toByteArray();  
	            return bytes;  
	        }catch (Exception e) {  
	            log.error("对象序列化失败");  
	        }  
	        return null;  
	    }  
	    /** 
	     *  
	     * <p>Title: unSerialize</p> 
	     * <p>Description: 反序列化</p> 
	     * @param bytes 
	     * @return 
	     * @author yebing
	     */  
	    public static Object unSerialize(byte[] bytes){  
	        ByteArrayInputStream in = null;  
	        try{  
	            in = new ByteArrayInputStream(bytes);  
	            ObjectInputStream objIn = new ObjectInputStream(in);  
	            return objIn.readObject();  
	        }catch (Exception e) {  
	            log.error("反序列化失败");  
	        }  
	        return null;  
	    }  
}