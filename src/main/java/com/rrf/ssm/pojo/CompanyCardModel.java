/**
 * 
 */
package com.rrf.ssm.pojo;

import java.util.List;

/*
 * @ClassName: CompanyCard类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午4:59:33
 */
public class CompanyCardModel {

	private String cardid;		//名片Id
	private String memberId;	//创建人Id
	private String membername;	//创建人名称
	private String companyName;	//企业名称
	private String businessInfo;//企业信息
	private String contact;		//联系方式/电子邮件/地址
	private Integer state;		//企业名片状态
	private String cardName;	//名片名称
	private UserInfoModel member;
	private ContactModel contactModel;

	public UserInfoModel getMember() {
		return member;
	}

	public void setMember(UserInfoModel member) {
		this.member = member;
	}

	public String getCardName() {
		return cardName;
	}
	public ContactModel getContactModel() {
		return contactModel;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBusinessInfo() {
		return businessInfo;
	}
	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public void setContactModel(ContactModel contactModel) {
		this.contactModel = contactModel;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
}
