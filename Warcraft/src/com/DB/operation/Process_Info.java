package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProcessInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Process_Info implements ITableInterface
{
    private List<ProcessInfo> resultList = null;
    private ProcessInfo aWriteRecord = null;
	IEQManagement gEQMHandle;
    
    public Process_Info(IEQManagement hEQMHandle)
    {
		gEQMHandle = hEQMHandle;
    }
    
    @Override
    public String GetTableName()
    {
        return "ProcessInfo";
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
        Iterator<ProcessInfo> it = resultList.iterator();
        while(it.hasNext())
        {
            ProcessInfo tempRecord = (ProcessInfo)it.next();
            switch (keyWord)
            {
            case "id":
                rtnRst.add(tempRecord.getId().toString());
                break;
            case "process_name":
                rtnRst.add(tempRecord.getProcessName());
                break;
            case "station_name":
                rtnRst.add(tempRecord.getStationName());
                break;
            case "process_order":
                rtnRst.add(tempRecord.getProcessOrder().toString());
                break;
            case "process_count":
                rtnRst.add(tempRecord.getProcessCount().toString());
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
    
    public void AddARecord(String processName, String stationName, String processOrder, String processCount, String vendor, String percent)
    {
        aWriteRecord = new ProcessInfo();
        aWriteRecord.setProcessName(processName);
        aWriteRecord.setStationName(stationName);
        aWriteRecord.setProcessOrder(Integer.parseInt(processOrder));
        aWriteRecord.setProcessCount(Integer.parseInt(processCount));
        gEQMHandle.addANewRecord();
    }

    @Override
    public String GetDatabaseKeyWord(String keyword) {
        String rtnRst = "";
        if(keyword.toLowerCase().indexOf("id") >= 0){
            rtnRst = "id";
        }
        else if(keyword.toLowerCase().indexOf("process_name") >= 0) {
            rtnRst = "processName";
        }
        else if(keyword.toLowerCase().indexOf("station_name") >= 0) {
            rtnRst = "stationName";
        }
        else if(keyword.toLowerCase().indexOf("process_order") >= 0) {
            rtnRst = "processOrder";
        }
        else if(keyword.toLowerCase().indexOf("process_count") >= 0) {
            rtnRst = "processCount";
        }
        return rtnRst;
    }
}
