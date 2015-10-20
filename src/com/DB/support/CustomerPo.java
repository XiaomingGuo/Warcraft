package com.DB.support;

/**
 * CustomerPo entity. @author MyEclipse Persistence Tools
 */

public class CustomerPo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String poName;
	private Integer status;

	// Constructors

	/** default constructor */
	public CustomerPo() {
	}

	/** full constructor */
	public CustomerPo(String poName, Integer status) {
		this.poName = poName;
		this.status = status;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPoName() {
		return this.poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}