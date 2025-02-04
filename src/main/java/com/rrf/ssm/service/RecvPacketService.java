/**
 * 
 */
package com.rrf.ssm.service;

import java.util.List;

import com.rrf.ssm.pojo.GetCashModel;
import com.rrf.ssm.pojo.RecvPacketModel;
import org.apache.ibatis.annotations.Param;

/*
 * @ClassName: RecvPacketService类
 * @Description:领取红包的类
 * @author 江西现代 - 邹智敏
 * @date 2018年1月3日	下午5:17:20
 */
public interface RecvPacketService {

	//领红包
	public	String RecvPacket(RecvPacketModel recvPacketModel);
	//领取红包记录
	boolean SaveRecvPacket(RecvPacketModel recvPacketModel);
	//获取收到的红包信息
	public	List<RecvPacketModel> getMyRecv(String userId);
	//提现
	public	int withdraw(GetCashModel getCash);
	//查询余额
	public	String getBalance(String userId);
	//查询用户是不是第一次领取红包
	int selectRecvOne(String recvid,String sendId);

	public GetCashModel selectGetCash(String memberid);
}
