package com.huhuo.integration.cust;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.integration.config.GlobalConstant;

/**
 * 包装返回客户端的消息对象
 * @author wuyuxuan
 * @param <T>
 */
public class Message<T> {
	
	public enum Status {
		SUCCESS, FAILURE, ERROR
	}
	/**
	 * 时间戳
	 */
	private Date timestamp = new Date();
	/**
	 * 状态，成功|失败
	 */
	private Status status;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 附带的后台业务对象，可选，供客户端使用
	 */
	
	@Autowired
	private T data;
	
	public Message(){
		
	}
	
	public Message(Status status, String msg) {
		super();
		setStatus(status);
		this.msg = msg;
	}
	
	public Message(Status status, String msg, T data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONStringWithDateFormat(this, GlobalConstant.DateFormat.LONG_FORMAT);
	}

	public String toString(String dateFormat){
		return JSONObject.toJSONStringWithDateFormat(this, dateFormat);
	}
}
