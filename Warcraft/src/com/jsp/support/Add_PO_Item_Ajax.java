package com.jsp.support;

import java.util.Arrays;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;

public class Add_PO_Item_Ajax extends PageParentClass
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
}
