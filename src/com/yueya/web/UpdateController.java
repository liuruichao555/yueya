package com.yueya.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueya.base.BaseController;
import com.yueya.common.Result;
import com.yueya.tag.model.Tag;
import com.yueya.tag.service.ITagService;
import com.yueya.util.CommonUtil;

@Controller
@RequestMapping(value = "/app")
@SuppressWarnings("unchecked")
public class UpdateController extends BaseController {
	@Resource
	private ITagService tagService;
	
	/**
	 * 软件是否更新
	 * @param version
	 * @return
	 */
	@RequestMapping("/isUpdate")
	@ResponseBody
	public Result<String> isUpdate(@RequestParam(value = "version", required = true) String version) {
		try {
			Long cliVersion = tagService.tagVersionStr2Num(version);
			Tag tag = tagService.getTagByKey("appVersion");
			Long curTag = tagService.tagVersionStr2Num(tag.getTagValue());
			int num = 0;
			if(curTag.intValue() > cliVersion.intValue())
				num = 1;
			result = new Result<String>(num, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("UpdateController:" + CommonUtil.getErrStr(e));
			result = new Result<String>(0, null, null);
		}
		return result;
	}

	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}
}
