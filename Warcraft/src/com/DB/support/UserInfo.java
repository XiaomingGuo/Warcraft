package com.DB.support;

import java.sql.Timestamp;


/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */

public class UserInfo  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String name;
     private String password;
     private String checkInId;
     private Integer isFixWorkGroup;
     private Timestamp createDate;
     private String department;
     private Integer permission;
     private Integer isAbsense;
     private String picture;


    // Constructors

    /** default constructor */
    public UserInfo() {
    }

	/** minimal constructor */
    public UserInfo(String name, String password, String checkInId, Timestamp createDate, String department, Integer permission, Integer isAbsense) {
        this.name = name;
        this.password = password;
        this.checkInId = checkInId;
        this.createDate = createDate;
        this.department = department;
        this.permission = permission;
        this.isAbsense = isAbsense;
    }
    
    /** full constructor */
    public UserInfo(String name, String password, String checkInId, Integer isFixWorkGroup, Timestamp createDate, String department, Integer permission, Integer isAbsense, String picture) {
        this.name = name;
        this.password = password;
        this.checkInId = checkInId;
        this.isFixWorkGroup = isFixWorkGroup;
        this.createDate = createDate;
        this.department = department;
        this.permission = permission;
        this.isAbsense = isAbsense;
        this.picture = picture;
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

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckInId() {
        return this.checkInId;
    }
    
    public void setCheckInId(String checkInId) {
        this.checkInId = checkInId;
    }

    public Integer getIsFixWorkGroup() {
        return this.isFixWorkGroup;
    }
    
    public void setIsFixWorkGroup(Integer isFixWorkGroup) {
        this.isFixWorkGroup = isFixWorkGroup;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getPermission() {
        return this.permission;
    }
    
    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public Integer getIsAbsense() {
        return this.isAbsense;
    }
    
    public void setIsAbsense(Integer isAbsense) {
        this.isAbsense = isAbsense;
    }

    public String getPicture() {
        return this.picture;
    }
    
    public void setPicture(String picture) {
        this.picture = picture;
    }
}