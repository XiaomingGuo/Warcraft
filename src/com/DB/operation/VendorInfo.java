package com.DB.operation;

/**
 * VendorInfo entity. @author MyEclipse Persistence Tools
 */

public class VendorInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String vendorName;
	private String storeroom;
	private String vendorFax;
	private String vendorTel;
	private String vendorEMail;
	private String vendorAddress;
	private String description;

	// Constructors

	/** default constructor */
	public VendorInfo() {
	}

	/** full constructor */
	public VendorInfo(String vendorName, String storeroom, String vendorFax,
			String vendorTel, String vendorEMail, String vendorAddress,
			String description) {
		this.vendorName = vendorName;
		this.storeroom = storeroom;
		this.vendorFax = vendorFax;
		this.vendorTel = vendorTel;
		this.vendorEMail = vendorEMail;
		this.vendorAddress = vendorAddress;
		this.description = description;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getStoreroom() {
		return this.storeroom;
	}

	public void setStoreroom(String storeroom) {
		this.storeroom = storeroom;
	}

	public String getVendorFax() {
		return this.vendorFax;
	}

	public void setVendorFax(String vendorFax) {
		this.vendorFax = vendorFax;
	}

	public String getVendorTel() {
		return this.vendorTel;
	}

	public void setVendorTel(String vendorTel) {
		this.vendorTel = vendorTel;
	}

	public String getVendorEMail() {
		return this.vendorEMail;
	}

	public void setVendorEMail(String vendorEMail) {
		this.vendorEMail = vendorEMail;
	}

	public String getVendorAddress() {
		return this.vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}