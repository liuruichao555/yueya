package com.yueya.util;


import org.apache.log4j.Logger;

import com.yueya.sms.JsonReqClient;

/**
 * 发送短信工具类
 * 
 * @author liuruichao
 * 
 */
@SuppressWarnings("unused")
public final class SmsUtil {
	private final static Logger logger = Logger.getLogger(SmsUtil.class);
	private final static JsonReqClient reqClient = new JsonReqClient();
	private final static String accountSid = "2073eb0323120d40c1d298ab9389fd50";
	private final static String authToken = "7d6d1c31b9f178f6051ef1f5567eb9ca";
	private final static String appId = "545083db91d14b0b91ea6a9d924a6f24";
	private final static String templateId = "1470";

	/**
	 * 发送短信验证码
	 * 
	 * @param code
	 * @throws Exception
	 *             发送失败
	 */
	public static void sendCode(String phone, String code) throws Exception {
		reqClient.templateSMS(accountSid, authToken, appId, templateId, phone,
				code);
		// HashMap<String, Object> result = null;
		// CCPRestSDK restAPI = new CCPRestSDK();
		// restAPI.init("sandboxapp.cloopen.com", "8883");//
		// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		// restAPI.setAccount("8a48b551495b42ea01496c3406db0c1e",
		// "70afd910121242d09207a18b0961c168");// 初始化主帐号名称和主帐号令牌
		// restAPI.setAppId("8a48b551495b42ea01496c352a5f0c20");// 初始化应用ID
		// // result = restAPI.sendTemplateSMS("15910591399,15618150615", "1",
		// new
		// // String[] {
		// // "123456", "88" });
		// result = restAPI.sendTemplateSMS(phone, "1",
		// new String[] { code, "30" });
		//
		// System.out.println("SDKTestGetSubAccounts result=" + result);
		// if (!"000000".equals(result.get("statusCode"))) {
		// // 异常返回输出错误码和错误信息
		// logger.error("send code error:" + "错误码=" + result.get("statusCode")
		// + " 错误信息= " + result.get("statusMsg"));
		// // System.out.println("错误码=" + result.get("statusCode") + " 错误信息= "
		// // + result.get("statusMsg"));
		// throw new Exception("验证码发送失败，请稍后再试！");
		// }
	}
}
