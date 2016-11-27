package com.jsp.support.PersonalMenu;

import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DBTableParent;
import com.jsp.support.PageParentClass;
import com.page.utilities.CPageAjaxUtil;
import com.page.utilities.CRecordsQueryUtil;

public class SummaryHoliday extends PageParentClass implements IPageInterface
{
    private String[] m_displayList = {"ID", "姓名", "工号", "日期", "请假时间(H)", "假期类型", "操作"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    
    public SummaryHoliday()
    {
        hQueryHandle = new CRecordsQueryUtil();
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
    private List<List<String>> GetAllHolidayData(String userID, String user_name, String queryDate, String holidayType)
    {
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        DBTableParent hHMHandle = new DatabaseStore("Holiday_Mark");
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
        if (hHMHandle.getTableInstance().RecordDBCount() > 0)
        {
            String[] sqlKeyList = {"check_in_id", "holiday_date", "holiday_time", "holiday_info", "id"};
            for(int iRecordIdx = 0; iRecordIdx < hHMHandle.getTableInstance().RecordDBCount(); iRecordIdx++)
            {
                rtnRst.get(0).add(Integer.toString(iRecordIdx+1));
                rtnRst.get(1).add(user_name);
            }
            for(int idx=0; idx < sqlKeyList.length; idx++)
            {
                rtnRst.get(idx+2).addAll(hHMHandle.getDBRecordList(sqlKeyList[idx]));
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
        hQueryHandle.setDBHandle(new DatabaseStore("Holiday_Type"));
        return hQueryHandle.GetTableContentByKeyWord("", "AllRecord", "holiday_name");
    }
    
    public String UpdateHoldayMarkRecord(String id, String holidayType, String holidayDate, String holidayTime)
    {
        DBTableParent hHMHandle = new DatabaseStore("Holiday_Mark");
        hHMHandle.UpdateRecordByKeyList("holiday_info", holidayType, Arrays.asList("id"), Arrays.asList(id));
        if(holidayDate.length() == 8)
            hHMHandle.UpdateRecordByKeyList("holiday_date", holidayDate, Arrays.asList("id"), Arrays.asList(id));
        if(holidayTime.length() == 1)
            hHMHandle.UpdateRecordByKeyList("holiday_time", holidayTime, Arrays.asList("id"), Arrays.asList(id));
        return "";
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayList;
    }
}
