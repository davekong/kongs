package com.kongs.common;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import com.kongs.common.util.MIMEUtil;

import eu.medsea.mimeutil.MimeType;

/**
 * @description  
 *
 * @date 2014-11-21 上午10:22:39
 *
 * @author 崔红涛
 *
 */
public class StreamView extends AbstractView {
	
	private final static Logger logger = Logger.getLogger(StreamView.class);

	private final static String defaultContentType="image/png";
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response)throws Exception {
			
		byte[]data=(byte[])model.get(Constants.STREAM_DATA_KEY);
		Boolean iscache=(Boolean)model.get(Constants.STREAM_CACHE_KEY);
		String mimeType=(String)model.get(Constants.STREAM_MIMETYPE_KEY);
		if(iscache==null)iscache=Boolean.TRUE;
		
		String contentType=null;
		if(StringUtils.isNotBlank(mimeType)){
			contentType=mimeType;
		}else{
			contentType=decideContentType(data);
		}
		
		super.setContentType(contentType);
		response.setContentType(contentType);
		 
		
		if(contentType.toLowerCase().contains("image")&&iscache){
			//设置图片缓存	 
			response.setHeader("Cache-Control","max-age="+3600*24*7); //缓存一星期
			
		}else{
			response.setHeader("Cache-Control","no-store, max-age=0, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache"); 
		}
			 
//		 response.setHeader("Cache-Control","no-store, max-age=0, no-cache, must-revalidate");    
//	     response.addHeader("Cache-Control", "post-check=0, pre-check=0");
//	     
		 
		 if(null==data)data=new byte[]{};
		
	     OutputStream os = response.getOutputStream();
	     
		 os.write(data);
		 os.flush();
	     os.close();
		 
	}
	
	
	private String decideContentType(byte[]data){
		
		if(null==data) return defaultContentType;
		 
		 
		 Collection<MimeType> mimeTypes = MIMEUtil.getMimeTypes(data);  
		
		 MimeType bestMimeType=null;
		 for(MimeType mimeType:mimeTypes){
			 bestMimeType=mimeType;
			 break;
		 }
		 String contentType="";
		 if(null!=bestMimeType)
			 contentType=bestMimeType.toString();
			 
		 if(!"".equals(contentType)){
			 contentType=contentType+";charset=UTF-8";
		 }
		 return contentType;
	}
	
	public static void main(String args[]){
//		String filename="C:\\Users\\Edward\\Desktop\\aaa.icon"; 
//		File file=new File(filename);
//		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");  
//		Collection<MimeType> mimeTypes = MimeUtil.getMimeTypes(file);  
//		
//		MimeType bestMimeType=null;
//		for(MimeType mimeType:mimeTypes){
//			 bestMimeType=mimeType;
//			 break;
//		 }
//		
//		System.out.println(bestMimeType);
//		logger.warn("11111");
		
		Boolean b=Boolean.TRUE;
		if(b) System.out.println("111");
	}

}
