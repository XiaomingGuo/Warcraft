package com.DB.factory;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class DatabaseStore extends DBTableParent
{
    private ITableInterface hTableHandle;
    
    public DatabaseStore(String tableName)
    {
        super(new EarthquakeManagement());
        hTableHandle = CreateTableInstance(tableName, this.getEQMHandle());
        this.getEQMHandle().setTableHandle(hTableHandle);
    }
    
    @Override
    public ITableInterface getTableInstance()
    {
        return hTableHandle;
    }
    
    public ITableInterface CreateTableInstance(String tableName, IEQManagement hEQHandle)
    {
        ITableInterface hTableHandle = null;
        try
        {
            hTableHandle = (ITableInterface) Class.forName("com.DB.operation."+tableName).newInstance();
            hTableHandle.setEQManagement(hEQHandle);
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return hTableHandle;
    }
}
