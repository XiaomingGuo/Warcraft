package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.*;
import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.page.utilities.*;

public class UserManagement extends PageParentClass implements IPageInterface
{
    private String[] m_displayArray = {"ID", "考勤工号", "考勤类型", "姓名", "创建时间", "部门", "密码", "用户权限", "操作"};
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
        DatabaseStore hDBHandle = new DatabaseStore("User_Info");
        hDBHandle.QueryAllRecord();
        return hDBHandle.getTableInstance().RecordDBCount();
    }
    
    public List<String> GetTitleName()
    {
        hQueryHandle.setDBHandle(new DatabaseStore("Title_Info"));
        return hQueryHandle.GetTableContentByKeyWord("", "AllRecord", "title_name");
    }
    
    public List<String> GetAllWorkGroupName()
    {
        hQueryHandle.setDBHandle(new DatabaseStore("Work_Group_Info"));
        return hQueryHandle.GetTableContentByKeyWord("", "AllRecord", "group_name");
    }
    
    public List<List<String>> GetUserInfo(int PageRecordCount, int BeginPage)
    {
        List<List<String>> rtnRst  = new ArrayList<List<String>>(); 
        DatabaseStore hDBHandle = new DatabaseStore("User_Info");
        
        hDBHandle.QueryRecordByFilterKeyListWithOrderAndLimit(null, null, Arrays.asList("id"), PageRecordCount*(BeginPage-1), PageRecordCount);
        if (hDBHandle.getTableInstance().RecordDBCount() > 0)
        {
            String[] sqlkeyList = {"check_in_id", "isFixWorkGroup", "name", "create_date", "department", "password", "permission", "id"};
            List<String> idList = new ArrayList<String>();
            for(int item=0; item < hDBHandle.getDBRecordList(sqlkeyList[0]).size(); item++)
                idList.add(Integer.toString(item+1));
            rtnRst.add(idList);
            for(int idx=0; idx < sqlkeyList.length; idx++)
            {
                if("isFixWorkGroup" == sqlkeyList[idx])
                    rtnRst.add(GetWorkGroupName(hDBHandle.getDBRecordList(sqlkeyList[idx])));
                else
                    rtnRst.add(hDBHandle.getDBRecordList(sqlkeyList[idx]));
            }
        }
        return rtnRst;
    }
    
    private List<String> GetWorkGroupName(List<String> dbRecordList)
    {
        List<String> rtnRst = new ArrayList<String>();
        
        for(int idx=0; idx < dbRecordList.size(); idx++)
        {
            hQueryHandle.setDBHandle(new DatabaseStore("Work_Group_Info"));
            List<String> queryList = hQueryHandle.GetTableContentByKeyWord("id", dbRecordList.get(idx), "group_name");
            if(queryList.size() > 0)
                rtnRst.add(queryList.get(0));
            else
                rtnRst.add("未定班次");
        }
        return rtnRst;
    }
    
    public boolean NewARecordInCustomerPoRecord(String barCode, String poName, String deliveryDate, String qty, String vendor, String percent)
    {
        if (CheckParamValidityEqualsLength(barCode, 8)&&CheckParamValidityMoreThanLength(poName, 6)&&CheckParamValidityEqualsLength(deliveryDate, 8)&&
                CheckParamValidityMoreThanValue(qty, 0)&&CheckParamValidityMoreThanLength(vendor, 1)&&CheckParamValidityMoreThanLength(percent, 0))
        {
        	DatabaseStore hDBHandle = new DatabaseStore("Customer_Po_Record");
        	hDBHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barCode, poName));
            if(hDBHandle.getTableInstance().RecordDBCount() <= 0)
            {
                ((Customer_Po_Record)hDBHandle.getTableInstance()).AddARecord(barCode, poName, deliveryDate, qty, vendor, percent);
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
        List<List<String>> recordList = GetUserInfo(20, BeginPage);
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    public String DoUserInfoManagement(String checkInId, String groupName, String name, String department, String password, String permission)
    {
    	DatabaseStore hDBHandle = new DatabaseStore("User_Info");
    	hDBHandle.QueryRecordByFilterKeyList(Arrays.asList("check_in_id"), Arrays.asList(checkInId));
        if(hDBHandle.getTableInstance().RecordDBCount() <= 0)
        {
            ((User_Info)hDBHandle.getTableInstance()).AddARecord(checkInId, GetWorkGroup(groupName), name, password, department, "0");
        }
        else
        {
            if(groupName.contains("请选择"))
            	hDBHandle.UpdateRecordByKeyList("isFixWorkGroup", "0", Arrays.asList("check_in_id"), Arrays.asList(checkInId));
            else
            	hDBHandle.UpdateRecordByKeyList("isFixWorkGroup", GetWorkGroup(groupName), Arrays.asList("check_in_id"), Arrays.asList(checkInId));
            hDBHandle.UpdateRecordByKeyList("name", name, Arrays.asList("check_in_id"), Arrays.asList(checkInId));
            hDBHandle.UpdateRecordByKeyList("department", department, Arrays.asList("check_in_id"), Arrays.asList(checkInId));
            hDBHandle.UpdateRecordByKeyList("password", password, Arrays.asList("check_in_id"), Arrays.asList(checkInId));
        }
        AddOrUpdatePermission(checkInId, permission);
        return "";
    }

    private String GetWorkGroup(String groupName)
    {
        String rtnRst = "0";
        hQueryHandle.setDBHandle(new DatabaseStore("Work_Group_Info"));
        List<String> queryList = hQueryHandle.GetTableContentByKeyWord("group_name", groupName, "id");
        if(queryList.size() > 0)
            rtnRst = queryList.get(0);
        return rtnRst;
    }
    
    private void AddOrUpdatePermission(String checkInId, String permission)
    {
        List<String> perList = Arrays.asList(permission.split("#"));
        hQueryHandle.setDBHandle(new DatabaseStore("User_Permission"));
        List<String> queryList = hQueryHandle.GetTableContentByKeyWord("check_in_id", checkInId, "title_name");
        
        for(int index = 0; index < perList.size(); index++)
        {
            String titleName = perList.get(index);
            if(queryList.contains(titleName))
                queryList.remove(titleName);
            else
                ((User_Permission)(hQueryHandle.getDBHandle().getTableInstance())).AddARecord(checkInId, titleName);
        }
        for(int delIdx = 0; delIdx < queryList.size(); delIdx++)
            hQueryHandle.getDBHandle().DeleteRecordByKeyList(Arrays.asList("check_in_id", "title_name"), Arrays.asList(checkInId, queryList.get(delIdx)));
    }
    
    public String DeleteUserInfoAndUserPermission(String userId)
    {
    	DatabaseStore hUIHandle = new DatabaseStore("User_Info");
    	DatabaseStore hUPHandle = new DatabaseStore("User_Permission");
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(userId));
        String checkInId = hUIHandle.getDBRecordList("check_in_id").get(0);
        hUPHandle.DeleteRecordByKeyList(Arrays.asList("check_in_id"), Arrays.asList(checkInId));
        hUIHandle.DeleteRecordByKeyList(Arrays.asList("id"), Arrays.asList(userId));
        return "";
    }
    
    public String GetUpdateReturnString(String userId)
    {
        String rtnRst = "remove$";
        
        String[] getKeyWord = new String[]{"isFixWorkGroup", "check_in_id", "name", "department", "password"};
        hQueryHandle.setDBHandle(new DatabaseStore("User_Info"));
        List<List<String>> queryUserInfoList = hQueryHandle.GetAllTableContentByKeyWord(getKeyWord, "id", userId);
        
        String workGroupId = queryUserInfoList.get(0).get(0);
        hQueryHandle.setDBHandle(new DatabaseStore("Work_Group_Info"));
        List<String> queryGroupNameList = hQueryHandle.GetTableContentByKeyWord("id", workGroupId, "group_name");
        
        String checkInId = queryUserInfoList.get(1).get(0);
        rtnRst += checkInId + "$";
        if(queryGroupNameList.size() > 0)
            rtnRst += queryGroupNameList.get(0) + "$";
        else
            rtnRst += "--请选择--$";
        rtnRst += queryUserInfoList.get(2).get(0) + "$";
        rtnRst += queryUserInfoList.get(3).get(0) + "$";
        rtnRst += queryUserInfoList.get(4).get(0) + "$";
        rtnRst += GetUserPermission(checkInId);
        return rtnRst;
    }
    
    private String GetUserPermission(String checkInId)
    {
        String rtnRst = "";
        hQueryHandle.setDBHandle(new DatabaseStore("User_Permission"));
        List<String> queryList = hQueryHandle.GetTableContentByKeyWord("check_in_id", checkInId, "title_name");
        
        rtnRst += queryList.get(0);
        for(int idx=1; idx < queryList.size(); idx++)
            rtnRst += "#" + queryList.get(idx);
        return rtnRst;
    }
}
