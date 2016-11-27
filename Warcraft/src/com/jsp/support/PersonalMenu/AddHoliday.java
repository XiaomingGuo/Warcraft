package com.jsp.support.PersonalMenu;

import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DBTableParent;
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
        hQueryHandle.setDBHandle(new DatabaseStore("Holiday_Type"));
        return GenSelectItemString(hQueryHandle.GetTableContentByKeyWord(null, "AllRecord", "holiday_name"));
    }
    
    private String GetUserNameString()
    {
        hQueryHandle.setDBHandle(new DatabaseStore("User_Info"));
        List<String> userName = hQueryHandle.GetTableContentByKeyWord(null, "AllRecord", "name");
        userName.remove("root");
        return GenSelectItemString(userName);
    }
    
    private String GetDepartmentName()
    {
        hQueryHandle.setDBHandle(new DatabaseStore("User_Info"));
        return GenSelectItemString(hQueryHandle.GetTableContentGroupByKeyWord(null, "AllRecord", "department", "department"));
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
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayList;
    }
}
