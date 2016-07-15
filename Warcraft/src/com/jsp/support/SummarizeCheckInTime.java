package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DateAdapter;
import com.page.utilities.*;

public class SummarizeCheckInTime extends PageParentClass implements IPageInterface
{
    public String[] m_displayArray = {"ID", "姓名", "工号", "漏打卡次数", "迟到早退总时间(分)", "查询时间范围"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    
    public SummarizeCheckInTime()
    {
        hQueryHandle = new CRecordsQueryUtil();
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayArray;
    }
    
    public void setQueryHandle(IRecordsQueryUtil hHandle)
    {
        this.hQueryHandle = hHandle;
    }
    
    public IRecordsQueryUtil getQueryHandle()
    {
        return this.hQueryHandle;
    }
    
    public List<String> GetAllUserRecordByName(String queryKeyVal, String getKeyWord)
    {
        hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        List<String> rtnRst = hQueryHandle.GetTableContentByKeyWord("name", queryKeyVal, getKeyWord);
        rtnRst.remove("99999");
        return rtnRst;
    }
    
    public List<String> GetAllUserRecordByCheckInId(String queryKeyVal, String getKeyWord)
    {
        hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        return hQueryHandle.GetTableContentByKeyWord("check_in_id", queryKeyVal, getKeyWord);
    }
    
    public int GetWorkDayOfAWeekByWorkGroupId(String queryKeyVal)
    {
        hQueryHandle.setTableHandle(new Work_Group_Info(new EarthquakeManagement()));
        return Integer.parseInt(hQueryHandle.GetTableContentByKeyWord("id", queryKeyVal, "work_days_aweek").get(0));
    }
    
    private List<String> GetAllCheckInDate(String queryDate, String checkInId)
    {
        List<String> rtnRst = new ArrayList<String>();
        int beginDate = Integer.parseInt(queryDate + "01");
        int maxDays = DateAdapter.getMaxDaysByYearMonth(queryDate);
        Holiday_Mark hHMHandle = new Holiday_Mark(new EarthquakeManagement());
        
        for(int dateOffset = 0; dateOffset < maxDays; dateOffset++ )
        {
            int curDate = beginDate + dateOffset;
            rtnRst.add(Integer.toString(curDate));
        }
        hHMHandle.QueryRecordByFilterKeyListAndBetweenAndIncludeDateSpan(Arrays.asList("check_in_id"), Arrays.asList(checkInId),
                                                                        "holiday_date", Integer.toString(beginDate), queryDate+Integer.toString(maxDays));
        List<String> tempList = hHMHandle.getDBRecordList("holiday_date");
        for(int idx=0; idx < tempList.size(); idx++)
        {
            if(rtnRst.contains(tempList.get(idx)))
                rtnRst.remove(tempList.get(idx));
        }
        return rtnRst;
    }
    
    private List<List<String>> GetAllDisplayData(String user_name, String queryDate)
    {
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        if(user_name.length() > 0&&queryDate.length() != 6)
            return rtnRst;
        
        List<String> checkInIdList = GetAllUserRecordByName(user_name.indexOf("请选择") >= 0?"AllRecord":user_name, "check_in_id");
        
        for(int idx = 0; idx < checkInIdList.size(); idx++)
        {
            List<String> checkInDateList = GetAllCheckInDate(queryDate, checkInIdList.get(idx));
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
    
    private List<String> GetWorkGroupTime(int workGroupId)
    {
        List<String> rtnRst = new ArrayList<String>();
        Work_Group_Info hWGIHandle = new Work_Group_Info(new EarthquakeManagement());
        hWGIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(Integer.toString(workGroupId)));
        rtnRst.add(hWGIHandle.getDBRecordList("check_in_time").get(0));
        rtnRst.add(hWGIHandle.getDBRecordList("check_out_time").get(0));
        return rtnRst;
    }
    
    private List<List<String>> GetOneDayCheckRawData(String checkInId, String checkInDate)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        hCIRDHandle.QueryRecordByFilterKeyListOrderbyListASC(Arrays.asList("check_in_id", "check_in_date"), 
                Arrays.asList(checkInId, checkInDate), Arrays.asList("check_in_time"));
        String[] getKeyWord = {"check_in_date", "check_in_time", "work_group"};
        for(int idx=0; idx < getKeyWord.length; idx++)
            rtnRst.add(hCIRDHandle.getDBRecordList(getKeyWord[idx]));
        return rtnRst;
    }
    
    private String GetTomorrowCheckOutDate(String checkInId, int checkInDate)
    {
        String usedDay = Integer.toString(checkInDate).substring(0, 6) + "00";
        int maxDay = DateAdapter.getMaxDaysByYearMonth(Integer.toString(checkInDate).substring(0, 6));
        if(checkInDate == Integer.parseInt(usedDay) + maxDay)
            usedDay = Integer.toString(Integer.parseInt(usedDay)+101);
        else
            usedDay = Integer.toString(checkInDate+1);
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        hCIRDHandle.QueryRecordByFilterKeyListAndBetweenDateSpanOrderByListASC(Arrays.asList("check_in_id", "check_in_date"), 
                Arrays.asList(checkInId, usedDay), "check_in_time", "00:00:00", "05:30:00", Arrays.asList("check_in_time"));
        if(hCIRDHandle.RecordDBCount() > 0)
            return hCIRDHandle.getDBRecordList("check_in_time").get(hCIRDHandle.RecordDBCount()-1);
        return null;
    }
    
    private String GetCurrentCheckInDate(List<String> checkInTimeList)
    {
        for(int idx=0; idx < checkInTimeList.size(); idx++)
        {
            if(DateAdapter.TimeSpan(checkInTimeList.get(idx), "12:00:00") > 0)
                return checkInTimeList.get(idx);
        }
        return null;
    }
    
    private List<String> GenCheckInAndOutTime(String checkInId, String checkInDate, List<String> workGroupTimeList, List<String> checkInTimeList)
    {
        List<String> rtnRst = new ArrayList<String>();
        if(DateAdapter.TimeSpan(workGroupTimeList.get(0), workGroupTimeList.get(1)) < 0)
        {
            if(Math.abs(DateAdapter.TimeSpan(checkInTimeList.get(0), checkInTimeList.get(checkInTimeList.size()-1))) > 300)
            {
                rtnRst.add(checkInTimeList.get(0));
                rtnRst.add(checkInTimeList.get(checkInTimeList.size()-1));
            }
            else
            {
                if(Math.abs(DateAdapter.TimeSpan(checkInTimeList.get(0), workGroupTimeList.get(0))) < 240)
                    rtnRst.add(checkInTimeList.get(0));
                else
                    rtnRst.add(null);
                if(Math.abs(DateAdapter.TimeSpan(workGroupTimeList.get(1), checkInTimeList.get(checkInTimeList.size()-1))) < 240)
                    rtnRst.add(checkInTimeList.get(checkInTimeList.size()-1));
                else
                    rtnRst.add(null);
            }
        }
        else
        {
            rtnRst.add(GetCurrentCheckInDate(checkInTimeList));
            rtnRst.add(GetTomorrowCheckOutDate(checkInId, Integer.parseInt(checkInDate)));
        }
        return rtnRst;
    }
    
    private List<Long> GetAbsenceDayAndDelayTime(String checkInId, String checkInDate)
    {
        List<Long> rtnRst = new ArrayList<Long>();
        List<List<String>> recordList = GetOneDayCheckRawData(checkInId, checkInDate);
        if(recordList.get(0).size() > 0)
        {
            int workGroupId = Integer.parseInt(recordList.get(2).get(0));
            if(workGroupId == 0)
            {
                rtnRst.add(2L);
                rtnRst.add(0L);
                return rtnRst;
            }
                
            List<String> workGroupTimeList = GetWorkGroupTime(workGroupId);
            List<String> checkINAndOutTime = GenCheckInAndOutTime(checkInId, checkInDate, workGroupTimeList, recordList.get(1));
            String checkInTime = checkINAndOutTime.get(0), checkOutTime = checkINAndOutTime.get(1);
            
            long absenceTime = 0, timeSpan = 0;
            if(null == checkInTime)
            {
                absenceTime += 1L;
                timeSpan += 0L;
            }
            else
            {
                if(DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(0)) > 0)
                    timeSpan += DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(0));
            }
            if(null == checkOutTime)
            {
                absenceTime += 1L;
                timeSpan += 0L;
            }
            else
            {
                if(DateAdapter.TimeSpan(workGroupTimeList.get(1), checkOutTime) > 0)
                    timeSpan += DateAdapter.TimeSpan(workGroupTimeList.get(1), checkOutTime);
            }
            rtnRst.add(absenceTime);
            rtnRst.add(timeSpan);
        }
        else
        {
            rtnRst.add(2L);
            rtnRst.add(0L);
        }
        return rtnRst;
    }
    
    // Finish End
    public String GenerateReturnString(String user_name, String queryDate)
    {
        List<List<String>> recordList = GetAllDisplayData(user_name, queryDate);
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
}
