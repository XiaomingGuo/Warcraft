package com.DB.support;



/**
 * CheckInRawData entity. @author MyEclipse Persistence Tools
 */

public class CheckInRawData  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String checkInId;
     private String checkInTime;
     private Integer workGroup;
     private Integer isEnsure;


    // Constructors

    /** default constructor */
    public CheckInRawData() {
    }

    
    /** full constructor */
    public CheckInRawData(String checkInId, String checkInTime, Integer workGroup, Integer isEnsure) {
        this.checkInId = checkInId;
        this.checkInTime = checkInTime;
        this.workGroup = workGroup;
        this.isEnsure = isEnsure;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckInId() {
        return this.checkInId;
    }
    
    public void setCheckInId(String checkInId) {
        this.checkInId = checkInId;
    }

    public String getCheckInTime() {
        return this.checkInTime;
    }
    
    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Integer getWorkGroup() {
        return this.workGroup;
    }
    
    public void setWorkGroup(Integer workGroup) {
        this.workGroup = workGroup;
    }

    public Integer getIsEnsure() {
        return this.isEnsure;
    }
    
    public void setIsEnsure(Integer isEnsure) {
        this.isEnsure = isEnsure;
    }
   








}