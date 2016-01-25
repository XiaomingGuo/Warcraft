package com.DB.support;

/**
 * MbMaterialPoRecord entity. @author MyEclipse Persistence Tools
 */

public class MbMaterialPoRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mbMaterialPoId;
	private String barCode;
	private String batchLot;
	private Integer inQty;
	private String inDate;

	// Constructors

	/** default constructor */
	public MbMaterialPoRecord() {
	}

	/** full constructor */
	public MbMaterialPoRecord(Integer mbMaterialPoId, String barCode,
			String batchLot, Integer inQty, String inDate) {
		this.mbMaterialPoId = mbMaterialPoId;
		this.barCode = barCode;
		this.batchLot = batchLot;
		this.inQty = inQty;
		this.inDate = inDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMbMaterialPoId() {
		return this.mbMaterialPoId;
	}

	public void setMbMaterialPoId(Integer mbMaterialPoId) {
		this.mbMaterialPoId = mbMaterialPoId;
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

	public String getInDate() {
		return this.inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

}