package com.DB.support;

/**
 * UserPermission entity. @author MyEclipse Persistence Tools
 */

public class UserPermission implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userName;
	private String titleName;

	// Constructors

	/** default constructor */
	public UserPermission() {
	}

	/** full constructor */
	public UserPermission(String userName, String titleName) {
		this.userName = userName;
		this.titleName = titleName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

}