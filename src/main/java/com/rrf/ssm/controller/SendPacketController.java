package com.rrf.ssm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.rrf.ssm.pojo.*;
import com.rrf.ssm.service.UserInfoService;
import com.rrf.ssm.utils.DataTimeUtils;
import com.rrf.ssm.utils.MyWeixinUtils;
import com.rrf.ssm.utils.RedEnvelopesUtils;
import com.rrf.ssm.utils.UuidUtils;
import com.rrf.ssm.utils.json.JSONAndObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.rrf.ssm.service.RecvPacketService;
import com.rrf.ssm.service.SendPacketService;

/*
 * @ClassName: SendPacketController类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月3日	下午1:44:24
 */
@Controller
@RequestMapping("/SendPacket")
public class SendPacketController {
	private static Logger logger = Logger.getLogger(SendPacketController.class);
	@Resource
	private SendPacketService sendPacketService;
	@Resource
	private RecvPacketService recvPacketService;
	@Resource
	private UserInfoService userInfoService;

	//用户确定发红包
	@RequestMapping(value="/SendPacket",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject SendPacket(HttpServletRequest request, HttpServletResponse response) throws Exception{

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		//获取前端传过来的json数据
		String json= JSONAndObject.GetPostData(request.getInputStream(), request.getContentLength(), "utf-8");

		JSONObject jsonObject = new JSONObject();
		//json转对象
		SendPacketModel sendPacket = JSON.parseObject(json,SendPacketModel.class);
		//用户下单的金额
		String money = sendPacket.getPacketmoney();
		//用户余额
		int balance = userInfoService.selectUserbalance(sendPacket.getSenderId());

		int redmoney = (int)(Double.valueOf(money).intValue()/1.01);

		System.out.println("红包金额："+redmoney);

		//服务费
		int serviceMoney =Double.valueOf(money).intValue()-redmoney;

		//用户余额减服务费的所剩的用户金额
		int surplus = balance-serviceMoney;
		//用户需要支付，默认是0元
		int NeedToPay = 0;
		//判断用户余额够不够支付服务费
		if(surplus>=0){
			//用户红包金额-微信支付服务金额
			NeedToPay = (int)(Double.valueOf(money).intValue()-serviceMoney);
			//修改用户余额金额
			userInfoService.updateUserbalance(sendPacket.getSenderId(),String.valueOf(surplus));
		}else{
			//用户应该支付
			NeedToPay = redmoney+Math.abs(surplus);
			//修改用户余额金额
			userInfoService.updateUserbalance(sendPacket.getSenderId(),"0");
		}
		//用户的openid
		String openid =sendPacket.getSenderId();
		//支付终端就用本机ip
		String ip ="127.0.0.1";
		//notify_url：微信支付结果通知地址
		String url = "https://redpacket.21d.me/EnvelopesRed/SendPacket/callback";
		//设置商户订单号
		String out_trade_no = UuidUtils.uuid();
		//设置WxPayModel所需的参数
		WxPayModel model= MyWeixinUtils.GetUser(null,out_trade_no,String.valueOf(NeedToPay),ip,url,null,openid);
		//开始下订单，并取值判断，成功与否
		Map<String, String> result = MyWeixinUtils.requestToPay(model);
		logger.debug("{微信下单，返回的结果：}"+result);
		//获取下单的结果
		String result_code = result.get("result_code");
		String err_code_des = result.get("err_code_des");

		//如果支付成功
		if(result_code != null && result_code.equals("SUCCESS")){
			//获取签名所需的数据，prepay_id
			String prepay_id = MyWeixinUtils.requestToPay(model).get("prepay_id");
			logger.debug("{获取prepay_id:" + prepay_id + "}");
			//设置packages所需的数据，如：prepay_id=wxpay15621515151
			model.setPackages("prepay_id="+prepay_id);
			//开始生成签名
			Map<String, String> map = MyWeixinUtils.generateSignature(model);
			//把商户订单号作为红包的id
			sendPacket.setSendId(out_trade_no);
			//设置发红红包的金额
			sendPacket.setPacketmoney(String.valueOf(redmoney));
			//设置发送红包的时间搓
			sendPacket.setTimestamp(DataTimeUtils.getData());
			sendPacket.setState("0");//0表示红包状态是关闭的，还没有开放领取，1表示可以领取了
			sendPacket.setRecvnumber("0");//领取红包的数量
			//红包列表
			String redpacketMoneyList = RedEnvelopesUtils.splitRedPackets(redmoney,Integer.parseInt(sendPacket.getPacketnumber()));
			logger.debug("{红包金额的列表：}"+redpacketMoneyList);
			//设置红包金额的列表
			sendPacket.setOvermoney(redpacketMoneyList);
			//开始记录发红包的表记录
			if(sendPacketService.SendPacket(sendPacket) >=1){
				map.put("sendid",out_trade_no);
				jsonObject.put("code",0);
				jsonObject.put("data",map);
			}else{
				jsonObject.put("code",1);
				jsonObject.put("data",0);
			}
		}else{
			jsonObject.put("code",1);
			jsonObject.put("data",err_code_des);
		}
		jsonObject.put("errmsg","ok");
		return jsonObject;
	}
	/**
	 * @author : 邹智敏
	 * @data : 2018/1/15
	 * @Description : 微信支付的回调处理
	 **/
	@RequestMapping(value = "/callback")
	public @ResponseBody String callBack(HttpServletRequest request,HttpServletResponse response)throws Exception {

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String notityXml = JSONAndObject.GetPostData(request.getInputStream(), request.getContentLength(), "utf-8");
			logger.debug("{微信异步回调结果：}"+notityXml);

			//将xml格式的文件转换为map格式的数据
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(notityXml);

			//获取错误代码描述
			String err_code_des = notifyMap.get("err_code_des");
			//业务结果
			String result_code = notifyMap.get("result_code");
			logger.debug("{用户支付成功？}"+result_code);
			if(result_code!=null && result_code.equals("SUCCESS")){

				//红包的订单号
				String out_trade_no = notifyMap.get("out_trade_no");
				//红包的金额
				String money= notifyMap.get("total_fee");
				//红包的openid
				String openid = notifyMap.get("openid");

				WxPayModel model  = MyWeixinUtils.GetUser(
						null,out_trade_no,money,
						null,null,null,openid);

				MyConfig config  = new MyConfig(model);
				WXPay wxpay = new WXPay(config);

				//判断签名是否正确，防止数据泄漏导致出现“假通知”，造成资金损失。
				if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
					logger.debug("{签名正确：进行红包的新增操作}");
					//开始修改红包的状态
					sendPacketService.updateRedPacketState(out_trade_no,"1");
					//支付完成的时间
					String times = notifyMap.get("time_end");
					//2、记录发红包的流水账单，其中:账单的类型是：支出
					GetCashModel getCash = new GetCashModel();
					getCash.setId(out_trade_no);
					getCash.setMember_id(openid);
					getCash.setMoney(money);
					getCash.setType("支出");
					getCash.setTimesTamp(times);
					//开始记录流水账单
					int result = recvPacketService.withdraw(getCash);
					if (result >= 1) {
						return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
					} else {
						return "<xml><return_code><![CDATA[FAILED]]></return_code><return_msg><![CDATA[update db failed]]></return_msg></xml>";
					}

				} else {
					return "<xml><return_code><![CDATA[FAILED]]></return_code><return_msg><![CDATA[verify sign error]]></return_msg></xml>";
				}
			}else{
				return "<xml><return_code><![CDATA[FAILED]]></return_code><return_msg><![CDATA["+err_code_des+"]]></return_msg></xml>";
			}
	}

