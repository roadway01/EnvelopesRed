package com.rrf.ssm.utils;


import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.rrf.ssm.pojo.MyConfig;
import com.rrf.ssm.pojo.TransfersModel;
import com.rrf.ssm.pojo.WxPayModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 邹智敏
 * @data : 2018/1/13
 * @Description : 微信支付和企业付款统一开发工具类
 **/
public class MyWeixinUtils {
    private static XMLUtils xmlUtils = new XMLUtils();
   /**
    * @author : 邹智敏
    * @data : 2018/1/13
    * @Description : 设置微信支付的所需要的参数值
    **/
    public static WxPayModel GetUser(String FilePath,String out_trade_no,String money,String ip,String url,String prepay_id,String openid)throws Exception{
        WxPayModel model = new WxPayModel();
        //小程序的appid
        model.setAppid("wx557bddb9721a704f");
        //商户号mchid
        model.setMchid("1494165302");
        //API安全秘钥
        model.setKey("wwz20180112qyhbhit56468984linwei");
        //签名证书的路径地址
        model.setFilePath(FilePath);
        //读取网络超时
        model.setReadTimeoutMs(8000);
        //网络链接超时
        model.setConnectTimeoutMs(8000);
        //商品描述
        model.setBody("发红包");
        //商户订单号
        model.setOut_trade_no(out_trade_no);
        //支付总金额
        model.setTotal_fee(money);
        //终端ip 如：127.0.0.1
        model.setSpbill_create_ip(ip);
        //异步通知回调地址：这里要自定义一个接口
        model.setNotify_url(url);
        //交易类型如：JSAPI
        model.setTrade_type("JSAPI");
        //时间戳
        model.setTimeStamp(DataTimeUtils.getData());
        //随机串
        model.setNonceStr(WXPayUtil.generateNonceStr());
        //签名方式；如MD5
        model.setSignType("MD5");
        //包数据
        model.setPackages(prepay_id);
        //用户标识
        model.setOpenid(openid);
        return model;
    }
    /**
     * @author : 邹智敏
     * @data : 2018/1/13
     * @Description : 调用wx.requestPayment(OBJECT)发起微信支付，所需要的参数值
     * timeStamp	String	是	时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间
     * nonceStr	    String	是	随机字符串，长度为32个字符以下。
     * package	    String	是	统一下单接口返回的 prepay_id 参数值，提交格式如：prepay_id=*
     * signType	    String	是	签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
     * paySign	    String	是	签名,具体签名方案参见微信公众号支付帮助文档;
     **/
    public static Map<String, String> generateSignature(WxPayModel model) throws Exception{
        Map<String, String> data = new HashMap<>();
        data.put("appId",model.getAppid());
        data.put("timeStamp",model.getTimeStamp());
        data.put("nonceStr",model.getNonceStr());
        data.put("package",model.getPackages());
        data.put("signType",model.getSignType());
        data.put("paySign",WXPayUtil.generateSignature(data,model.getKey()));
        return data;
    }

    /**
     * @author : 邹智敏
     * @data : 2018/1/13
     * @Description : 统一支付下单
     **/
    public static Map<String, String> requestToPay(WxPayModel model) throws Exception{
        WXPay wxPay = new WXPay(new MyConfig(model), WXPayConstants.SignType.MD5);
        Map<String, String> reqData = new HashMap<>();
        reqData.put("body",model.getBody());
        reqData.put("out_trade_no",model.getOut_trade_no());
        reqData.put("total_fee",model.getTotal_fee());
        reqData.put("spbill_create_ip",model.getSpbill_create_ip());
        reqData.put("notify_url",model.getNotify_url());
        reqData.put("trade_type",model.getTrade_type());
        reqData.put("openid",model.getOpenid());
        Map<String, String> resp = wxPay.unifiedOrder(reqData);
        return resp;
    }


    /**
     * @author : 邹智敏
     * @data : 2018/1/14
     * @Description : 设置企业付款中的参数值
     **/
    public static TransfersModel redPackets(Integer money, String openId,String Partner_trade_no,String FilePath) throws Exception {
        TransfersModel transfers=new TransfersModel();
        //设置随机字符串
        String nonce = WXPayUtil.generateNonceStr();
        // 自己的公众账号
        transfers.setMch_appid("wx557bddb9721a704f");
        //自己的 商户号
        transfers.setMchid("1494165302");
        // 随机字符串
        transfers.setNonce_str(nonce);
        // 用户openId
        transfers.setOpenid(openId);
        // 校验用户姓名选项
        transfers.setCheck_name("NO_CHECK");
        // 付款金额
        transfers.setAmount(money);
        // 企业付款描述信息
        transfers.setDesc("微信企业付款");
        // 调用接口的机器Ip地址
        transfers.setSpbill_create_ip("120.0.0.1");
        // 商户订单号
        transfers.setPartner_trade_no(Partner_trade_no);
        //设置key
        transfers.setKey("wwz20180112qyhbhit56468984linwei");
        //生成签名
        String sign = SignUtils.createSendRedPackOrderSign(transfers);
        //设置签名
        transfers.setSign(sign);
        //设置企业付款的接口
        transfers.setUrl("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
        //设置签名证书的结果
        transfers.setFilePath(FilePath);

        return transfers;
    }

    public static String getXml(TransfersModel transfers){

        xmlUtils.xstream().alias("xml", transfers.getClass());

        String xml = xmlUtils.xstream().toXML(transfers);

        return xml;
    }

    public static void main(String[] args) throws Exception{

        //企业需要付款的金额
        Integer money = 100;
        //用户的openid
        String openid = "oEasD5tnWGZR4WCsr8AaVw6X9BP4";
        for(int i = 0;i<3;i++){
            //商品订单号
            String Partner_trade_no = UuidUtils.uuid();
            //签名证书的路径
            String FilePath = "c://WxSignBook/apiclient_cert.p12";
            //返回的transfers参数数据
            TransfersModel transfers = redPackets(money,openid,Partner_trade_no,FilePath);
            //获取xml数据
            String xml = getXml(transfers);
            //签名证书认证
            String response = SslUtils.ssl(xml,transfers);
            System.out.println("签名证书认证："+response);
            //解析签名证书
            Map<String, String> responseMap = xmlUtils.parseXml(response);
            System.out.println("解析签名证书："+responseMap);
        }

       /* //获取返回的信息
        String result_code = responseMap.get("result_code");
        String err_message = responseMap.get("err_code_des");*/
        //打印结果
        //System.out.println("企业付款结果"+result_code+";\t错误原因："+err_message);

    }
}
