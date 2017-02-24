package com.yueya.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueya.base.BaseController;
import com.yueya.common.Result;
import com.yueya.event.dto.EventTypeDTO;
import com.yueya.event.model.EventType;
import com.yueya.event.service.IEventTypeService;
import com.yueya.tag.model.Tag;
import com.yueya.tag.service.ITagService;
import com.yueya.util.CommonUtil;

@Controller
@RequestMapping(value = "/event/type")
@SuppressWarnings("unchecked")
public class EventTypeController extends BaseController {
	@Resource
	private IEventTypeService eventTypeService;
	@Resource
	private ITagService tagService;
	
	/**
	 * 更新活动图片
	 * @return
	 */
	@RequestMapping("/update/image")
	@ResponseBody
	public Result<String> updateImage() {
		List<EventType> list = eventTypeService.getEventTypeImage();
		StringBuffer sbu = new StringBuffer();
		for(EventType eventType : list) {
			if(eventType.getImageUrl() != null && eventType.getImageUrl().length() > 0)
			sbu.append(eventType.getImageUrl()).append(",");
		}
		if(sbu.length() > 0)
			sbu.deleteCharAt(sbu.length() - 1);
		result = new Result<String>(1, null, sbu.toString());
		return result;
	}

	/**
	 * 查看是否有更新
	 * 
	 * @param tagVersion
	 * @return
	 */
	@RequestMapping("/isupdate")
	@ResponseBody
	public Result<String> isUpdate(
			@RequestParam(value = "tagVersion", required = false) String tagVersion) {
		try {
			int num = 0;
			Tag tag = tagService.getTagByKey("eventTypeVersion");
			if (tagVersion == null || tagVersion.length() <= 0)
				num = 1;
			else {
				Long cliVersion = tagService.tagVersionStr2Num(tagVersion);
				Long curTag = tagService.tagVersionStr2Num(tag.getTagValue());
				if (curTag.intValue() > cliVersion.intValue())
					num = 1;
			}
			tagVersion = tag.getTagValue();
			result = new Result<String>(num, null, tagVersion);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventTypeController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	/**
	 * 获取所有活动类型
	 * @return
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Result<List<EventTypeDTO>> allEventType() {
		try {
			List<EventType> rootList = eventTypeService.getRootType();
			List<EventTypeDTO> dtoList = new ArrayList<EventTypeDTO>();
			EventTypeDTO dto = null;
			for (EventType type : rootList) {
				dto = new EventTypeDTO();
				dto.setEventType(type);
				dtoList.add(dto);
			}
			result = new Result<List<EventTypeDTO>>(1, null, dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EventTypeController:" + CommonUtil.getErrStr(e));
			result = new Result<List<EventTypeDTO>>(0, null, null);
		}
		return result;
	}

	public void setEventTypeService(IEventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}

	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}
}
