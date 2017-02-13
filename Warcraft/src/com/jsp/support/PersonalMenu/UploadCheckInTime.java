package com.jsp.support.PersonalMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.Interface.IPageAjaxUtil;
import com.Warcraft.Interface.IPageInterface;
import com.Warcraft.Interface.IRecordsQueryUtil;
import com.Warcraft.SupportUnit.DBTableParent;
import com.Warcraft.SupportUnit.DateAdapter;
import com.jsp.support.PageParentClass;
import com.page.utilities.CPageAjaxUtil;
import com.page.utilities.CRecordsQueryUtil;

public class UploadCheckInTime extends PageParentClass implements IPageInterface
{
    String[] m_displayList = {"ID", "姓名", "工号", "部门", "选择班次", "操作"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    
    public UploadCheckInTime()
    {
        hQueryHandle = new CRecordsQueryUtil();
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
    private List<String> GetAllWorkGroup()
    {
        DBTableParent hWGIHandle = new DatabaseStore("Work_Group_Info");
        hWGIHandle.QueryAllRecord();
        return hWGIHandle.getDBRecordList("group_name");
    }
    
    private List<String> GetAllUserName()
    {
    	DBTableParent hUIHandle = new DatabaseStore("User_Info");
    	hUIHandle.QueryAllRecord();
        return hUIHandle.getDBRecordList("name");
    }
    
    public String GetUserNameByKeyWord(String keyWord, String keyVal, String getKeyWord)
    {
        hQueryHandle.setDBHandle(new DatabaseStore("User_Info"));
        return hQueryHandle.GetTableContentByKeyWord(keyWord, keyVal, getKeyWord).get(0);
    }
    
    private List<String> GetDepartmentName()
    {
    	DBTableParent hUIHandle = new DatabaseStore("User_Info");
    	hUIHandle.QueryRecordGroupByList(Arrays.asList("department"));
        return hUIHandle.getDBRecordList("department");
    }
    
    private List<List<String>> GetAllDisplayInfo(String userName)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hUIHandle = new DatabaseStore("User_Info");
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(userName));
        String[] sqlKeyList = {"id", "name", "check_in_id", "department", "isFixWorkGroup"};
        if (hUIHandle.getTableInstance().RecordDBCount() > 0)
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
    
    private String GetDepartmentString()
    {
        List<String> tempList = GetDepartmentName();
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
                rtnRst += GetDepartmentString() + "$";
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
    
    public String SubmitUploadCheckInData(String strCheckInId, String strWorkGroup, String beginDate, String endDate)
    {
        String rtnRst = "";
        DBTableParent hWGIHandle = new DatabaseStore("Work_Group_Info");
        hWGIHandle.QueryRecordByFilterKeyList(Arrays.asList("group_name"), Arrays.asList(strWorkGroup));
        
        DBTableParent hCIRDHandle = new DatabaseStore("Check_In_Raw_Data");
        if(hWGIHandle.getTableInstance().RecordDBCount() > 0)
        {
            String workGroupId = hWGIHandle.getDBRecordList("id").get(0);
            //hCIRDHandle.QueryRecordByFilterKeyListAndBetweenAndIncludeDateSpan(Arrays.asList("check_in_id"), Arrays.asList(strCheckInId),
            //        "check_in_date", beginDate, endDate);
            hCIRDHandle.QueryRecordByFilterKeyListAndBetweenAndIncludeDateSpan(Arrays.asList("check_in_id", "isEnsure"), Arrays.asList(strCheckInId, "0"),
                                                                        "check_in_date", beginDate, endDate);
            List<String> updateIdList = hCIRDHandle.getDBRecordList("id");
            if(updateIdList.size() > 0)
            {
                for(int idx=0; idx < updateIdList.size(); idx++)
                {
                    hCIRDHandle.UpdateRecordByKeyList("work_group", workGroupId, Arrays.asList("id"), Arrays.asList(updateIdList.get(idx)));
                    //hCIRDHandle.UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("id"), Arrays.asList(updateIdList.get(idx)));
                }
            }
        }
        else
            rtnRst += "error:班次不存在或用户不存在!";
        return rtnRst;
    }
    
    public String EnsureAllArrangeCheckInData(String userName, String submitDate)
    {
        String rtnRst = "";
        String beginDate = submitDate+"01", endDate = submitDate+Integer.toString(DateAdapter.getDayCountOfAMonth(submitDate));
        String strCheckInId = GetUserNameByKeyWord("name", userName, "check_in_id");
        
        DBTableParent hCIRDHandle = new DatabaseStore("Check_In_Raw_Data");
        hCIRDHandle.QueryRecordByFilterKeyListAndBetweenAndIncludeDateSpan(Arrays.asList("check_in_id", "isEnsure"), Arrays.asList(strCheckInId, "0"),
                                                                    "check_in_date", beginDate, endDate);
        List<String> updateIdList = hCIRDHandle.getDBRecordList("id");
        if(updateIdList.size() > 0)
        {
            for(int idx=0; idx < updateIdList.size(); idx++)
                hCIRDHandle.UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("id"), Arrays.asList(updateIdList.get(idx)));
        }
        return rtnRst;
    }
    
    public String SubmitAddCheckInData(String strCheckInId, String strWorkGroup, String addDate)
    {
        String rtnRst = "";
        DBTableParent hWGIHandle = new DatabaseStore("Work_Group_Info");
        hWGIHandle.QueryRecordByFilterKeyList(Arrays.asList("group_name"), Arrays.asList(strWorkGroup));
        
        DBTableParent hCIRDHandle = new DatabaseStore("Check_In_Raw_Data");
        if(hWGIHandle.getTableInstance().RecordDBCount() > 0)
        {
            String workGroupId = hWGIHandle.getDBRecordList("id").get(0);
            String checkInDate = addDate.split("#")[0];
            String checkInTime = addDate.split("#")[1];
            
            hCIRDHandle.QueryRecordByFilterKeyList(Arrays.asList("check_in_id", "check_in_date", "check_in_time"), Arrays.asList(strCheckInId, checkInDate, checkInTime));
            if(hCIRDHandle.getTableInstance().RecordDBCount() <= 0)
            {
                ((Check_In_Raw_Data)hCIRDHandle.getTableInstance()).AddARecord(strCheckInId, checkInDate, checkInTime, workGroupId);
                hCIRDHandle.UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("check_in_id", "check_in_date", "check_in_time", "work_group"), Arrays.asList(strCheckInId, checkInDate, checkInTime, workGroupId));
            }
        }
        else
            rtnRst += "error:班次不存在或用户不存在!";
        return rtnRst;
    }
    
