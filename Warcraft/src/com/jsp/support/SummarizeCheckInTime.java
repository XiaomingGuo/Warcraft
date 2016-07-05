package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DateAdapter;
import com.page.utilities.*;

public class SummarizeCheckInTime extends PageParentClass
{
    String[] m_displayArray = {"ID", "姓名", "工号", "漏打卡次数", "迟到早退总时间(分)", "查询时间范围"};
    private IRecordsQueryUtil hQueryHandle;
    
    public SummarizeCheckInTime() {hQueryHandle = new CRecordsQueryUtil();}
    
    public List<String> GetAllUserRecordByName(String queryKeyVal, String getKeyWord)
    {
        hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        return hQueryHandle.GetTableContentByKeyWord("name", queryKeyVal, getKeyWord);
    }
    
    public List<String> GetAllUserRecordByCheckInId(String queryKeyVal, String getKeyWord)
    {
    	hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        return hQueryHandle.GetTableContentByKeyWord("check_in_id", queryKeyVal, getKeyWord);
    }
    
    private List<String> GetAllCheckInDate(String queryDate)
    {
        List<String> rtnRst = new ArrayList<String>();
        int beginDate = Integer.parseInt(queryDate + "01");
        int maxDays = DateAdapter.getMaxDaysByYearMonth(queryDate);
        
        for(int dateOffset = 0; dateOffset < maxDays; dateOffset++ )
        {
            int curDate = beginDate + dateOffset;
            if(DateAdapter.getDayOfAWeek(Integer.toString(curDate)) > 1)
                rtnRst.add(Integer.toString(curDate));
        }
        return rtnRst;
    }
    
