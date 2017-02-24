package com.yueya.tag.service.impl;

import org.springframework.stereotype.Service;

import com.yueya.tag.model.Tag;
import com.yueya.tag.service.ITagService;

import com.yueya.base.impl.BaseServiceImpl;

@Service
public class TagServiceImpl extends BaseServiceImpl<Tag> implements ITagService {
	@Override
	public Long tagVersionStr2Num(String tagVersion) {
		String[] nums = tagVersion.split("\\.");
		Long result = Long.valueOf(nums[0]) * 1000 + Long.valueOf(nums[1])
				* 100 + Long.valueOf(nums[2]) * 10;
		return result;
	}

	@Override
	public Tag getTagByKey(String key) {
		String hql = "from Tag where tagName=?";
		return queryBySingle(hql, new Object[] { key });
	}
}
