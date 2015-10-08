package com.DB.operation;

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
	private String description;
	private String picture;

	// Constructors

	/** default constructor */
	public ProductInfo() {
	}

	/** minimal constructor */
	public ProductInfo(String barCode, String name, String productType,
			Float weight, String description) {
		this.barCode = barCode;
		this.name = name;
		this.productType = productType;
		this.weight = weight;
		this.description = description;
	}

	/** full constructor */
	public ProductInfo(String barCode, String name, String productType,
			Float weight, String description, String picture) {
		this.barCode = barCode;
		this.name = name;
		this.productType = productType;
		this.weight = weight;
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