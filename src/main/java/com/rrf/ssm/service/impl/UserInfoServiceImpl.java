/**
 * 
 */
package com.rrf.ssm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rrf.ssm.dao.UserInfoModelDao;
import com.rrf.ssm.pojo.UserInfoModel;
import com.rrf.ssm.service.UserInfoService;

/*
 * @ClassName: UserInfoServiceImpl类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:43:22
 */
@Service("IUserInfo")
public class UserInfoServiceImpl implements UserInfoService{
	
	@Resource
	private UserInfoModelDao dao;
	
	@Override
	public int InsertUserInfo(UserInfoModel userInfoModel) {
		return dao.InsertUserInfo(userInfoModel);
	}

	@Override
	public int selectUserName(String memberName) {
		return dao.selectUserName(memberName);
	}

	@Override
	public int selectUserbalance(String id) {
		return dao.selectUserbalance(id);
	}

	@Override
	public int updateUserbalance(String id, String memberbalance) {
		
		return dao.updateUserbalance(id, memberbalance);
	}

	@Override
	public int updatememberName(String id, String membername) {
		return dao.updatememberName(id,membername);
	}
}
