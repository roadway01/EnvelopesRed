/**
 * 
 */
package com.rrf.ssm.controller;

import java.util.UUID;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.rrf.ssm.pojo.CompanyCardModel;
import com.rrf.ssm.service.ComPanyCardService;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.rrf.ssm.pojo.UserInfoModel;
import com.rrf.ssm.service.UserInfoService;

/*
 * @ClassName: UserInfoController类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月2日	下午5:44:23
 */
@Controller
@RequestMapping("/UserInfo")
public class UserInfoController {

	private static Logger logger = Logger.getLogger(UserInfoController.class);
	
	@Resource(name="IUserInfo")
	private UserInfoService userInfoService;
	@Resource
	private ComPanyCardService comPanyCardService;
	
	//用户信息新增
	@RequestMapping(value="/InsertUserInfo",method = {RequestMethod.POST})
	@ResponseBody
	public JSONObject InsertUserInfo(@RequestBody UserInfoModel userInfoModel){
		JSONObject json = new JSONObject();
		//查询数据库中有没有该用户，有,则不新建，反之，新建
		int i = userInfoService.selectUserName(userInfoModel.getMemberid());
		//如果没有该用户，则进行用户信息的新增
		if(i<=0){
			userInfoModel.setMemberbalance("0"); //账户余额,默认是0
			int inserResult = userInfoService.InsertUserInfo(userInfoModel);//返回的结果
			if(inserResult>=1){
				json.put("codel",0);//0表示成功，1表示失败
				json.put("data",inserResult);
			}else{
				json.put("code",1);
				json.put("data",0);
			}
		}else{
			json.put("code",1);
			json.put("data",-1);
		}
		json.put("errmsg","ok");
		return json;
	}
}
