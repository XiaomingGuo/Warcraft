package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.HolidayType;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Holiday_Type implements ITableInterface
{
    private List<HolidayType> resultList = null;
    private HolidayType aWriteRecord = null;
	IEQManagement gEQMHandle;
    
    public Holiday_Type(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
    @Override
    public String GetTableName()
    {
        return "HolidayType";
    }
    
    @Override
    public int RecordDBCount()
    {
        int rtnRst = 0;
        if (resultList != null)
            rtnRst = resultList.size();
        return rtnRst;
    }
    
    @Override
    public List<String> getDBRecordList(String keyWord)
    {
        List<String> rtnRst = new ArrayList<String>();
        Iterator<HolidayType> it = resultList.iterator();
        while(it.hasNext())
        {
            HolidayType tempRecord = (HolidayType)it.next();
            switch (keyWord)
            {
            case "id":
                rtnRst.add(tempRecord.getId().toString());
                break;
            case "holiday_name":
                rtnRst.add(tempRecord.getHolidayName());
                break;
            default:
                break;
            }
        }
        return rtnRst;
    }
    
    @Override
    public void setResultList(Query query)
    {
        this.resultList = query.list();
    }
    
    @Override
    public Object getAWriteRecord()
    {
        return aWriteRecord;
    }
    
    public void AddARecord(String holidayName)
    {
        aWriteRecord = new HolidayType();
        aWriteRecord.setHolidayName(holidayName);
        gEQMHandle.addANewRecord();
    }
    
    @Override
    public String GetDatabaseKeyWord(String keyword) {
        String rtnRst = "";
        if(keyword.toLowerCase().indexOf("id") == 0){
            rtnRst = "id";
        }
        else if(keyword.toLowerCase().indexOf("holiday_name") == 0) {
            rtnRst = "holidayName";
        }
        return rtnRst;
    }
}
