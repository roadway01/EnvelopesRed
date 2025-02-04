package com.rrf.ssm.utils;/**
 * @author : 邹智敏
 * @data : 2018年-01月-13日 11:36
 * @Description : 这里是此类的说明
 **/

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : 邹智敏
 * @data : 2018年-01月-13日 11:36
 * @Description : 日期类
 **/
public class DataTimeUtils {

    /**
     * @author : 邹智敏
     * @data : 2018/1/15
     * @Description : 返回系统时间，日期格式是：20170102123125
     **/
    public static String getData() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(currentTime);
        String data = formatter.format(date);
        return data;
    }

    public static String getDataTime() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
        Date date = new Date(currentTime);
        String data = formatter.format(date);
        return data;
    }

    //判断时间是否超过了24小时
    public static boolean getTime24(String date1, String date2) throws Exception {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date start = sdf.parse(date1);
        java.util.Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime();
        double result = cha * 1.0 / (1000 * 60 * 60);
        if(result<=24){
            return true;
        }else{
            return false;
        }
    }

    //java定时任务的开启
    public static void DTimeRenWu(){
        Runnable runnable = new Runnable() {
            public void run() {
                //这里开始处理物理逻辑
                System.out.println("Hello !!");

            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        /**
         * @author : 邹智敏
         * @data : 2018/2/8
         * @Description :
         * 第二个参数为首次执行的延时时间，就是子线程10s后，线程启动，
         * 第三个参数为定时执行的间隔时间，我设置的是24小时，也就是一天之后去执行该任务
         **/
        service.scheduleAtFixedRate(runnable, 10, 24*1000*60*60, TimeUnit.SECONDS);
        //service.shutdown();
    }

    public static void main(String[] args) throws Exception{
        DTimeRenWu();
    }

}
