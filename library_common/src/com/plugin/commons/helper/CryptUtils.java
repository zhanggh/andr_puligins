package com.plugin.commons.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptUtils {

	
	public static String SHA1(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");     //选择SHA-1，也可以选择MD5
            byte[] digest = md.digest(inStr.getBytes());       //返回的是byet[]，要转化为String存储比较方便
            outStr = byte2hex(digest);
        }
        catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return outStr;
    }
    
    
    public static String bytetoString(byte[] digest) {
        String str = "";
        String tempStr = "";
        
        for (int i = 1; i < digest.length; i++) {
            tempStr = (Integer.toHexString(digest[i] & 0xff));
            if (tempStr.length() == 1) {
                str = str + "0" + tempStr;
            }
            else {
                str = str + tempStr;
            }
        }
        return str.toLowerCase();
    }
    
    /**
	    * 功能：转换成十六进制字符串
	    * 字节数组转16进制字符串
	    * b：要转16进制字符串的字节数组
	    */
	    public static String byte2hex(byte[] b) {
	       String hs="";
	       String stmp="";
	       for (int n=0;n<b.length;n++) {
	           stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
	           if (stmp.length()==1) hs=hs+"0"+stmp;
	           else hs=hs+stmp;
	        }
	       return hs.toLowerCase();

	    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
