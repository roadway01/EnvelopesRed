/**
 * 
 */
package com.rrf.ssm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/*
 * @ClassName: JedisPool类
 * @Description:
 * @author 江西现代 - 邹智敏
 * @date 2018年2月27日	下午2:37:21
 */
public class JedisPoolUtils {
	
    private static JedisPool jedisPool = null;
    
    public Properties Info(){
    	Properties pro = null;
    	InputStream is = null;
    	try {
    		pro = new Properties();
    	    is = this.getClass().getResourceAsStream("/JedisConfig.properties");
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
    	return pro;
    }
    
    //创建一个redis的链接池
    public static JedisPool createPool(){
    	JedisPoolUtils util = new JedisPoolUtils();
		String addr = (String) util.Info().get("JEDIS_ADDR");
		int port = Integer.parseInt((String) util.Info().get("JEDIS_PORT"));
		String auth = (String) util.Info().get("JEDIS_AUTH");
		int maxActive = Integer.parseInt((String) util.Info().get("JEDIS_MAX_ACTIVE"));
		int maxIdle =  Integer.parseInt((String) util.Info().get("JEDIS_MAX_IDLE"));
		int maxWait = Integer.parseInt((String) util.Info().get("JEDIS_MAX_WAIT"));
		int timeout = Integer.parseInt((String) util.Info().get("JEDIS_TIMEOUT"));
		boolean testOnBorrow = Boolean.parseBoolean((String)util.Info().get("JEDIS_TEST_ON_BORROW"));
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxActive);
		config.setMaxWaitMillis(maxWait);
		config.setTestOnBorrow(testOnBorrow);
		jedisPool = new JedisPool(config,addr,port,timeout,auth);
		return jedisPool;
    }
	public static void main(String[] args) {
		Jedis jedis = createPool().getResource();
		jedis.hset("com.web.jedis", "name", "邹智敏");
		jedis.hset("com.web.jedis", "age", "12");
		jedis.hset("com.web.jedis", "sex", "男");
	}
}
