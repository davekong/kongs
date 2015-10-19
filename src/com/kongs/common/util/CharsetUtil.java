package com.kongs.common.util;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

/**
 * @description  
 *
 * @date 2014-11-26 下午01:59:52
 *
 * @author 崔红涛
 *
 */
public class CharsetUtil {
	
	private static final Logger logger = Logger.getLogger(CharsetUtil.class);
	
	private static CodepageDetectorProxy detector =null;
	
	static {
		// 1、CodepageDetector是探测器，它把探测任务交给具体的探测实现类的实例完成。
		// 2、CodepageDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法加进来，
		//    如ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector。
		// 3、detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的
		//    字符集编码。
		detector =   CodepageDetectorProxy.getInstance(); 
		
		//JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码测定。
	    //所以，一般有了这个探测器就可满足大多数项目的要求，
		detector.add(JChardetFacade.getInstance());
		
		//可用于检查HTML、XML等文件或字符流的编码，构造方法中的参数用于 指示是否显示探测过程的详细信息
		//detector.add(new ParsingDetector(false));  
		
		//ASCIIDetector用于ASCII编码测定   
		//detector.add(ASCIIDetector.getInstance());   
		
		//UnicodeDetector用于Unicode家族编码的测定   
		//detector.add(UnicodeDetector.getInstance());   
		
	}
	
	
	public static Charset detectorByFile(File file){
		
		Charset charset=null;
		FileInputStream fis=null;
		BufferedInputStream bi=null;
		try {
			fis=new FileInputStream(file);
			bi=new BufferedInputStream(fis);
			charset = detector.detectCodepage(bi,100);  
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.fillInStackTrace());
		}finally{
			try{
				if(null!=bi)bi.close();
				if(null!=fis)fis.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.fillInStackTrace());
			}
		}
		
		return charset;
	}
	
	public static Charset detectorByFile(String filename){
		return detectorByFile(new File(filename));
	}
	
	
	public static void main(String args[]){
		System.out.println(detectorByFile(new File("C:\\Users\\Edward\\Desktop\\01_spring3.0-bean注解.htm")));
		System.out.println(detectorByFile(new File("C:\\Users\\Edward\\Desktop\\a.html")));
	}
	

}
