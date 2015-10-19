package com.kongs.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.FileCopyUtils;


/**
 * @description  
 *
 * @date 2014-11-20 下午08:01:57
 *
 * @author 崔红涛
 *
 */
public class ImageHttpMessageConveter extends AbstractHttpMessageConverter<File>{
	
	/** Creates a new instance of the {@code ByteArrayHttpMessageConverter}. */
	public ImageHttpMessageConveter() {
		super(new MediaType("image", "*"));
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return File.class.equals(clazz);
	}

	@Override
	public File readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException {
		return null;
	}

	@Override
	protected Long getContentLength(File file, MediaType contentType) {
		return (long) file.length();
	}

	@Override
	protected void writeInternal(File file, HttpOutputMessage outputMessage) throws IOException {
		byte [] bytes=FileCopyUtils.copyToByteArray(new FileInputStream(file));
		FileCopyUtils.copy(bytes, outputMessage.getBody());
	}
}
