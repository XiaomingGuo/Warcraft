package com.jsp.support.PersonalMenu;

import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DateAdapter;
import com.jsp.support.PageParentClass;
import com.page.utilities.CPageAjaxUtil;
import com.page.utilities.CRecordsQueryUtil;

public class SummaryHoliday extends PageParentClass implements IPageInterface
{
    private String[] m_displayList = {"ID", "姓名", "工号", "日期", "假期类型", "操作"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    
    public SummaryHoliday()
    {
        hQueryHandle = new CRecordsQueryUtil();
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
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
    
    private String GetCheckInIdFromUserInfo(String user_name)
    {
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(user_name));
        return hUIHandle.getDBRecordList("check_in_id").get(0);
    }
    
    private List<List<String>> GetAllHolidayData(String userID, String user_name, String queryDate, String holidayType)
    {
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        Holiday_Mark hHMHandle = new Holiday_Mark(new EarthquakeManagement());
        String beginDate = queryDate, endDate = queryDate;
        if(queryDate.length() == 4)
        {
            beginDate = queryDate + "0100";
            endDate = queryDate + "1232";
        }
        else if(queryDate.length() == 6)
        {
            beginDate = queryDate + "00";
            endDate = queryDate + "32";
        }
        if(holidayType.contains("请选择"))
            hHMHandle.QueryRecordByFilterKeyListAndBetweenDateSpan(Arrays.asList("check_in_id"), Arrays.asList(userID), "holiday_date", beginDate, endDate);
        else
            hHMHandle.QueryRecordByFilterKeyListAndBetweenDateSpan(Arrays.asList("check_in_id", "holiday_info"), Arrays.asList(userID, holidayType), "holiday_date", beginDate, endDate);
        if (hHMHandle.RecordDBCount() > 0)
        {
            String[] sqlKeyList = {"check_in_id", "holiday_date", "holiday_info", "id"};
            for(int iRecordIdx = 0; iRecordIdx < hHMHandle.RecordDBCount(); iRecordIdx++)
            {
                rtnRst.get(0).add(Integer.toString(iRecordIdx+1));
                rtnRst.get(1).add(user_name);
            }
            for(int idx=2; idx < sqlKeyList.length + 2; idx++)
            {
                rtnRst.get(idx).addAll(hHMHandle.getDBRecordList(sqlKeyList[idx-2]));
            }
        }
        return rtnRst;
    }
    
    public String GenerateReturnString(String userID, String user_name, String queryDate, String holidayType)
    {
        if(queryDate.length() < 4)
            return "remove$error:日期输入错误 !";
        List<List<String>> recordList = GetAllHolidayData(userID, user_name, queryDate, holidayType);
        if(recordList.get(0).size() == 0)
            return "remove$error:无记录!";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    public List<String> GetHolidayTypeName()
    {
        hQueryHandle.setTableHandle(new Holiday_Type(new EarthquakeManagement()));
        return hQueryHandle.GetTableContentByKeyWord("", "AllRecord", "holiday_name");
    }
    
    public String UpdateHoldayMarkRecord(String id, String holidayType, String holidayDate)
    {
        Holiday_Mark hHMHandle = new Holiday_Mark(new EarthquakeManagement());
        hHMHandle.UpdateRecordByKeyList("holiday_info", holidayType, Arrays.asList("id"), Arrays.asList(id));
        if(holidayDate.length() == 8)
            hHMHandle.UpdateRecordByKeyList("holiday_date", holidayDate, Arrays.asList("id"), Arrays.asList(id));
        return "";
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayList;
    }
}
