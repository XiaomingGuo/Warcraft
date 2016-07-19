package com.DB.support;

/**
 * HolidayMark entity. @author MyEclipse Persistence Tools
 */

public class HolidayMark implements java.io.Serializable {

	// Fields

	private Integer id;
	private String checkInId;
	private String holidayDate;

	// Constructors

	/** default constructor */
	public HolidayMark() {
	}

	/** full constructor */
	public HolidayMark(String checkInId, String holidayDate) {
		this.checkInId = checkInId;
		this.holidayDate = holidayDate;
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

}