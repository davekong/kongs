package com.kongs.common.util;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.kongs.common.Constants;

/**
 * @description  随机数工具类
 *
 * @date 2014-11-16 下午04:11:58
 *
 * @author 崔红涛
 *
 */
public class RandomUtil {
	
	//生成uuid
	public static String createUuid(){
		String id=java.util.UUID.randomUUID().toString();
		return id.replace("-", "");
	}
	
	//随机数
	public static String createRandom(){
		
		StringBuffer sb=new StringBuffer();
		
		//追加  当前简写年月日
		sb.append(DateUtil.getSimpleYearAndMonthAndDay());
		
		//追加 当前时分秒微妙
		sb.append(DateUtil.getHourAndMinAndSecAndMil());
		
		//生成1000~9999之间的随机数
		sb.append(Math.round(Math.random()*(9999-1000)+1000));
		
		return sb.toString();
	}
	
	//生成订单号
	public static String createOrderNo(String scenicCode){
		String code="0000";//默认
		if(StringUtils.isNotBlank(scenicCode)){
			code=scenicCode;
		}
		return code+createRandom();
	}
	
	//生成支付号
	public static String createPayNo(){
		
		return null;
	}
	
	//创建游客账号
	public static String createTAccount(){
		//游客标识
		String flag="t";
		return flag+createRandom();
	}
	//创建景区账号
	public static String createSAccount(){
		//景区标识
		String flag="s";
		return flag+createRandom();
	}
	//创建运营商账号
	public static String createIAccount(){
		//运营商标识
		String flag="i";
		return flag+createRandom();
	}
	
	//构建游客匿名
	public static String createTSmallName(){
		return "smile";//乐乐
	}
	//构建景区匿名
	public static String createSSmallName(String scenicName){
		return scenicName;
	}
	//构建运行商匿名
	public static String createISmallName(){
		return "ISP";//爱斯帕
	}
	
	public static String createTTmpPwd(){
		return  Constants.DEFAULT_PASSWORD;
	}
	
	
	//生成子号，将来做验证码
	public static String createSubno(){
		return "subno";
	}
	
	//生成订单确认号
	public static String creatConfirmno(String sceniccode){
//		return sceniccode+getCharAndNumr(8);
		
		return getCharAndNumr(10);
	}
	
	public static String getYzm(){
		return getCharAndNumr(6);
	}
	 /**
	  * java生成随机数字和字母组合
	  * @param length[生成随机数的长度]
	  * @return
	  */
	 public static String getCharAndNumr(int length) {
	  String val = "";
	  Random random = new Random();
	  while (val.length()<length) {
		  String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; 
		   // 字符串
		   if ("char".equalsIgnoreCase(charOrNum)) {
		    // 取得大写字母还是小写字母,65:大写，97：小写。我们此处只取大写
		    //int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; 
			  /* int choice=65;
		    val += (char) (choice + random.nextInt(26));*/
			   continue;
		   } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
		    val += String.valueOf(random.nextInt(10));
		   }
		
	}
	  /*for (int i = 0; i < length; i++) {
	   // 输出字母还是数字
	   String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; 
	   // 字符串
	   if ("char".equalsIgnoreCase(charOrNum)) {
	    // 取得大写字母还是小写字母,65:大写，97：小写。我们此处只取大写
	    //int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; 
		   int choice=65;
	    val += (char) (choice + random.nextInt(26));
		   continue;
	   } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
	    val += String.valueOf(random.nextInt(10));
	   }
	  }*/
	  return val;
	 }
	 
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * 生成指定长度的数字字母（含大小写字母、数字）混合随机码
	 * @param length
	 * @return
	 */
	public static String generateCouponString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	
	public static void main(String args[]){
		int i=0;
		Set s=new HashSet();
		 while (i<100) {
			/*int a[] = new int[10];
			      for(int i=0;i<a.length;i++ ) {
			          a[i] = (int)(10*(Math.random()));
			          System.out.print(a[i]);
			      }*/
			 String sg=generateCouponString(12);
			 s.add(sg);
			i++;
			System.out.println(sg);
		}
		
	}
	
}
