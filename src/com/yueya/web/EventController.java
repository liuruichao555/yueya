package com.yueya.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueya.base.BaseController;
import com.yueya.common.PageBean;
import com.yueya.common.Result;
import com.yueya.customer.dto.CustomerDTO;
import com.yueya.customer.model.Customer;
import com.yueya.customer.service.ICustomerService;
import com.yueya.event.dto.EventDTO;
import com.yueya.event.dto.EventSignupDTO;
import com.yueya.event.model.Event;
import com.yueya.event.model.EventSignup;
import com.yueya.event.service.IEventCommentService;
import com.yueya.event.service.IEventFavorService;
import com.yueya.event.service.IEventService;
import com.yueya.event.service.IEventSignupService;
import com.yueya.util.CommonUtil;
import com.yueya.util.DateFormatUtil;
import com.yueya.util.KeywordFilterUtil;
import com.yueya.util.MySessionContext;

@Controller
@RequestMapping(value = "/event")
@SuppressWarnings("unchecked")
public final class EventController extends BaseController {
	@Resource
	private IEventService eventService;
	@Resource
	private IEventCommentService eventCommentService;
	@Resource
	private IEventFavorService eventFavorService;
	@Resource
	private IEventSignupService eventSignupService;
	@Resource
	private ICustomerService customerService;

