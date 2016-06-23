package com.DB.support;

import java.sql.Timestamp;

/**
 * MbMaterialPo entity. @author MyEclipse Persistence Tools
 */

public class MbMaterialPo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private String poName;
	private String dateOfDelivery;
	private String vendor;
	private Integer poQty;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public MbMaterialPo() {
	}

	/** full constructor */
	public MbMaterialPo(String barCode, String poName, String dateOfDelivery,
			String vendor, Integer poQty, Timestamp createDate) {
		this.barCode = barCode;
		this.poName = poName;
		this.dateOfDelivery = dateOfDelivery;
		this.vendor = vendor;
		this.poQty = poQty;
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

	public String getDateOfDelivery() {
		return this.dateOfDelivery;
	}

	public void setDateOfDelivery(String dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Integer getPoQty() {
		return this.poQty;
	}

	public void setPoQty(Integer poQty) {
		this.poQty = poQty;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}