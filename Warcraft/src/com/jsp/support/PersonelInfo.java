package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DateAdapter;
import com.page.utilities.CPageAjaxUtil;
import com.page.utilities.CRecordsQueryUtil;

public class PersonelInfo extends PageParentClass implements IPageInterface
{
    private String[] m_displayList = {"ID", "姓名", "工号", "打卡日期", "打卡时间", "班次", "操作"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    
    public PersonelInfo()
    {
        hQueryHandle = new CRecordsQueryUtil();
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
    public List<String> GetAllUserName()
    {
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryAllRecord();
        return hUIHandle.getDBRecordList("name");
    }
    
    private String GetCheckInIdFromUserInfo(String user_name)
    {
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(user_name));
        return hUIHandle.getDBRecordList("check_in_id").get(0);
    }
    
    private List<List<String>> GetAllCheckInRawData(String user_name, String queryDate)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        String beginDate = queryDate + "00";
        String endDate = queryDate + "32";
        hCIRDHandle.QueryRecordByFilterKeyListAndBetweenDateSpan(Arrays.asList("check_in_id"), Arrays.asList(GetCheckInIdFromUserInfo(user_name)), "check_in_date", beginDate, endDate);
        if (hCIRDHandle.RecordDBCount() > 0)
        {
            String[] sqlKeyList = {"id", "check_in_id", "check_in_date", "check_in_time", "work_group"};
            for(int idx=0; idx < sqlKeyList.length; idx++)
            {
                rtnRst.add(hCIRDHandle.getDBRecordList(sqlKeyList[idx]));
            }
        }
        return rtnRst;
    }
    
    private String PrepareHeader(List<List<String>> recordList)
    {
        String rtnRst = "remove$";
        if (recordList.size() > 0)
        {
            rtnRst += Integer.toString(m_displayList.length) + "$";
            rtnRst += Integer.toString(recordList.get(0).size()) + "$";
            for(int i = 0; i < m_displayList.length; i++)
            {
                rtnRst += m_displayList[i] + "$";
            }
        }
        return rtnRst;
    }
    
    public String GenerateReturnString(String user_name, String queryDate)
    {
        List<List<String>> recordList = GetAllCheckInRawData(user_name, queryDate);
        String rtnRst = PrepareHeader(recordList);
        
        if (recordList.size() > 0)
        {
            //"id", "check_in_id", "check_in_time", "work_group"
            //"ID", "姓名", "工号", "打卡时间", "班次"
            for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
            {
                for(int iCol = 0; iCol < m_displayList.length; iCol++)
                {
                    if("ID" == m_displayList[iCol])
                    {
                        rtnRst += Integer.toString(iRow+1) + "$";
                    }
                    else if("姓名" == m_displayList[iCol])
                    {
                        rtnRst += user_name + "$";
                    }
                    else if("工号" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(1).get(iRow) + "$";
                    }
                    else if("打卡日期" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(2).get(iRow) + "$";
                    }
                    else if("打卡时间" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(3).get(iRow) + "$";
                    }
                    else if("班次" == m_displayList[iCol])
                    {
                        if(recordList.get(4).get(iRow).indexOf("0") == 0)
                            rtnRst += "未排班$";
                        else
                            rtnRst += GetWorkGroupName(recordList.get(4).get(iRow)) + "$";
                    }
                    else if("操作" == m_displayList[iCol])
                        rtnRst += recordList.get(0).get(iRow) + "$";
                }
            }
            rtnRst += CheckPrecedingMonthData(recordList.get(1).get(0), queryDate) + "$";
        }
        return rtnRst;
    }
    
    private String CheckPrecedingMonthData(String checkInId, String queryDate)
    {
        String preMonth = DateAdapter.getPrecedingMonth(queryDate);
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        hCIRDHandle.QueryRecordByFilterKeyListAndBetweenDateSpan(Arrays.asList("check_in_id", "isEnsure"), Arrays.asList(checkInId, "0"), "check_in_date", preMonth + "00", preMonth + "32");
        if(hCIRDHandle.RecordDBCount() > 0)
            return "1";
        return "0";
    }
    
    private String GetWorkGroupName(String id)
    {
        Work_Group_Info hUIHandle = new Work_Group_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(id));
        return hUIHandle.getDBRecordList("group_name").get(0);
    }
    
    public List<String> GetWorkGroupName()
    {
        hQueryHandle.setTableHandle(new Work_Group_Info(new EarthquakeManagement()));
        return hQueryHandle.GetTableContentByKeyWord("", "AllRecord", "group_name");
    }
    
    public String UpdateCheckInRawDataRecord(String id, String workGroup)
    {
        hQueryHandle.setTableHandle(new Work_Group_Info(new EarthquakeManagement()));
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        String workGroupId = hQueryHandle.GetTableContentByKeyWord("group_name", workGroup, "id").get(0);
        hCIRDHandle.UpdateRecordByKeyList("work_group", workGroupId, Arrays.asList("id"), Arrays.asList(id));
        return "";
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayList;
    }
}
