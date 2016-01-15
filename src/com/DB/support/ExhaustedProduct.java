package com.DB.support;

import java.sql.Timestamp;

/**
 * ExhaustedProduct entity. @author MyEclipse Persistence Tools
 */

public class ExhaustedProduct implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private String batchLot;
	private Integer inQty;
	private Integer outQty;
	private Float pricePerUnit;
	private Double totalPrice;
	private String orderName;
	private String vendorName;
	private String inStoreDate;
	private Integer isEnsure;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public ExhaustedProduct() {
	}

	/** full constructor */
	public ExhaustedProduct(String barCode, String batchLot, Integer inQty,
			Integer outQty, Float pricePerUnit, Double totalPrice,
			String orderName, String vendorName, String inStoreDate,
			Integer isEnsure, Timestamp createDate) {
		this.barCode = barCode;
		this.batchLot = batchLot;
		this.inQty = inQty;
		this.outQty = outQty;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
		this.orderName = orderName;
		this.vendorName = vendorName;
		this.inStoreDate = inStoreDate;
		this.isEnsure = isEnsure;
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

	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getInStoreDate() {
		return this.inStoreDate;
	}

	public void setInStoreDate(String inStoreDate) {
		this.inStoreDate = inStoreDate;
	}

	public Integer getIsEnsure() {
		return this.isEnsure;
	}

	public void setIsEnsure(Integer isEnsure) {
		this.isEnsure = isEnsure;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}