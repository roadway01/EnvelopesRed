/**
 * @author : 邹智敏
 * @data : 2018年-01月-13日 11:31
 * @Description : 这里是此类的说明
 **/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.wxpay.sdk.WXPayUtil;
import com.rrf.ssm.controller.ImgFIleUpload;
import com.rrf.ssm.utils.UuidUtils;
import com.rrf.ssm.utils.json.JSONAndObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : 邹智敏
 * @data : 2018年-01月-13日 11:31
 * @Description : 这里是此类的说明
 **/
public class Test {
    public static void main(String[] args)throws Exception {
        //商品订单号
        String Partner_trade_no = UuidUtils.uuid();
        System.out.println("第1次的商户定单号："+Partner_trade_no);
        System.out.println("第2次的商户定单号："+Partner_trade_no);
        System.out.println("第3次的商户定单号："+Partner_trade_no);
    }

}