    private List<List<String>> GenDisplayResultList()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        for(int idx = 0; idx < m_displayArray.length; idx++)
            rtnRst.add(new ArrayList<String>());
        return rtnRst;
    }
    
    private List<List<String>> GetAllDisplayData(String user_name, String queryDate)
    {
        List<List<String>> rtnRst = GenDisplayResultList();
        if(user_name.length() > 0&&queryDate.length() != 6)
            return rtnRst;
        
        List<String> checkInIdList = GetAllUserRecordByName(user_name.indexOf("请选择") >= 0?"AllRecord":user_name, "check_in_id");
        checkInIdList.remove("99999");
        List<String> checkInDateList = GetAllCheckInDate(queryDate);
        
        for(int idx = 0; idx < checkInIdList.size(); idx++)
        {
            rtnRst.get(0).add(Integer.toString(idx + 1));
            List<String> checkInResult = GetAPersonCheckInSummary(checkInIdList.get(idx), checkInDateList);
            for(int item = 1; item < m_displayArray.length; item++)
                rtnRst.get(item).add(checkInResult.get(item-1));
        }
        return rtnRst;
    }
    
    private List<String> GetAPersonCheckInSummary(String checkInId, List<String> checkInDateList)
    {
        List<String> rtnRst = new ArrayList<String>();
        int absenceDay = 0, delayTime = 0;
        rtnRst.add(GetAllUserRecordByCheckInId(checkInId, "name").get(0));
        rtnRst.add(checkInId);
        //"ID", "姓名", "工号", "漏打卡次数", "迟到早退总时间(分)", "查询时间范围"
        for(int idx = 0; idx < checkInDateList.size(); idx++)
        {
            List<Long> tempValueList = GetAbsenceDayAndDelayTime(checkInId, checkInDateList.get(idx));
            absenceDay += tempValueList.get(0);
            delayTime += tempValueList.get(1);
        }
        rtnRst.add(Integer.toString(absenceDay));
        rtnRst.add(Integer.toString(delayTime));
        rtnRst.add(checkInDateList.get(0)+"~"+checkInDateList.get(checkInDateList.size()-1));
        return rtnRst;
    }
    
    private List<String> GetWorkGroupTime(String workGroup)
    {
        List<String> rtnRst = new ArrayList<String>();
        Work_Group_Info hWGIHandle = new Work_Group_Info(new EarthquakeManagement());
        hWGIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(workGroup));
        String checkInTime = hWGIHandle.getDBRecordList("check_in_time").get(0);
        String checkOutTime = hWGIHandle.getDBRecordList("check_out_time").get(0);
        String middleTime = DateAdapter.getMiddleTimeBetweenTimeSpan(checkInTime, checkOutTime);
        rtnRst.add(checkInTime);
        rtnRst.add(middleTime);
        rtnRst.add(checkOutTime);
        return rtnRst;
    }
    
    private List<Long> GetAbsenceDayAndDelayTime(String checkInId, String checkInDate)
    {
        List<Long> rtnRst = new ArrayList<Long>();
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        hCIRDHandle.QueryRecordByFilterKeyListOrderbyListASC(Arrays.asList("check_in_id", "check_in_date"), 
                Arrays.asList(checkInId, checkInDate), Arrays.asList("check_in_time"));
        if(hCIRDHandle.RecordDBCount() > 0&&Integer.parseInt(hCIRDHandle.getDBRecordList("work_group").get(0)) > 0)
        {
            List<String> workGroupTimeList = GetWorkGroupTime(hCIRDHandle.getDBRecordList("work_group").get(0));
            List<String> checkInTimeList = hCIRDHandle.getDBRecordList("check_in_time");
            String checkInTime, checkOutTime;
            if(DateAdapter.TimeSpan(workGroupTimeList.get(0), "16:29:00") < 0)
            {
                checkInTime = checkInTimeList.get(0);
                checkOutTime = checkInTimeList.get(checkInTimeList.size()-1);
            }
            else
            {
                checkInTime = checkInTimeList.get(checkInTimeList.size()-1);
                checkOutTime = checkInTimeList.get(0);
            }
            long timeSpan = 0;
            if(DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(1)) > 0||DateAdapter.TimeSpan(checkOutTime, workGroupTimeList.get(2)) < 0)
            {
                rtnRst.add(1L);
                if(DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(1)) > 0&&DateAdapter.TimeSpan(workGroupTimeList.get(2), checkOutTime) > 0)
                {
                    timeSpan = DateAdapter.TimeSpan(workGroupTimeList.get(2), checkOutTime);
                }
                else if(DateAdapter.TimeSpan(checkOutTime, workGroupTimeList.get(1)) < 0&&DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(0)) > 0)
                {
                    timeSpan = DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(0));
                }
                rtnRst.add(timeSpan);
            }
            else
            {
                rtnRst.add(0L);
                if(DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(0)) > 0)
                    timeSpan += DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(0));
                if(DateAdapter.TimeSpan(workGroupTimeList.get(2), checkOutTime) > 0)
                    timeSpan += DateAdapter.TimeSpan(workGroupTimeList.get(2), checkOutTime);
                rtnRst.add(timeSpan);
            }
        }
        else
        {
            rtnRst.add(2L);
            rtnRst.add(0L);
        }
        
        return rtnRst;
    }
    
    // Finish End
    
    private String PrepareHeader(List<List<String>> recordList)
    {
        String rtnRst = "remove$";
        if (recordList.size() > 0)
        {
            rtnRst += Integer.toString(m_displayArray.length) + "$";
            rtnRst += Integer.toString(recordList.get(0).size()) + "$";
            for(int i = 0; i < m_displayArray.length; i++)
            {
                rtnRst += m_displayArray[i] + "$";
            }
        }
        return rtnRst;
    }
    
    public String GenerateReturnString(String user_name, String queryDate)
    {
        List<List<String>> recordList = GetAllDisplayData(user_name, queryDate);
        if(null == recordList)
            return "";
        String rtnRst = PrepareHeader(recordList);
        
        if (recordList.size() > 0)
        {
            //"ID", "姓名", "工号", "漏打卡次数", "迟到早退总时间(分)", "查询时间范围"
            for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
            {
                for(int iCol = 0; iCol < m_displayArray.length; iCol++)
                {
                    rtnRst += recordList.get(iCol).get(iRow) + "$";
                }
            }
        }
        return rtnRst;
    }
}