    public String SubmitAddHolidaysDate(String strCheckInId, String addDate, String holidayType, String holidayTime)
    {
        String rtnRst = "";
        DBTableParent hHMHandle = new DatabaseStore("Holiday_Mark");
        hHMHandle.QueryRecordByFilterKeyList(Arrays.asList("check_in_id", "holiday_date"), Arrays.asList(strCheckInId, addDate));
        if(hHMHandle.getTableInstance().RecordDBCount() <= 0)
            ((Holiday_Mark)hHMHandle.getTableInstance()).AddARecord(strCheckInId, addDate, holidayType, holidayTime);
        else
            rtnRst += "remove$error:节假日或转班信息已经存在!";
        return rtnRst;
    }
    
    public String SubmitAddBatchHolidaysDate(String strCheckInId, String holidayType, String beginDate, String endDate)
    {
        String rtnRst = "";
        if(beginDate.substring(0, 6).equals(endDate.substring(0, 6)))
        {
            for(int idate = Integer.parseInt(beginDate); idate <= Integer.parseInt(endDate); idate++)
                SubmitAddHolidaysDate(strCheckInId, Integer.toString(idate), holidayType, "8");
        }
        else
            rtnRst = "error:假期不能跨月提交!";
        return rtnRst;
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayList;
    }
}
