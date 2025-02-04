/**
 * 
 */
package com.rrf.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rrf.ssm.dao.SendPacketModelDao;
import com.rrf.ssm.pojo.RecvPacketModel;
import com.rrf.ssm.pojo.SendPacketModel;
import com.rrf.ssm.service.SendPacketService;

/*
 * @ClassName: SendPacketServiceImpl类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月3日	下午1:41:32
 */
@Service
public class SendPacketServiceImpl implements SendPacketService{

	
	@Resource
	private SendPacketModelDao sendPacketModelDao;
	
	@Override
	public List<SendPacketModel> getSendInfo(String sendId) {
		return sendPacketModelDao.getSendInfo(sendId);
	}
	@Override
	public List<SendPacketModel> getMySend(String UserId) {
		return sendPacketModelDao.getMySend(UserId);
	}
	
	@Override
	public String getSendId(String senderID) {
		return sendPacketModelDao.getSendId(senderID);
	}

	@Override
	public SendPacketModel getMe(String sendId) {
		return sendPacketModelDao.getMe(sendId);
	}

	@Override
	public int updateMOneyAndNum(String number, String sendId,String recvnumber,String overmoney) {
		return sendPacketModelDao.updateMOneyAndNum(number,sendId,recvnumber,overmoney);
	}

	@Override
	public int updateRedPacketState(String sendId, String state) {
		return sendPacketModelDao.updateRedPacketState(sendId,state);
	}

	@Override
	public SendPacketModel Forward(String sendid) {
		return sendPacketModelDao.Forward(sendid);
	}

	@Override
	public SendPacketModel notConmpanyForward(String sendid) {
		return sendPacketModelDao.notConmpanyForward(sendid);
	}

	@Override
	public int updateRedPacketqrcode(String sendid,String qrcode) {
		return sendPacketModelDao.updateRedPacketqrcode(sendid,qrcode);
	}

	@Override
	public int SendPacket(SendPacketModel sendPacket) {
		return sendPacketModelDao.SendPacket(sendPacket);
	}

}