	/**
	 * 增加活动分享总数
	 * 
	 * @param eventId
	 * @return
	 */
	@RequestMapping("/share/add")
	@ResponseBody
	public Result<String> addShare(
			@RequestParam(value = "eventId", required = true) Integer eventId) {
		try {
			eventService.addShare(eventId);
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 获取参加此次活动的所有人
	 * 
	 * @param eventId
	 * @return
	 */
	@RequestMapping("/signup/allCus")
	@ResponseBody
	public Result<List<EventSignupDTO>> listBySignUp(
			@RequestParam(value = "eventId", required = true) Integer eventId) {
		try {
			List<EventSignup> list = eventSignupService.getSignupCustomer(
					eventId, true);
			List<EventSignupDTO> dtoList = new ArrayList<>();
			EventSignupDTO dto = null;
			for (EventSignup eventSignup : list) {
				dto = new EventSignupDTO();
				dto.setEventSignup(eventSignup);
				dtoList.add(dto);
			}
			result = new Result<List<EventSignupDTO>>(1, null, dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventController:" + CommonUtil.getErrStr(e));
			result = new Result<List<EventSignupDTO>>(0, null, null);
		}
		return result;
	}

	/**
	 * 取消报名
	 * 
	 * @param eventId
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/signout")
	@ResponseBody
	public Result<String> signout(
			@RequestParam(value = "eventId", required = true) Integer eventId,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");
			if (eventSignupService.isSignup(eventId, customer.getId())) {
				eventSignupService.delSignup(eventId, customer.getId());
			}
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 报名
	 * 
	 * @param eventId
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/signup")
	@ResponseBody
	public Result<String> signup(
			@RequestParam(value = "eventId", required = true) Integer eventId,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "code", required = false) String code) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			if (session.getAttribute("code") == null
					|| session.getAttribute("phone") == null) {
				result = new Result<String>(2, null, null);
				return result;
			}
			String scode = (String) session.getAttribute("code");
			String sphone = (String) session.getAttribute("phone");
			if (!scode.equals(code) || !phone.equals(sphone)) {
				// 手机验证码错误
				result = new Result<String>(2, null, null);
				return result;
			}
			Customer customer = (Customer) session.getAttribute("userInfo");
			if (!eventSignupService.isSignup(eventId, customer.getId())) {
				eventSignupService.signUp(eventId, customer);
			}
			session.removeAttribute("code");
			session.removeAttribute("phone");
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 赞 取消赞
	 * 
	 * @param id
	 * @param flag
	 * @return
	 */
	@RequestMapping("/favor")
	@ResponseBody
	public Result<String> favor(
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "flag", required = true) Integer flag,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null)
				throw new Exception("session is null");
			eventService.favor(id, flag,
					(Customer) session.getAttribute("userInfo"));
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 活动列表
	 * 
	 * @param typeId
	 * @param cityId
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result<PageBean<EventDTO>> list(
			@RequestParam(value = "typeId", required = true) String typeId,
			@RequestParam(value = "cityId", required = true) Integer cityId,
			@RequestParam(value = "curPage", required = true) Integer curPage,
			@RequestParam(value = "sessionid", required = false) String sessionid) {
		try {
			PageBean<Event> pageBean = eventService.list(typeId, cityId,
					curPage);
			PageBean<EventDTO> list = new PageBean<EventDTO>();
			list.setPageIndex(pageBean.getPageIndex());
			list.setPageSize(pageBean.getPageSize());
			list.setTotalCount(pageBean.getTotalCount());
			list.setTotalPage(pageBean.getTotalPage());
			EventDTO dto = null;
			List<EventDTO> data = new ArrayList<EventDTO>();
			HttpSession session = null;
			if (sessionid != null)
				session = MySessionContext.getSession(sessionid);
			Customer customer = null;
			if (session != null && session.getAttribute("userInfo") != null)
				customer = (Customer) session.getAttribute("userInfo");
			for (Event event : pageBean.getData()) {
				dto = new EventDTO();
				dto.setEventByList(event);
				dto.setCommentCount(eventCommentService.getCountByEvent(event
						.getId()));
				if (session != null && customer != null) {
					dto.setFlag(eventFavorService.isFavor(customer.getId(),
							event.getId()) ? 1 : 0);
				} else {
					dto.setFlag(0);
				}
				data.add(dto);
			}
			list.setData(data);
			result = new Result<PageBean<EventDTO>>(1, null, list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventController:" + CommonUtil.getErrStr(e));
			result = new Result<PageBean<Event>>(0, null, null);
		}
		return result;
	}

	/**
	 * 添加活动
	 * 
	 * @param event
	 * @param sessionid
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result<String> addEvent(
			Event event,
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "code", required = false) String code) {
		HttpSession session = MySessionContext.getSession(sessionid);
		if (event.getTitle() == null || event.getTitle().length() <= 0
				|| event.getContent() == null
				|| event.getContent().length() <= 0
				|| event.getStartTime() == null || session == null
				|| event.getPhone() == null || event.getPhone().length() <= 0
				|| session.getAttribute("userInfo") == null) {
			result = new Result<String>(0, null, null);
		} else {
			try {
				//敏感词过滤
				event.setTitle(KeywordFilterUtil.searchKeyword(event.getTitle()));
				event.setContent(KeywordFilterUtil.searchKeyword(event.getContent()));
				
				if (session.getAttribute("code") == null
						|| session.getAttribute("phone") == null) {
					result = new Result<String>(2, null, null);
					return result;
				}
				String scode = (String) session.getAttribute("code");
				String sphone = (String) session.getAttribute("phone");
				if (!scode.equals(code) || !event.getPhone().equals(sphone)) {
					// 手机验证码错误
					result = new Result<String>(2, null, null);
					return result;
				}
				Customer customer = (Customer) session.getAttribute("userInfo");
				event.setCustomer(customer);
				// TODO: 0表示需要审核，1表示不需要审核
				event.setStatus(1);
				event.setFavor(0);
				event.setShareCount(0);
				event.setJoinCount(0);
				if (event.getPersonCount() == null)
					event.setPersonCount(0);
				event.setCreateTime(new Date());
				// 处理结束时间
				if (event.getEndTime() == null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(event.getStartTime());
					cal.add(Calendar.DATE, 1);
					String dateStr = DateFormatUtil.dateToStr(cal.getTime());
					event.setEndTime(DateFormatUtil.strToDateDic(dateStr
							+ " 00:00:00"));
				} else if (event.getEndTime().before(event.getStartTime()))
					throw new Exception("活动结束时间大于开始时间");
				eventService.saveEntity(event);
				customerService.addPoints(2, customer.getId());
				session.removeAttribute("code");
				session.removeAttribute("phone");
//				final Event tEvent = event;
				// 发送推送
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						EventPush push = new EventPush();
//						push.setEvent(tEvent);
//						PushUtil.pushNewEventByIOS(push);
//					}
//				}).start();
				result = new Result<String>(1, null, event.getId() + "");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("EventController:" + CommonUtil.getErrStr(e));
				result = new Result<String>(0, null, null);
			}
		}
		return result;
	}

	/**
	 * 活动详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public Result<EventDTO> detail(
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "sessionid", required = false) String sessionid) {
		try {
			Event event = eventService.getEntityById(id);
			EventDTO dto = new EventDTO();
			dto.setEventByDetail(event);
			dto.setCommentCount(eventCommentService.getCountByEvent(event
					.getId()));
			// 报名用户
			List<EventSignup> list = eventSignupService.getSignupCustomer(id,
					false);
			List<CustomerDTO> cusList = new ArrayList<>();
			CustomerDTO cusDTO = null;
			for (EventSignup eventSignup : list) {
				cusDTO = new CustomerDTO();
				cusDTO.setCustomer(eventSignup.getCustomer());
				cusList.add(cusDTO);
			}
			dto.setSignUp(cusList);
			HttpSession session = MySessionContext.getSession(sessionid);
			// 登录用户是否报名，没有登录用户默认没有报名
			if (session != null && session.getAttribute("userInfo") != null) {
				Customer customer = (Customer) session.getAttribute("userInfo");
				dto.setIsSignup(eventSignupService.isSignup(id,
						customer.getId()) ? "1" : "0");
				dto.setFlag(eventFavorService.isFavor(customer.getId(),
						event.getId()) ? 1 : 0);
			}
			// 判断用户是否赞过该活动
			result = new Result<EventDTO>(1, null, dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventController:" + CommonUtil.getErrStr(e));
			result = new Result<EventDTO>(0, null, null);
		}
		return result;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		// 注册自定义的属性编辑器
		// 1、日期
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}

	public void setEventCommentService(IEventCommentService eventCommentService) {
		this.eventCommentService = eventCommentService;
	}

	public void setEventFavorService(IEventFavorService eventFavorService) {
		this.eventFavorService = eventFavorService;
	}

	public void setEventSignupService(IEventSignupService eventSignupService) {
		this.eventSignupService = eventSignupService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
