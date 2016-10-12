package com.DB.support;

/**
 * ProductInfo entity. @author MyEclipse Persistence Tools
 */

public class ProductInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private String name;
	private String productType;
	private Float weight;
	private Float samplePrice;
	private String sampleVendor;
	private String processName;
	private Integer capacity;
	private String description;
	private String picture;

	// Constructors

	/** default constructor */
	public ProductInfo() {
	}

	/** minimal constructor */
	public ProductInfo(String barCode, String name, String productType,
			Float weight, Float samplePrice, String description) {
		this.barCode = barCode;
		this.name = name;
		this.productType = productType;
		this.weight = weight;
		this.samplePrice = samplePrice;
		this.description = description;
	}

	/** full constructor */
	public ProductInfo(String barCode, String name, String productType,
			Float weight, Float samplePrice, String sampleVendor,
			String processName, Integer capacity, String description,
			String picture) {
		this.barCode = barCode;
		this.name = name;
		this.productType = productType;
		this.weight = weight;
		this.samplePrice = samplePrice;
		this.sampleVendor = sampleVendor;
		this.processName = processName;
		this.capacity = capacity;
		this.description = description;
		this.picture = picture;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Float getWeight() {
		return this.weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getSamplePrice() {
		return this.samplePrice;
	}

	public void setSamplePrice(Float samplePrice) {
		this.samplePrice = samplePrice;
	}

	public String getSampleVendor() {
		return this.sampleVendor;
	}

	public void setSampleVendor(String sampleVendor) {
		this.sampleVendor = sampleVendor;
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}