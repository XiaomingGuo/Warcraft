package com.DB.operation;

/**
 * ShippingRecord entity. @author MyEclipse Persistence Tools
 */

public class ShippingRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String customerPo;
	private String barCode;
	private String batchLot;
	private String orderName;
	private Integer shipQty;
	private Integer shippingNo;

	// Constructors

	/** default constructor */
	public ShippingRecord() {
	}

	/** full constructor */
	public ShippingRecord(String customerPo, String barCode, String batchLot,
			String orderName, Integer shipQty, Integer shippingNo) {
		this.customerPo = customerPo;
		this.barCode = barCode;
		this.batchLot = batchLot;
		this.orderName = orderName;
		this.shipQty = shipQty;
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

	public Integer getShipQty() {
		return this.shipQty;
	}

	public void setShipQty(Integer shipQty) {
		this.shipQty = shipQty;
	}

	public Integer getShippingNo() {
		return this.shippingNo;
	}

	public void setShippingNo(Integer shippingNo) {
		this.shippingNo = shippingNo;
	}

}