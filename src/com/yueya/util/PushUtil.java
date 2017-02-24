package com.yueya.util;

import org.apache.log4j.Logger;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushBroadcastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.google.gson.JsonObject;
import com.yueya.event.dto.EventPush;

/**
 * 百度推送
 * 
 * @author liuruichao
 * 
 */
public final class PushUtil {
	private static final String apiKey = "Ac9mIhl64zWLoZt4uZDWzcR7";
	private static final String secretKey = "Gr7tdEt1gGhOKhbWtVR2aCMH5pVjFxZr";
	private static final Logger logger = Logger.getLogger(PushUtil.class);

	/**
	 * ios 最新活动 推送
	 * 
	 * @param result
	 */
	public static void pushNewEventByIOS(EventPush eventPush) {
		// 1. 设置developer平台的ApiKey/SecretKey
		ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
		// 2. 创建BaiduChannelClient对象实例
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		// 3. 若要了解交互细节，请注册YunLogHandler类
		channelClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		try {
			// 4. 创建请求类对象
			PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
			request.setDeviceType(4); // device_type => 1: web 2: pc 3:android 4:ios 5:wp
			request.setMessageType(1);
			request.setDeployStatus(1); // DeployStatus => 1: Developer 2:Production
			JsonObject obj = new JsonObject();
			JsonObject aps = new JsonObject();
			aps.addProperty("alert", "附近有新活动啦");
			aps.addProperty("sound", "");
			aps.addProperty("badge", "1");
			obj.add("aps", aps);
			obj.addProperty("status", "1");
			obj.addProperty("id", eventPush.getId());
			obj.addProperty("title", eventPush.getTitle());
			obj.addProperty("content", eventPush.getContent());
			obj.addProperty("cityId", eventPush.getCityId());
			obj.addProperty("typeId", eventPush.getTypeId());
			System.out.println(obj.toString());
			request.setMessage(obj.toString());
			// 5. 调用pushMessage接口
			PushBroadcastMessageResponse response = channelClient
					.pushBroadcastMessage(request);
			// 6. 认证推送成功
			logger.info("PushUtil: push amount : " + response.getSuccessAmount());
		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			logger.error("PushUtil:" + CommonUtil.getErrStr(e));
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			logger.error("PushUtil:"
					+ String.format(
							"request_id: %d, error_code: %d, error_message: %s",
							e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		}
	}
}
