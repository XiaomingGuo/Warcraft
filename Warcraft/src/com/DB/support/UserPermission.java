package com.DB.support;

/**
 * UserPermission entity. @author MyEclipse Persistence Tools
 */

public class UserPermission implements java.io.Serializable {

	// Fields

	private Integer id;
	private String checkInId;
	private String titleName;

	// Constructors

	/** default constructor */
	public UserPermission() {
	}

	/** full constructor */
	public UserPermission(String checkInId, String titleName) {
		this.checkInId = checkInId;
		this.titleName = titleName;
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

	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

}