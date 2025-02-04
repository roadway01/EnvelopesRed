package com.rrf.ssm.pojo;/**
 * @author : 邹智敏
 * @data : 2018年-01月-13日 14:38
 * @Description : 企业支付所需的参数
 **/

/**
 * @author : 邹智敏
 * @data : 2018年-01月-13日 14:38
 * @Description : 企业付款类
 **/
public class TransfersModel {

    // 公众账号
    private String mch_appid;
    // 商户号
    private String mchid;
    //设备号
    private String device_info;
    // 随机字符串
    private String nonce_str;
    // 签名
    private String sign;
    //商户订单号
    private String partner_trade_no;
    //用户openid
    private String openid;
    //校验用户姓名选项  NO_CHECK：不校验真实姓名
    private String check_name;
    //收款用户姓名
    private String re_user_name;
    // 付款金额
    private Integer amount;
    // 企业付款描述信息
    private String desc;
    // 调用接口的机器Ip地址
    private String spbill_create_ip;
    //自己申请的key
    private String key;
    //企业付款的接口地址
    private String url;
    //签名证书的路径
    private String FilePath;

    public String getUrl() {
        return url;
    }

    public String getFilePath() {
        return FilePath;
    }

    public String getKey() {
        return key;
    }

    public String getMch_appid() {
        return mch_appid;
    }

    public String getMchid() {
        return mchid;
    }

    public String getDevice_info() {
        return device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public String getOpenid() {
        return openid;
    }

    public String getCheck_name() {
        return check_name;
    }

    public String getRe_user_name() {
        return re_user_name;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getDesc() {
        return desc;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }
}
