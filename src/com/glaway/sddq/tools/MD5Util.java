package com.glaway.sddq.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * MD5加密处理工具类
 * 
 * @author zhuhao
 * @version [版本号, 2019年1月10日]
 * @since [产品/模块版本]
 */
public class MD5Util {

	/**
	 * 
	 * MD5加密
	 * 
	 * @param str
	 * @return [参数说明]
	 * 
	 */
	private static String getMD5Str(String str) {
		MessageDigest md;
		String encodeStr = "";

		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return encodeStr;
	}

	/**
	 * 
	 * 16位字符串
	 * 
	 * @param b
	 * @return [参数说明]
	 * 
	 */
	private static String byte2Hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String temp = null;
		for (int i = 0; i < b.length; i++) {
			temp = Integer.toHexString(b[i] & 0xFF);
			if (temp.length() == 1) {
				sb.append("0");
			}
			sb.append(temp);
		}
		return sb.toString();
	}
}
