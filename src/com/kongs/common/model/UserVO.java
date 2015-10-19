package com.kongs.common.model;


/**
 * @description  
 *
 * @date 2014-11-21 上午09:16:41
 *
 * @author 崔红涛
 *
 */
public class UserVO {
	
	public final static String TYPE_TOUR="10";
	public final static String TYPE_SCENIC="20";
	public final static String TYPE_ISP="90";
	
	private String id;//id
	private String account;//账号
	private String smallname;//匿名
	private String mobile;//手机
	public  String type;//用户类型
	public String  scenicid;//对于景区用户有效
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSmallname() {
		return smallname;
	}
	public void setSmallname(String smallname) {
		this.smallname = smallname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScenicid() {
		return scenicid;
	}
	public void setScenicid(String scenicid) {
		this.scenicid = scenicid;
	}
}
