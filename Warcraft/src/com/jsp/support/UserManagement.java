package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryAllRecord();
        return hUIHandle.RecordDBCount();
    }
    
    public List<String> GetTitleName()
    {
        hQueryHandle.setTableHandle(new Title_Info(new EarthquakeManagement()));
        return hQueryHandle.GetTableContentByKeyWord("", "AllRecord", "title_name");
    }
    
    public List<String> GetAllWorkGroupName()
    {
        hQueryHandle.setTableHandle(new Work_Group_Info(new EarthquakeManagement()));
        return hQueryHandle.GetTableContentByKeyWord("", "AllRecord", "group_name");
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
                if("isFixWorkGroup" == sqlkeyList[idx])
                    rtnRst.add(GetWorkGroupName(hUIHandle.getDBRecordList(sqlkeyList[idx])));
                else
                    rtnRst.add(hUIHandle.getDBRecordList(sqlkeyList[idx]));
            }
        }
        return rtnRst;
    }
    
    private List<String> GetWorkGroupName(List<String> dbRecordList)
    {
        List<String> rtnRst = new ArrayList<String>();
        
        for(int idx=0; idx < dbRecordList.size(); idx++)
        {
            hQueryHandle.setTableHandle(new Work_Group_Info(new EarthquakeManagement()));
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
        List<List<String>> recordList = GetUserInfo(20, BeginPage);
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    public String DoUserInfoManagement(String checkInId, String groupName, String name, String department, String password, String permission)
    {
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("check_in_id"), Arrays.asList(checkInId));
        if(hUIHandle.RecordDBCount() <= 0)
        {
            hUIHandle.AddARecord(checkInId, GetWorkGroup(groupName), name, password, department, "0");
        }
        else
        {
            if(groupName.contains("请选择"))
                hUIHandle.UpdateRecordByKeyList("isFixWorkGroup", "0", Arrays.asList("check_in_id"), Arrays.asList(checkInId));
            else
                hUIHandle.UpdateRecordByKeyList("isFixWorkGroup", GetWorkGroup(groupName), Arrays.asList("check_in_id"), Arrays.asList(checkInId));
            hUIHandle.UpdateRecordByKeyList("name", name, Arrays.asList("check_in_id"), Arrays.asList(checkInId));
            hUIHandle.UpdateRecordByKeyList("department", department, Arrays.asList("check_in_id"), Arrays.asList(checkInId));
            hUIHandle.UpdateRecordByKeyList("password", password, Arrays.asList("check_in_id"), Arrays.asList(checkInId));
        }
        AddOrUpdatePermission(checkInId, permission);
        return "";
    }

    private String GetWorkGroup(String groupName)
    {
        String rtnRst = "0";
        hQueryHandle.setTableHandle(new Work_Group_Info(new EarthquakeManagement()));
        List<String> queryList = hQueryHandle.GetTableContentByKeyWord("group_name", groupName, "id");
        if(queryList.size() > 0)
            rtnRst = queryList.get(0);
        return rtnRst;
    }
    
    private void AddOrUpdatePermission(String checkInId, String permission)
    {
        List<String> perList = Arrays.asList(permission.split("#"));
        hQueryHandle.setTableHandle(new User_Permission(new EarthquakeManagement()));
        List<String> queryList = hQueryHandle.GetTableContentByKeyWord("check_in_id", checkInId, "title_name");
        
        for(int index = 0; index < perList.size(); index++)
        {
            String titleName = perList.get(index);
            if(queryList.contains(titleName))
                queryList.remove(titleName);
            else
                ((User_Permission)(hQueryHandle.getTableHandle())).AddARecord(checkInId, titleName);
        }
        for(int delIdx = 0; delIdx < queryList.size(); delIdx++)
            ((User_Permission)(hQueryHandle.getTableHandle())).DeleteRecordByKeyList(Arrays.asList("check_in_id", "title_name"), Arrays.asList(checkInId, queryList.get(delIdx)));
    }
    
    public String DeleteUserInfoAndUserPermission(String userId)
    {
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        User_Permission hUPHandle = new User_Permission(new EarthquakeManagement());
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
        hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        List<List<String>> queryUserInfoList = hQueryHandle.GetAllTableContentByKeyWord(getKeyWord, "id", userId);
        
        String workGroupId = queryUserInfoList.get(0).get(0);
        hQueryHandle.setTableHandle(new Work_Group_Info(new EarthquakeManagement()));
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
        hQueryHandle.setTableHandle(new User_Permission(new EarthquakeManagement()));
        List<String> queryList = hQueryHandle.GetTableContentByKeyWord("check_in_id", checkInId, "title_name");
        
        rtnRst += queryList.get(0);
        for(int idx=1; idx < queryList.size(); idx++)
            rtnRst += "#" + queryList.get(idx);
        return rtnRst;
    }
}
