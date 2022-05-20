package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000119652886";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCJDAMmUI0CVVyJj66w5UNZE48J0fhfNDF2TYbNPxCQFAR8DTfnVpzKm8AMDzx6OEeZnei9FZgJ0GLh6/cYu9MsmnPw6wh+cPNMrKo3H5n4ziajId7ZUdLWqfB6ydrXEEh3XcwczTVwv4l/G/fo9OLiAfQhM4zj3+IE0Egh5PqWwvPn94kX+xC8ndNW0B3FlB9CQ8TxAJGmmVu5g6Qn/NrBqFXVyiy+dPrX44f4N655Vj6j2cRM4JtLw4eZJGcV6dqYo80MQYU94bbS/+1aMhSU4tEOjYZSUGoV/US/MO+IGMvXTgrtZsKDzx2CQ+25c8p8T5VoJn6XUlepo2bCwUgDAgMBAAECggEAUZcGNQRVvuLlEbyp6wTDAMAlM1p/PObM37quG3mFOdHBDb2s35xwAiuRvVxAzB6/edRKIHA/sPAQPqKF3ILN6UsW5YJ37xb9slNFj0XQHBFXbV2X19ff5w8LbX7JO6qlCfOctjTxsOtHR5Z2FInenWE+X5naMjwyylzZ5Nv22FPeuNcevmIxgCLMcsbso0F4uNVTqmZl1vSgDLQW+X0zQSk6zipvNhjmPaQ5QB2tiztmThVqUyQ1aT6fNSWJT4uHJum+315+Du+yza9EQk7UKs7G+Gb1Nc7UNk9nEEbslqFh7b9iTrqTADyYDJwjDll8/lu+Ejlf4hIE7dW3RiFH+QKBgQDKyGOvT2ybLNkhYoXCaWzcJg6axSVa8TMECRqXZn98X3aDUnEOsrnfUnrZjDkipPfy1MLGHMA+zKESdR8v1PuFY2Y1EUhS2bXfaYxS3cJ947iOEdg59qhAMKy8rKHDMR2J3UY49Eiae+StagVA8O8ljydgG1OXZC7UEkb7RymlNwKBgQCtA0X8EAdMYzw3qym38cnRLBXnWMs47jeS71n3t3g06Fe1B+PkSKGxj2EDD0V0rp2F9vHTziaDG0dhnAHrSXs5ccgnyEkSEwlZyAMBoAKn4NpBXYa/sW6HkdhQYf1gcF+pJr/7FcqMWNTolzDGG0ImfgSVmzHowiefEhkTFYxZlQKBgQCXoHdJYu4mqcZBvFWgJQyNILfS6o8gDTLEVbpYcv/ok8keMMrw3ps1bYsGmF8wlIICRSzOzjjBvGjEUGddcLgR6V1HWQDnUns37shKPh9r2pbpRFaWIOV1+e9vtPXP6i3YJGiZta+ENBetw5TiFkX7o6shpc0/al3JnVEOGwv8yQKBgEmtfC80kDcah3lZfcGkyQI32PgVWy3aXHZJUbHpQtqdG0SvoIAF4j3gRjrsVPK0rcZv/Fnrj4EDq+lIa5TMTRqQduFiLKPy0WeXgpg9kQP3AFsXvhuCLwSyukC5ChL4p/Q72UPO6tvLtJd8Uzv6Pbhwr/kaBy4NCJx4M49p/MPlAoGBAKKKR7oUw10UWsb1oI6bQFiB/lQCjQrnM86Tiaj22Y36np9zApFwvxWibQeHFXcXiGhOpWuM5nm+7wP4KaUT5ALHoiBtjXMc3oC8pPrS4HjImwYFXcgifZaLWZBUnNjvlk+oivoGMMnCBtuSOVR0MEboNZga/N7Mec/8IDUaoK+N";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl3zPpvJWK6jMrxKendO0/ZE0W7ZE3EAjswC1tlzM70E1Amavaxx2m8FCBvp9SFwilzqcXNjh9NQot2SeSHzBH7dAfSxNQf0o/IrZVbF5nZA8tC88aHB8+RzCxfRD6PjIz4V7bH065ZFJQe3Pt8QvkEqYWWS0k1rWjpTlyexv9XxsNDEzijXOQDnAHAbJLOWp71qceClaN+nUfpp1fhkts23mES2hG0lyKh9MXLF7+yt0PDo0tY+Ud3WUFMVeoeQK93ceT51H6b/sgOkeHgvWjAfIWDBQ/0c5J1jQxDHoD2LPlTwyHzaoFSTsldLMUi7yMU6Yo7AG2sn2kBgy8siHJQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://s10.z100.vip:31741//notify.do";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://s10.z100.vip:31741//html/frontpage/zhifuchengong.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

