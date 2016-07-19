package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.page.utilities.*;

public class UserManagement extends PageParentClass implements IPageInterface
{
    public String[] m_displayArray = {"ID", "考勤工号", "考勤类型", "姓名", "创建时间", "部门", "密码", "用户权限", "操作"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    
    public UserManagement()
    {
        hQueryHandle = new CRecordsQueryUtil();
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
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
            String[] sqlkeyList = {"check_in_id", "isFixWorkGroup", "name", "create_date", "department", "password", "permission", "id"};
            List<String> idList = new ArrayList<String>();
            for(int item=0; item < hUIHandle.getDBRecordList(sqlkeyList[0]).size(); item++)
                idList.add(Integer.toString(item+1));
            rtnRst.add(idList);
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
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayArray;
    }
    
    public String GenerateReturnString(int BeginPage)
    {
        String[] codeKeyList = {"ID", "check_in_id", "isFixWorkGroup", "name", "create_date", "department", "password", "permission", "submit"};
        
        List<List<String>> recordList = GetUserInfo(20, BeginPage);
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
}
