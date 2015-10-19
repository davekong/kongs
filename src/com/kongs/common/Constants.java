package com.kongs.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kongs.common.util.FileUtil;

/**
 * @description  
 *
 * @date 2014-11-21 上午11:17:21
 *
 * @author 崔红涛
 *
 */
public class Constants {

	
	
	public final static Integer MAX_QPQX=20;
	
	public final static Integer DEFAULT_PAGENO=1;
	public final static Integer DEFAULT_PAGESIZE=10;
	
	/************************************
	 * 
	 *session
	 * 
	 ************************************/
	public final static String SESSION_USER_KEY="user";
	public final static String SESSION_REFERER_KEY="refeer";
	
	public static final String NHISTORY = "0";// 非历史版本
	public static final String HISTORY = "1";// 历史版本
	public static final String DHISTORY = NHISTORY;// 默认历史版本
	
	public static final String NDEL = "0";// 未删除
	public static final String DEL = "1";// 已删除
	public static final String DDEL = NDEL;// 删除状态默认值
	
	
	//--在线支付方式
	public final static String ONLINE_TYPE_ZFB="zfb";//支付宝
	public final static String ONLINE_TYPE_YL="yl";//银联
	
	public final static String ONLINE_FLAG_ZFB_SUCCESS="success";
	public final static String ONLINE_FLAG_ZFB_FAIL="fail";
	
	public final static String ONLINE_FLAG_YL_SUCCESS="ok";
	public final static String ONLINE_FLAG_YL_FAIL="fail";
	
	
	/************************************
	 * 
	 * web首页
	 * 
	 ************************************/
	//轮转景区1~5
	public static final Integer INDEX_SCENIC_CYCLE_START_RESULT=1;
	public static final Integer INDEX_SCENIC_CYCLE_END_RESULT=5;
	
	//乐乐推荐6~11
	public static final Integer INDEX_SCENIC_RECOMMEND_START_RESULT=1;
	public static final Integer INDEX_SCENIC_RECOMMEND_END_RESULT=9;
	
	//TOP hot 1~6
	public static final Integer INDEX_TOP_HOT_START_RESULT=1;
	public static final Integer INDEX_TOP_HOT_END_RESULT=6;
	
	//热点景区7~10
	public static final Integer INDEX_SCENIC_HOT_START_RESULT=1;
	public static final Integer INDEX_SCENIC_HOT_END_RESULT=8;
	
	//特价门票1~12
	public static final Integer INDEX_SCENIC_GAPPRICE_START_RESULT=1;
	public static final Integer INDEX_SCENIC_GAPPRICE_END_RESULT=12;
	
	//周边游1~12
	public static final Integer INDEX_SCENIC_AREA_START_RESULT=1;
	public static final Integer INDEX_SCENIC_AREA_END_RESULT=12;
	public static final String  INDEX_SCENIC_DEFAULT_AREA="001";//默认显示郑州
	
	//主题游1~12
	public static final Integer INDEX_SCENIC_THEME_START_RESULT=1;
	public static final Integer INDEX_SCENIC_THEME_END_RESULT=12;
	
	//主题
	public static final String INDEX_SCENIC_THEME_CODE_MINGSHANDACHUAN="023";//名山大川
	public static final String INDEX_SCENIC_THEME_CODE_MINGSHENGGUJI="003";//名胜古迹
	public static final String INDEX_SCENIC_THEME_CODE_ZHUTILEYUAN="002";//主题乐园
	
	//自定义栏目显示景区数1-9
	public static final Integer INDEX_SCHANNEL_SCENIC_START_RESULT=1;
	public static final Integer INDEX_SCHANNEL_SCENIC_END_RESULT=6;
	
	//首页活动景区数
	public static final Integer INDEX_ACTIVITY_SCENIC_START_RESULT=1;
	public static final Integer INDEX_ACTIVITY_SCENIC_END_RESULT=6;
	
	//首页活动数
	public static final Integer INDEX_ACTIVITY_START_RESULT=1;
	public static final Integer INDEX_ACTIVITY_END_RESULT=3;
	
	//搜索
	//城市1-15
	public static final Integer INDEX_SEARCH_CITY_START_RESULT=1;
	public static final Integer INDEX_SEARCH_CITY_END_RESULT=15;
	//景区1-15
	public static final Integer INDEX_SEARCH_SCENIC_START_RESULT=1;
	public static final Integer INDEX_SEARCH_SCENIC_END_RESULT=10;
	
	//每个栏目攻略的推荐个数
	public static final Integer INDEX_STRATEGY_RECOMMEND_START_RESULT = 1;
	public static final Integer INDEX_STRATEGY_RECOMMEND_END_RESULT = 4;

