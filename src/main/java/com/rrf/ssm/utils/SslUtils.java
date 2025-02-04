package com.rrf.ssm.utils;

import com.alibaba.fastjson.JSONObject;
import com.rrf.ssm.pojo.TransfersModel;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

/**
 * @author : 邹智敏
 * @data : 2018年-01月-15日 17:48
 * @Description : 使用ssl进行签名验证
 **/
public class SslUtils {
    //证书
    public static String ssl(String xml,TransfersModel transfers){
        StringBuffer message = new StringBuffer();
        try {
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");

            FileInputStream instream = new FileInputStream(new File(transfers.getFilePath()));

            keyStore.load(instream, transfers.getMchid().toCharArray());

            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, transfers.getMchid().toCharArray())
                    .build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();

            HttpPost httpost = new HttpPost(transfers.getUrl());
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(xml, "UTF-8"));

            CloseableHttpResponse response = httpclient.execute(httpost);

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        message.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
                httpclient.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return message.toString();
    }
    /**
     * 获取网页源代码
     * @param url 网页链接
     * @param encoding 网页编码集
     * @RequestMethod 请求方式GET
     * @return String
     */
    public static String getHtmlResouceUrlGet(String url, String encoding){
        InputStreamReader isr = null;
        BufferedReader reader = null;
        StringBuffer strb = new StringBuffer();
        try {
            //建立网络连接
            URL htmlUrl = new URL(url);
            //打开网络
            HttpsURLConnection uc = (HttpsURLConnection )htmlUrl.openConnection();
            //设置请求方式
            uc.setRequestMethod("GET");
            //设置请求超时
            uc.setReadTimeout(8000);
            //设置请求方式
            uc.setConnectTimeout(8000);
            //建立写入流
            isr = new InputStreamReader(uc.getInputStream(),encoding);
            //建立缓存区
            reader = new BufferedReader(isr);
            //建立临时文件
            String temp = null;
            while((temp=reader.readLine()) != null){
                strb.append(temp+"\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("网络连接建立失败!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("网络没连接或网络不稳定!");
        }
        return strb.toString();
    }

    /**
     * 获取网页源代码
     * @param url 网页链接
     * @RequestMethod 请求方式POST
     * @return String
     */
    public static boolean getHtmlResouceUrlPost(String url, JSONObject json,String imgpath){
        URL u = null;
        HttpsURLConnection con = null;
        try {
            u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(json.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        InputStream isr = null;
        FileOutputStream out = null;
        try {
            //一定要有返回值，否则无法把请求发送给server端。
            isr = con.getInputStream();
            out= new FileOutputStream(new File(imgpath));
            byte[] b = new byte[1024*2];
            int temp=0;
            while ((temp = isr.read(b)) != -1) {
                out.write(b,0,temp);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(isr != null){
                try{
                    isr.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(out != null){
                try{
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
