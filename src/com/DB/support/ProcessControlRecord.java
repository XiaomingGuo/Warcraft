package com.DB.support;

import java.sql.Timestamp;

/**
 * ProcessControlRecord entity. @author MyEclipse Persistence Tools
 */

public class ProcessControlRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private String poName;
	private Integer processId;
	private Integer qty;
	private String operator;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public ProcessControlRecord() {
	}

	/** full constructor */
	public ProcessControlRecord(String barCode, String poName,
			Integer processId, Integer qty, String operator,
			Timestamp createDate) {
		this.barCode = barCode;
		this.poName = poName;
		this.processId = processId;
		this.qty = qty;
		this.operator = operator;
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

	public Integer getProcessId() {
		return this.processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}