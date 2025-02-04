/**
 * 
 */
package com.rrf.ssm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rrf.ssm.pojo.RecvPacketModel;
import com.rrf.ssm.pojo.SendPacketModel;

/*
 * @ClassName: RedPacketModelDao类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:29:47
 */
public interface SendPacketModelDao {
	//发红包
	int SendPacket(SendPacketModel sendPacket);
	//获取发出的红包领取信息
	List<SendPacketModel> getSendInfo(@Param("sendId") String sendId);
	//获取发出的红包信息
	List<SendPacketModel> getMySend(@Param("memberid")String UserId);
	//获取发送红包的id
	String getSendId(@Param("senderId") String senderID);
	//获取单条数据
	SendPacketModel getMe(@Param("sendId") String sendId);
	//修改红包的金额和数量
	int updateMOneyAndNum(@Param("packetnumber")String number,@Param("sendId")String sendId,@Param("recvnumber")String recvnumber,@Param("overmoney") String overmoney);
	//修改红包的状态
	int updateRedPacketState(@Param("sendId")String sendId,@Param("state")String state);
	//企业红包转发
	SendPacketModel Forward(@Param("sendId") String sendid);
	//普通红包转发
	SendPacketModel notConmpanyForward(@Param("sendId") String sendid);
	//修改分享的二维码
	int updateRedPacketqrcode(@Param("sendId") String sendid,@Param("qrcode") String qrcode);
}
