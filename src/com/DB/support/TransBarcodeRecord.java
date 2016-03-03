package com.DB.support;

/**
 * TransBarcodeRecord entity. @author MyEclipse Persistence Tools
 */

public class TransBarcodeRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String fromBarCode;
	private String fromBatchLot;
	private Integer fromQty;
	private String toBarCode;
	private String toBatchLot;
	private Integer toQty;

	// Constructors

	/** default constructor */
	public TransBarcodeRecord() {
	}

	/** full constructor */
	public TransBarcodeRecord(String fromBarCode, String fromBatchLot,
			Integer fromQty, String toBarCode, String toBatchLot, Integer toQty) {
		this.fromBarCode = fromBarCode;
		this.fromBatchLot = fromBatchLot;
		this.fromQty = fromQty;
		this.toBarCode = toBarCode;
		this.toBatchLot = toBatchLot;
		this.toQty = toQty;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFromBarCode() {
		return this.fromBarCode;
	}

	public void setFromBarCode(String fromBarCode) {
		this.fromBarCode = fromBarCode;
	}

	public String getFromBatchLot() {
		return this.fromBatchLot;
	}

	public void setFromBatchLot(String fromBatchLot) {
		this.fromBatchLot = fromBatchLot;
	}

	public Integer getFromQty() {
		return this.fromQty;
	}

	public void setFromQty(Integer fromQty) {
		this.fromQty = fromQty;
	}

	public String getToBarCode() {
		return this.toBarCode;
	}

	public void setToBarCode(String toBarCode) {
		this.toBarCode = toBarCode;
	}

	public String getToBatchLot() {
		return this.toBatchLot;
	}

	public void setToBatchLot(String toBatchLot) {
		this.toBatchLot = toBatchLot;
	}

	public Integer getToQty() {
		return this.toQty;
	}

	public void setToQty(Integer toQty) {
		this.toQty = toQty;
	}

}