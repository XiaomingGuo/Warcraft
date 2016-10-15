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
        else if("Customer_Po" == tableName)
            return new Customer_Po(this.getEQMHandle());
        else if("Discard_Material_Record" == tableName)
            return new Discard_Material_Record(this.getEQMHandle());
        else if("Exhausted_Manu_Storage_Record" == tableName)
            return new Exhausted_Manu_Storage_Record(this.getEQMHandle());
        else if("Exhausted_Material" == tableName)
            return new Exhausted_Material(this.getEQMHandle());
        else if("Exhausted_Other" == tableName)
            return new Exhausted_Other(this.getEQMHandle());
        else if("Exhausted_Product" == tableName)
            return new Exhausted_Product(this.getEQMHandle());
        else if("Exhausted_Semi_Product" == tableName)
            return new Exhausted_Semi_Product(this.getEQMHandle());
        else if("Holiday_Mark" == tableName)
            return new Holiday_Mark(this.getEQMHandle());
        else if("Holiday_Type" == tableName)
            return new Holiday_Type(this.getEQMHandle());
        else if("Manu_Storage_Record" == tableName)
            return new Manu_Storage_Record(this.getEQMHandle());
        else if("Material_Storage" == tableName)
            return new Material_Storage(this.getEQMHandle());
        else if("Mb_Material_Po_Record" == tableName)
            return new Mb_Material_Po_Record(this.getEQMHandle());
        else if("Mb_Material_Po" == tableName)
            return new Mb_Material_Po(this.getEQMHandle());
        else if("Other_Record" == tableName)
            return new Other_Record(this.getEQMHandle());
        else if("Other_Storage" == tableName)
            return new Other_Storage(this.getEQMHandle());
        else if("Over_Time_Record" == tableName)
            return new Over_Time_Record(this.getEQMHandle());
        else if("Process_Control_Record" == tableName)
            return new Process_Control_Record(this.getEQMHandle());
        else if("Process_Info" == tableName)
            return new Process_Info(this.getEQMHandle());
        else if("Product_Info" == tableName)
            return new Product_Info(this.getEQMHandle());
        else if("Product_Order_Record" == tableName)
            return new Product_Order_Record(this.getEQMHandle());
        else if("Product_Order" == tableName)
            return new Product_Order(this.getEQMHandle());
        else if("Product_Storage" == tableName)
            return new Product_Storage(this.getEQMHandle());
        else if("Product_Type" == tableName)
            return new Product_Type(this.getEQMHandle());
        else if("Semi_Product_Storage" == tableName)
            return new Semi_Product_Storage(this.getEQMHandle());
        else if("Shipping_No" == tableName)
            return new Shipping_No(this.getEQMHandle());
        else if("Shipping_Record" == tableName)
            return new Shipping_Record(this.getEQMHandle());
        else if("Storeroom_Name" == tableName)
            return new Storeroom_Name(this.getEQMHandle());
        else if("Title_Info" == tableName)
            return new Title_Info(this.getEQMHandle());
        else if("Trans_Barcode_Record" == tableName)
            return new Trans_Barcode_Record(this.getEQMHandle());
        else if("User_Info" == tableName)
            return new User_Info(this.getEQMHandle());
        else if("User_Permission" == tableName)
            return new User_Permission(this.getEQMHandle());
        else if("Vendor_Info" == tableName)
            return new Vendor_Info(this.getEQMHandle());
        else if("Work_Group_Info" == tableName)
            return new Work_Group_Info(this.getEQMHandle());
        return null;
    }
}
