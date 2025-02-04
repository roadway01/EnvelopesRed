/**
 * 
 */
package com.rrf.ssm.pojo;

/*
 * @ClassName: GetCash类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:14:17
 */
public class GetCashModel {

	private String id;
	private String member_id;
	private String money;
	private String type;
	private String timesTamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTimesTamp() {
		return timesTamp;
	}
	public void setTimesTamp(String timesTamp) {
		this.timesTamp = timesTamp;
	}
}
