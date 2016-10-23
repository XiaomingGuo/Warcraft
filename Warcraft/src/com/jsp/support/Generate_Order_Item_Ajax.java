package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;

public class Generate_Order_Item_Ajax extends PageParentClass
{
    public boolean NewARecordInCustomerPoRecord(String barCode, String poName, String deliveryDate, String qty, String vendor, String percent)
    {
        if (CheckParamValidityEqualsLength(barCode, 8)&&CheckParamValidityMoreThanLength(poName, 6)&&CheckParamValidityEqualsLength(deliveryDate, 8)&&
                CheckParamValidityMoreThanValue(qty, 0)&&CheckParamValidityMoreThanLength(vendor, 1)&&CheckParamValidityMoreThanLength(percent, 0))
        {
        	DatabaseStore hCPRHandle = new DatabaseStore("Customer_Po_Record");
            hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barCode, poName));
            if(hCPRHandle.getTableInstance().RecordDBCount() <= 0)
            {
                ((Customer_Po_Record)hCPRHandle.getTableInstance()).AddARecord(barCode, poName, deliveryDate, qty, vendor, percent);
                return true;
            }
        }
        return false;
    }
    
    public int GetQtyByBarcodeAndPOName(String strBarcode, String appPOName, String getKeyValue)
    {
        int rtnRst = 0;
    	DatabaseStore hCPRHandle = new DatabaseStore("Product_Order_Record");
    	hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(strBarcode, appPOName));
        
        if (hCPRHandle.getTableInstance().RecordDBCount() > 0)
        {
            List<String> po_Qty_List = hCPRHandle.getDBRecordList(getKeyValue);
            for (int i = 0; i < po_Qty_List.size(); i++)
            {
                rtnRst += Integer.parseInt(po_Qty_List.get(i));
            }
        }
        return rtnRst;
    }
    
    public int GetUncompleteOrderRecord(String barCode)
    {
        int rtnRst = 0;
    	DatabaseStore hCPRHandle = new DatabaseStore("Product_Order_Record");
    	hCPRHandle.QueryRecordByFilterKeyListAndLessThanKeyValue(Arrays.asList("Bar_Code"), Arrays.asList(barCode), "status", "5");
        if (hCPRHandle.getTableInstance().RecordDBCount() > 0)
        {
            List<String> po_Qty_List = hCPRHandle.getDBRecordList("QTY");
            List<String> po_Oqc_List = hCPRHandle.getDBRecordList("OQC_QTY");
            for (int i = 0; i < po_Qty_List.size(); i++)
            {
                rtnRst += Integer.parseInt(po_Qty_List.get(i))-Integer.parseInt(po_Oqc_List.get(i));
            }
        }
        return rtnRst;
    }
}
