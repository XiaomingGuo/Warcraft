package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.TitleInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Title_Info implements ITableInterface
{
    private List<TitleInfo> resultList = null;
    private TitleInfo aWriteRecord = null;
	IEQManagement gEQMHandle;
    
    public Title_Info(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
    @Override
    public String GetTableName()
    {
        return "TitleInfo";
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
        Iterator<TitleInfo> it = resultList.iterator();
        while(it.hasNext())
        {
            TitleInfo tempRecord = (TitleInfo)it.next();
            switch (keyWord)
            {
            case "id":
                rtnRst.add(tempRecord.getId().toString());
                break;
            case "title_name":
                rtnRst.add(tempRecord.getTitleName());
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
    
    public void AddARecord(String titleName)
    {
        aWriteRecord = new TitleInfo();
        aWriteRecord.setTitleName(titleName);
        gEQMHandle.addANewRecord();
    }
    
    @Override
    public String GetDatabaseKeyWord(String keyword) {
        String rtnRst = "";
        if(keyword.toLowerCase().indexOf("id") == 0){
            rtnRst = "id";
        }
        else if(keyword.toLowerCase().indexOf("title_name") == 0) {
            rtnRst = "titleName";
        }
        return rtnRst;
    }
}
