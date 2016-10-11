package com.jsp.support.PersonalMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DateAdapter;
import com.jsp.support.PageParentClass;
import com.page.utilities.*;

public class SummarizeCheckInTime extends PageParentClass implements IPageInterface
{
    public String[] m_displayArray = {"ID", "姓名", "工号", "漏打卡次数", "迟到早退(分)", "2小时加班(小时)", "4小时加班(小时)", "总加班(小时)", "年假(天)", "事假(天)", "查询时间范围"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    private List<List<String>> g_recordList, g_HolidayMarkList, g_OverTimeRecord, g_WorkGroupRecord;
    
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
        if(getKeyWord.contains("name"))
            rtnRst.remove("root");
        else if(getKeyWord.contains("check_in_id"))
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
        
        for(int dateOffset = 0; dateOffset < maxDays; dateOffset++ )
        {
            int curDate = beginDate + dateOffset;
            rtnRst.add(Integer.toString(curDate));
        }
        List<String> tempList = GetPersonHolidayMarkPerMonth(checkInId, queryDate, g_HolidayMarkList);
        for(int idx=0; idx < tempList.size(); idx++)
        {
            if(rtnRst.contains(tempList.get(idx)))
                rtnRst.remove(tempList.get(idx));
        }
        return rtnRst;
    }
    
    private List<String> GetPersonHolidayMarkPerMonth(String checkInId, String queryDate, List<List<String>> recordList)
    {
        List<String> rtnRst = new ArrayList<String>();
        if(recordList != null&&recordList.size() > 0)
        {
            int beginDate = Integer.parseInt(queryDate + "01");
            int maxDays = DateAdapter.getMaxDaysByYearMonth(queryDate);
            
            for(int dateOffset = 0; dateOffset < maxDays; dateOffset++ )
            {
                String checkInDate = Integer.toString(beginDate+dateOffset);
                for(int item=0; item < recordList.get(0).size(); item++)
                {
                    if(recordList.get(0).get(item).equals(checkInId)&&recordList.get(1).get(item).equals(checkInDate))
                        rtnRst.add(recordList.get(1).get(item));
                }
            }
        }
        return rtnRst;
    }
    
