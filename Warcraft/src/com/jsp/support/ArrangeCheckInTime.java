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
    
    private List<List<String>> GetAllDisplayInfo()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryAllRecord();
        String[] sqlKeyList = {"id", "name", "check_in_id", "department"};
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
    
    public String GenerateReturnString()
    {
        List<List<String>> recordList = GetAllDisplayInfo();
        String rtnRst = PrepareHeader(recordList);
        
        if (recordList.size() > 0)
        {
            //"id", "name", "check_in_id", "department"
            //"ID", "姓名", "工号", "部门", "选择班次", "操作"
            for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
            {
                for(int iCol = 0; iCol < m_displayList.length; iCol++)
                {
                    if(recordList.get(1).get(iRow).equals("root"))
                        break;
                    if("ID" == m_displayList[iCol])
                    {
                        rtnRst += Integer.toString(iRow) + "$";
                    }
                    else if("姓名" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(1).get(iRow) + "$";
                    }
                    else if("工号" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(2).get(iRow) + "$";
                    }
                    else if("部门" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(3).get(iRow) + "$";
                    }
                    else if("选择班次" == m_displayList[iCol])
                    {
                        rtnRst += GetWorkGroupString() + "$";
                    }
                    else if("操作" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(0).get(iRow) + "$";
                    }
                }
            }
        }
        return rtnRst;
    }
    
    private String PrepareHeader(List<List<String>> recordList)
    {
        String rtnRst = "remove$";
        List<String> tempList = GetAllWorkGroup();
        if (recordList.size() > 0)
        {
            rtnRst += Integer.toString(m_displayList.length) + "$";
            rtnRst += Integer.toString(recordList.get(0).size()-1) + "$";
            rtnRst += Integer.toString(tempList.size()) + "$";
            for(int i = 0; i < m_displayList.length; i++)
            {
                rtnRst += m_displayList[i] + "$";
            }
        }
        return rtnRst;
    }
    
    public String SubmitArrangeCheckInData(String strUserId, String strWorkGroup)
    {
        String rtnRst = "";
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(strUserId));
        
        Work_Group_Info hWGIHandle = new Work_Group_Info(new EarthquakeManagement());
        hWGIHandle.QueryRecordByFilterKeyList(Arrays.asList("group_name"), Arrays.asList(strWorkGroup));
        
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        if(hUIHandle.RecordDBCount() > 0&&hWGIHandle.RecordDBCount() > 0)
        {
            String workNum = hUIHandle.getDBRecordList("check_in_id").get(0);
            String workGroupId = hUIHandle.getDBRecordList("id").get(0);
            hCIRDHandle.QueryRecordByFilterKeyList(Arrays.asList("check_in_id", "isEnsure"), Arrays.asList(workNum, "0"));
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
