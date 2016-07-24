package com.DB.support;

/**
 * HolidayType entity. @author MyEclipse Persistence Tools
 */

public class HolidayType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String holidayName;

	// Constructors

	/** default constructor */
	public HolidayType() {
	}

	/** full constructor */
	public HolidayType(String holidayName) {
		this.holidayName = holidayName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHolidayName() {
		return this.holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

}