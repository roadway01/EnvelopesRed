package com.rrf.ssm.pojo;/**
 * @author : 邹智敏
 * @data : 2018年-01月-11日 15:57
 * @Description : 这里是此类的说明
 **/

/**
 * @author : 邹智敏
 * @data : 2018年-01月-11日 15:57
 * @Description : 这里是此类的说明
 **/
public class WxPayModel {
    private String FilePath;            //签名证书的路径地址
    private String openid;              //用户标识
    private String appid;               //小程序的appid
    private String mchid;               //商户号mchid
    private String key;                 //API安全秘钥
    private int ReadTimeoutMs;          //读取网络超时
    private int ConnectTimeoutMs;       //网络链接超时
    private String body;                //商品描述
    private String out_trade_no;        //商户订单号
    private String total_fee;           //支付总金额
    private String spbill_create_ip;    //终端ip 如：127.0.0.1
    private String notify_url;          //异步通知回调地址：这里要自定义一个接口
    private String trade_type;          //交易类型如：JSAPI
    private String timeStamp;           //时间戳
    private String nonceStr;            //随机串
    private String packages;            //数据包 参数是：prepay_id=wx2017033010242291fcfe0db70013231072
    private String signType;            //签名方式；如MD5

    public String getFilePath() {
        return FilePath;
    }

    public String getAppid() {
        return appid;
    }

    public String getMchid() {
        return mchid;
    }

    public String getKey() {
        return key;
    }

    public int getReadTimeoutMs() {
        return ReadTimeoutMs;
    }

    public int getConnectTimeoutMs() {
        return ConnectTimeoutMs;
    }

    public String getBody() {
        return body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getPackages() {
        return packages;
    }

    public String getSignType() {
        return signType;
    }

    public String getOpenid() {
        return openid;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setReadTimeoutMs(int readTimeoutMs) {
        ReadTimeoutMs = readTimeoutMs;
    }

    public void setConnectTimeoutMs(int connectTimeoutMs) {
        ConnectTimeoutMs = connectTimeoutMs;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
