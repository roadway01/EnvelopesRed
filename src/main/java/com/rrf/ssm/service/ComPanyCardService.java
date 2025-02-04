/**
 * 
 */
package com.rrf.ssm.service;

import java.util.List;

import com.rrf.ssm.pojo.CompanyCardModel;
import com.rrf.ssm.pojo.RecvCardModel;
import org.apache.ibatis.annotations.Param;

/*
 * @ClassName: ComPanyCardService类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月3日	上午9:46:19
 */
public interface ComPanyCardService {
	//企业名片的插入
	public int createCard(CompanyCardModel companyCardModel);
	//获取所有收到的名片
	public List<CompanyCardModel> getRecvCards(String userId);
	//修改创建的企业名片信息
	public int updateCardInfo(CompanyCardModel companyCardModel);
	//获取所有我创建的名片
	public List<CompanyCardModel> getMyCreateCards(String UserId);
	//我获取的企业名片
	public int getMeCompanyCard(RecvCardModel recvCardModel);
	//删除企业名片
	int deleteCompanyCard(String cardid);
}
