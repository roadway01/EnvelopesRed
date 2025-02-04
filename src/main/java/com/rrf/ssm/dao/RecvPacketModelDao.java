/**
 * 
 */
package com.rrf.ssm.dao;

import java.util.List;

import com.rrf.ssm.pojo.GetCashModel;
import org.apache.ibatis.annotations.Param;

import com.rrf.ssm.pojo.RecvPacketModel;

/*
 * @ClassName: RecvPacketModelDao类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:34:51
 */
public interface RecvPacketModelDao {
	//领红包
	String RecvPacket(RecvPacketModel recvPacketModel);
	//领取红包记录
	boolean SaveRecvPacket(RecvPacketModel recvPacketModel);
	//获取收到的红包信息
	List<RecvPacketModel> getMyRecv(@Param("memberid") String userId);
	//提现
	int withdraw(GetCashModel getCash);

	GetCashModel selectGetCash(@Param("id") String id);
	//查询余额
	String getBalance(String userId);
	//查询用户是不是第一次领取红包
	int selectRecvOne(@Param("recvid") String recvid,@Param("sendId") String sendId);
}
