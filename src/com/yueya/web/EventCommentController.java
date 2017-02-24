package com.yueya.web;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yueya.base.BaseController;
import com.yueya.common.PageBean;
import com.yueya.common.Result;
import com.yueya.customer.model.Customer;
import com.yueya.event.dto.EventCommentDTO;
import com.yueya.event.model.CommentImage;
import com.yueya.event.model.EventComment;
import com.yueya.event.service.ICommentImageService;
import com.yueya.event.service.IEventCommentService;
import com.yueya.util.CommonUtil;
import com.yueya.util.DateFormatUtil;
import com.yueya.util.FileUtil;
import com.yueya.util.KeywordFilterUtil;
import com.yueya.util.MySessionContext;

@Controller
@RequestMapping(value = "/event/comment")
@SuppressWarnings("unchecked")
public class EventCommentController extends BaseController {
	@Resource
	private IEventCommentService commentService;
	@Resource
	private ICommentImageService commentImageService;

	@RequestMapping("/list")
	@ResponseBody
	public Result<PageBean<EventCommentDTO>> list(
			@RequestParam(value = "curPage", required = true) Integer curPage,
			@RequestParam(value = "eventId", required = true) Integer eventId) {
		try {
			PageBean<EventComment> pageBean = commentService.list(curPage, eventId);
			PageBean<EventCommentDTO> list = new PageBean<EventCommentDTO>();
			list.setPageIndex(pageBean.getPageIndex());
			list.setPageSize(pageBean.getPageSize());
			list.setTotalCount(pageBean.getTotalCount());
			list.setTotalPage(pageBean.getTotalPage());
			EventCommentDTO dto = null;
			List<EventCommentDTO> data = new ArrayList<EventCommentDTO>();
			for(EventComment comment : pageBean.getData()) {
				dto = new EventCommentDTO();
				dto.setEventComment(comment);
				data.add(dto);
			}
			list.setData(data);
			result = new Result<PageBean<EventCommentDTO>>(1, null, list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventCommentController:" + CommonUtil.getErrStr(e));
			result = new Result<PageBean<EventCommentDTO>>(0, null, null);
		}
		return result;
	}

	/**
	 * 添加评论
	 * 
	 * @param sessionid
	 * @param content
	 * @param eventId
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result<EventCommentDTO> addComment(
			@RequestParam(value = "sessionid", required = true) String sessionid,
			@RequestParam(value = "content", required = true) String content,
			@RequestParam(value = "eventId", required = true) Integer eventId) {
		try {
			//敏感词过滤
			content = KeywordFilterUtil.searchKeyword(content);
			
			HttpSession session = MySessionContext.getSession(sessionid);
			if (session == null)
				throw new Exception();
			Customer customer = (Customer) session.getAttribute("userInfo");
			EventComment comment = commentService.addEventComment(eventId,
					content, customer.getId());
			EventCommentDTO dto = new EventCommentDTO();
			dto.setEventComment(comment);
			result = new Result<EventCommentDTO>(1, null, dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventCommentController:" + CommonUtil.getErrStr(e));
			result = new Result<EventComment>(0, null, null);
		}
		return result;
	}

	/**
	 * 上传image
	 * 
	 * @param file
	 * @param eventId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/upload/image")
	@ResponseBody
	public Result<String> uploadImage(
			@RequestParam(value = "image", required = true) MultipartFile file,
			@RequestParam(value = "commentId", required = true) Integer commentId,
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
			EventComment comment = commentService.getEntityById(commentId);
			CommentImage image = new CommentImage();
			image.setComment(comment);
			image.setUrl("/upload/" + random);
			commentImageService.saveEntity(image);
			result = new Result<String>(1, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventCommentController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
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

	public void setCommentService(IEventCommentService commentService) {
		this.commentService = commentService;
	}

	public void setCommentImageService(ICommentImageService commentImageService) {
		this.commentImageService = commentImageService;
	}
}
