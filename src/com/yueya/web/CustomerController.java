package com.yueya.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yueya.base.BaseController;
import com.yueya.common.PageBean;
import com.yueya.common.Result;
import com.yueya.customer.dto.CustomerDTO;
import com.yueya.customer.model.Customer;
import com.yueya.customer.model.CustomerLike;
import com.yueya.customer.service.ICustomerLikeService;
import com.yueya.customer.service.ICustomerService;
import com.yueya.customer.service.IMessageService;
import com.yueya.event.dto.EventDTO;
import com.yueya.event.model.Event;
import com.yueya.event.model.EventSignup;
import com.yueya.event.model.EventType;
import com.yueya.event.service.IEventService;
import com.yueya.event.service.IEventSignupService;
import com.yueya.event.service.IEventTypeService;
import com.yueya.topic.dto.TopicDTO;
import com.yueya.topic.model.Topic;
import com.yueya.topic.service.ITopicReplyService;
import com.yueya.topic.service.ITopicService;
import com.yueya.util.CommonUtil;
import com.yueya.util.DateFormatUtil;
import com.yueya.util.FileUtil;
import com.yueya.util.MySessionContext;

@Controller
@RequestMapping(value = "/customer")
@SuppressWarnings("unchecked")
public class CustomerController extends BaseController {
	@Resource
	private ICustomerService customerService;
	@Resource
	private ICustomerLikeService customerLikeService;
	@Resource
	private IEventTypeService eventTypeService;
	@Resource
	private ITopicService topicService;
	@Resource
	private IEventService eventService;
	@Resource
	private IEventSignupService eventSignupService;
	@Resource
	private ITopicReplyService topicReplyService;
	@Resource
	private IMessageService messageService;

