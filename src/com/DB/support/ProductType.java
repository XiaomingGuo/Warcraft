package com.DB.support;

/**
 * ProductType entity. @author MyEclipse Persistence Tools
 */

public class ProductType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String storeroom;

	// Constructors

	/** default constructor */
	public ProductType() {
	}

	/** full constructor */
	public ProductType(String name, String storeroom) {
		this.name = name;
		this.storeroom = storeroom;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreroom() {
		return this.storeroom;
	}

	public void setStoreroom(String storeroom) {
		this.storeroom = storeroom;
	}

}