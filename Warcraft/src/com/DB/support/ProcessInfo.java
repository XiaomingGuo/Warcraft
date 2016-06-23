package com.DB.support;

/**
 * ProcessInfo entity. @author MyEclipse Persistence Tools
 */

public class ProcessInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String processName;
	private String stationName;
	private Integer processOrder;
	private Integer processCount;

	// Constructors

	/** default constructor */
	public ProcessInfo() {
	}

	/** full constructor */
	public ProcessInfo(String processName, String stationName,
			Integer processOrder, Integer processCount) {
		this.processName = processName;
		this.stationName = stationName;
		this.processOrder = processOrder;
		this.processCount = processCount;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Integer getProcessOrder() {
		return this.processOrder;
	}

	public void setProcessOrder(Integer processOrder) {
		this.processOrder = processOrder;
	}

	public Integer getProcessCount() {
		return this.processCount;
	}

	public void setProcessCount(Integer processCount) {
		this.processCount = processCount;
	}

}