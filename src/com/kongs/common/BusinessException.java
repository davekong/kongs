package com.kongs.common;

/**
 * @description  作用：包装检查型异常
 *
 * @date 2014-5-28 下午04:16:54
 *
 * @author 崔红涛
 *
 */
public class BusinessException extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
