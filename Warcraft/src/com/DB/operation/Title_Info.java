package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.TitleInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Title_Info extends DBTableParent implements ITableInterface
{
    private List<TitleInfo> resultList = null;
    private TitleInfo aWriteRecord = null;
    
    public Title_Info(IEQManagement hEQMHandle)
    {
        super(hEQMHandle);
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
    
    public void GetRecordByPoName(String poName)
    {
        execQueryAsc("po_name", poName, "id");
    }
    
    private void execQueryAsc(String keyWord, String value, String orderKey)
    {
        String hql = String.format("from TitleInfo cpr where cpr.%s='%s' order by cpr.%s asc", GetDatabaseKeyWord(keyWord), value, GetDatabaseKeyWord(orderKey));
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryRecordOrderByIdASC(String po_name)
    {
        String hql = String.format("from TitleInfo cpr where cpr.poName='%s' order by cpr.id asc", po_name);
        getEQMHandle().EQQuery(hql);
    }
    
    public void AddARecord(String titleName)
    {
        aWriteRecord = new TitleInfo();
        aWriteRecord.setTitleName(titleName);
        getEQMHandle().addANewRecord();
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
    
    @Override
    public void DeleteRecordByKeyWord(String keyWord, List<String> delList)
    {
        for (String item : delList)
        {
            String hql = String.format("delete TitleInfo cpr where cpr.%s='%s'", GetDatabaseKeyWord(keyWord), item);
            getEQMHandle().DeleteAndUpdateRecord(hql);
        }
    }
}