	//合作伙伴
	public static final Integer INDEX_PARTNERS_RECOMMEND_START_RESULT = 1;
	public static final Integer INDEX_PARTNERS_RECOMMEND_END_RESULT = 8;
	
	/************************************
	 * 
	 * 资讯
	 * 
	 ************************************/
	//景区资讯-默认页码和页数据大小
	public final static Integer NEWS_LIST_DEFAULT_PAGENO = 1;
	public final static Integer NEWS_LIST_DEFAULT_PAGESIZE = 10;
	//景区资讯-景区推荐起止记录数
	public static final Integer NEWS_SCENIC_RECOMMEND_START_RESULT=1;
	public static final Integer NEWS_SCENIC_RECOMMEND_END_RESULT=6;
	
	/************************************
	 * 
	 * 游玩攻略
	 * 
	 ************************************/
	//游玩攻略-默认页码和页数据大小
	public final static Integer ARTICLE_LIST_DEFAULT_PAGENO=1;
	public final static Integer ARTICLE_LIST_DEFAULT_PAGESIZE=10;

	//游玩攻略-景区推荐起止记录数 1~6
	public static final Integer ARTICLE_SCENIC_RECOMMEND_START_RESULT=1;
	public static final Integer ARTICLE_SCENIC_RECOMMEND_END_RESULT=6;
	
	/************************************
	 * 
	 * 门票预订
	 * 
	 ************************************/
	//门票预订首页  城市起止记录数1-10
	public static final Integer SCENIC_INDEX_AREA_START_RESULT=1;
	public static final Integer SCENIC_INDEX_AREA_END_RESULT=10;
	//门票预订首页  主题起止记录数1-5
	public static final Integer SCENIC_INDEX_THEME_START_RESULT=1;
	public static final Integer SCENIC_INDEX_THEME_END_RESULT=5;
	//门票预订首页 景区级别
//	public static final String SCENIC_INDEX_SCENIC_LEVEL1="001";
//	public static final String SCENIC_INDEX_SCENIC_LEVEL2="002";
//	public static final String SCENIC_INDEX_SCENIC_LEVEL3="003";
//	public static final String SCENIC_INDEX_SCENIC_LEVEL4="004";
//	public static final String SCENIC_INDEX_SCENIC_LEVEL5="005";
	
	// 门票预订首页     乐乐推荐起止记录数7-11
	public static final Integer SCENIC_INDEX_RECOMMEND_START_RESULT = 1;
	public static final Integer SCENIC_INDEX_RECOMMEND_END_RESULT = 5;
	// 门票预订首页     默认页码和页数据大小
	public static final Integer SCENIC_INDEX_PAGESIZE = 6;//第一次访问默认分页pagesize
	public static final Integer SCENIC_INDEX_PAGENO = 1;//第一次访问默认分页pageno
	
	// 门票预订详情     
	public static final Double SCENIC_DETAIL_AROUND_DISTANCE = 50.00;//当前经纬度半径30千米周围(单位:千米)
	public static final Double SCENIC_DETAIL_EARTH_RADIUS = 6371.00;//地球半径(单位:千米)
	
	/************************************
	 * 
	 * 攻略首页
	 * 
	 ************************************/
	//--显示栏目
	public static final Integer GCHANNEL_START_RESULT = 1;
	public static final Integer GCHANNEL_END_RESULT = 5;
	
	//每个栏目攻略的推荐个数
	public static final Integer STRATEGY_INDEX_CHANNEL_RECOMMEND_START_RESULT = 1;
	public static final Integer STRATEGY_INDEX_CHANNEL_RECOMMEND_END_RESULT = 6;
	
	//封面--推荐个数
	public static final Integer STRATEGY_INDEX_RECOMMEND_START_RESULT = 1;
	public static final Integer STRATEGY_INDEX_RECOMMEND_END_RESULT = 5;
	
	//最受欢迎排行榜--推荐个数
	public static final Integer STRATEGY_INDEX_MOST_POPULAR_START_RESULT = 1;
	public static final Integer STRATEGY_INDEX_MOST_POPULAR_END_RESULT = 10;
	
	
	/************************************
	 * 
	 * 帮助
	 * 
	 ************************************/
	//景区资讯-默认页码和页数据大小
	public final static Integer HELP_LIST_DEFAULT_PAGENO = 1;
	public final static Integer HELP_LIST_DEFAULT_PAGESIZE = 10;
	
