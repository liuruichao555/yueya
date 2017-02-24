package com.yueya.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueya.base.BaseController;
import com.yueya.common.PageBean;
import com.yueya.common.Result;
import com.yueya.customer.dto.MessageDTO;
import com.yueya.customer.model.Customer;
import com.yueya.customer.model.Message;
import com.yueya.customer.service.IMessageService;
import com.yueya.util.CommonUtil;
import com.yueya.util.MySessionContext;

@Controller
@RequestMapping(value = "/message")
@SuppressWarnings("unchecked")
public class MessageController extends BaseController {
	@Resource
	private IMessageService messageService;

	/**
	 * 标记为已读
	 * @param id
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/read")
	@ResponseBody
	public Result<String> updateStaus(
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if(session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			messageService.updateStatus(id, 1);
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MessageController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 获取最新的用户通知
	 * 
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/new")
	@ResponseBody
	public Result<PageBean<MessageDTO>> getNewMessage(
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "curPage", required = true) Integer curPage) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			PageBean<Message> pageBean = messageService.getNewMessage(customer.getId(), curPage);
			PageBean<MessageDTO> dtoBean = new PageBean<MessageDTO>();
			dtoBean.setPageIndex(pageBean.getPageIndex());
			dtoBean.setPageSize(pageBean.getPageSize());
			dtoBean.setTotalCount(pageBean.getTotalCount());
			dtoBean.setTotalPage(pageBean.getTotalPage());
			List<MessageDTO> dtoList = new ArrayList<MessageDTO>();
			MessageDTO dto = null;
			for (Message message : pageBean.getData()) {
				dto = new MessageDTO();
				dto.setMessage(message);
				dtoList.add(dto);
			}
			dtoBean.setData(dtoList);
			result = new Result<PageBean<MessageDTO>>(1, null, dtoBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MessageController:" + CommonUtil.getErrStr(e));
			result = new Result<PageBean<MessageDTO>>(0, null, null);
		}
		return result;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
}
