package com.yueya.event.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yueya.customer.dto.CustomerDTO;
import com.yueya.event.model.Event;
import com.yueya.util.DateFormatUtil;

public class EventDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String content;
	private String startTime;
	private String endTime;
	private Integer personCount;
	private String joinCount;
	private Integer typeId;
	private String phone;
	private Integer status;// 状态： 1 未开始 2 进行中 3已经结束
	private Integer cityId;
	private Integer favor;// 赞
	private String cusName;
	private String cusId;
	private String imageUrl;
	private String address;
	private Integer commentCount;
	private String createTime;
	private Integer flag = 0;// 是否赞
	private List<CustomerDTO> signUp = new ArrayList<CustomerDTO>();
	private String isSignup = "0";// 是否报名
	private String latitude;
	private String longitude;
	private String shareCount;// 分享总数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getPersonCount() {
		return personCount;
	}

	public void setPersonCount(Integer personCount) {
		this.personCount = personCount;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getFavor() {
		return favor;
	}

	public void setFavor(Integer favor) {
		this.favor = favor;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public List<CustomerDTO> getSignUp() {
		return signUp;
	}

	public void setSignUp(List<CustomerDTO> signUp) {
		this.signUp = signUp;
	}

	public void setIsSignup(String isSignup) {
		this.isSignup = isSignup;
	}

	public String getIsSignup() {
		return isSignup;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getJoinCount() {
		return joinCount;
	}

	public void setJoinCount(String joinCount) {
		this.joinCount = joinCount;
	}

	public String getShareCount() {
		return shareCount;
	}

	public void setShareCount(String shareCount) {
		this.shareCount = shareCount;
	}

	/**
	 * 列表
	 * 
	 * @param event
	 */
	public void setEventByList(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.content = event.getContent();
		this.startTime = DateFormatUtil.dateToStr(event.getStartTime());
		this.endTime = event.getEndTime() == null ? "" : DateFormatUtil
				.dateToStr(event.getEndTime());
		this.createTime = DateFormatUtil.dateToStr(event.getCreateTime());
		this.personCount = event.getPersonCount() == null ? 0 : event
				.getPersonCount();
		this.typeId = event.getTypeId();
		this.phone = event.getPhone();
		this.status = calStatus(event.getStartTime(), event.getEndTime());
		this.cityId = event.getCityId();
		this.favor = event.getFavor();
		this.cusName = event.getCustomer().getUsername();
		this.imageUrl = event.getCustomer().getImageUrl() == null ? "" : event
				.getCustomer().getImageUrl();
		this.address = event.getAddress() == null ? "" : event.getAddress();
		this.content = event.getContent();
		this.cusId = event.getCustomer().getId() + "";
		this.latitude = event.getLatitude();
		this.longitude = event.getLongitude();
		this.joinCount = event.getJoinCount() + "";
		this.shareCount = event.getShareCount() + "";
	}

	/**
	 * 活动详情
	 * 
	 * @param event
	 */
	public void setEventByDetail(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.content = event.getContent();
		this.startTime = DateFormatUtil.dateToStr(event.getStartTime());
		this.endTime = event.getEndTime() == null ? "" : DateFormatUtil
				.dateToStr(event.getEndTime());
		this.createTime = DateFormatUtil.dateToStr(event.getCreateTime());
		this.personCount = event.getPersonCount() == null ? 0 : event
				.getPersonCount();
		this.typeId = event.getTypeId();
		this.phone = event.getPhone();
		this.status = calStatus(event.getStartTime(), event.getEndTime());
		this.cityId = event.getCityId();
		this.favor = event.getFavor();
		this.cusName = event.getCustomer().getUsername();
		this.imageUrl = event.getCustomer().getImageUrl() == null ? "" : event
				.getCustomer().getImageUrl();
		this.address = event.getAddress() == null ? "" : event.getAddress();
		this.cusId = event.getCustomer().getId() + "";
		this.latitude = event.getLatitude();
		this.longitude = event.getLongitude();
		this.joinCount = event.getJoinCount() + "";
		this.shareCount = event.getShareCount() + "";
	}
	
	/**
	 * 计算该活动状态
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Integer calStatus(Date startTime, Date endTime) {
		Integer result = 0;
		Date curDate = new Date();
		//当前时间 在 开始时间之前 表示未开始，报名中
		if(curDate.before(startTime))
			result = 1;
		else if(curDate.after(startTime) && curDate.before(endTime))
			result = 2;
		else if(curDate.after(endTime))
			result = 3;
		return result;
			
	}
}
