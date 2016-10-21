package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.*;

public class Planned_Production extends PageParentClass
{
    public List<String> GetPlannedPOList()
    {
    	DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
        hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("status"), Arrays.asList("0"));
        return hCPHandle.getDBRecordList("po_name");
    }
}
