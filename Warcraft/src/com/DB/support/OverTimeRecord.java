package com.DB.support;

/**
 * OverTimeRecord entity. @author MyEclipse Persistence Tools
 */

public class OverTimeRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String checkInId;
	private String overTimeDate;
	private String overTimeHour;

	// Constructors

	/** default constructor */
	public OverTimeRecord() {
	}

	/** full constructor */
	public OverTimeRecord(String checkInId, String overTimeDate,
			String overTimeHour) {
		this.checkInId = checkInId;
		this.overTimeDate = overTimeDate;
		this.overTimeHour = overTimeHour;
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

	public String getOverTimeDate() {
		return this.overTimeDate;
	}

	public void setOverTimeDate(String overTimeDate) {
		this.overTimeDate = overTimeDate;
	}

	public String getOverTimeHour() {
		return this.overTimeHour;
	}

	public void setOverTimeHour(String overTimeHour) {
		this.overTimeHour = overTimeHour;
	}

}