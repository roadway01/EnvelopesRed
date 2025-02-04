package com.rrf.ssm.utils.json;
import org.apache.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**************************************************************************
 * 实现JSON格式的数据读取操作
 * 
 **************************************************************************/
public class JSONAndObject {
	private static Logger logger = Logger.getLogger(JSONAndObject.class);
	/*获取页面传来的JSON参数，转为JSON对象*/
	public static String GetPostData(InputStream in, int size, String charset) {
		if (in != null && size > 0) {
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader=null;
			try{
				reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				String line=null;
				while((line = reader.readLine())!=null){
					buffer.append(line);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(null!=reader){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return buffer.toString();
		}
		else
			return null;
	}

	/*
	* 例如数据： "{"key1":"http://www.asdga.com/15151.jpg","key2":"http://www.asga.com/18181818.jpg"}"
	* 转成：[{"key1=http://www.asdga.com/15151.jpg"}, {"key2=http://www.asga.com/18181818.jpg"}, {"key3=http://www.asga.com/18181818.jpg"}]
	*
	* */
	public static List<String> getLIst(String text){
		text = text.substring(1,text.length()-1);
		System.out.println(text);
		String[] str1 = text.split("\",\"");
		List<String> list = new ArrayList<>();
		for(int i = 0;i<str1.length;i++){
			String[] str2 = str1[i].split("\":\"");
			String value = str2[1];
			list.add(value.replace("\"",""));
		}
		System.out.println(list.toString());
		return list;
	}
}
