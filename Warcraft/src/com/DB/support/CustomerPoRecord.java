package com.DB.support;

import java.sql.Timestamp;

/**
 * CustomerPoRecord entity. @author MyEclipse Persistence Tools
 */

public class CustomerPoRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private String poName;
	private String deliveryDate;
	private Integer qty;
	private Integer outQty;
	private String vendor;
	private Integer percent;
	private Integer isEnsure;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public CustomerPoRecord() {
	}

	/** full constructor */
	public CustomerPoRecord(String barCode, String poName, String deliveryDate,
			Integer qty, Integer outQty, String vendor, Integer percent,
			Integer isEnsure, Timestamp createDate) {
		this.barCode = barCode;
		this.poName = poName;
		this.deliveryDate = deliveryDate;
		this.qty = qty;
		this.outQty = outQty;
		this.vendor = vendor;
		this.percent = percent;
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

	public String getPoName() {
		return this.poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
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

	public Integer getOutQty() {
		return this.outQty;
	}

	public void setOutQty(Integer outQty) {
		this.outQty = outQty;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Integer getPercent() {
		return this.percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
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