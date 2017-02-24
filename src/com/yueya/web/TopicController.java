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
import com.yueya.customer.model.Customer;
import com.yueya.customer.service.ICustomerService;
import com.yueya.event.service.IEventTypeService;
import com.yueya.topic.dto.TopicDTO;
import com.yueya.topic.dto.TopicReplyDTO;
import com.yueya.topic.model.Topic;
import com.yueya.topic.model.TopicImage;
import com.yueya.topic.model.TopicReply;
import com.yueya.topic.service.ITopicImageService;
import com.yueya.topic.service.ITopicReplyService;
import com.yueya.topic.service.ITopicService;
import com.yueya.util.CommonUtil;
import com.yueya.util.DateFormatUtil;
import com.yueya.util.FileUtil;
import com.yueya.util.KeywordFilterUtil;
import com.yueya.util.MySessionContext;

@Controller
@RequestMapping(value = "/topic")
@SuppressWarnings("unchecked")
public final class TopicController extends BaseController {
	@Resource
	private ITopicService topicService;
	@Resource
	private ITopicReplyService topicReplyService;
	@Resource
	private IEventTypeService eventTypeService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ITopicImageService topicImageService;

	/**
	 * 上传Image
	 * 
	 * @param file
	 * @param topicId
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload/image")
	@ResponseBody
	public Result<String> uploadImage(
			@RequestParam(value = "image", required = true) MultipartFile file,
			@RequestParam(value = "topicId", required = true) Integer topicId,
			HttpServletRequest request) {
		try {
			String basePath = request.getSession().getServletContext()
					.getRealPath("/");
			String dicName = DateFormatUtil.dateToDicname(new Date());
			String random = dicName + "/" + UUID.randomUUID().toString()
					+ ".jpg";
			File baseFile = new File(basePath + "/upload/" + dicName);
			if (!baseFile.exists())
				baseFile.mkdirs();
			String filePath = basePath + "upload/" + random;
			FileUtil.writeFile(filePath, file.getInputStream());
			Topic topic = topicService.getEntityById(topicId);
			TopicImage topicImage = new TopicImage();
			topicImage.setTopic(topic);
			topicImage.setUrl("/upload/" + random);
			topicImageService.saveEntity(topicImage);
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventCommentController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 话题详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public Result<TopicDTO> detail(
			@RequestParam(value = "id", required = true) Integer id) {
		try {
			Topic topic = topicService.getEntityById(id);
			if (topic == null) {
				result = new Result<Topic>(0, null, null);
				return result;
			}
			TopicDTO dto = new TopicDTO();
			dto.setTopicByDetail(topic);
			// 获取10条评论
			List<TopicReply> tList = topicReplyService.getReplyByTop(
					topic.getId(), 10);
			List<TopicReplyDTO> dtoList = new ArrayList<TopicReplyDTO>();
			TopicReplyDTO replyDto = null;
			for (TopicReply reply : tList) {
				replyDto = new TopicReplyDTO();
				replyDto.setTopicReply(reply);
				dtoList.add(replyDto);
			}
			dto.setReplys(dtoList);
			result = new Result<TopicDTO>(1, null, dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TopicController:" + CommonUtil.getErrStr(e));
			result = new Result<TopicDTO>(0, null, null);
		}
		return result;
	}

	@RequestMapping("/add")
	@ResponseBody
	public Result<TopicDTO> addTopic(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "eventType", required = true) Integer eventType,
			@RequestParam(value = "sessionid", required = true) String sessionid) {
		try {
			// 敏感词过滤
			title = KeywordFilterUtil.searchKeyword(title);
			content = KeywordFilterUtil.searchKeyword(content);

			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null || session.getAttribute("userInfo") == null)
				throw new Exception("session is null");
			Customer customer = (Customer) session.getAttribute("userInfo");

			Topic topic = new Topic();
			topic.setContent(content);
			topic.setCreateTime(new Date());
			topic.setCustomer(customer);
			topic.setLastUpdateTime(new Date());
			topic.setReplyCount(0);
			topic.setTitle(title);
			topic.setType(eventTypeService.getEntityById(eventType));
			topicService.saveEntity(topic);
			customerService.addPoints(2, customer.getId());
			TopicDTO dto = new TopicDTO();
			dto.setTopicByList(topic);
			dto.setReplys(new ArrayList<TopicReplyDTO>(0));
			result = new Result<TopicDTO>(1, null, dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TopicController:" + CommonUtil.getErrStr(e));
			result = new Result<TopicDTO>(0, null, null);
		}
		return result;
	}

	/**
	 * 话题列表
	 * 
	 * @param types
	 * @param curPage
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result<PageBean<TopicDTO>> list(
			@RequestParam(value = "types", required = true) String types,
			@RequestParam(value = "curPage", required = true) Integer curPage) {
		try {
			PageBean<Topic> pageBean = topicService.list(types, curPage);
			PageBean<TopicDTO> dtoPage = new PageBean<TopicDTO>();
			TopicDTO dto = null;
			List<TopicDTO> dtoList = new ArrayList<TopicDTO>();
			// List<TopicReplyDTO> replyList = null;
			// TopicReplyDTO replyDTO = null;
			// 将持久化对象转为DTO对象
			for (Topic topic : pageBean.getData()) {
				dto = new TopicDTO();
				dto.setTopicByList(topic);
				// List<TopicReply> tList = topicReplyService.getReplyByTop(
				// topic.getId(), 5);
				// 获得Topic前5条评论
				// replyList = new ArrayList<TopicReplyDTO>(0);
				// for (TopicReply reply : tList) {
				// replyDTO = new TopicReplyDTO();
				// replyDTO.setTopicReply(reply);
				// replyList.add(replyDTO);
				// }
				// 添加5条评论
				// dto.setReplys(replyList);
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
			logger.error("TopicController:" + CommonUtil.getErrStr(e));
			result = new Result<PageBean<TopicDTO>>(0, null, null);
		}
		return result;
	}

	public void setTopicService(ITopicService topicService) {
		this.topicService = topicService;
	}

	public void setTopicReplyService(ITopicReplyService topicReplyService) {
		this.topicReplyService = topicReplyService;
	}

	public void setEventTypeService(IEventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setTopicImageService(ITopicImageService topicImageService) {
		this.topicImageService = topicImageService;
	}
}
