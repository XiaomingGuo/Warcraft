package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.*;

public class Planned_Production extends PageParentClass
{
    public List<String> GetPlannedPOList()
    {
        Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
        hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("status"), Arrays.asList("0"));
        return hCPHandle.getDBRecordList("po_name");
    }
}
