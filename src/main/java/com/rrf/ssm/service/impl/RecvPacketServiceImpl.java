/**
 * 
 */
package com.rrf.ssm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import com.rrf.ssm.pojo.GetCashModel;
import org.springframework.stereotype.Service;
import com.rrf.ssm.dao.RecvPacketModelDao;
import com.rrf.ssm.pojo.RecvPacketModel;
import com.rrf.ssm.service.RecvPacketService;

/*
 * @ClassName: RecvPacketServiceImpl类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月3日	下午5:20:17
 */
@Service
public class RecvPacketServiceImpl implements RecvPacketService{

	
	@Resource
	private RecvPacketModelDao  recvPacketModelDao;
	
	@Override
	public String RecvPacket(RecvPacketModel recvPacketModel) {
		
		return recvPacketModelDao.RecvPacket(recvPacketModel);
	}

	@Override
	public List<RecvPacketModel> getMyRecv(String userId) {
		
		return recvPacketModelDao.getMyRecv(userId);
	}

	@Override
	public int withdraw(GetCashModel getCash) {
		
		return recvPacketModelDao.withdraw(getCash);
	}

	@Override
	public String getBalance(String userId) {
		
		return recvPacketModelDao.getBalance(userId);
	}

	@Override
	public int selectRecvOne(String recvid, String sendId) {
		return recvPacketModelDao.selectRecvOne(recvid,sendId);
	}

	@Override
	public GetCashModel selectGetCash(String memberid) {
		return recvPacketModelDao.selectGetCash(memberid);
	}

	@Override
	public boolean SaveRecvPacket(RecvPacketModel recvPacketModel) {
		return recvPacketModelDao.SaveRecvPacket(recvPacketModel);
	}


}
