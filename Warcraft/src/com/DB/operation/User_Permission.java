package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.UserPermission;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class User_Permission implements ITableInterface
{
    private List<UserPermission> resultList = null;
    private UserPermission aWriteRecord = null;
    IEQManagement gEQMHandle;
    
    public User_Permission(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
    @Override
    public String GetTableName()
    {
        return "UserPermission";
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
        Iterator<UserPermission> it = resultList.iterator();
        while(it.hasNext())
        {
            UserPermission tempRecord = (UserPermission)it.next();
            switch (keyWord)
            {
            case "id":
                rtnRst.add(tempRecord.getId().toString());
                break;
            case "user_name":
                rtnRst.add(tempRecord.getCheckInId());
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
    
    public void AddARecord(String checkInId, String titleName)
    {
        aWriteRecord = new UserPermission();
        aWriteRecord.setCheckInId(checkInId);
        aWriteRecord.setTitleName(titleName);
        gEQMHandle.addANewRecord();
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
        else if(keyword.toLowerCase().indexOf("title_name") >= 0) {
            rtnRst = "titleName";
        }
        return rtnRst;
    }
}
