/**
 * 
 */
package com.rrf.ssm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.rrf.ssm.pojo.*;
import com.rrf.ssm.service.ComPanyCardService;
import com.rrf.ssm.service.SendPacketService;
import com.rrf.ssm.service.impl.ComPanyCardServiceImpl;
import com.rrf.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.rrf.ssm.service.RecvPacketService;
import com.rrf.ssm.service.UserInfoService;
import redis.clients.jedis.Jedis;

/*
 * @ClassName: RecvPacketController类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年1月4日	上午11:11:13
 */
@Controller
@RequestMapping("/CRecvPacket")
public class RecvPacketController {

	private static Logger logger = Logger.getLogger(UserInfoController.class);

	private static XMLUtils xmlUtils = new XMLUtils();

	@Resource
	private RecvPacketService recvPacketService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private SendPacketService sendPacketService;
	@Resource
	private ComPanyCardService comPanyCardService;

	//此接口是判断该红包有没有领
	@RequestMapping(value="/recvInfo",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject recvInfo(@RequestParam(value="sendid",required=false)String sendid,
							   @RequestParam(value="userId",required=false)String userid){
		JSONObject json = new JSONObject();

		//查询数据库中有没有该用户，有,则领取红包，反之，告诉用户，您没有权限领取该红包
		int i = userInfoService.selectUserName(userid);
		if(i<=0){//表示该用户不在数据库中
			json.put("code",1);
			json.put("data",1);
			return json;
		}

		//领取红包之前，我要查看该用户是不是第一次领取的
		int count = recvPacketService.selectRecvOne(userid,sendid);
		if(count>=1){
			json.put("code",1);
			json.put("data",1);//表示该用户已经领取了该红包
		}else{
			json.put("code",0);
			json.put("data",0);
		}
		json.put("errmsg","ok");
		return json;
	}

	//领红包
	@RequestMapping(value="/RecvPacket",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject RecvPacket(@RequestBody RecvPacketModel recvPacketModel){

		JSONObject json = new JSONObject();

		//根据sendid查询，发送红包的金额和数量
		SendPacketModel model = sendPacketService.getMe(recvPacketModel.getSendId());

		//红包金额列表
		String redMoneyList = model.getOvermoney();
		String[] redmoneyStr = redMoneyList.split(",");
		if(Integer.parseInt(model.getPacketnumber())<=0 ||  redmoneyStr.length <= 0 ){
			json.put("code",0);
			json.put("errmsg","ok");
			json.put("data",-1);//表示红包领取完了
			return json;
		}

		//领取红包之前，我要查看该用户是不是第一次领取的
		int count = recvPacketService.selectRecvOne(recvPacketModel.getRecvId(),recvPacketModel.getSendId());
		if(count>=1){
			json.put("code",1);
			json.put("data",1);//表示该用户已经领取了该红包
			json.put("errmsg",1);
			return json;
		}
		//随机获取红包金额 ;
		String redMoney = RedEnvelopesUtils.getRandomNumber(redmoneyStr);

		//开始生成红包id和时间戳
		recvPacketModel.setPacketId(UuidUtils.uuid());
		recvPacketModel.setTimesTamp(DataTimeUtils.getData());
		recvPacketModel.setPacketMoney(redMoney);

		//记录领取红包成功
		if(recvPacketService.SaveRecvPacket(recvPacketModel)){

			//开始插入流水记录
			GetCashModel getCash = new GetCashModel();
			getCash.setId(UuidUtils.uuid());
			getCash.setMember_id(recvPacketModel.getRecvId());
			getCash.setMoney(recvPacketModel.getPacketMoney());
			getCash.setType("支入");
			getCash.setTimesTamp(DataTimeUtils.getData());
			recvPacketService.withdraw(getCash);

			//修改用户余额
			String mony = recvPacketService.getBalance(recvPacketModel.getRecvId());
			String memberbalance = String.valueOf(Double.valueOf(mony).intValue() + Double.valueOf(redMoney).intValue());
			userInfoService.updateUserbalance(getCash.getMember_id(), memberbalance);

			//2、修改该红包的金额和红包数量
			String number = String.valueOf(Integer.parseInt(model.getPacketnumber())-1);
			String recvnumber = String.valueOf(Integer.parseInt(model.getRecvnumber())+1);
			sendPacketService.updateMOneyAndNum(number,recvPacketModel.getSendId(),recvnumber,RedEnvelopesUtils.getNewStr(redmoneyStr,redMoney));

			//判断红包的类型,1表示企业红包，0表示普通红包
			String type = recvPacketModel.getPacketType();
			if(type.trim().equals("1")){
				//开始新增，我收到的企业名片
				RecvCardModel recvCardModel = new RecvCardModel();
				recvCardModel.setCardid(model.getCardid());
				recvCardModel.setMemberid(recvPacketModel.getRecvId());
				comPanyCardService.getMeCompanyCard(recvCardModel);
			}
			json.put("code",0);
			json.put("data",redMoney);
		}
		json.put("errmsg","ok");
		return json;
	}
	//我收到的红包
	@RequestMapping(value="/getMyRecv",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getMyRecv(@RequestParam(value="userId",required=false) String userId){
		JSONObject json = new JSONObject();
		List<RecvPacketModel> result = recvPacketService.getMyRecv(userId);
		if(result.size()>0 && result != null){
			//红包领取失败，返回金额是0
			json.put("code",0);
			json.put("data",result);
		}else{
			json.put("code",1);
			json.put("data",0);
		}
		json.put("errmsg","ok");
		return json;
	}
	//提现，插入资金流转记录
	@RequestMapping(value="/withdraw",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject withdraw(@RequestBody GetCashModel getCash, HttpServletRequest request) throws Exception{
		JSONObject json = new JSONObject();
		//先查询该用户的余额
		String mony = recvPacketService.getBalance(getCash.getMember_id());
		//用户余额还剩多少
		String memberbalance = String.valueOf((Double.valueOf(mony).intValue() - Double.valueOf(getCash.getMoney()).intValue()));
		logger.debug("{用户的余额是："+ mony+"分}");
		//先判断用户余额够不够，如果不够，取消提现，并提醒用户，余额不足
		if((Double.valueOf(memberbalance).intValue() < 0)){
			json.put("code",0);
			json.put("data","余额不足");
			json.put("errmsg","ok");
			return json;
		}else{
				Jedis jedis = JedisPoolUtils.createPool().getResource();
				//从redis中获取商品订单号
				String Partner_trade_no = jedis.get(getCash.getMember_id());
				logger.debug("获取redis中的值是："+Partner_trade_no);
				//判断session是否自动失效了
				if("".equals(Partner_trade_no) || Partner_trade_no == null) {
					json.put("code",1);
					json.put("data","提现取消");
					return json;
				}
				//先记录一条流水记录
				getCash.setType("支出");//默认的类型
				getCash.setId(UuidUtils.uuid());
				getCash.setTimesTamp(DataTimeUtils.getData());
				//先新增一条，用户可以提现记录
				int result = recvPacketService.withdraw(getCash);
				if(result>=1){
					//企业需要付款的金额
					Integer money = Double.valueOf(getCash.getMoney()).intValue();
					//用户的openid
					String openid = getCash.getMember_id();
					//签名证书的路径
					String FilePath = request.getSession().getServletContext().getRealPath("/WEB-INF")+"/SignFile/apiclient_cert.p12";
					//返回的transfers参数数据
					TransfersModel transfers = MyWeixinUtils.redPackets(money,openid,Partner_trade_no,FilePath);
					//获取xml数据
					String xml = MyWeixinUtils.getXml(transfers);
					//签名证书认证
					String response = SslUtils.ssl(xml,transfers);
					//解析签名证书
					Map<String, String> responseMap = xmlUtils.parseXml(response);

					//以下字段在return_code为SUCCESS的时候有返回 ; SYSTEMERROR
					String return_code = responseMap.get("return_code");
					//返回的错误信息
					String err_message = responseMap.get("err_code_des");
					//返回的结果集
					String result_code = responseMap.get("result_code");
					//支付成功 才有商户订单号返回
					if("SUCCESS".equals(result_code) && "SUCCESS".equals(return_code)){
						//删除redis中对应的值,确保订单号的唯一
						jedis.del("Partner_trade_no");
						//修改用户的余额
						int userresult = userInfoService.updateUserbalance(getCash.getMember_id(), memberbalance);
						json.put("code",0);
						json.put("data","提现成功");
					}else{
						json.put("code",1);
						json.put("data",err_message);
					}
				}
				json.put("errmsg","ok");
		}
		return json;
	}
	//查询余额
	@RequestMapping(value="/getBalance",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getBalance(@RequestParam(value="userId",required=false) String userId,HttpServletRequest request){
		JSONObject json = new JSONObject();
		String result = recvPacketService.getBalance(userId);
		logger.debug("{用户余额："+result+"}");
		String Partner_trade_no = UuidUtils.uuid();
		//这里开始设置redis
		Jedis jedis = JedisPoolUtils.createPool().getResource();
		jedis.set(userId,Partner_trade_no);
		logger.debug("设置redis中的值是："+Partner_trade_no);

		if(result.trim().equals("") && result.trim() == null){
			//红包领取失败，返回金额是0
			json.put("code",1);
			json.put("data","0");
		}else{
			//红包领取成功
			json.put("code",0);
			json.put("data",result);
		}
		json.put("errmsg","ok");
		return json;
	}
}
