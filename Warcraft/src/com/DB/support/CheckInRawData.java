package com.DB.support;

import java.sql.Time;
import java.util.Date;

/**
 * CheckInRawData entity. @author MyEclipse Persistence Tools
 */

public class CheckInRawData implements java.io.Serializable {

	// Fields

	private Integer id;
	private String checkInId;
	private Date checkInDate;
	private Time checkInTime;
	private Integer workGroup;
	private Integer isEnsure;

	// Constructors

	/** default constructor */
	public CheckInRawData() {
	}

	/** full constructor */
	public CheckInRawData(String checkInId, Date checkInDate, Time checkInTime,
			Integer workGroup, Integer isEnsure) {
		this.checkInId = checkInId;
		this.checkInDate = checkInDate;
		this.checkInTime = checkInTime;
		this.workGroup = workGroup;
		this.isEnsure = isEnsure;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCheckInId() {
		return this.checkInId;
	}

	public void setCheckInId(String checkInId) {
		this.checkInId = checkInId;
	}

	public Date getCheckInDate() {
		return this.checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Time getCheckInTime() {
		return this.checkInTime;
	}

	public void setCheckInTime(Time checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Integer getWorkGroup() {
		return this.workGroup;
	}

	public void setWorkGroup(Integer workGroup) {
		this.workGroup = workGroup;
	}

	public Integer getIsEnsure() {
		return this.isEnsure;
	}

	public void setIsEnsure(Integer isEnsure) {
		this.isEnsure = isEnsure;
	}

}