	/************************************
	 * 
	 *视图
	 * 
	 ************************************/
	public final static String STREAM_VIEW_NAME="stream";
	public final static String STREAM_DATA_KEY="stream";
	public final static String STREAM_CACHE_KEY="iscache";
	public final static String STREAM_MIMETYPE_KEY="mimetype";
	
	public final static String DOWNLOAD_VIEW_NAME="download";
	public final static String DOWNLOAD_DATA_KEY="download";
	public final static String DOWNLOAD_FILENAME_KEY="filename";
	
	
	
	/*
	 *form 表单之ajax验证
	 *
	 *例如form表单中的手机号是否存在。
	 *
	 *我们使用jquery easyui的验证框架，因此有以下限制
	 *
	 *1、control方法返回类型为string，并注解为ResponseBody
	 *2、成功返回值为“true”  失败返回值为除“true”之外的任意字符串
	 */
	 public final static String FROM_VALID_SUCCESS="true";
	 public final static String FROM_VALID_FAILURE="false";
	 
	 
	 public static final List EMPTY_LIST = new ArrayList();//空集合
	 public static final Map EMPTY_MAP = new HashMap();//空map
	 public static final String EMPTY_STRING="";//空字符串
	 
	 public final static String TREE_ROOT_ID="999999999";
	 public final static String TREE_ROOT_NAME="根";
	 public final static String TREE_ROOT_PID="-1";
	 
	 public final static String SELECT_ROOT_ID="999999999";
	 public final static String SELECT_ROOT_NAME="请选择";
	 public final static String SELECT_ROOT_PID="-1";
	 
	 
	 public final static String DEFAULT_PASSWORD="a123456";
	 
	//订单评论，点评方式
	public static final String REMARK_TYPE_WEB="1";	//来自web
	public static final String REMARK_TYPE_MOBILE="2";//来自手机端
	
	
	/************************************
	 * 
	 * 优惠券规则固定ID值（发放优惠券使用）
	 * 
	 ************************************/
	
	/**
	 * 优惠券规则ID常量-注册赠送
	 */
	public final static String couponrule_regist= "couponrule_zczs";
	/**
	 * 优惠券规则ID常量-首单赠送
	 */
	public final static String couponrule_firstorder = "couponrule_sdzs";
	 
	 /************************************
	  * 
	  *文件目录
	  * 
	  ************************************/
	 public  static String FILE_ROOT_DIR;//文件根目录
	 static {
		try {
			// 读取配置文件
			Properties props = new Properties();
			// 类加载获得流
			InputStream in = Constants.class.getClassLoader().getResourceAsStream("config/constants.properties");
			props.load(in);
			// 解析配置文件
			FILE_ROOT_DIR = props.getProperty("FILE_ROOT_DIR");
			FILE_ROOT_DIR=FileUtil.toLeftSlash(FILE_ROOT_DIR);
			if(!FILE_ROOT_DIR.endsWith(FileUtil.LEFT_FLASH))FILE_ROOT_DIR+=FileUtil.LEFT_FLASH;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 public final static String FILE_TMP_DIR=FILE_ROOT_DIR+"tmp"+FileUtil.LEFT_FLASH;//临时文件目录
	 public final static String FILE_UPLOAD_DIR=FILE_ROOT_DIR+"upload"+FileUtil.LEFT_FLASH;//存放上传文件主目录
	 public final static String FILE_CONTENT_DIR=FILE_ROOT_DIR+"content"+FileUtil.LEFT_FLASH;//存放文件内容主目录
	 public final static String FILE_CKEDITOR_DIR=FILE_ROOT_DIR+"cke"+FileUtil.LEFT_FLASH;//存放由ckeditor上传主目录
     
	 public final static String FILE_DEFAULT_BUSINIESSTYPE="common";//默认业务类型
	 public final static String FILE_DOT=".";//.
     public final static String FILE_HTML_SUFFIX="html";//html文件后缀
     public final static String FILE_DEFAULT_SUFFIX=FILE_HTML_SUFFIX;//默认文件后缀
     
     public final static Integer IMAGE_COMPRESSION_DEFAULT_WIDTH=150;//图片压缩后默认宽度
     public final static Integer IMAGE_COMPRESSION_DEFAULT_HEIGHT=150;//图片压缩后默认高度
     
     public final static String BAIDUMAP_AK="4mvGkfaZCwi6XceZ9YrtWhz4";//百度地图AK值
     public final static String IP_LOCATION_BAIDU_URL="http://api.map.baidu.com/location/ip";//百度IP定位查询地址     
     public final static String IP_FOR_TEST="218.28.191.24";//测试IP
     public final static String CPK_NAME_PRE="TravelFun_v";//cpk前缀
}
