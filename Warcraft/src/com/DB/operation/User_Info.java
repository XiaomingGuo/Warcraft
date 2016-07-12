package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.UserInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class User_Info extends DBTableParent implements ITableInterface
{
    private List<UserInfo> resultList = null;
    private UserInfo aWriteRecord = null;
    
    public User_Info(IEQManagement hEQMHandle)
    {
        super(hEQMHandle);
    }
    
    @Override
    public String GetTableName()
    {
        return "UserInfo";
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
        Iterator<UserInfo> it = resultList.iterator();
        while(it.hasNext())
        {
            UserInfo tempRecord = (UserInfo)it.next();
            switch (keyWord)
            {
            case "id":
                rtnRst.add(tempRecord.getId().toString());
                break;
            case "check_in_id":
                rtnRst.add(tempRecord.getCheckInId());
                break;
            case "isFixWorkGroup":
                rtnRst.add(tempRecord.getIsFixWorkGroup().toString());
                break;
            case "name":
                rtnRst.add(tempRecord.getName());
                break;
            case "password":
                rtnRst.add(tempRecord.getPassword());
                break;
            case "create_date":
                rtnRst.add(tempRecord.getCreateDate().toString());
                break;
            case "department":
                rtnRst.add(tempRecord.getDepartment());
                break;
            case "permission":
                rtnRst.add(tempRecord.getPermission().toString());
                break;
            case "picture":
                rtnRst.add(null);
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

    @Override
    public String GetDatabaseKeyWord(String keyword) {
        String rtnRst = "";
        if(keyword.toLowerCase().indexOf("id") == 0){
            rtnRst = "id";
        }
        else if(keyword.toLowerCase().indexOf("check_in_id") == 0) {
            rtnRst = "checkInId";
        }
        else if(keyword.toLowerCase().indexOf("isfixworkgroup") == 0) {
            rtnRst = "isfixworkgroup";
        }
        else if(keyword.toLowerCase().indexOf("name") >= 0) {
            rtnRst = "name";
        }
        else if(keyword.toLowerCase().indexOf("password") >= 0) {
            rtnRst = "password";
        }
        else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
            rtnRst = "createDate";
        }
        else if(keyword.toLowerCase().indexOf("department") >= 0) {
            rtnRst = "department";
        }
        else if(keyword.toLowerCase().indexOf("permission") >= 0) {
            rtnRst = "permission";
        }
        else if(keyword.toLowerCase().indexOf("picture") >= 0) {
            rtnRst = "picture";
        }
        return rtnRst;
    }
    
    public void AddARecord(String checkInId, String isFixWorkGroup, String name, String password, String department, String permission)
    {
        aWriteRecord = new UserInfo();
        aWriteRecord.setCheckInId(checkInId);
        aWriteRecord.setIsFixWorkGroup(Integer.parseInt(isFixWorkGroup));
        aWriteRecord.setName(name);
        aWriteRecord.setPassword(password);
        aWriteRecord.setDepartment(department);
        aWriteRecord.setPermission(Integer.parseInt(permission));
        getEQMHandle().addANewRecord();
    }

    @Override
    public void DeleteRecordByKeyWord(String keyWord, List<String> delList) {
        
    }
    
    public void QueryRecordByFilterKeyList(List<String> keyList,
            List<String> valueList)
    {
        String hql = "from UserInfo ui where ";
        for(int idx=0; idx<keyList.size()-1; idx++)
        {
            hql += String.format("ui.%s='%s' and ", GetDatabaseKeyWord(keyList.get(idx)), valueList.get(idx));
        }
        hql+= String.format("ui.%s='%s'", GetDatabaseKeyWord(keyList.get(keyList.size()-1)), valueList.get(valueList.size()-1));
        getEQMHandle().EQQuery(hql);
    }
}