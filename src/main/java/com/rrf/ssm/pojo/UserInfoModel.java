/**
 * 
 */
package com.rrf.ssm.pojo;

/*
 * @ClassName: UserInfoModel类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午4:57:21
 */
public class UserInfoModel {

	private String memberid;
	private String membername;
	private String membernickname;
	private String memberbalance;  //账户余额
	private String headurl;			//用户头像
	
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMembernickname() {
		return membernickname;
	}
	public void setMembernickname(String membernickname) {
		this.membernickname = membernickname;
	}
	public String getMemberbalance() {
		return memberbalance;
	}
	public void setMemberbalance(String memberbalance) {
		this.memberbalance = memberbalance;
	}
}
