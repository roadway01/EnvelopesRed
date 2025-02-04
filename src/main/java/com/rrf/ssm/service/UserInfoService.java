/**
 * 
 */
package com.rrf.ssm.service;

import com.rrf.ssm.pojo.UserInfoModel;
import org.apache.ibatis.annotations.Param;

/*
 * @ClassName: UserInfoService类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:42:08
 */
public interface UserInfoService {
	//插入用户信息
	public int InsertUserInfo(UserInfoModel userInfoModel);
	//查询用户
	public int selectUserName(String memberName);
	//查询该用户的余额
	public int selectUserbalance(String id);
	//修改用户余额的金额
	public int updateUserbalance(String id,String memberbalance);
	//修改用户的真是姓名
	public int updatememberName(String id,String membername);
}
