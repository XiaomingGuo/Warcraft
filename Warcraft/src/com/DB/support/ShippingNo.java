package com.DB.support;

import java.sql.Timestamp;

/**
 * ShippingNo entity. @author MyEclipse Persistence Tools
 */

public class ShippingNo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String customerPo;
	private Timestamp createDate;
	private Integer shippingNo;

	// Constructors

	/** default constructor */
	public ShippingNo() {
	}

	/** full constructor */
	public ShippingNo(String customerPo, Timestamp createDate,
			Integer shippingNo) {
		this.customerPo = customerPo;
		this.createDate = createDate;
		this.shippingNo = shippingNo;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerPo() {
		return this.customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Integer getShippingNo() {
		return this.shippingNo;
	}

	public void setShippingNo(Integer shippingNo) {
		this.shippingNo = shippingNo;
	}

}