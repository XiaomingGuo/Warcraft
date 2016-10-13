package com.DB.factory;

import com.DB.operation.*;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class DatabaseStore extends DBTableParent
{
    private ITableInterface hTableHandle;
    
    public DatabaseStore(String tableName)
    {
        super(new EarthquakeManagement());
        hTableHandle = CreateTableInstance(tableName);
        this.getEQMHandle().setTableHandle(hTableHandle);
    }
    
    @Override
    public ITableInterface getTableInstance()
    {
        return hTableHandle;
    }
    public ITableInterface CreateTableInstance(String tableName)
    {
        if("Check_In_Raw_Data" == tableName)
            return new Check_In_Raw_Data(this.getEQMHandle());
        else if("Customer_Po_Record" == tableName)
            return new Customer_Po_Record(this.getEQMHandle());
        else if("Work_Group_Info" == tableName)
            return new Work_Group_Info(this.getEQMHandle());
        else if("User_Info" == tableName)
            return new User_Info(this.getEQMHandle());
        return null;
    }
}
