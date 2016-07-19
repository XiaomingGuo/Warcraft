package com.jsp.support;

import java.util.List;

import com.DB.operation.EarthquakeManagement;
import com.DB.operation.User_Info;
import com.Warcraft.Interface.IPageAjaxUtil;
import com.Warcraft.Interface.IPageInterface;
import com.Warcraft.Interface.IRecordsQueryUtil;
import com.page.utilities.CPageAjaxUtil;
import com.page.utilities.CRecordsQueryUtil;

public class Query_AllUserInfo_Ajax extends PageParentClass implements IPageInterface
{
    public String[] m_displayArray = {"ID", "姓名", "工号", "部门", "选择班次", "操作"};
    private IRecordsQueryUtil hQueryHandle;
    private IPageAjaxUtil hAjaxHandle;
    
    public Query_AllUserInfo_Ajax()
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
    
    public String GenerateReturnString(String user_name, String department)
    {
        List<List<String>> recordList = GetAllDisplayData(user_name, department);
        if(recordList.size() == 0)
            return "";
        String rtnRst = hAjaxHandle.GenerateAjaxString(recordList);
        return rtnRst;
    }
    
    public List<List<String>> GetAllUserRecord()
    {
        String[] sqlGetArray = {"id", "name", "check_in_id", "department", "isFixWorkGroup"};
        hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        return hQueryHandle.GetAllTableContent(sqlGetArray);
    }
    
    private List<List<String>> GetAllUserRecordByDepartment(String department)
    {
        String[] sqlGetArray = {"id", "name", "check_in_id", "department", "isFixWorkGroup"};
        hQueryHandle.setTableHandle(new User_Info(new EarthquakeManagement()));
        return hQueryHandle.GetAllTableContentByKeyWord(sqlGetArray, "department", department);
    }
    
    private List<List<String>> GetAllDisplayData(String user_name, String department)
    {
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        List<List<String>> recordList = null;
        if(department.indexOf("请选择") < 0)
            recordList = GetAllUserRecordByDepartment(department);
        else if(user_name.indexOf("请选择") >= 0)
            recordList = GetAllUserRecord();
        
        if(null == recordList||recordList.size() == 0)
            return null;
        int index = 1;
        for(int idx = 0; idx < recordList.get(0).size(); idx++)
        {
            if(recordList.get(1).get(idx).contains("root"))
                continue;
            rtnRst.get(0).add(Integer.toString(index));
            for(int item = 1; item < recordList.size(); item++)
                rtnRst.get(item).add(recordList.get(item).get(idx));
            rtnRst.get(recordList.size()).add(recordList.get(0).get(idx));
            index++;
        }
        return rtnRst;
    }
}
