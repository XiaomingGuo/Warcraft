package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.UserPermission;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class User_Permission extends DBTableParent implements ITableInterface
{
    private List<UserPermission> resultList = null;
    private UserPermission aWriteRecord = null;
    
    public User_Permission(IEQManagement hEQMHandle)
    {
        super(hEQMHandle);
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
    
    public void GetRecordByPoName(String poName)
    {
        execQueryAsc("po_name", poName, "id");
    }
    
    private void execQueryAsc(String keyWord, String value, String orderKey)
    {
        String hql = String.format("from UserPermission cpr where cpr.%s='%s' order by cpr.%s asc", GetDatabaseKeyWord(keyWord), value, GetDatabaseKeyWord(orderKey));
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryRecordOrderByIdASC(String po_name)
    {
        String hql = String.format("from UserPermission cpr where cpr.poName='%s' order by cpr.id asc", po_name);
        getEQMHandle().EQQuery(hql);
    }
    
    public void AddARecord(String checkInId, String titleName)
    {
        aWriteRecord = new UserPermission();
        aWriteRecord.setCheckInId(checkInId);
        aWriteRecord.setTitleName(titleName);
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
        else if(keyword.toLowerCase().indexOf("title_name") >= 0) {
            rtnRst = "titleName";
        }
        return rtnRst;
    }
    
    @Override
    public void DeleteRecordByKeyWord(String keyWord, List<String> delList)
    {
        for (String item : delList)
        {
            String hql = String.format("delete UserPermission cpr where cpr.%s='%s'", GetDatabaseKeyWord(keyWord), item);
            getEQMHandle().DeleteAndUpdateRecord(hql);
        }
    }
}
