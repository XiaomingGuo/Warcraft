package com.DB.operation;

import java.sql.Timestamp;

/**
 * ShippingNo entity. @author MyEclipse Persistence Tools
 */

public class ShippingNo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String customerPo;
	private Timestamp deliveryDate;
	private Integer shippingNo;

	// Constructors

	/** default constructor */
	public ShippingNo() {
	}

	/** full constructor */
	public ShippingNo(String customerPo, Timestamp deliveryDate,
			Integer shippingNo) {
		this.customerPo = customerPo;
		this.deliveryDate = deliveryDate;
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

	public Timestamp getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getShippingNo() {
		return this.shippingNo;
	}

	public void setShippingNo(Integer shippingNo) {
		this.shippingNo = shippingNo;
	}

}