package com.kongs.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResponseVO<T> {
	private Boolean success;//�Ƿ�ִ�гɹ�;
	private String message ;//��ʾ��Ϣ��
	private T data = null;//���
	
	public ResponseVO(){};
	/**
	 * ���캯��
	 * @param success �Ƿ�ִ�гɹ�
	 * @param msg ��ʾ��Ϣ
	 */
	public ResponseVO(Boolean success,String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * ���캯��
	 * @param success �Ƿ�ִ�гɹ�
	 * @param msg ��ʾ��Ϣ
	 * @param data ���ص����
	 */
	public ResponseVO(Boolean success,String message,T data) {
		this.success = success;
		this.message= message;
		this.data=data;
	}
	public static ResponseVO<String> createDefalutFailureResponseVO(){
		return new ResponseVO<String>(false,"500,failure!");
	}
	
	public static ResponseVO<String> createDefalutSuccessResponseVO(){
		return new ResponseVO<String>(true,"200,success!");
	}
	public  String toString(){
		return  JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

