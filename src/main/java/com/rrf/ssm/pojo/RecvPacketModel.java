/**
 * 
 */
package com.rrf.ssm.pojo;

/*
 * @ClassName: RecvPacketModel类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:12:41
 */
public class RecvPacketModel {
	private String packetId;
	private String recvId;
	private String packetType;
	private String packetMoney;
	private String sendId;
	private String timesTamp;
	private UserInfoModel user;
	private SendPacketModel send;

	public SendPacketModel getSend() {
		return send;
	}

	public void setSend(SendPacketModel send) {
		this.send = send;
	}

	public UserInfoModel getUser() {
		return user;
	}
	public void setUser(UserInfoModel user) {
		this.user = user;
	}
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public String getRecvId() {
		return recvId;
	}
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
	public String getPacketType() {
		return packetType;
	}
	public void setPacketType(String packetType) {
		this.packetType = packetType;
	}
	public String getPacketMoney() {
		return packetMoney;
	}
	public void setPacketMoney(String packetMoney) {
		this.packetMoney = packetMoney;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getTimesTamp() {
		return timesTamp;
	}
	public void setTimesTamp(String timesTamp) {
		this.timesTamp = timesTamp;
	}
}
