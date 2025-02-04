/**
 * 
 */
package com.rrf.ssm.pojo;

import java.util.HashMap;
import java.util.List;

/*
 * @ClassName: SendPacketModel类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:04:49
 */
public class SendPacketModel {

	private String sendId;			//红包Id
	private String senderId;		//发送人Id
	private String packettype;		//红包类型
	private String packetmoney;		//发出红包金额
	private String packetnumber;	//发出红包数量
	private String cardid;			//企业名片（红包类型为企业红包时填写)
	private String timestamp;		//发出红包的时间戳
	private String imgUrls; 		//企业图片
	private String content;			//红包内容
	private String state;			//红包状态
	private String recvnumber;      //领取红包的数量
	private String remark;			//红包描述信息
	private String qrcode;			//红包二维码
	private String overmoney;		//红包所剩金额
	private UserInfoModel user;
	private CompanyCardModel company;
	private List<String> imglist; //临时的图片路径


	public String getOvermoney() {
		return overmoney;
	}

	public void setOvermoney(String overmoney) {
		this.overmoney = overmoney;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getPackettype() {
		return packettype;
	}

	public void setPackettype(String packettype) {
		this.packettype = packettype;
	}

	public String getPacketmoney() {
		return packetmoney;
	}

	public void setPacketmoney(String packetmoney) {
		this.packetmoney = packetmoney;
	}

	public String getPacketnumber() {
		return packetnumber;
	}

	public void setPacketnumber(String packetnumber) {
		this.packetnumber = packetnumber;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRecvnumber() {
		return recvnumber;
	}

	public void setRecvnumber(String recvnumber) {
		this.recvnumber = recvnumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserInfoModel getUser() {
		return user;
	}

	public void setUser(UserInfoModel user) {
		this.user = user;
	}

	public CompanyCardModel getCompany() {
		return company;
	}

	public void setCompany(CompanyCardModel company) {
		this.company = company;
	}

	public List<String> getImglist() {
		return imglist;
	}

	public void setImglist(List<String> imglist) {
		this.imglist = imglist;
	}
}