	/**
	 * 查看他人信息
	 * 
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/other")
	@ResponseBody
	public Result<CustomerDTO> otherCustomer(
			@RequestParam(value = "customerId", required = true) Integer customerId) {
		try {
			Customer customer = customerService.getEntityById(customerId);
			CustomerDTO dto = new CustomerDTO();
			dto.setCustomer(customer);
			dto.setTopicCount(customerService.getTopicCount(customer.getId())
					+ "");
			dto.setJoinEventCount(customerService.getJoinEventCount(customer
					.getId()) + "");
			dto.setCreateEventCount(customerService
					.getCreateEventCount(customer.getId()) + "");
			dto.setMsgCount(messageService.getNewCountByCustomer(customer
					.getId()) + "");
			result = new Result<CustomerDTO>(1, null, dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<CustomerDTO>(0, null, null);
		}
		return result;
	}

	/**
	 * 用户发布的活动
	 * 
	 * @param sessionid
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/center/event/create/list")
	@ResponseBody
	public Result<PageBean<EventDTO>> createEventList(
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "curPage", required = true) Integer curPage) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			PageBean<Event> pageBean = eventService.listByCreateCustomer(
					customer.getId(), curPage);
			PageBean<EventDTO> dtoPage = new PageBean<EventDTO>();
			EventDTO dto = null;
			List<EventDTO> dtoList = new ArrayList<EventDTO>();
			for (Event event : pageBean.getData()) {
				dto = new EventDTO();
				dto.setEventByList(event);
				dtoList.add(dto);
			}
			dtoPage.setData(dtoList);
			dtoPage.setPageIndex(pageBean.getPageIndex());
			dtoPage.setPageSize(pageBean.getPageSize());
			dtoPage.setTotalCount(pageBean.getTotalCount());
			dtoPage.setTotalPage(pageBean.getTotalPage());
			result = new Result<PageBean<EventDTO>>(1, null, dtoPage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<PageBean<EventDTO>>(0, null, null);
		}
		return result;
	}

	/**
	 * 用户参加的活动
	 * 
	 * @param sessionid
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/center/event/join/list")
	@ResponseBody
	public Result<PageBean<EventDTO>> joinEventList(
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "curPage", required = true) Integer curPage) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			PageBean<EventSignup> pageBean = eventSignupService
					.getSignupByCustomer(customer.getId(), curPage);
			PageBean<EventDTO> dtoPage = new PageBean<EventDTO>();
			EventDTO dto = null;
			List<EventDTO> dtoList = new ArrayList<EventDTO>();
			for (EventSignup eventSignup : pageBean.getData()) {
				dto = new EventDTO();
				dto.setEventByList(eventSignup.getEvent());
				dtoList.add(dto);
			}
			dtoPage.setData(dtoList);
			dtoPage.setPageIndex(pageBean.getPageIndex());
			dtoPage.setPageSize(pageBean.getPageSize());
			dtoPage.setTotalCount(pageBean.getTotalCount());
			dtoPage.setTotalPage(pageBean.getTotalPage());
			result = new Result<PageBean<EventDTO>>(1, null, dtoPage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<PageBean<EventDTO>>(0, null, null);
		}
		return result;
	}

	/**
	 * 用户发布的话题
	 * 
	 * @param sessionid
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/center/topic/list")
	@ResponseBody
	public Result<PageBean<TopicDTO>> topicList(
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "curPage", required = true) Integer curPage) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			PageBean<Topic> pageBean = topicService.listByCustomer(
					customer.getId(), curPage);
			PageBean<TopicDTO> dtoPage = new PageBean<TopicDTO>();
			TopicDTO dto = null;
			List<TopicDTO> dtoList = new ArrayList<TopicDTO>();
			for (Topic topic : pageBean.getData()) {
				dto = new TopicDTO();
				dto.setTopicByList(topic);
				dto.setReplyCount(topicReplyService.getCountByTopic(topic
						.getId()) + "");
				dtoList.add(dto);
			}
			dtoPage.setData(dtoList);
			dtoPage.setPageIndex(pageBean.getPageIndex());
			dtoPage.setPageSize(pageBean.getPageSize());
			dtoPage.setTotalCount(pageBean.getTotalCount());
			dtoPage.setTotalPage(pageBean.getTotalPage());
			result = new Result<PageBean<TopicDTO>>(1, null, dtoPage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<PageBean<TopicDTO>>(0, null, null);
		}
		return result;
	}

	/**
	 * 获取用户发布话题总数，参加活动总数，发布活动总数
	 * 
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/center/info")
	@ResponseBody
	public Result<CustomerDTO> center(
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			customer = customerService.getEntityById(customer.getId());
			CustomerDTO dto = new CustomerDTO();
			dto.setCustomer(customer);
			dto.setTopicCount(customerService.getTopicCount(customer.getId())
					+ "");
			dto.setJoinEventCount(customerService.getJoinEventCount(customer
					.getId()) + "");
			dto.setCreateEventCount(customerService
					.getCreateEventCount(customer.getId()) + "");
			dto.setMsgCount(messageService.getNewCountByCustomer(customer
					.getId()) + "");
			result = new Result<CustomerDTO>(1, null, dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<CustomerDTO>(0, null, null);
		}
		return result;
	}

	/**
	 * 更新用户兴趣，包括删除和修改
	 * 
	 * @param typeId
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/like/update")
	@ResponseBody
	public Result<String> updateLike(
			@RequestParam(value = "typeId", required = true) String typeId,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			customerLikeService.updateCustomerLike(customer, typeId);
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 用户取消兴趣
	 * 
	 * @param typeId
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/like/del")
	@ResponseBody
	public Result<String> delLike(
			@RequestParam(value = "typeId", required = true) Integer typeId,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			customerLikeService.delCustomerLike(customer.getId(), typeId);
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 用户添加兴趣
	 * 
	 * @param typeId
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/like/add")
	@ResponseBody
	public Result<String> addLike(
			@RequestParam(value = "typeId", required = true) String typeId,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			String[] types = typeId.split(",");
			Customer customer = (Customer) session.getAttribute("userInfo");
			CustomerLike customerLike = null;
			EventType eventType = null;
			for (String type : types) {
				eventType = eventTypeService.getEntityById(Integer
						.valueOf(type));
				customerLike = new CustomerLike();
				customerLike.setCustomer(customer);
				customerLike.setEventType(eventType);
				customerLikeService.addCustomerLike(customerLike);
			}
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param username
	 * @param phone
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result<CustomerDTO> updateUser(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "realName", required = false) String realName,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "gender", required = false) Integer gender,
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "like", required = false) String like) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			// 修改username时判断重名,不修改则不用判断
			if (!customer.getUsername().equals(userName)) {
				if (customerService.exsitsUsername(userName)) {
					// 重名
					result = new Result<Customer>(2, null, null);
					return result;
				}
			}
			customer.setUsername(userName);
			if (phone != null && phone.length() > 0)
				customer.setPhone(phone);
			if (realName != null && phone.length() > 0)
				customer.setRealname(realName);
			if (age != null)
				customer.setAge(age);
			if (gender != null)
				customer.setGender(gender);
			customerService.updateCustomer(customer, like);
			session.setAttribute("userInfo", customer);
			CustomerDTO dto = new CustomerDTO();
			dto.setCustomer(customer);
			result = new Result<CustomerDTO>(1, null, dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CustomerController:" + CommonUtil.getErrStr(e));
			result = new Result<CustomerDTO>(0, null, null);
		}
		return result;
	}

	/**
	 * 上传头像
	 * 
	 * @param file
	 * @param sessionid
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload/avatar")
	@ResponseBody
	public Result<String> uploadAvtar(
			@RequestParam(value = "avatar", required = true) MultipartFile file,
			@RequestParam(value = "sessionid", required = true) String sessionid,
			HttpServletRequest request) {
		HttpSession session = MySessionContext.getSession(sessionid);
		if (session == null || session.getAttribute("userInfo") == null) {
			result = new Result<Customer>(0, null, null);
		} else {
			try {
				String basePath = request.getSession().getServletContext()
						.getRealPath("/");
				String dicName = DateFormatUtil.dateToDicname(new Date());
				String random = dicName + "/" + UUID.randomUUID().toString()
						+ ".jpg";
				File baseFile = new File(basePath + "/avatar/" + dicName);
				if (!baseFile.exists())
					baseFile.mkdirs();
				String filePath = basePath + "avatar/" + random;
				FileUtil.writeFile(filePath, file.getInputStream());
				String imageUrl = "/avatar/" + random;
				Customer customer = (Customer) session.getAttribute("userInfo");
				customer.setImageUrl(imageUrl);
				customerService.updateEntity(customer);
				result = new Result<String>(1, null, imageUrl);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("CustomerController:" + CommonUtil.getErrStr(e));
				result = new Result<String>(0, null, null);
			}
		}
		return result;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setCustomerLikeService(ICustomerLikeService customerLikeService) {
		this.customerLikeService = customerLikeService;
	}

	public void setEventTypeService(IEventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}

	public void setTopicService(ITopicService topicService) {
		this.topicService = topicService;
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}

	public void setEventSignupService(IEventSignupService eventSignupService) {
		this.eventSignupService = eventSignupService;
	}

	public void setTopicReplyService(ITopicReplyService topicReplyService) {
		this.topicReplyService = topicReplyService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
}
