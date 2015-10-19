package com.kongs.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	//����ת�ַ� 
	//pattern: yyyy-MM-dd hh:mm:ss
	//pattern: yyyy-MM-dd
	//pattern: yyyy/MM/dd  hh:mm:ss
	
	//pattern: yyyy-MM-dd HH:mm:ss
	//pattern: yyyy/MM/dd HH:mm:ss
	
	public  static  String date_string(Date i_Date,String pattern){
		DateFormat df=new SimpleDateFormat(pattern);
		return df.format(i_Date);
	} 
	
	//�ַ� ת����
	//pattern: yyyy-MM-dd hh:mm:ss
	//pattern: yyyy-MM-dd
	//pattern: yyyy/MM/dd  hh:mm:ss
	
	//pattern: yyyy-MM-dd HH:mm:ss
	//pattern: yyyy/MM/dd HH:mm:ss
	
	//�������Ҫע���ˣ����������ġ������ַ��ʽ���͡�pattern��ʽ����ƥ��ͻ��׳��쳣
	public static Date string_date(String i_str,String pattern){
		try {
			DateFormat df=new SimpleDateFormat(pattern);
			return df.parse(i_str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//��ʽ��date
	public static Date formate_date(Date i_date,String pattern){
		return string_date(date_string(i_date,pattern),pattern);
	}
	
	//TimestampתDate
	public static Date timestamp_date(Timestamp t){
		return new Date(t.getTime());
	}
	
	
	
	//������
	public static Integer yearsBetween(Date date1,Date date2){
		String strDate1=date_string(date1,"yyyy-MM-dd");
		String str1=strDate1.substring(0,4);
		
		String strDate2=date_string(date2,"yyyy-MM-dd");
		String str2=strDate2.substring(0,4);
		
		return Math.abs(Integer.parseInt(str1)-Integer.parseInt(str2));
	}
	
	//����ղ�
	public static int daysBetween(Date date1,Date date2)  
    {    
		  
		 date1=formate_date(date1,"yyyy-MM-dd");  
	     date2=formate_date(date2,"yyyy-MM-dd");

	     Calendar cal = Calendar.getInstance();    
	     cal.setTime(date1);    
	     
	     long time1 = cal.getTimeInMillis();                 
	     cal.setTime(date2);    
	     
	     long time2 = cal.getTimeInMillis();   
	     
        long between_days=(time2-time1)/(1000*60*60*24);  
            
       return Math.abs(Integer.parseInt(String.valueOf(between_days)));           
    }    
	
	
	public static String getSimpleYearAndMonthAndDay(){
		String strDate=date_string(new Date(),"yyyyMMdd");
		return strDate.substring(2,8);
	}
	
	//���ʱ�������
	public static String getHourAndMinAndSecAndMil(){
		String strDate=date_string(new Date(),"yyyy-MM-dd HH:mm:ss:SSS");
		return strDate.substring(11).replace(":", "") ;
	}
	
	
	
	//ȡ�ĵ�ǰ���������µ�һ��
	public static String firstOfMonth(){
		String strDate=date_string(new Date(),"yyyy-MM-dd");
		String str=strDate.substring(0,8);
		str+="01";
		return str;
	}
	
	//ȡ�õ�ǰ���������ܵ���һ����
	public static String firstOfWeek(){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		//System.out.println(Calendar.DAY_OF_WEEK);
		int index =cal.get(Calendar.DAY_OF_WEEK);//��������Ϊ������һ�ܵĵ�һ��
		if(1==index)
			index=8;
		cal.add(Calendar.DATE, -(index-2));
		return sd.format(cal.getTime()); 
	}
	
	//��ȡ��ǰ����amount���Ժ������
	public static Date addDay(Date date,Integer amount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, amount);
		return calendar.getTime();
	}
	
	public static void main(String args[]){
		/**/Timestamp tt=new Timestamp(1987,12,14,12,12,13,888888000);
		Date d=new Date();
		System.out.println(date_string(tt,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(date_string(d,"yyyy-MM-dd HH:mm:ss"));
		
//		System.out.println(new Date());
//		System.out.println(formate_date(new Date(),"yyyy-MM-dd hh:mm:ss"));
//		System.out.println(date_string(new Date(),"yyyy-MM-dd hh:mm:ss"));
		
		System.out.println(daysBetween(new Date(),string_date("2099-12-31 23:59:59","yyyy-MM-dd HH:mm:ss")));
		
		
		System.out.println(getSimpleYearAndMonthAndDay());
		System.out.println(getHourAndMinAndSecAndMil());
	}
}
