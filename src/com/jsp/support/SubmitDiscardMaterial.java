package com.jsp.support;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class SubmitDiscardMaterial extends PageParentClass
{
	public int CheckPOStatus(String appBarcode, String appOrderName)
	{
		IStorageTableInterface hHandle = GenStorageHandle(appBarcode);
		return hHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(appBarcode, appOrderName));
	}
	
	public boolean ExcuteDiscardMaterial(String appBarcode, String appPOName, String appOperator, String appreason, int used_count)
	{
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
		IStorageTableInterface hHandle = GenStorageHandle(appBarcode);
		int repertory_count = ((DBTableParent)hHandle).GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(appBarcode, appPOName));
		if (repertory_count >= used_count)
		{
			hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hHandle.GetUsedBarcode(appBarcode, "Semi"), appPOName));
			List<List<String>> material_info_List = new ArrayList<List<String>>();
			for(int idx=0; idx < keyArray.length; idx++)
			{
				material_info_List.add(hHandle.getDBRecordList(keyArray[idx]));
			}
			
			for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
			{
				String batchLot =  material_info_List.get(0).get(iCol);
				int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
				int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
				int recordCount = sql_in_count - sql_out_count;
				Discard_Material_Record hDMRHandle = new Discard_Material_Record(new EarthquakeManagement());
				String strWriteBarCode = hDMRHandle.GetUsedBarcode(appBarcode, "Semi");
				if (recordCount >= used_count)
				{
					hDMRHandle.AddARecord(appPOName, strWriteBarCode, batchLot, appOperator, Integer.toString(used_count), appreason);
					((DBTableParent)hHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(sql_in_count-used_count), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(strWriteBarCode, batchLot));
					CheckMoveToExhaustedTable(appBarcode, batchLot);
					break;
				}
				else
				{
					hDMRHandle.AddARecord(appPOName, strWriteBarCode, batchLot, appOperator, Integer.toString(recordCount), appreason);
					if(sql_out_count == 0)
						((DBTableParent)hHandle).DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(strWriteBarCode, batchLot));
					else
					{
						((DBTableParent)hHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(sql_out_count), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(strWriteBarCode, batchLot));
						CheckMoveToExhaustedTable(appBarcode, batchLot);
					}
					used_count -= recordCount;
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
