package com.DB.support;

/**
 * HolidayMark entity. @author MyEclipse Persistence Tools
 */

public class HolidayMark implements java.io.Serializable {

	// Fields

	private Integer id;
	private String checkInId;
	private String holidayDate;
	private Integer holidayTime;
	private String holidayInfo;

	// Constructors

	/** default constructor */
	public HolidayMark() {
	}

	/** full constructor */
	public HolidayMark(String checkInId, String holidayDate,
			Integer holidayTime, String holidayInfo) {
		this.checkInId = checkInId;
		this.holidayDate = holidayDate;
		this.holidayTime = holidayTime;
		this.holidayInfo = holidayInfo;
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

	public String getHolidayDate() {
		return this.holidayDate;
	}

	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	public Integer getHolidayTime() {
		return this.holidayTime;
	}

	public void setHolidayTime(Integer holidayTime) {
		this.holidayTime = holidayTime;
	}

	public String getHolidayInfo() {
		return this.holidayInfo;
	}

	public void setHolidayInfo(String holidayInfo) {
		this.holidayInfo = holidayInfo;
	}

}