package com.DB.support;

import java.sql.Timestamp;

/**
 * OtherRecord entity. @author MyEclipse Persistence Tools
 */

public class OtherRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private String batchLot;
	private String proposer;
	private Integer qty;
	private String userName;
	private Timestamp createDate;
	private Integer isApprove;
	private Integer mergeMark;

	// Constructors

	/** default constructor */
	public OtherRecord() {
	}

	/** minimal constructor */
	public OtherRecord(String barCode, String proposer, Integer qty,
			String userName, Timestamp createDate, Integer isApprove,
			Integer mergeMark) {
		this.barCode = barCode;
		this.proposer = proposer;
		this.qty = qty;
		this.userName = userName;
		this.createDate = createDate;
		this.isApprove = isApprove;
		this.mergeMark = mergeMark;
	}

	/** full constructor */
	public OtherRecord(String barCode, String batchLot, String proposer,
			Integer qty, String userName, Timestamp createDate,
			Integer isApprove, Integer mergeMark) {
		this.barCode = barCode;
		this.batchLot = batchLot;
		this.proposer = proposer;
		this.qty = qty;
		this.userName = userName;
		this.createDate = createDate;
		this.isApprove = isApprove;
		this.mergeMark = mergeMark;
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

	public String getBatchLot() {
		return this.batchLot;
	}

	public void setBatchLot(String batchLot) {
		this.batchLot = batchLot;
	}

	public String getProposer() {
		return this.proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Integer getIsApprove() {
		return this.isApprove;
	}

	public void setIsApprove(Integer isApprove) {
		this.isApprove = isApprove;
	}

	public Integer getMergeMark() {
		return this.mergeMark;
	}

	public void setMergeMark(Integer mergeMark) {
		this.mergeMark = mergeMark;
	}

}