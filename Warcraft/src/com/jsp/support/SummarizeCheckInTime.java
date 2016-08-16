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
    private List<List<String>> g_recordList;
    
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
    
    private List<List<String>> GetAllUserInfo()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryAllRecord();
        String[] keywordList = new String[] {"name", "check_in_id"};
        for(int idx=0; idx < keywordList.length; idx++)
        {
            if(hUIHandle.getDBRecordList(keywordList[idx]).equals("root")||hUIHandle.getDBRecordList(keywordList[idx]).equals("99999"))
                rtnRst.add(hUIHandle.getDBRecordList(keywordList[idx]));
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
                rtnRst.get(item).add(checkInResult.get(item-3));
        }
        return rtnRst;
    }
    
    private List<List<String>>GetAllCheckInRawDataRecord(String checkInId, String queryDate)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        hCIRDHandle.QueryRecordByFilterKeyListAndBetweenDateSpanOrderByListASC(Arrays.asList("check_in_id"), 
                Arrays.asList(checkInId), "check_in_date", queryDate+"00", queryDate+"32", Arrays.asList("check_in_time"));
        String[] getKeyWord = {"check_in_date", "check_in_time", "work_group"};
        for(int idx=0; idx < getKeyWord.length; idx++)
            rtnRst.add(hCIRDHandle.getDBRecordList(getKeyWord[idx]));
        return rtnRst;
    }
    
    private List<String> GetAPersonCheckInSummary(String checkInId, String queryDate, List<String> checkInDateList)
    {
        List<String> rtnRst = new ArrayList<String>();
        int absenceDay = 0, delayTime = 0;
        //"check_in_date", "check_in_time", "work_group"
        g_recordList = GetAllCheckInRawDataRecord(checkInId, queryDate);
        
        for(int idx = 0; idx < checkInDateList.size(); idx++)
        {
            List<Long> tempValueList = GetAbsenceDayAndDelayTime(checkInId, checkInDateList.get(idx), GetOneDayCheckRawData(g_recordList, checkInDateList.get(idx)));
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
    
    private List<List<String>> GetOneDayCheckRawData(List<List<String>> recordList, String checkInDate)
    {
        //"check_in_date", "check_in_time", "work_group"
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        for(int idx=0; idx < recordList.size(); idx++)
        {
            List<String> tempList = new ArrayList<String>();
            for(int item=0; item < recordList.get(0).size(); item++)
            {
                if(recordList.get(0).get(item).equals(checkInDate))
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
        List<String> tempRecord = GetOneDayCheckRawData(g_recordList, usedDay).get(1);
        //"check_in_date", "check_in_time", "work_group"
        //"check_in_time", "00:00:00", "05:30:00"
        String rtnRst = null;
        for(int idx = 0; idx < tempRecord.size(); idx++)
        {
            int curHour = Integer.parseInt(tempRecord.get(idx).split(":")[0]);
            if(curHour > 0 && curHour < 6)
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
        if(DateAdapter.TimeSpan(workGroupTimeList.get(0), workGroupTimeList.get(1)) < 0)
        {
            if(Math.abs(DateAdapter.TimeSpan(checkInTimeList.get(0), checkInTimeList.get(checkInTimeList.size()-1))) > 300)
            {
                rtnRst.add(checkInTimeList.get(0));
                rtnRst.add(checkInTimeList.get(checkInTimeList.size()-1));
            }
            else
            {
                if(Math.abs(DateAdapter.TimeSpan(checkInTimeList.get(0), workGroupTimeList.get(0))) < 359)
                    rtnRst.add(checkInTimeList.get(0));
                else
                    rtnRst.add(null);
                if(Math.abs(DateAdapter.TimeSpan(workGroupTimeList.get(1), checkInTimeList.get(checkInTimeList.size()-1))) < 359)
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
            int workGroupId = Integer.parseInt(recordList.get(2).get(0));
            if(workGroupId == 0)
                return Arrays.asList(2L, 0L);
            
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
            return Arrays.asList(2L, 0L);
        return rtnRst;
    }
    
    // Finish End
    public String GenerateReturnString(String user_id, String userName, String queryDate)
    {
        if(queryDate.length() != 6)
            return "error:日期输入错误 !";
        List<List<String>> recordList = GetAllDisplayData(user_id, userName, queryDate);
        if(recordList.size() == 0)
            return "error:无刷卡记录!";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    public String GenerateBeLateAndLeaveEarlyReturnString(String user_id, String userName, String queryDate)
    {
        List<List<String>> recordList = GetBeLateAndLeaveEarlyDisplayData(user_id, userName, queryDate);
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    public String GenerateMissCheckInDataReturnString(String user_id, String userName, String queryDate)
    {
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
        if(queryDate.length() != 6||user_id.length() <= 0||userName.length() <= 0)
            return rtnRst;
        
        List<String> checkInDateList = GetAllCheckInDate(queryDate, user_id);
        List<List<String>> missCheckInResult = GetAPersonBeLateAndLeaveEarlySummary(user_id, queryDate, checkInDateList);
        
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
    
    private List<List<String>> GetAPersonBeLateAndLeaveEarlySummary(
            String user_id, String queryDate, List<String> checkInDateList)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        //"check_in_date", "check_in_time", "work_group"
        g_recordList = GetAllCheckInRawDataRecord(user_id, queryDate);
        for(int iCount=0; iCount < 3; iCount++)
            rtnRst.add(new ArrayList<String>());
        
        for(int idx = 0; idx < checkInDateList.size(); idx++)
        {
            List<List<String>> dayCheckInRecord = GetOneDayCheckRawData(g_recordList, checkInDateList.get(idx));
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
            int workGroupId = Integer.parseInt(recordList.get(2).get(0));
            
            List<String> workGroupTimeList = GetWorkGroupTime(workGroupId);
            List<String> checkINAndOutTime = GenCheckInAndOutTime(user_id, checkInDate, workGroupTimeList, recordList.get(1));
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
        if(queryDate.length() != 6||user_id.length() <= 0||userName.length() <= 0)
            return rtnRst;
        
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
        g_recordList = GetAllCheckInRawDataRecord(user_id, queryDate);
        for(int iCount=0; iCount < 3; iCount++)
            rtnRst.add(new ArrayList<String>());
        
        for(int idx = 0; idx < checkInDateList.size(); idx++)
        {
            List<List<String>> dayCheckInRecord = GetOneDayCheckRawData(g_recordList, checkInDateList.get(idx));
            if(!IsAbsenceDay(user_id, checkInDateList.get(idx), dayCheckInRecord))
            {
                for(int iCol=0; iCol < dayCheckInRecord.size(); iCol++)
                {
                    if(dayCheckInRecord.get(0).size() > 0)
                        rtnRst.get(iCol).addAll(dayCheckInRecord.get(iCol));
                    else
                    {
                        if(0 == iCol)
                            rtnRst.get(iCol).addAll(Arrays.asList(checkInDateList.get(idx)));
                        else
                            rtnRst.get(iCol).addAll(Arrays.asList("0"));
                    }
                }
            }
        }
        return rtnRst;
    }
    
    private boolean IsAbsenceDay(String user_id, String checkInDate,
            List<List<String>> recordList)
    {
        if(recordList.get(0).size() > 0)
        {
            int workGroupId = Integer.parseInt(recordList.get(2).get(0));
            if(workGroupId == 0)
                return false;
            
            List<String> workGroupTimeList = GetWorkGroupTime(workGroupId);
            List<String> checkINAndOutTime = GenCheckInAndOutTime(user_id, checkInDate, workGroupTimeList, recordList.get(1));
            String checkInTime = checkINAndOutTime.get(0), checkOutTime = checkINAndOutTime.get(1);
            
            if(null == checkInTime||null == checkOutTime)
                return false;
        }
        else
            return false;
        return true;
    }
}