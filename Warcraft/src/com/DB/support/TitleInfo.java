package com.DB.support;

/**
 * TitleInfo entity. @author MyEclipse Persistence Tools
 */

public class TitleInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String titleName;

	// Constructors

	/** default constructor */
	public TitleInfo() {
	}

	/** full constructor */
	public TitleInfo(String titleName) {
		this.titleName = titleName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

}