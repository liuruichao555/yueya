package com.yueya.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueya.base.BaseController;
import com.yueya.common.Result;
import com.yueya.util.CommonUtil;
import com.yueya.util.MySessionContext;
import com.yueya.util.RandomUtil;
import com.yueya.util.SmsUtil;

@Controller
@RequestMapping(value = "/sms")
@SuppressWarnings("unchecked")
public class SMSController extends BaseController {
	/**
	 * 发送短信验证码
	 * @param sessionid
	 * @param phone
	 * @return
	 */
	@RequestMapping("/send/code")
	@ResponseBody
	public Result<String> sendCode(
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "phone", required = true) String phone) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			String code = RandomUtil.getRandomCodeBy6();
			session.setAttribute("code", code);
			session.setAttribute("phone", phone);
			SmsUtil.sendCode(phone, code);
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SMSController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}
}
