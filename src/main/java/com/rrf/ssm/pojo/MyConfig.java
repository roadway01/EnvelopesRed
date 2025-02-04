package com.rrf.ssm.pojo;/**
 * @author : 邹智敏
 * @data : 2018年-01月-09日 16:39
 * @Description : 我的微信小程序配置
 **/

import com.github.wxpay.sdk.WXPayConfig;
import com.rrf.ssm.pojo.WxPayModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author : 邹智敏
 * @data : 2018年-01月-09日 16:39
 * @Description : 这里是设置自己申请微信小程序参数，主要实现wxpayConfig这个类
 **/
public class MyConfig implements WXPayConfig {

    private WxPayModel model;

    public MyConfig(WxPayModel model) {
        this.model = model;
    }

    @Override
    public String getAppID() {
        return model.getAppid();
    }

    @Override
    public String getMchID() {
        return model.getMchid();
    }

    @Override
    public String getKey() {
        return model.getKey();
    }

    //获取证书
    @Override
    public InputStream getCertStream() {
        File file = new File(model.getFilePath());
        InputStream certStream = null;
        try{
            certStream = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            certStream.read(b);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(certStream != null){
                try{
                    certStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return certStream;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return model.getConnectTimeoutMs();
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return model.getReadTimeoutMs();
    }
}