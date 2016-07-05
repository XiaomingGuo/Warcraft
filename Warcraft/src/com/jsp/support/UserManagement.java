package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;

public class UserManagement extends PageParentClass
{
    public int GetUserCount()
    {
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryAllRecord();
        return hUIHandle.RecordDBCount();
    }
    
    public List<List<String>> GetUserInfo(int PageRecordCount, int BeginPage)
    {
        List<List<String>> rtnRst  = new ArrayList<List<String>>(); 
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        
        hUIHandle.QueryRecordByFilterKeyListWithOrderAndLimit(null, null, Arrays.asList("id"), PageRecordCount*(BeginPage-1), PageRecordCount);
        if (hUIHandle.RecordDBCount() > 0)
        {
            String[] sqlkeyList = {"id", "check_in_id", "name", "create_date", "department", "password", "permission"};
            for(int idx=0; idx < sqlkeyList.length; idx++)
            {
            	rtnRst.add(hUIHandle.getDBRecordList(sqlkeyList[idx]));
            }
        }
        return rtnRst;
    }
    public boolean NewARecordInCustomerPoRecord(String barCode, String poName, String deliveryDate, String qty, String vendor, String percent)
    {
        if (CheckParamValidityEqualsLength(barCode, 8)&&CheckParamValidityMoreThanLength(poName, 6)&&CheckParamValidityEqualsLength(deliveryDate, 8)&&
                CheckParamValidityMoreThanValue(qty, 0)&&CheckParamValidityMoreThanLength(vendor, 1)&&CheckParamValidityMoreThanLength(percent, 0))
        {
            Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
            hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barCode, poName));
            if(hCPRHandle.RecordDBCount() <= 0)
            {
                hCPRHandle.AddARecord(barCode, poName, deliveryDate, qty, vendor, percent);
                return true;
            }
        }
        return false;
    }
}
