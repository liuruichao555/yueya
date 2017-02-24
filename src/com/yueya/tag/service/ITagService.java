package com.yueya.tag.service;

import com.yueya.base.IBaseService;
import com.yueya.tag.model.Tag;

public interface ITagService extends IBaseService<Tag>{
	Long tagVersionStr2Num(String tagVersion);
	Tag getTagByKey(String key);
}
