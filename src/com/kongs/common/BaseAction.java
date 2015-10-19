package com.kongs.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.kongs.common.model.UserVO;

/**
 * @description  
 *
 * @date 2014-11-16 下午03:42:06
 *
 * @author 崔红涛
 *
 */
public class BaseAction {
	
	
	/**
	 * 构造流视图
	 */
	protected ModelAndView createStreamViewOnly(byte[] bytes){
		ModelAndView mav=new ModelAndView();
		mav.setViewName(Constants.STREAM_VIEW_NAME);
		mav.addObject(Constants.STREAM_DATA_KEY,bytes);
		return mav;
	}
	
	protected ModelAndView createStreamViewOnly(byte[] bytes,boolean isCache){
		ModelAndView mav=new ModelAndView();
		mav.setViewName(Constants.STREAM_VIEW_NAME);
		mav.addObject(Constants.STREAM_DATA_KEY,bytes);
		mav.addObject(Constants.STREAM_CACHE_KEY,isCache);
		return mav;
	}
	
	protected ModelAndView createDownloadViewOnly(byte[] bytes,String filename){
		ModelAndView mav=new ModelAndView();
		mav.setViewName(Constants.DOWNLOAD_VIEW_NAME);
		mav.addObject(Constants.DOWNLOAD_DATA_KEY,bytes);
		mav.addObject(Constants.DOWNLOAD_FILENAME_KEY,filename);
		return mav;
	}
	
	
	protected UserVO getNowUser(HttpSession session){
		return (UserVO)session.getAttribute(Constants.SESSION_USER_KEY);
	}
	
	//
	protected void setReferer(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = "";
		//因为如果用80端口的时候，浏览器会自动屏蔽掉端口，
		//从而得到的referer就成了如http://www.lvyoule.net，再用basepath=http://www.lvyoule.net：80去替换的时候就会失败
		//因此对80端口在此处做单独处理，解决登陆或者退出时候404的错误
		if (80==request.getServerPort()) {
			basePath =request.getScheme() + "://"+ request.getServerName() + path + "/";
		}else{
			basePath =request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
		}
		
		HttpSession session=request.getSession();
		
		//上次referer
		Object o1=session.getAttribute(Constants.SESSION_REFERER_KEY);
		String last=o1==null?null:o1.toString();
		
		//本次referer
		Object o2=request.getHeader("referer");
		String current=o2==null?null:o2.toString();
		if(null!=current){
			//
			current=current.replaceFirst(basePath, "");
			if(current.startsWith("pt/tuser/regist")||current.startsWith("pt/tuser/login")){//如果为注册或登陆相关
				current=null;
			}
		}
		
		String referer=null;
		if(null==current){
			referer=last;
		}else{
			referer=current;
		}
		
		session.setAttribute(Constants.SESSION_REFERER_KEY, referer);
	}
	
	protected String getReferer(HttpServletRequest request){
		HttpSession session=request.getSession();
		Object o=session.getAttribute(Constants.SESSION_REFERER_KEY);//先获取
		String referer = o==null?"":o.toString();
		referer=referer.replaceFirst("http://www.lvyoule.net/", "");
		referer=referer.replaceFirst("https://www.lvyoule.net/", "");
		session.removeAttribute(Constants.SESSION_REFERER_KEY);//后清除
		return referer;//如果没有则为首页（""即为首页）
	}
	
	protected String getBasePath(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
		return basePath;
	}
	
	
}
