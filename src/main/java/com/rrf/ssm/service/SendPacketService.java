/**
 * 
 */
package com.rrf.ssm.service;

import java.util.List;

import com.rrf.ssm.pojo.RecvPacketModel;
import com.rrf.ssm.pojo.SendPacketModel;
import org.apache.ibatis.annotations.Param;

/*
 * @ClassName: SendPacketService类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月3日	下午1:40:35
 */
public interface SendPacketService {
	//发红包
	public int SendPacket(SendPacketModel sendPacket);
	//获取发出的红包领取信息
	public List<SendPacketModel> getSendInfo(String sendId);
	//获取发出的红包信息
	public List<SendPacketModel> getMySend(String UserId);
	//获取发送红包的id
	public String getSendId(String senderID);
	//获取单条数据
	public SendPacketModel getMe(String sendId);
	//修改红包的金额和数量
	public int updateMOneyAndNum(String number,String sendId,String recvnumber,String overmoney);
	//修改红包的状态
	public int updateRedPacketState(String sendId,String state);
	//转发红包
	public SendPacketModel Forward(String sendid);
	//转发红包
	public SendPacketModel notConmpanyForward(String sendid);
	//修改分享的二维码
	public int updateRedPacketqrcode(String sendid,String qrcode);
}
