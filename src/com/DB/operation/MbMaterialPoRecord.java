package com.DB.operation;

import java.sql.Timestamp;

/**
 * MbMaterialPoRecord entity. @author MyEclipse Persistence Tools
 */

public class MbMaterialPoRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mbMaterialPoId;
	private Integer inQty;
	private Timestamp inDate;

	// Constructors

	/** default constructor */
	public MbMaterialPoRecord() {
	}

	/** full constructor */
	public MbMaterialPoRecord(Integer mbMaterialPoId, Integer inQty,
			Timestamp inDate) {
		this.mbMaterialPoId = mbMaterialPoId;
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

	public Integer getInQty() {
		return this.inQty;
	}

	public void setInQty(Integer inQty) {
		this.inQty = inQty;
	}

	public Timestamp getInDate() {
		return this.inDate;
	}

	public void setInDate(Timestamp inDate) {
		this.inDate = inDate;
	}

}