/**
 * 
 */
package com.rrf.ssm.dao;

import org.apache.ibatis.annotations.Param;

import com.rrf.ssm.pojo.UserInfoModel;

/**
 * @ClassName: UserInfoModelDao类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午4:56:59
 */
public interface UserInfoModelDao {
	//插入用户信息
	int InsertUserInfo(UserInfoModel userInfoModel);
	//判断用户存不存在
	int selectUserName(@Param("memberid") String memberid);
	//查询该用户的余额
	int selectUserbalance(String id);
	//修改用户余额的金额
	int updateUserbalance(@Param("memberid")String id,@Param("memberbalance")String memberbalance);
	//修改用户的真是姓名
	int updatememberName(@Param("memberid")String id,@Param("membername")String membername);
}
