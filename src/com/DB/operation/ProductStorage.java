package com.DB.operation;

import java.sql.Timestamp;

/**
 * ProductStorage entity. @author MyEclipse Persistence Tools
 */

public class ProductStorage implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private String batchLot;
	private String orderName;
	private Integer inQty;
	private Integer outQty;
	private Float pricePerUnit;
	private Double totalPrice;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public ProductStorage() {
	}

	/** full constructor */
	public ProductStorage(String barCode, String batchLot, String orderName,
			Integer inQty, Integer outQty, Float pricePerUnit,
			Double totalPrice, Timestamp createDate) {
		this.barCode = barCode;
		this.batchLot = batchLot;
		this.orderName = orderName;
		this.inQty = inQty;
		this.outQty = outQty;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
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

	public String getBatchLot() {
		return this.batchLot;
	}

	public void setBatchLot(String batchLot) {
		this.batchLot = batchLot;
	}

	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Integer getInQty() {
		return this.inQty;
	}

	public void setInQty(Integer inQty) {
		this.inQty = inQty;
	}

	public Integer getOutQty() {
		return this.outQty;
	}

	public void setOutQty(Integer outQty) {
		this.outQty = outQty;
	}

	public Float getPricePerUnit() {
		return this.pricePerUnit;
	}

	public void setPricePerUnit(Float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}