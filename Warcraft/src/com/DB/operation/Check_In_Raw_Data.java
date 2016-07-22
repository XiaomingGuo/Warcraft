package com.DB.operation;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.CheckInRawData;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Check_In_Raw_Data extends DBTableParent implements ITableInterface
{
    private List<CheckInRawData> resultList = null;
    private CheckInRawData aWriteRecord = null;
    
    public Check_In_Raw_Data(IEQManagement hEQMHandle)
    {
        super(hEQMHandle);
    }
    
    @Override
    public String GetTableName()
    {
        return "CheckInRawData";
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
        Iterator<CheckInRawData> it = resultList.iterator();
        while(it.hasNext())
        {
            CheckInRawData tempRecord = (CheckInRawData)it.next();
            switch (keyWord)
            {
            case "id":
                rtnRst.add(tempRecord.getId().toString());
                break;
            case "check_in_id":
                rtnRst.add(tempRecord.getCheckInId());
                break;
            case "check_in_date":
                rtnRst.add(tempRecord.getCheckInDate());
                break;
            case "check_in_time":
                rtnRst.add(tempRecord.getCheckInTime().toString());
                break;
            case "work_group":
                rtnRst.add(tempRecord.getWorkGroup().toString());
                break;
            case "isEnsure":
                rtnRst.add(tempRecord.getIsEnsure().toString());
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
    
    public void AddARecord(String checkInId, String checkInDate, String checkInTime, String workGroup)
    {
        aWriteRecord = new CheckInRawData();
        aWriteRecord.setCheckInId(checkInId);
        aWriteRecord.setCheckInDate(checkInDate);
        aWriteRecord.setCheckInTime(Time.valueOf(checkInTime));
        aWriteRecord.setWorkGroup(Integer.parseInt(workGroup));
        getEQMHandle().addANewRecord();
    }
    
    @Override
    public String GetDatabaseKeyWord(String keyword) {
        String rtnRst = "";
        if(keyword.toLowerCase().indexOf("id") == 0){
            rtnRst = "id";
        }
        else if(keyword.toLowerCase().indexOf("check_in_id") == 0) {
            rtnRst = "checkInId";
        }
        else if(keyword.toLowerCase().indexOf("check_in_date") >= 0) {
            rtnRst = "checkInDate";
        }
        else if(keyword.toLowerCase().indexOf("check_in_time") >= 0) {
            rtnRst = "checkInTime";
        }
        else if(keyword.toLowerCase().indexOf("work_group") >= 0) {
            rtnRst = "workGroup";
        }
        else if(keyword.toLowerCase().indexOf("isensure") >= 0) {
            rtnRst = "isEnsure";
        }
        return rtnRst;
    }
}
