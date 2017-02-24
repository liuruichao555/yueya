package com.yueya.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.stereotype.Controller;

import com.yueya.base.BaseController;
import com.yueya.common.PageBean;
import com.yueya.common.Result;
import com.yueya.customer.model.Customer;
import com.yueya.customer.service.ICustomerService;
import com.yueya.topic.dto.TopicReplyDTO;
import com.yueya.topic.model.Topic;
import com.yueya.topic.model.TopicReply;
import com.yueya.topic.service.ITopicReplyService;
import com.yueya.topic.service.ITopicService;
import com.yueya.util.CommonUtil;
import com.yueya.util.KeywordFilterUtil;
import com.yueya.util.MySessionContext;

@Controller
@RequestMapping(value = "/topic/reply")
@SuppressWarnings("unchecked")
public class TopicReplyController extends BaseController {
	@Resource
	private ITopicService topicService;
	@Resource
	private ITopicReplyService topicReplyService;
	@Resource
	private ICustomerService customerService;
	
	/**
	 * 评论列表
	 * 
	 * @param curPage
	 * @param topicId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result<PageBean<TopicReplyDTO>> list(
			@RequestParam(value = "curPage", required = true) Integer curPage,
			@RequestParam(value = "topicId", required = true) Integer topicId) {
		try {
			//获得评论列表
			PageBean<TopicReply> pageBean = topicReplyService.list(topicId,
					curPage);
			List<TopicReplyDTO> dtoList = new ArrayList<TopicReplyDTO>();
			TopicReplyDTO dto = null;
			for (TopicReply reply : pageBean.getData()) {
				dto = new TopicReplyDTO();
				dto.setTopicReply(reply);
				dtoList.add(dto);
			}
			// 转换为DTO 类型 pageBean
			PageBean<TopicReplyDTO> dtoPage = new PageBean<TopicReplyDTO>();
			dtoPage.setData(dtoList);
			dtoPage.setPageIndex(pageBean.getPageIndex());
			dtoPage.setPageSize(pageBean.getPageSize());
			dtoPage.setTotalCount(pageBean.getTotalCount());
			dtoPage.setTotalPage(pageBean.getTotalPage());
			result = new Result<PageBean<TopicReplyDTO>>(1, null, dtoPage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TopicController:" + CommonUtil.getErrStr(e));
			result = new Result<PageBean<TopicReplyDTO>>(0, null, null);
		}
		return result;
	}

	/**
	 * 回复主题
	 * 
	 * @param topicId
	 * @param content
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result<TopicReplyDTO> add(
			@RequestParam(value = "topicId", required = true) Integer topicId,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			//敏感词过滤
			content = KeywordFilterUtil.searchKeyword(content);
			
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			Topic topic = topicService.getEntityById(topicId);
			TopicReply reply = new TopicReply();
			reply.setContent(content);
			reply.setCreateTime(new Date());
			reply.setCustomer(customer);
			reply.setTopic(topic);
			topic.setLastUpdateTime(new Date());
			topicService.updateEntity(topic);
			topicReplyService.addTopic(reply);
			customerService.addPoints(1, customer.getId());
			// 持久化对象转为DTO对象
			TopicReplyDTO dto = new TopicReplyDTO();
			dto.setTopicReply(reply);
			result = new Result<TopicReplyDTO>(1, null, dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TopicController:" + CommonUtil.getErrStr(e));
			result = new Result<TopicReplyDTO>(0, null, null);
		}
		return result;
	}

	public void setTopicService(ITopicService topicService) {
		this.topicService = topicService;
	}

	public void setTopicReplyService(ITopicReplyService topicReplyService) {
		this.topicReplyService = topicReplyService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
