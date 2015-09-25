package com.DB.operation;

import java.sql.Timestamp;

/**
 * ProductOrderRecord entity. @author MyEclipse Persistence Tools
 */

public class ProductOrderRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private String deliveryDate;
	private Integer qty;
	private Integer completeQty;
	private Integer oqcQty;
	private String poName;
	private String orderName;
	private Integer status;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public ProductOrderRecord() {
	}

	/** full constructor */
	public ProductOrderRecord(String barCode, String deliveryDate, Integer qty,
			Integer completeQty, Integer oqcQty, String poName,
			String orderName, Integer status, Timestamp createDate) {
		this.barCode = barCode;
		this.deliveryDate = deliveryDate;
		this.qty = qty;
		this.completeQty = completeQty;
		this.oqcQty = oqcQty;
		this.poName = poName;
		this.orderName = orderName;
		this.status = status;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getCompleteQty() {
		return this.completeQty;
	}

	public void setCompleteQty(Integer completeQty) {
		this.completeQty = completeQty;
	}

	public Integer getOqcQty() {
		return this.oqcQty;
	}

	public void setOqcQty(Integer oqcQty) {
		this.oqcQty = oqcQty;
	}

	public String getPoName() {
		return this.poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
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

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}