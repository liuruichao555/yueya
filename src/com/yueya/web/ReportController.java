package com.yueya.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueya.base.BaseController;
import com.yueya.common.Result;
import com.yueya.customer.model.Report;
import com.yueya.customer.service.ICustomerService;
import com.yueya.customer.service.IReportService;
import com.yueya.util.CommonUtil;
import com.yueya.util.MySessionContext;

@Controller
@RequestMapping(value = "/report")
@SuppressWarnings("unchecked")
public class ReportController extends BaseController {
	@Resource
	private IReportService reportService;
	@Resource
	private ICustomerService customerService;

	/**
	 * 举报
	 * @param reason
	 * @param customerId
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result<String> add(
			@RequestParam(value = "reason", required = true) String reason,
			@RequestParam(value = "customerId", required = true) Integer customerId,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if(session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Report report = new Report();
			report.setCustomer(customerService.getEntityById(customerId));
			report.setReason(reason);
			report.setStatus(0);
			reportService.saveEntity(report);
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ReportController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
