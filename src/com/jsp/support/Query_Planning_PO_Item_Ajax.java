package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.*;

public class Query_Planning_PO_Item_Ajax extends PageParentClass
{
    String[] m_sqlKeyList = {"Bar_Code", "po_name", "date_of_delivery", "vendor_name",  "QTY"};
    public List<List<String>> GetAllStorageRecord(String po_name)
    {
        List<List<String>> rtnRst = GetStoragePlanningRecord(new Material_Storage(new EarthquakeManagement()), po_name);
        List<List<String>> tempSemiRecord = GetStoragePlanningRecord(new Semi_Product_Storage(new EarthquakeManagement()), po_name);
        if(!rtnRst.isEmpty() && !tempSemiRecord.isEmpty())
        {
            for(int idx=0; idx < m_sqlKeyList.length; idx++)
                rtnRst.get(idx).addAll(tempSemiRecord.get(idx));
        }
        else if(rtnRst.isEmpty()&&!tempSemiRecord.isEmpty())
            rtnRst = tempSemiRecord;
        return rtnRst;
    }
    
    private List<List<String>> GetStoragePlanningRecord(IStorageTableInterface hHandle, String po_name)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        ((DBTableParent)hHandle).QueryRecordByFilterKeyListGroupByList(Arrays.asList("po_name", "isEnsure"), Arrays.asList(po_name, "1"), Arrays.asList("Bar_Code"));
        if (hHandle.RecordDBCount() > 0)
        {
            for(int idx=0; idx < m_sqlKeyList.length; idx++)
            {
                if("date_of_delivery" == m_sqlKeyList[idx])
                    rtnRst.add(GetDeliveryDate(hHandle.getDBRecordList("Bar_Code"), po_name));
                else if("QTY" == m_sqlKeyList[idx])
                    rtnRst.add(GetRepertoryOfStorage(hHandle.getDBRecordList("Bar_Code"), po_name));
                else
                    rtnRst.add(hHandle.getDBRecordList(m_sqlKeyList[idx]));
            }
        }
        return rtnRst;
    }
    
    private List<String> GetRepertoryOfStorage(List<String> barcodeList, String po_name)
    {
        List<String> rtnRst = new ArrayList<String>();
        List<String> in_Qty_List = GetQTYOfStorage("IN_QTY", barcodeList, po_name);
        List<String> out_Qty_List = GetQTYOfStorage("OUT_QTY",  barcodeList, po_name);
        for (int idx = 0; idx < in_Qty_List.size(); idx++)
        {
            int in_qty = Integer.parseInt(in_Qty_List.get(idx));
            int out_qty = Integer.parseInt(out_Qty_List.get(idx));
            rtnRst.add(Integer.toString(in_qty - out_qty));
        }
        return rtnRst;
    }
    
    private List<String> GetQTYOfStorage(String getKeyWord, List<String> barcodeList, String po_name)
    {
        List<String> rtnRst = new ArrayList<String>();
        for (String barcode : barcodeList)
        {
            IStorageTableInterface hHandle = GenProcessStorageHandle(barcode);
            int curQty = hHandle.GetIntSumOfValue(getKeyWord, Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, po_name, "1"));
            rtnRst.add(Integer.toString(curQty));
        }
        return rtnRst;
    }

    private List<String> GetDeliveryDate(List<String> barcodeList, String po_name)
    {
        List<String> rtnRst = new ArrayList<String>();
        Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
        for (String barcode : barcodeList)
        {
            hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, po_name));;
            if(hCPRHandle.RecordDBCount() <= 0)
                hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(barcode, "Semi_Pro_Storage"), po_name));
            rtnRst.add(hCPRHandle.getDBRecordList("delivery_date").get(0));
        }
        return rtnRst;
    }
    
    public int GetOUT_QTYByBarCode(String barcode)
    {
        IStorageTableInterface hHandle = GenStorageHandle(barcode);
        return hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
    }
}
