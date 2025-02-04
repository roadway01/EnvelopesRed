package com.rrf.ssm.utils;

import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import com.qcloud.cos.sign.Sign;
import com.rrf.ssm.controller.SendPacketController;
import com.rrf.ssm.pojo.FileInfo;
import com.rrf.ssm.pojo.TransfersModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author : 邹智敏
 * @data : 2018年-01月-15日 17:40
 * @Description : 这里是此类的说明
 **/
public class SignUtils {
    private static Logger logger = Logger.getLogger(SignUtils.class);
    /***
     * 获取图片签名
     * @param cosPath 上传路径
     * @return
     */
    public static String getPeriodEffectiveSign(String cosPath){
        FileInfo f=new FileInfo();
        Credentials cred = new Credentials(f.getAppId(), f.getSecretId(), f.getSecretKey());
        //过期时间
        long  expired = System.currentTimeMillis() / 1000 + 6000;
        String signStr = "";
        try {
            signStr = Sign.getPeriodEffectiveSign(f.getBucket(), cosPath, cred, expired);
        } catch (Exception e) {
            System.out.println("过期时间："+expired+"="+e.toString());
            e.printStackTrace();
        }
        return signStr;
    }

    //对象存储中的文件上传
    public static String getFileUpload(String imgTotalPath, String dirfile){
        FileInfo f=new FileInfo();
        // 初始化秘钥信息
        Credentials cred = new Credentials(f.getAppId(), f.getSecretId(), f.getSecretKey());
        // 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
        clientConfig.setRegion("gz");
        // 初始化cosClient
        COSClient cosClient = new COSClient(clientConfig, cred);
        //上传后的地址
        String imgpath = "/"+DataTimeUtils.getData()+".jpg";
        UploadFileRequest uploadFileRequest = new UploadFileRequest(f.getBucket(),imgpath, imgTotalPath);
        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
        String code = JSONObject.parseObject(uploadFileRet).get("code").toString();
        String sourceUrl = "";
        if(code.equals("0")){
            String data = JSONObject.parseObject(uploadFileRet).get("data").toString();
            sourceUrl = JSONObject.parseObject(data).get("source_url").toString();
            //递归删除文件
            try{
                /*dirfile 文件夹的路径*/
                deleteDir(dirfile);
            }catch (Exception e){
                logger.debug(e.getMessage());
            }
        }
       return sourceUrl;
    }

    //删除该文件夹中的图片
    private static boolean deleteDir(String path) {
        File file = new File(path);
        if(!file.exists()){//判断是否待删除目录是否存在
            file.mkdir();
            return false;
        }
        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for(String name : content){
            File temp = new File(path, name);
            if(temp.isDirectory()){//判断是否是目录
                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            }else{
                if(!temp.delete()){//直接删除文件
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }


    /**
     * @author : 邹智敏
     * @data : 2018/1/14
     * @Description : sign:生成企业付款的签名
     **/
    public static String createSendRedPackOrderSign(TransfersModel transfers){
        StringBuffer sign = new StringBuffer();
        sign.append("amount=").append(transfers.getAmount());
        sign.append("&check_name=").append(transfers.getCheck_name());
        sign.append("&desc=").append(transfers.getDesc());
        sign.append("&mch_appid=").append(transfers.getMch_appid());
        sign.append("&mchid=").append(transfers.getMchid());
        sign.append("&nonce_str=").append(transfers.getNonce_str());
        sign.append("&openid=").append(transfers.getOpenid());
        sign.append("&partner_trade_no=").append(transfers.getPartner_trade_no());
        sign.append("&spbill_create_ip=").append(transfers.getSpbill_create_ip());
        sign.append("&key=").append(transfers.getKey());
        return  DigestUtils.md5Hex(sign.toString()).toUpperCase();
    }

    public static void main(String[] args) {
        String imgTotalPath = "E:/IDEAcode/EnvelopesRed/src/main/webapp/public/images/3.jpg";
        getFileUpload(imgTotalPath,"E:/IDEAcode/EnvelopesRed/src/main/webapp/public/images/qrcode/");
    }
}
