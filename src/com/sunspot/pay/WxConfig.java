package com.sunspot.pay;

import java.net.URLEncoder;

/**
 * 微信支付重定向链接
 * @author LuoAnDong
 *
 */
public class WxConfig
{  
 
    /**
     * 微信内置重定向链接<br/>scope=snsapi_base 不弹出确认框<br/>scope=snsapi_userinfo　弱出确认框　
     * @param orderNo  订单号
     * @param money 交易金额
     * @param memberId 会员号
     * @param balance 1 使用余额<br/> 0 不使用余额　
     * @param orderId 数据库中订单的id
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String rebackUrl(String money,String memberId,String balance, String orderId){
        String appid = "wx17c4617ad4cd4a50";
        String backUri = "http://www.limmai.com/wxpay/topayServlet";
        String orderNo=appid+Sha1Util.getTimeStamp();
        backUri = backUri+"?userId="+memberId+"&orderNo="+orderNo+"&o="+orderId+"&m="+money+"&balance="+balance; 
        backUri = URLEncoder.encode(backUri); 
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + appid+
                "&redirect_uri=" +
                 backUri+
                "&response_type=code&scope=snsapi_base&state=123#wechat_redirect"; 
        return url ; 
    }
}