	//转发
	@RequestMapping("/Forward")
	@ResponseBody
	public JSONObject Forward(@RequestParam(value="sendId",required=false) String sendId){
		JSONObject json = new JSONObject();
		SendPacketModel result = sendPacketService.getMe(sendId);
		if(result.getPackettype().trim().equals("1")){//1表示企业红包
			result = sendPacketService.Forward(sendId);
			if(result == null){
				json.put("code", 1);
				json.put("data",0);
				json.put("errmsg","ok");
				return json;
			}
			String imglist = result.getImgUrls();
			//获取联系方式
			ContactModel cantactModel = JSON.parseObject(result.getCompany().getContact(),ContactModel.class);
			CompanyCardModel model = new CompanyCardModel();
			model.setContactModel(cantactModel);
			model.setCardName(result.getCompany().getCardName());
			model.setBusinessInfo(result.getCompany().getBusinessInfo());
			result.setCompany(model);
			if(imglist != null && !imglist.trim().equals("{}") && imglist.length()!=0){
				result.setImglist(JSONAndObject.getLIst(imglist));
			}
		}
		if(result.getPackettype().trim().equals("0")){//0表示普通红包
			result = sendPacketService.notConmpanyForward(sendId);
			if(result == null){
				json.put("code", 1);
				json.put("data",0);
				json.put("errmsg","ok");
				return json;
			}
			String imglist = result.getImgUrls();
			if(imglist != null && !imglist.trim().equals("{}") && imglist.length()!=0){
				result.setImglist(JSONAndObject.getLIst(imglist));
			}
		}
		if(result != null){
			json.put("code", 0);
			json.put("data",result);
		}else {
			json.put("code", 1);
			json.put("data",0);
		}
		json.put("errmsg","ok");
		return json;
	}

	//获取单个红包领取的详细信息
	@RequestMapping("/getSendInfo")
	@ResponseBody
	public JSONObject getSendInfo(@RequestParam(value="sendId",required=false) String sendId){
		JSONObject json = new JSONObject();
		List<SendPacketModel> result = sendPacketService.getSendInfo(sendId);
		if(result!=null && result.size() > 0){
			json.put("code", 0);
			json.put("data",result);
		}else {
			json.put("code", 1);
			json.put("data",0);
		}
		json.put("errmsg","ok");
		return json;
	}
	
	//获取我发出的红包
	@RequestMapping("/getMySend")
	@ResponseBody
	public JSONObject  getMySend(@RequestParam(value="userId",required=false) String userId){
		JSONObject json = new JSONObject();
		List<SendPacketModel> result = sendPacketService.getMySend(userId);
		if(result!=null && result.size() >0){
			json.put("code", 0);
			json.put("data",result);
		}else {
			json.put("code", 1);
			json.put("data",0);
		}
		json.put("errmsg","ok");
		return json;
	}
}
