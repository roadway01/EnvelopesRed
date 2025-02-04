package com.rrf.ssm.pojo;/**
 * @author : 邹智敏
 * @data : 2018年-01月-13日 18:30
 * @Description : 这里是此类的说明
 **/

/**
 * @author : 邹智敏
 * @data : 2018年-01月-13日 18:30
 * @Description : 微信正式发起微信支付所需要的字段
 **/
public class SignModel {

    private String timeStamp;
    private String nonceStr;
    private String signType;
    private String packages;
    private String paySign;

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public String getPackages() {
        return packages;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
