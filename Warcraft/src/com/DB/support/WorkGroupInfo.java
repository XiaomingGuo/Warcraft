package com.DB.support;

import java.sql.Time;

/**
 * WorkGroupInfo entity. @author MyEclipse Persistence Tools
 */

public class WorkGroupInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String groupName;
	private Time checkInTime;
	private Time checkOutTime;
	private Integer workDaysAweek;

	// Constructors

	/** default constructor */
	public WorkGroupInfo() {
	}

	/** full constructor */
	public WorkGroupInfo(String groupName, Time checkInTime, Time checkOutTime,
			Integer workDaysAweek) {
		this.groupName = groupName;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.workDaysAweek = workDaysAweek;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Time getCheckInTime() {
		return this.checkInTime;
	}

	public void setCheckInTime(Time checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Time getCheckOutTime() {
		return this.checkOutTime;
	}

	public void setCheckOutTime(Time checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Integer getWorkDaysAweek() {
		return this.workDaysAweek;
	}

	public void setWorkDaysAweek(Integer workDaysAweek) {
		this.workDaysAweek = workDaysAweek;
	}

}