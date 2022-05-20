package com.woniuxy.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;
import com.woniuxy.utils.commons.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/zfb.do")
public class ZhiFuBaoController extends BaseServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);


        //获取参数
        String uname = request.getParameter("uname");
        String prive = request.getParameter("prive");
        String dindan = request.getParameter("dindan");



        //商户订单号，商户网站订单系统中唯一订单号，必填
        //new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        String out_trade_no =dindan;
        //付款金额，必填
        //new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
        String total_amount =prive;
        //订单名称，必填
        //new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        String subject ="尊敬的SVIP："+ uname;
        //商品描述，可空
        //new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
        String body ="无";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //输出
        response.getWriter().println(result);
    }
}
