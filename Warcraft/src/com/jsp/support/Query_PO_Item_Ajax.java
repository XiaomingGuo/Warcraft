package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.*;

public class Query_PO_Item_Ajax extends PageParentClass
{
    public String CheckCustomerPoStatus(String po_name, String status)//GetCustomerPoStatus
    {
        String rtnRst = status;
        if(status != null)
        {
        	DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
            hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(po_name));
            if (hCPHandle.getTableInstance().RecordDBCount() > 0)
            {
                String tempPo = hCPHandle.getDBRecordList("status").get(0);
                if (Integer.parseInt(tempPo) > Integer.parseInt(status))
                    rtnRst = "error:该PO单已经存在!";
                else
                    rtnRst = tempPo;
            }
        }
        return rtnRst + "$";
    }
    
    public List<List<String>> GetCustomerPoRecordList(String po_name)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
        hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(po_name));
        if (hCPRHandle.getTableInstance().RecordDBCount() > 0)
        {
            String[] sqlKeyList = {"id", "vendor", "Bar_Code", "po_name", "delivery_date", "QTY", "percent", "isEnsure", "create_date"};
            for(int idx=0; idx < sqlKeyList.length; idx++)
            {
                rtnRst.add(hCPRHandle.getDBRecordList(sqlKeyList[idx]));
            }
        }
        return rtnRst;
    }
    
    public int GetSurplusPurchaseQty(String strBarcode, String po_name)
    {
        int rtnRst = 0;
        DBTableParent hMMPHandle = new DatabaseStore("Mb_Material_Po");
        rtnRst = hMMPHandle.GetIntSumOfValue("PO_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(strBarcode, po_name));
        rtnRst -= GetHasFinishPurchaseNum(strBarcode, po_name);
        return rtnRst;
    }
    
    public int GetInProcessQty(String barcode, String po_name)
    {
    	DBTableParent hHandle = GenStorageHandle(barcode);
        return ((DBTableParent)hHandle).GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, po_name, "1"));
    }
}
