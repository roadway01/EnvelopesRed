/**
 * 
 */
package com.rrf.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.rrf.ssm.pojo.RecvCardModel;
import org.springframework.stereotype.Service;

import com.rrf.ssm.dao.CompanyCardModelDao;
import com.rrf.ssm.pojo.CompanyCardModel;
import com.rrf.ssm.service.ComPanyCardService;

/*
 * @ClassName: ComPanyCardServiceImpl类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月3日	上午9:47:20
 */
@Service("IComPanyCard")
public class ComPanyCardServiceImpl implements ComPanyCardService{

	@Resource
	private CompanyCardModelDao companyCardModelDao;
	
	@Override
	public int createCard(CompanyCardModel companyCardModel) {
		return companyCardModelDao.createCard(companyCardModel);
	}

	@Override
	public List<CompanyCardModel> getRecvCards(String userId) {
		return companyCardModelDao.getRecvCards(userId);
	}

	@Override
	public int updateCardInfo(CompanyCardModel companyCardModel) {
		return companyCardModelDao.updateCardInfo(companyCardModel);
	}

	@Override
	public List<CompanyCardModel> getMyCreateCards(String UserId) {
		return companyCardModelDao.getMyCreateCards(UserId);
	}

	@Override
	public int getMeCompanyCard(RecvCardModel recvCardModel) {
		return companyCardModelDao.getMeCompanyCard(recvCardModel);
	}

	@Override
	public int deleteCompanyCard(String cardid) {
		return companyCardModelDao.deleteCompanyCard(cardid);
	}

}
