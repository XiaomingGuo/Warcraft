package com.jsp.support;

import java.util.Arrays;

import com.DB.factory.DatabaseStore;

public class OtherInformation extends PageParentClass
{
    public boolean UpdateOtherInformationRecord(String proName, String barCode, String description)
    {
        if (CheckParamValidityEqualsLength(barCode, 8))
        {
            DatabaseStore hPIHandle = new DatabaseStore("Product_Info");
            hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(proName));
            if(hPIHandle.getTableInstance().RecordDBCount() <= 0)
            {
                hPIHandle.UpdateRecordByKeyList("name", proName, Arrays.asList("Bar_Code"), Arrays.asList(barCode));
                hPIHandle.UpdateRecordByKeyList("description", description, Arrays.asList("Bar_Code"), Arrays.asList(barCode));
            }
        }
        return false;
    }
}
