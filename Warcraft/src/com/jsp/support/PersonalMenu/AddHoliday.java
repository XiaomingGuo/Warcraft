package com.jsp.support.PersonalMenu;

import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.jsp.support.PageParentClass;
import com.page.utilities.CPageAjaxUtil;
import com.page.utilities.CRecordsQueryUtil;

public class AddHoliday extends PageParentClass implements IPageInterface
{
    String[] m_displayList = {"ID", "姓名", "工号", "部门", "假期类型", "操作"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    
    public AddHoliday()
    {
        hQueryHandle = new CRecordsQueryUtil();
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
    private String GenSelectItemString(List<String> paramList)
    {
        String rtnRst = paramList.get(0);
        for(int idx = 1; idx < paramList.size(); idx++)
            rtnRst += "#" + paramList.get(idx);
        return rtnRst;
    }
    
    private String GetHolidayNameString()
    {
        hQueryHandle.setTableHandle(new Holiday_Type(new EarthquakeManagement()));
        return GenSelectItemString(hQueryHandle.GetTableContentByKeyWord(null, "AllRecord", "holiday_name"));
    }
    
    private String GetUserNameString()
    {
        hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        List<String> userName = hQueryHandle.GetTableContentByKeyWord(null, "AllRecord", "name");
        userName.remove("root");
        return GenSelectItemString(userName);
    }
    
    private String GetDepartmentName()
    {
        hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        return GenSelectItemString(hQueryHandle.GetTableContentByKeyWord(null, "AllRecord", "department"));
    }
    
    public String GenerateReturnString()
    {
        List<List<String>> recordList = GetAllDisplayData();
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    private List<List<String>> GetAllDisplayData()
    {
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        rtnRst.get(0).add("...");
        rtnRst.get(1).add(GetUserNameString());
        rtnRst.get(2).add("...");
        rtnRst.get(3).add(GetDepartmentName());
        rtnRst.get(4).add(GetHolidayNameString());
        rtnRst.get(5).add("1");
        return rtnRst;
    }
    
    public String SubmitAddHolidaysDate(String strCheckInId, String addDate, String holidayType)
    {
        String rtnRst = "";
        Holiday_Mark hHMHandle = new Holiday_Mark(new EarthquakeManagement());
        hHMHandle.QueryRecordByFilterKeyList(Arrays.asList("check_in_id", "holiday_date"), Arrays.asList(strCheckInId, addDate));
        if(hHMHandle.RecordDBCount() <= 0)
            hHMHandle.AddARecord(strCheckInId, addDate, holidayType);
        else
            rtnRst += "error:节假日或转班信息已经存在!";
        return rtnRst;
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayList;
    }
}
