package com.rrf.ssm.pojo;


import java.io.InputStream;
import java.util.Properties;



public class FileInfo {
	private int appId;
    private String secretId;
    private String secretKey;
    private String bucket;
	public int getAppId() {
		try {
			this.appId = Integer.parseInt(this.info().getProperty("appId"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appId;
	}
	public void setAppId(int appId) {
	 
			this.appId = appId;
		 
	}
	public String getSecretId() {
		try {
			this.secretId = this.info().getProperty("secretId");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return secretId;
	}
	public void setSecretId(String secretId) {
		 
			this.secretId =secretId;
	}
	public String getSecretKey() {
		try {
			this.secretKey = this.info().getProperty("secretKey");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		 
			this.secretKey = secretKey;
		 
	}
	public String getBucket() {
		try {
			this.bucket = this.info().getProperty("bucket");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bucket;
	}
	public void setBucket(String bucket) {
	 this.bucket = bucket;
		 
	}
	
	
	public Properties info() throws Exception{
		    Properties prop = new Properties();// 属�?集合对象   
		    InputStream is=this.getClass().getResourceAsStream("/CosFile.properties");
            prop.load(is);
            is.close(); 
			return prop;
	}

}
