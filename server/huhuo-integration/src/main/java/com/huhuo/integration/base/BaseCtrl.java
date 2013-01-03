package com.huhuo.integration.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.algorithm.HuhuoEncryptUtil;
import com.huhuo.integration.algorithm.GzipUtil;
import com.huhuo.integration.config.GlobalConstant;


public class BaseCtrl {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private String encoding = GlobalConstant.Encoding.UTF8;
	private String dateFormat = GlobalConstant.DateFormat.LONG_FORMAT;
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String parseReqParam(InputStream in, boolean isDecrypt) throws IOException{
		ByteArrayOutputStream param = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while((len=in.read(b)) != -1) {
			param.write(b, 0, len);
		}
		if(isDecrypt){
			b = HuhuoEncryptUtil.decrypt(param.toByteArray());
		}else{
			b = param.toByteArray();
		}
		return new String(b, encoding);
	}
	
	public String parseReqParam(InputStream in) throws IOException{
		return parseReqParam(in, false);
	}
	
	protected void write(InputStream data, OutputStream out) throws IOException{
		byte[] b = new byte[1024];
		int len = 0;
		while((len=data.read(b))!=-1){
			out.write(b, 0, len);
		}
	}
	
	protected void write(Object obj, OutputStream out) throws IOException{
		write(obj, out, false, false);
	}
	
	protected void write(Object obj, OutputStream out, boolean encrypted) throws IOException{
		write(obj, out, encrypted, false);
	}
	
	/**
	 * 将对象obj转化成json字符串后输出至客户端
	 * @param obj 输出结果
	 * @param out
	 * @param encrypted 输出结果是否需要加密
	 * @param compressed 输出结果是否需要压缩
	 * @throws IOException
	 */
	protected void write(Object obj, OutputStream out, boolean encrypted, boolean compressed) throws IOException{
		String content = null;
		if(obj instanceof String) {
			content = String.valueOf(obj);
		} else {
			content = JSONObject.toJSONStringWithDateFormat(obj, dateFormat);
		}
		
		byte[] response = content.getBytes(encoding);
		if(compressed) {
			response = GzipUtil.compress(response);
		}
		if(encrypted) {
			response = HuhuoEncryptUtil.decrypt(response);
		}
		
		out.write(response);
	}

}
