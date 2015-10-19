package com.kongs.common;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


/**
 * @注意 
 * 	由于dispacher servlet 只拦截以"action"为后缀的请求，所以当请求jsp、js等其他资源时发生问题时
 *  并不能为CustomMappingExceptionResover 异常解析器所控制，因此需要在web.xml中配置错误页面！
 *  如果请求的action没有进入到control(即没有进行映射)，同样不会被CustomMappingExceptionResover所映射！
 *  
 * @date 2014-5-28 下午06:59:29
 * 
 * @author 崔红涛
 * 
 */
public class CustomMappingExceptionResover extends
		SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) {
		boolean isAjax=false;
		
		//ContentNegotiatingViewResolver 
		if(null==response.getContentType()){
			//判定是否含有responsebody 注解，
			//application/json只是responsebody注解最终响应类型之一！
			//但这里我假定它的contenttype就为application/json，
			HandlerMethod hmethod=(HandlerMethod)handler;
			Method method=hmethod.getMethod();
			Annotation a=AnnotationUtils.findAnnotation(method, ResponseBody.class) ;
			Type type=method.getGenericReturnType();
			isAjax=a==null?false:true;
			
		}else{
			String x_Requested_With=request.getHeader("X-Requested-With");
			isAjax=x_Requested_With!=null&&x_Requested_With.indexOf("XMLHttpRequest")!=-1?true:false;
		}
		
		StringBuffer sb=new StringBuffer();
		if (!isAjax) {//非json方式
			
			sb.append("请求URL：");
			sb.append(request.getContextPath()+request.getServletPath());
			sb.append("。<br>异常信息：");
			sb.append(ex.getClass().getSimpleName());
			sb.append("(");
			sb.append(ex.getMessage());
			sb.append(")。<br>");
			
			
			//根据当前异常从
			//exceptionMappings(exception：viewname,...) 、defaultErrorView(默认viewname) ；前者优先级高！
			//获取view name
			String viewName = determineViewName(ex, request);
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);//设置视图的返回码
			}
			
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject("message", sb.toString());
			mav.addObject(super.DEFAULT_EXCEPTION_ATTRIBUTE, ex);
			
			return mav;
		} else {//json
			response.setContentType("application/json;;charset=UTF-8");
			
			
		    ObjectMapper objectMapper = new ObjectMapper();
		    JsonEncoding encoding =JsonEncoding.UTF8;
		    JsonGenerator jsonGenerator =null;
			try {
				jsonGenerator =objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), encoding);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			sb.append("异常信息：");
			sb.append(ex.getClass().getSimpleName());
			sb.append("(");
			sb.append(ex.getMessage());
			sb.append(")");
			
			ResponseVO vo=new ResponseVO(false,sb.toString());
			
			try {
				objectMapper.writeValue(jsonGenerator,vo);
			} catch (JsonGenerationException e1) {
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
		
	}
	
	public static void main(String args[]){
		String s1="abc";
		String s2="abc";
		System.out.println(s1.indexOf(s2));
		
	}
}
