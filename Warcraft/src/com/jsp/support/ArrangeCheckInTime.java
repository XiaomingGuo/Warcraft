package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;

public class ArrangeCheckInTime extends PageParentClass
{
    String[] m_displayList = {"ID", "姓名", "工号", "部门", "选择班次", "操作"};
    
    private List<String> GetAllWorkGroup()
    {
        Work_Group_Info hWGIHandle = new Work_Group_Info(new EarthquakeManagement());
        hWGIHandle.QueryAllRecord();
        return hWGIHandle.getDBRecordList("group_name");
    }
    
    private List<String> GetAllUserName()
    {
        User_Info hWGIHandle = new User_Info(new EarthquakeManagement());
        hWGIHandle.QueryAllRecord();
        return hWGIHandle.getDBRecordList("name");
    }
    
    private List<List<String>> GetAllDisplayInfo(String userName)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(userName));
        String[] sqlKeyList = {"id", "name", "check_in_id", "department", "isFixWorkGroup"};
        if (hUIHandle.RecordDBCount() > 0)
        {
            for(int idx=0; idx < sqlKeyList.length; idx++)
                rtnRst.add(hUIHandle.getDBRecordList(sqlKeyList[idx]));
        }
        return rtnRst;
    }
    
    private String GetWorkGroupString()
    {
        List<String> tempList = GetAllWorkGroup();
        String rtnRst = tempList.get(0);
        for(int idx = 1; idx < tempList.size(); idx++)
            rtnRst += "#" + tempList.get(idx);
        return rtnRst;
    }
    
    private String GetUserNameString()
    {
        List<String> tempList = GetAllUserName();
        tempList.remove("root");
        String rtnRst = tempList.get(0);
        for(int idx = 1; idx < tempList.size(); idx++)
            rtnRst += "#" + tempList.get(idx);
        return rtnRst;
    }
    
    public String GetUserNameString(String userName)
    {
        String rtnRst = PrepareHeader();
        List<List<String>> recordList = GetAllDisplayInfo(userName);
        for(int iCol = 0; iCol < m_displayList.length; iCol++)
        {
            if("ID" == m_displayList[iCol])
            {
                rtnRst += Integer.toString(iCol+1) + "$";
            }
            else if("姓名" == m_displayList[iCol])
            {
                rtnRst += recordList.get(iCol).get(0) + "$";
            }
            else if("工号" == m_displayList[iCol])
            {
                rtnRst += recordList.get(iCol).get(0) + "$";
            }
            else if("部门" == m_displayList[iCol])
            {
                rtnRst += recordList.get(iCol).get(0) + "$";
            }
            else if("选择班次" == m_displayList[iCol])
            {
                rtnRst += recordList.get(iCol).get(0) + "$";
            }
            else
            {
                rtnRst += "...$";
            }
        }
        return rtnRst;
    }
    
    public String GenerateReturnString()
    {
        String rtnRst = PrepareHeader();
        //"id", "name", "check_in_id", "department"
        //"ID", "姓名", "工号", "部门", "选择班次", "操作"
        for(int iCol = 0; iCol < m_displayList.length; iCol++)
        {
            if("ID" == m_displayList[iCol])
            {
                rtnRst += "...$";
            }
            else if("姓名" == m_displayList[iCol])
            {
                rtnRst += GetUserNameString() + "$";
            }
            else if("工号" == m_displayList[iCol])
            {
                rtnRst += "...$";
            }
            else if("部门" == m_displayList[iCol])
            {
                rtnRst += "...$";
            }
            else if("选择班次" == m_displayList[iCol])
            {
                rtnRst += GetWorkGroupString() + "$";
            }
            else if("操作" == m_displayList[iCol])
            {
                rtnRst += "...$";
            }
        }
        return rtnRst;
    }
    
    private String PrepareHeader()
    {
        String rtnRst = "remove$";
        rtnRst += Integer.toString(m_displayList.length) + "$";
        rtnRst += "1$";
        for(int i = 0; i < m_displayList.length; i++)
        {
            rtnRst += m_displayList[i] + "$";
        }
        return rtnRst;
    }
    
    public String SubmitArrangeCheckInData(String strCheckInId, String strWorkGroup, String beginDate, String endDate)
    {
        String rtnRst = "";
        Work_Group_Info hWGIHandle = new Work_Group_Info(new EarthquakeManagement());
        hWGIHandle.QueryRecordByFilterKeyList(Arrays.asList("group_name"), Arrays.asList(strWorkGroup));
        
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        if(hWGIHandle.RecordDBCount() > 0)
        {
            String workGroupId = hWGIHandle.getDBRecordList("id").get(0);
            hCIRDHandle.QueryRecordByFilterKeyListAndBetweenDateSpan(Arrays.asList("check_in_id", "isEnsure"), Arrays.asList(strCheckInId, "0"),
                                                                        "check_in_date", beginDate, endDate);
            List<String> updateIdList = hCIRDHandle.getDBRecordList("id");
            if(updateIdList.size() > 0)
            {
                for(int idx=0; idx < updateIdList.size(); idx++)
                {
                    hCIRDHandle.UpdateRecordByKeyList("work_group", workGroupId, Arrays.asList("id"), Arrays.asList(updateIdList.get(idx)));
                    hCIRDHandle.UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("id"), Arrays.asList(updateIdList.get(idx)));
                }
            }
        }
        else
            rtnRst += "error:班次不存在或用户不存在!";
        return rtnRst;
    }
}
