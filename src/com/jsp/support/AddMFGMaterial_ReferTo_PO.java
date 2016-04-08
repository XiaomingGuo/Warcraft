package com.jsp.support;

import java.util.Arrays;
import java.util.List;
import com.DB.operation.*;

public class AddMFGMaterial_ReferTo_PO extends PageParentClass
{
	public List<String> GetAllPOListNotFinishPurchange()
	{
		List<String> rtnRst = null;
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.QueryRecordGroupByList(Arrays.asList("po_name"));
		rtnRst = hMMPHandle.getDBRecordList("po_name");
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		for (int index = 0; index < rtnRst.size(); index++)
		{
			hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name", "status"), Arrays.asList(rtnRst.get(index), "5"));
			if(hCPHandle.RecordDBCount() > 0)
				rtnRst.remove(rtnRst.get(index));
		}
		return rtnRst;
	}
}
