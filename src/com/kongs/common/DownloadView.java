package com.kongs.common;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import com.kongs.common.util.RandomUtil;

/**
 * @description  
 *
 * @date 2014-11-21 上午10:22:39
 *
 * @author 崔红涛
 *
 */
public class DownloadView extends AbstractView {
	
	private final static Logger logger = Logger.getLogger(DownloadView.class);

	private final static String contentType="application/octe-stream";
	private final static String unknownSuffix="unkonwn";
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response)throws Exception {
			
		byte[]data=(byte[])model.get(Constants.DOWNLOAD_DATA_KEY);
		String filename=(String)model.get(Constants.DOWNLOAD_FILENAME_KEY);
		
		super.setContentType(contentType);
		response.setContentType(contentType);
		 
		if(StringUtils.isBlank(filename)){
			filename=RandomUtil.createRandom()+"."+unknownSuffix;
		}
		response.setHeader("Content-Disposition", "attachment; filename=\""+ filename + "\"");
			 
		 if(null==data)data=new byte[]{};
		
	     OutputStream os = response.getOutputStream();
	     
		 os.write(data);
		 os.flush();
	     os.close();
		 
	}

}