    private List<List<String>> GetAllUserInfo()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryAllRecord();
        String[] keywordList = new String[] {"name", "check_in_id"};
        for(int idx=0; idx < keywordList.length; idx++)
        {
            List<String> tempList = hUIHandle.getDBRecordList(keywordList[idx]);
            if(hUIHandle.getDBRecordList(keywordList[idx]).contains("root"))
                tempList.remove("root");
            else if(hUIHandle.getDBRecordList(keywordList[idx]).contains("99999"))
                tempList.remove("99999");
            rtnRst.add(tempList);
        }
        return rtnRst;
    }
    
    private List<List<String>> GetAllDisplayData(String user_id, String userName, String queryDate)
    {
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        List<List<String>> userInfo = null;
        if(user_id.isEmpty())
            userInfo = GetAllUserInfo();
        List<String> checkInNameList = user_id.isEmpty()?userInfo.get(0):Arrays.asList(userName);
        List<String> checkInIdList = user_id.isEmpty()?userInfo.get(1):Arrays.asList(user_id);
        
        for(int idx = 0; idx < checkInIdList.size(); idx++)
        {
            List<String> checkInDateList = GetAllCheckInDate(queryDate, checkInIdList.get(idx));
            rtnRst.get(0).add(Integer.toString(idx + 1));
            rtnRst.get(1).add(checkInNameList.get(idx));
            rtnRst.get(2).add(checkInIdList.get(idx));
            List<String> checkInResult = GetAPersonCheckInSummary(checkInIdList.get(idx), queryDate, checkInDateList);
            for(int item = 3; item < m_displayArray.length; item++)
            {
                rtnRst.get(item).add(checkInResult.get(item-3));
            }
        }
        return rtnRst;
    }
    
    private int GetADayWorkGroup(List<List<String>> recordList, String checkInId, String checkInDate)
    {
        int idx=0;
        for(idx=0; idx < recordList.get(0).size(); idx++)
        {
            if(recordList.get(0).get(idx).equals(checkInId)&&recordList.get(1).get(idx).equals(checkInDate))
                break;
        }
        if(recordList.get(0).size() == idx)
            return 0;
        return Integer.parseInt(recordList.get(3).get(idx));
    }
    
    private List<String> GetAPersonCheckInSummary(String checkInId, String queryDate, List<String> checkInDateList)
    {
        List<String> rtnRst = new ArrayList<String>();
        int absenceDay = 0, delayTime = 0, overTime = 0, overTime2Hour = 0, overTime4Hour = 0;
        
        for(int idx = 0; idx < checkInDateList.size(); idx++)
        {
            List<Long> tempValueList = GetAbsenceDayAndDelayTime(checkInId, checkInDateList.get(idx), GetOneDayCheckRawData(g_recordList, checkInId, checkInDateList.get(idx)));
            List<Long> overTimeList = tempValueList.size() > 0?GetActiveOverTime(checkInId, checkInDateList.get(idx), tempValueList.get(2), GetADayWorkGroup(g_recordList, checkInId, checkInDateList.get(idx))):Arrays.asList(0L,0L);
            absenceDay += tempValueList.get(0);
            delayTime += tempValueList.get(1);
            overTime2Hour += overTimeList.get(0);
            overTime4Hour += overTimeList.get(1);
            overTime += tempValueList.get(2);
        }
        rtnRst.add(Integer.toString(absenceDay));
        rtnRst.add(Integer.toString(delayTime));
        rtnRst.add(Integer.toString(overTime2Hour));
        rtnRst.add(Integer.toString(overTime4Hour));
        rtnRst.add(Integer.toString(overTime/60));
        rtnRst.add(GetHolidayMark(checkInId, queryDate, "年假", g_HolidayMarkList));
        rtnRst.add(GetHolidayMark(checkInId, queryDate, "事假", g_HolidayMarkList));
        rtnRst.add(queryDate+"01~"+queryDate + Integer.toString(DateAdapter.getMaxDaysByYearMonth(queryDate)));
        return rtnRst;
    }
    
    private String GetOverTimeRecord(String checkInId, String checkInDate, List<List<String>> recordList)
    {
        String rtnRst = "";
        if(recordList.size() > 0)
        {
            for(int item=0; item < recordList.get(0).size(); item++)
            {
                if(recordList.get(0).get(item).equals(checkInId)&&recordList.get(1).get(item).equals(checkInDate))
                {
                    rtnRst = recordList.get(2).get(item);
                    break;
                }
            }
        }
        return rtnRst;
    }
    
    private List<Long> GetActiveOverTime(String checkInId, String checkInDate, Long overTime, int workGroupId)
    {
        List<Long> rtnRst = new ArrayList<Long>();
        String curOverTimeHour = GetOverTimeRecord(checkInId, checkInDate, g_OverTimeRecord);
        List<String> workGroupTimeList = GetWorkGroupTime(workGroupId, g_WorkGroupRecord);
        if(!curOverTimeHour.isEmpty())
        {
            long applyOverTime = Long.parseLong(curOverTimeHour);
            long overTimeHour = overTime/60;
            if(DateAdapter.TimeSpan(workGroupTimeList.get(0), workGroupTimeList.get(1)) < 0)
            {
                if(overTimeHour <= 2)
                {
                    rtnRst.add(overTimeHour);
                    rtnRst.add(0L);
                }
                else if(overTimeHour >= 4)
                {
                    rtnRst.add(2L);
                    if(applyOverTime > 2)
                        rtnRst.add(2L);
                    else
                        rtnRst.add(0L);
                }
                else
                {
                    rtnRst.add(2L);
                    if(applyOverTime > 2)
                        rtnRst.add(overTimeHour-2L);
                    else
                        rtnRst.add(0L);
                }
            }
            else
            {
                rtnRst.add(0L);
                if(overTimeHour > 4)
                    rtnRst.add(4L);
                else
                    rtnRst.add(overTimeHour);
            }
        }
        else
            rtnRst = Arrays.asList(0L, 0L);
        return rtnRst;
    }
    
    
    private String GetHolidayMark(String checkInId, String queryDate, String holidayType, List<List<String>> recordList)
    {
        int rtnRst = 0;
        if(recordList != null&&recordList.size() > 0)
        {
            int beginDate = Integer.parseInt(queryDate + "01");
            int maxDays = DateAdapter.getMaxDaysByYearMonth(queryDate);
            
            for(int dateOffset = 0; dateOffset < maxDays; dateOffset++ )
            {
                String checkInDate = Integer.toString(beginDate+dateOffset);
                for(int item=0; item < recordList.get(0).size(); item++)
                {
                    if(recordList.get(0).get(item).equals(checkInId)&&recordList.get(1).get(item).equals(checkInDate)&&
                            recordList.get(2).get(item).equals(holidayType))
                        rtnRst++;
                }
            }
        }
        return Integer.toString(rtnRst);
    }
    
    private List<String> GetWorkGroupTime(int workGroupId, List<List<String>> recordList)
    {
        List<String> rtnRst = new ArrayList<String>();
        if(recordList.size() > 0)
        {
            for(int item=0; item < recordList.get(0).size(); item++)
            {
                if(recordList.get(0).get(item).equals(Integer.toString(workGroupId)))
                {
                    rtnRst.add(recordList.get(2).get(item));
                    rtnRst.add(recordList.get(3).get(item));
                }
            }
        }
        if(rtnRst.size() <= 0)
            rtnRst = Arrays.asList("00:00:00", "00:00:00");
        return rtnRst;
    }
    
    private List<List<String>> GetOneDayCheckRawData(List<List<String>> recordList, String checkInId, String checkInDate)
    {
        //"check_in_date", "check_in_time", "work_group"
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        for(int idx=0; idx < recordList.size(); idx++)
        {
            List<String> tempList = new ArrayList<String>();
            for(int item=0; item < recordList.get(0).size(); item++)
            {
                if(recordList.get(0).get(item).equals(checkInId)&&recordList.get(1).get(item).equals(checkInDate))
                    tempList.add(recordList.get(idx).get(item));
            }
            rtnRst.add(tempList);
        }
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
        List<String> tempRecord = GetOneDayCheckRawData(g_recordList, checkInId, usedDay).get(2);
        String rtnRst = null;
        for(int idx = 0; idx < tempRecord.size(); idx++)
        {
            if(DateAdapter.TimeSpan(tempRecord.get(idx), "05:30:00") < 0)
                rtnRst = tempRecord.get(idx);
        }
        return rtnRst;
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
        String midTime = DateAdapter.GetMiddleTimeBetweenTimes(workGroupTimeList.get(0), workGroupTimeList.get(1));
        if(DateAdapter.TimeSpan(workGroupTimeList.get(0), workGroupTimeList.get(1)) < 0)
        {
            if(DateAdapter.TimeBetweenTimespan(checkInTimeList.get(0), "00:00:00", midTime)&&
                    DateAdapter.TimeBetweenTimespan(checkInTimeList.get(checkInTimeList.size()-1), midTime, "23:59:59"))
            {
                rtnRst.add(checkInTimeList.get(0));
                rtnRst.add(checkInTimeList.get(checkInTimeList.size()-1));
            }
            else
            {
                if(DateAdapter.TimeBetweenTimespan(checkInTimeList.get(0), "00:00:00", midTime))
                    rtnRst.add(checkInTimeList.get(0));
                else
                    rtnRst.add(null);
                if(DateAdapter.TimeBetweenTimespan(checkInTimeList.get(checkInTimeList.size()-1), midTime, "23:59:59"))
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
    
    private List<Long> GetAbsenceDayAndDelayTime(String checkInId, String checkInDate, List<List<String>> recordList)
    {
        List<Long> rtnRst = new ArrayList<Long>();
        if(recordList.get(0).size() > 0)
        {
            int workGroupId = Integer.parseInt(recordList.get(3).get(recordList.get(0).size()-1));
            if(workGroupId == 0)
                return Arrays.asList(2L, 0L, 0L);
            
            List<String> workGroupTimeList = GetWorkGroupTime(workGroupId, g_WorkGroupRecord);
            List<String> checkINAndOutTime = GenCheckInAndOutTime(checkInId, checkInDate, workGroupTimeList, recordList.get(2));
            String checkInTime = checkINAndOutTime.get(0), checkOutTime = checkINAndOutTime.get(1);
            
            long absenceTime = 0, timeSpan = 0, overTime = 0;
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
                else
                    overTime += DateAdapter.TimeSpan(checkOutTime, workGroupTimeList.get(1));
            }
            rtnRst.add(absenceTime);
            rtnRst.add(timeSpan);
            rtnRst.add(overTime);
        }
        else
            return Arrays.asList(2L, 0L, 0L);
        return rtnRst;
    }
    
    // Finish End
    public String GenerateReturnString(String user_id, String userName, String queryDate)
    {
        if(queryDate.length() != 6)
            return "error:日期输入错误 !";
        GetAllGlobeRawDataFromDatabase(queryDate);
        List<List<String>> recordList = GetAllDisplayData(user_id, userName, queryDate);
        if(recordList.size() == 0)
            return "error:无刷卡记录!";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    private void GetAllGlobeRawDataFromDatabase(String queryDate)
    {
        g_recordList = GetCheckInRawDataRecordByDate(queryDate);
        g_HolidayMarkList = GetAllHolidayMarkRecordByDate(queryDate);
        g_OverTimeRecord = GetAllOverTimeRecordByDate(queryDate);
        g_WorkGroupRecord = GetAllWorkGroupRecord();
    }
    
    private List<List<String>> GetAllWorkGroupRecord()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        Work_Group_Info hWGIHandle = new Work_Group_Info(new EarthquakeManagement());
        hWGIHandle.QueryAllRecord();
        String[] getKeyWord = {"id", "group_name", "check_in_time", "check_out_time"};
        if(hWGIHandle.RecordDBCount() > 0)
        {
            for(int idx=0; idx < getKeyWord.length; idx++)
                rtnRst.add(hWGIHandle.getDBRecordList(getKeyWord[idx]));
        }
        return rtnRst;
    }
    
    private List<List<String>> GetAllOverTimeRecordByDate(String queryDate)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        Over_Time_Record hOTRHandle = new Over_Time_Record(new EarthquakeManagement());
        hOTRHandle.QueryRecordBetweenDateSpanAndOrderByListASC("over_time_date", queryDate + "00", queryDate + "32", Arrays.asList("over_time_date"));
        if(hOTRHandle.RecordDBCount() > 0)
        {
            String[] getKeyWord = {"check_in_id", "over_time_date", "over_time_hour"};
            for(int idx=0; idx < getKeyWord.length; idx++)
                rtnRst.add(hOTRHandle.getDBRecordList(getKeyWord[idx]));
        }
        return rtnRst;
    }
    
    private List<List<String>> GetAllHolidayMarkRecordByDate(String queryDate)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        Holiday_Mark hHMHandle = new Holiday_Mark(new EarthquakeManagement());
        
        hHMHandle.QueryRecordBetweenDateSpanAndOrderByListASC("holiday_date", queryDate + "00", queryDate + "32", Arrays.asList("holiday_date"));
        String[] getKeyWord = {"check_in_id", "holiday_date", "holiday_info"};
        for(int idx=0; idx < getKeyWord.length; idx++)
            rtnRst.add(hHMHandle.getDBRecordList(getKeyWord[idx]));
        return rtnRst;
    }
    
    private List<List<String>> GetCheckInRawDataRecordByDate(String queryDate)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        hCIRDHandle.QueryRecordBetweenDateSpanAndOrderByListASC("check_in_date", queryDate+"00", queryDate+"32", Arrays.asList("check_in_time"));
        String[] getKeyWord = {"check_in_id", "check_in_date", "check_in_time", "work_group"};
        for(int idx=0; idx < getKeyWord.length; idx++)
            rtnRst.add(hCIRDHandle.getDBRecordList(getKeyWord[idx]));
        return rtnRst;
    }
    
    public String GenerateBeLateAndLeaveEarlyReturnString(String user_id, String userName, String queryDate)
    {
        if(queryDate.length() != 6||user_id.length() <= 0||userName.length() <= 0)
            return "";
        GetAllGlobeRawDataFromDatabase(queryDate);
        List<List<String>> recordList = GetBeLateAndLeaveEarlyDisplayData(user_id, userName, queryDate);
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    public String GenerateMissCheckInDataReturnString(String user_id, String userName, String queryDate)
    {
        if(queryDate.length() != 6||user_id.length() <= 0||userName.length() <= 0)
            return "";
        GetAllGlobeRawDataFromDatabase(queryDate);
        List<List<String>> recordList = GetMissCheckInDataDisplayData(user_id, userName, queryDate);
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    private List<List<String>> GetBeLateAndLeaveEarlyDisplayData(
            String user_id, String userName, String queryDate)
    {
        m_displayArray = new String[]{"ID", "姓名", "工号", "打卡日期", "打卡时间(分)", "班次"};
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        
        List<String> checkInDateList = GetAllCheckInDate(queryDate, user_id);
        List<List<String>> missCheckInResult = GetAPersonBeLateAndLeaveEarlySummary(user_id, queryDate, checkInDateList);
        
        for(int idx=0; idx < missCheckInResult.get(0).size(); idx++)
        {
            rtnRst.get(0).add(Integer.toString(idx + 1));
            rtnRst.get(1).add(userName);
            int item = 2;
            for(; item < m_displayArray.length-1; item++)
                rtnRst.get(item).add(missCheckInResult.get(item-2).get(idx));
            rtnRst.get(item).add(CurWorkGroupDescription(missCheckInResult.get(item-2).get(idx)));
        }
        return rtnRst;
    }
    
    private List<List<String>> GetAPersonBeLateAndLeaveEarlySummary(String user_id, String queryDate, List<String> checkInDateList)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        //"check_in_date", "check_in_time", "work_group"
        GetAllGlobeRawDataFromDatabase(queryDate);
        for(int iCount=0; iCount < 4; iCount++)
            rtnRst.add(new ArrayList<String>());
        
        for(int idx = 0; idx < checkInDateList.size(); idx++)
        {
            List<List<String>> dayCheckInRecord = GetOneDayCheckRawData(g_recordList, user_id, checkInDateList.get(idx));
            if(!IsBeLateAndLeaveEarlyDay(user_id, checkInDateList.get(idx), dayCheckInRecord))
            {
                for(int iCol=0; iCol < dayCheckInRecord.size(); iCol++)
                    rtnRst.get(iCol).addAll(dayCheckInRecord.get(iCol));
            }
        }
        return rtnRst;
    }
    
    private boolean IsBeLateAndLeaveEarlyDay(String user_id, String checkInDate,
            List<List<String>> recordList)
    {
        if(recordList.get(0).size() > 0)
        {
            int workGroupId = Integer.parseInt(recordList.get(3).get(recordList.get(0).size()-1));
            if(workGroupId <= 0)
                return true;
            List<String> workGroupTimeList = GetWorkGroupTime(workGroupId, g_WorkGroupRecord);
            List<String> checkINAndOutTime = GenCheckInAndOutTime(user_id, checkInDate, workGroupTimeList, recordList.get(2));
            String checkInTime = checkINAndOutTime.get(0), checkOutTime = checkINAndOutTime.get(1);
            
            if(null != checkInTime)
            {
                if(DateAdapter.TimeSpan(checkInTime, workGroupTimeList.get(0)) > 0)
                    return false;
            }
            if(null != checkOutTime)
            {
                if(DateAdapter.TimeSpan(workGroupTimeList.get(1), checkOutTime) > 0)
                    return false;
            }
        }
        return true;
    }
    
    private List<List<String>> GetMissCheckInDataDisplayData(String user_id,
            String userName, String queryDate)
    {
        m_displayArray = new String[]{"ID", "姓名", "工号", "打卡日期", "打卡时间(分)", "班次"};
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        
        List<String> checkInDateList = GetAllCheckInDate(queryDate, user_id);
        List<List<String>> missCheckInResult = GetAPersonMissCheckInSummary(user_id, queryDate, checkInDateList);
        
        for(int idx=0; idx < missCheckInResult.get(0).size(); idx++)
        {
            rtnRst.get(0).add(Integer.toString(idx + 1));
            rtnRst.get(1).add(userName);
            rtnRst.get(2).add(user_id);
            int item = 3;
            for(; item < m_displayArray.length-1; item++)
                rtnRst.get(item).add(missCheckInResult.get(item-3).get(idx));
            rtnRst.get(item).add(CurWorkGroupDescription(missCheckInResult.get(item-3).get(idx)));
        }
        return rtnRst;
    }
    
    private String CurWorkGroupDescription(String wordGroupId)
    {
        String rtnRst = "";
        if(wordGroupId.indexOf("0") == 0)
            rtnRst += "未排班";
        else
            rtnRst += GetWorkGroupName(wordGroupId);
        return rtnRst;
    }
    
    private String GetWorkGroupName(String id)
    {
        Work_Group_Info hUIHandle = new Work_Group_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(id));
        return hUIHandle.getDBRecordList("group_name").get(0);
    }
    
    private List<List<String>> GetAPersonMissCheckInSummary(String user_id,
            String queryDate, List<String> checkInDateList)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        //"check_in_date", "check_in_time", "work_group"
        GetAllGlobeRawDataFromDatabase(queryDate);
        for(int iCount=0; iCount < 3; iCount++)
            rtnRst.add(new ArrayList<String>());
        
        for(int idx = 0; idx < checkInDateList.size(); idx++)
        {
            List<List<String>> dayCheckInRecord = GetOneDayCheckRawData(g_recordList, user_id, checkInDateList.get(idx));
            String checkInStatus = IsAbsenceDay(user_id, checkInDateList.get(idx), dayCheckInRecord);
            if(!checkInStatus.isEmpty())
            {
                rtnRst.get(0).add(checkInDateList.get(idx));
                rtnRst.get(1).add(checkInStatus);
                rtnRst.get(2).add(dayCheckInRecord.get(3).size()==0?"0":dayCheckInRecord.get(3).get(dayCheckInRecord.get(3).size()-1));
            }
        }
        return rtnRst;
    }
    
    private String IsAbsenceDay(String user_id, String checkInDate,
            List<List<String>> recordList)
    {
        if(recordList.get(0).size() > 0)
        {
            int workGroupId = Integer.parseInt(recordList.get(3).get(0));
            if(workGroupId == 0)
                return "未排班";
            
            List<String> workGroupTimeList = GetWorkGroupTime(workGroupId, g_WorkGroupRecord);
            List<String> checkINAndOutTime = GenCheckInAndOutTime(user_id, checkInDate, workGroupTimeList, recordList.get(2));
            String checkInTime = checkINAndOutTime.get(0), checkOutTime = checkINAndOutTime.get(1);
            if(null == checkInTime&&null == checkOutTime)
                return "未刷上班&下班卡";
            else if(null == checkInTime)
                return "未刷上班卡";
            else if(null == checkOutTime)
                return "未刷下班卡";
        }
        else
            return "未刷上班&下班卡";
        return "";
    }
}