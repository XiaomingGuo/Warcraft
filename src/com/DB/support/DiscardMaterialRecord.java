package com.DB.support;

/**
 * DiscardMaterialRecord entity. @author MyEclipse Persistence Tools
 */

public class DiscardMaterialRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderName;
	private String barCode;
	private String batchLot;
	private Integer qty;
	private String reason;

	// Constructors

	/** default constructor */
	public DiscardMaterialRecord() {
	}

	/** minimal constructor */
	public DiscardMaterialRecord(String orderName, String barCode, Integer qty,
			String reason) {
		this.orderName = orderName;
		this.barCode = barCode;
		this.qty = qty;
		this.reason = reason;
	}

	/** full constructor */
	public DiscardMaterialRecord(String orderName, String barCode,
			String batchLot, Integer qty, String reason) {
		this.orderName = orderName;
		this.barCode = barCode;
		this.batchLot = batchLot;
		this.qty = qty;
		this.reason = reason;
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

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}