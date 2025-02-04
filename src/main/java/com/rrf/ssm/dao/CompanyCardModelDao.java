/**
 * 
 */
package com.rrf.ssm.dao;

import java.util.List;

import com.rrf.ssm.pojo.CompanyCardModel;
import com.rrf.ssm.pojo.RecvCardModel;
import org.apache.ibatis.annotations.Param;

/*
 * @ClassName: CompanyCardModelDao类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:20:55
 */
public interface CompanyCardModelDao {
	//企业名片的插入
	int createCard(CompanyCardModel companyCardModel);
	//获取所有收到的名片
	List<CompanyCardModel> getRecvCards(String userId);
	//修改创建的企业名片信息
	int updateCardInfo(CompanyCardModel companyCardModel);
	//获取所有我创建的名片
	List<CompanyCardModel> getMyCreateCards(String UserId);
	//我获取的企业名片
	int getMeCompanyCard(RecvCardModel recvCardModel);
	//删除企业名片
	int deleteCompanyCard(@Param("cardid") String cardid);
}
