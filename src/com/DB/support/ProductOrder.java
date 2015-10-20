package com.DB.support;

import java.util.List;

/**
 * ProductOrder entity. @author MyEclipse Persistence Tools
 */

public class ProductOrder implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderName;
	private Integer status;

	// Constructors

	/** default constructor */
	public ProductOrder() {
	}

	/** full constructor */
	public ProductOrder(String orderName, Integer status) {
		this.orderName = orderName;
		this.status = status;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}