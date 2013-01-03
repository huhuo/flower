package com.huhuo.integration.algorithm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import com.huhuo.integration.config.GlobalConstant.Encoding;

/**
 * MD5工具类
 * @author wuyuxuan
 */
public class MD5Utils {
	
	public static final String MD5 = "MD5";
	
	public static String defaultEncoding = Encoding.UTF8;
	
	/**
	 * 获取数据摘要信息
	 * 
	 * @param data 数据
	 * @return 32个字节的摘要信息
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encode(byte[] data){
		MessageDigest digest = MessageDigestFactory.getMessageDigest(MD5);
		return digest.digest(data);
	}
	
	/**
	 * @see #encodeMD5(byte[])
	 * 
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encode(String data, String charsetName) throws UnsupportedEncodingException{
		return encode(data.getBytes(charsetName));
	}
	/**
	 * @see #encode(String, String)
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] encode(String data) throws UnsupportedEncodingException{
		return encode(data, defaultEncoding);
	}
	
	/**
	 * 获取数据摘要信息
	 * 
	 * @param data
	 * @return 32个十六进制字符串的摘要信息
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeHex(byte[] data){
		byte[] md5data = encode(data);
		return String.valueOf(Hex.encodeHex(md5data));
	}
	
	/**
	 * @see #encodeMD5Hex(byte[])
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeHex(String data){
		return encodeHex(data.getBytes());
	}
}
