/**
 * 
 */
package com.rrf.ssm.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.rrf.ssm.pojo.ContactModel;
import com.rrf.ssm.service.UserInfoService;
import com.rrf.ssm.utils.UuidUtils;
import com.rrf.ssm.utils.json.JSONAndObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.rrf.ssm.pojo.CompanyCardModel;
import com.rrf.ssm.service.ComPanyCardService;

/*
 * @ClassName: ComPanyCardController类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月3日	上午9:49:29
 */
@Controller
@RequestMapping("/ComPanyCard")
public class ComPanyCardController {
	private static Logger logger = Logger.getLogger(ComPanyCardController.class);
	@Resource
	private ComPanyCardService comPanyCardService;
	@Resource
	private UserInfoService userInfoService;

	//企业名片的插入
	@RequestMapping(value="/createCard",method={RequestMethod.POST})
	@ResponseBody
	public JSONObject createCard(HttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String json= JSONAndObject.GetPostData(request.getInputStream(), request.getContentLength(), "utf-8");
			CompanyCardModel companyCardModel = JSON.parseObject(json,CompanyCardModel.class);
			String membername = companyCardModel.getMembername();
			companyCardModel.setCardid(UuidUtils.uuid());	//这里是名片的id
			companyCardModel.setState(1);					//1表示正常，0表示异常
			int createResult =  comPanyCardService.createCard(companyCardModel);
			if(createResult>=1){
				jsonObject.put("codel",0);//0表示成功，1表示失败
				jsonObject.put("data",companyCardModel.getCardid());
			}else{
				jsonObject.put("code",1);
				jsonObject.put("data",0);
			}
			jsonObject.put("errmsg","ok");
		}catch(Exception e){
			e.printStackTrace();
		}
		return jsonObject;
	}
	//获取我收到的名片
	@RequestMapping(value="/getRecvCards",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getRecvCards(@RequestParam(value="userId",required=false) String userId){
		JSONObject json = new JSONObject();
		List<CompanyCardModel>  result = comPanyCardService.getRecvCards(userId);
		for (CompanyCardModel model:result){
			ContactModel cantactModel = JSON.parseObject(model.getContact(),ContactModel.class);
			model.setContactModel(cantactModel);
		}
		if(result.size()>0 && result !=null){
			json.put("codel",0);//0表示有数据
			json.put("data",result);
		}else{
			json.put("code",1);//1表示没有数据
			json.put("data",0);
		}
		json.put("errmsg","ok");
		return json;
	}
	//修改创建的企业名片信息
	@RequestMapping(value="/updateCardInfo",method={RequestMethod.POST})
	@ResponseBody
	public JSONObject updateCardInfo(HttpServletRequest request,HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			//获取前端传过来的json数据
			String json= JSONAndObject.GetPostData(request.getInputStream(), request.getContentLength(), "utf-8");
			CompanyCardModel companyCardModel = JSON.parseObject(json,CompanyCardModel.class);
			companyCardModel.setState(1);					//1表示正常，0表示异常
			int createResult =  comPanyCardService.updateCardInfo(companyCardModel);
			if(createResult>=1){
				jsonObject.put("codel",0);//0表示成功，1表示失败
				jsonObject.put("data",createResult);
			}else{
				jsonObject.put("code",1);
				jsonObject.put("data",0);
			}
			jsonObject.put("errmsg","ok");
			return jsonObject;
		}catch (Exception e){
			e.printStackTrace();
			return jsonObject;
		}
	}
	//获取所有我创建的名片
	@RequestMapping(value="/getMyCreateCards",method={RequestMethod.POST})
	@ResponseBody
	public JSONObject getMyCreateCards(@RequestParam(value="userId",required=false) String userId){
		JSONObject json = new JSONObject();
		List<CompanyCardModel> result = comPanyCardService.getMyCreateCards(userId);
		for (CompanyCardModel model:result){
			ContactModel cantactModel = JSON.parseObject(model.getContact(),ContactModel.class);
			model.setContactModel(cantactModel);
		}
		if(result != null && result.size()>0){
			json.put("codel",0);//0表示成功，1表示失败
			json.put("data",result);
		}else{
			json.put("code",1);
			json.put("data",0);
		}
		json.put("errmsg","ok");
		return json;
	}
	//删除企业名片
	@RequestMapping(value="/deleteCompanyCard",method={RequestMethod.POST})
	@ResponseBody
	public JSONObject deleteCompanyCard(@RequestParam(value="cardid",required=false) String cardid){
		JSONObject json = new JSONObject();
		int result = comPanyCardService.deleteCompanyCard(cardid);
		if(result>=1){
			json.put("code",0);//0表示成功，1表示失败
			json.put("data",result);
		}else{
			json.put("code",1);
			json.put("data",0);
		}
		json.put("errmsg","ok");
		return json;
	}
}
