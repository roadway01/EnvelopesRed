package com.rrf.ssm.controller;

import com.alibaba.fastjson.JSONObject;
import com.rrf.ssm.service.SendPacketService;
import com.rrf.ssm.utils.DataTimeUtils;
import com.rrf.ssm.utils.SignUtils;
import com.rrf.ssm.utils.SslUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 邹智敏
 * @data : 2018年-01月-15日 16:11
 * @Description : 图片上传签名接口
 **/
@Controller
@RequestMapping("/ImgFIleUpload")
public class ImgFIleUpload {

    private static Logger logger = Logger.getLogger(SendPacketController.class);

    @Resource
    private SendPacketService send;

    //对象存储的签名
    @RequestMapping(value="/sign",method = RequestMethod.POST)
    @ResponseBody
    public String getSign(@RequestParam(value="fileName",required=false) String fileName){
        logger.debug("{从前端获取来的图片名称："+fileName+"}");
        String signStr = SignUtils.getPeriodEffectiveSign("/"+fileName);
        return signStr;
    }

    //获取token
    private static String getToken(String appid,String secret) throws Exception{
        String path = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret+"&grant_type=client_credential";
        String jsontoken = SslUtils.getHtmlResouceUrlGet(path,"UTF-8");
        return JSONObject.parseObject(jsontoken).get("access_token").toString();
    }

    //二维码图片的路径
    @RequestMapping(value="/getQRcode",method = RequestMethod.POST)
    @ResponseBody
    public String getQRcode(String scene,String code,HttpServletRequest request) throws Exception{
        String path = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+getToken("wx557bddb9721a704f","6ca306b43a37843c2e8f6d84a30b857d");
        JSONObject json = new JSONObject();
        json.put("scene",scene);
        json.put("code",code);
        json.put("page","pages/getEnvelpe/envelop");
        //文件上传的路径
        String Filejia = request.getSession().getServletContext().getRealPath("/public/images/qrcode/");
        String filepath = "/"+DataTimeUtils.getDataTime()+".jpg";
        String fileTotal = Filejia+filepath;
        boolean falg = SslUtils.getHtmlResouceUrlPost(path,json,fileTotal);
        if(falg){
            String qqyImgPath = SignUtils.getFileUpload(fileTotal,Filejia);
            if(!qqyImgPath.equals("")){
                //修改分享的二维码
                send.updateRedPacketqrcode(scene,qqyImgPath);
                return qqyImgPath;
            }
        }
        return null;
    }

    //form表单文件上传
    private List<String> doUploadFile(CommonsMultipartFile files[], HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("text/html;charset=UTF-8");

        if(files==null || files.length==0) return null;

        String path=request.getSession().getServletContext().getRealPath("/public/images/userimg/");
        /*判断目录是否存在，如果不存在，则创建*/
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();

        List<String> lFiles=new ArrayList<String>();//已经成功的文件名
        for (int i = 0; i < files.length; i++) {
            // 获得原始文件名
            String fileName = files[i].getOriginalFilename();
            if (fileName.trim().equals("")) continue;
            // 新文件名
            String newFileName = DataTimeUtils.getDataTime()+fileName.substring(fileName.lastIndexOf("."));
            if (!files[i].isEmpty()) {
                FileOutputStream fos = null;
                InputStream in = null;
                try {
                    fos = new FileOutputStream(path+"/"+ newFileName);
                    in = files[i].getInputStream();
                    byte[] bytes = new byte[1024*2];
                    int b = 0;
                    while ((b = in.read(bytes)) != -1) {
                        fos.write(bytes,0,b);
                    }
                    fos.close();
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }finally{
                    if(in != null){
                        try{
                            in.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(fos != null){
                        try{
                            fos.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            lFiles.add(newFileName);
        }
        return lFiles;
    }

    @RequestMapping(value = "/UploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String UploadFile(@RequestParam("file") CommonsMultipartFile files[], HttpServletRequest request, HttpServletResponse response) throws java.io.IOException  {
        List<String> filelist =  doUploadFile(files,request,response);
        String path=request.getSession().getServletContext().getRealPath("/public/images/userimg/");
        if(filelist.size()<=0) return null;
        String qqyImgPath = null;
        for(int i = 0;i<filelist.size();i++){
            qqyImgPath = SignUtils.getFileUpload(path+"/"+filelist.get(i),path);
        }
        return qqyImgPath;
    }

    //获取openid
    @RequestMapping(value="/getOpenid",method = RequestMethod.POST)
    @ResponseBody
    public String getOpenid(String jscode) throws Exception{
        String path = "https://api.weixin.qq.com/sns/jscode2session?appid=wx557bddb9721a704f&secret=6ca306b43a37843c2e8f6d84a30b857d&js_code="+jscode+"&grant_type=client_credential";
        String OPENID = SslUtils.getHtmlResouceUrlGet(path,"UTF-8");
        return OPENID;
    }
}
