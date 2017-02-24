package com.yueya.web;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueya.base.BaseController;
import com.yueya.common.Result;
import com.yueya.customer.model.Customer;
import com.yueya.customer.service.ICustomerService;
import com.yueya.util.CommonUtil;

@Controller
@RequestMapping(value = "/customer/oauth")
@SuppressWarnings("unchecked")
public final class OauthController extends BaseController {
	@Resource
	private ICustomerService customerService;

	/**
	 * 如果没有注册则注册，如果注册则直接登录
	 * 
	 * @param username
	 *            用户名
	 * @param openid
	 *            第三方登录id
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Result<String> oauthLogin(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "gender", required = false) Integer gender,
			HttpSession session) {
		try {
			boolean flag = false;
			Customer customer = customerService.getCustomerByOpenId(openid);
			if (customer == null) {
				customer = new Customer();
				customer.setUsername(username);
				customer.setOpenid(openid);
				customer.setAge(age);
				customer.setGender(gender);
				customerService.saveEntity(customer);
				flag = true;
			}
			session.setAttribute("userInfo", customer);
			//1代表注册，需要上传头像， 0代表登录，不需要上传头像
			result = new Result<String>(1, null, session.getId() + "_" + (flag ? "1" : "0") + "_" + customer.getId());
		} catch (Exception e) {
			logger.error("OauthController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
