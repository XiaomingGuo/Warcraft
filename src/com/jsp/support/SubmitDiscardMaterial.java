package com.jsp.support;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;

public class SubmitDiscardMaterial
{
	public int CheckPOStatus(String appBarcode, String appOrderName)
	{
		Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Order_Name"), Arrays.asList(appBarcode, appOrderName));
		String POName = hPORHandle.getDBRecordList("po_name").get(0);
		return hPORHandle.GetIntSumOfValue("QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(appBarcode, POName)) - 
				hPORHandle.GetIntSumOfValue("completeQTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(appBarcode, POName));
	}
	
	public boolean ExcuteDiscardMaterial(String appBarcode, String appOrderName, String appProduct_QTY, String appreason, int used_count)
	{
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
		Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
		int repertory_count = hMSHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code"), Arrays.asList(appBarcode)) - 
							hMSHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(appBarcode));
		if (repertory_count >= used_count)
		{
			hMSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hMSHandle.GetUsedBarcode(appBarcode, "material_storage")));
			List<List<String>> material_info_List = new ArrayList<List<String>>();
			for(int idx=0; idx < keyArray.length; idx++)
			{
				material_info_List.add(hMSHandle.getDBRecordList(keyArray[idx]));
			}
			
			for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
			{
				String batchLot =  material_info_List.get(0).get(iCol);
				int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
				int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
				int recordCount = sql_in_count - sql_out_count;
				Discard_Material_Record hDMRHandle = new Discard_Material_Record(new EarthquakeManagement());
				String strWriteBarCode = hDMRHandle.GetUsedBarcode(appBarcode, "discard_material_record");
				if (recordCount >= used_count)
				{
					hDMRHandle.AddARecord(appOrderName, strWriteBarCode, batchLot, appProduct_QTY, appreason);
					break;
				}
				else
				{
					hDMRHandle.AddARecord(appOrderName, strWriteBarCode, batchLot, Integer.toString(recordCount), appreason);
				}
			}
		}
		else
		{
			return false;
		}
		return true;
	}
}
