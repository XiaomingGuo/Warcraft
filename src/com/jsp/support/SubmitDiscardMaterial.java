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
	
	public boolean ExcuteDiscardMaterial(String appBarcode, String appOrderName, String appOperator, String appProduct_QTY, String appreason, int used_count)
	{
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
		IStorageTableInterface hHandle = GenStorageHandle(appBarcode);
		int repertory_count = ((DBTableParent)hHandle).GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(appBarcode));
		if (repertory_count >= used_count)
		{
			hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hHandle.GetUsedBarcode(appBarcode, "Semi")));
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
				if (recordCount > used_count)
				{
					hDMRHandle.AddARecord(appOrderName, strWriteBarCode, batchLot, appOperator, appProduct_QTY, appreason);
					((DBTableParent)hHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(recordCount-used_count), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(strWriteBarCode, batchLot));
					break;
				}
				else
				{
					hDMRHandle.AddARecord(appOrderName, strWriteBarCode, batchLot, appOperator, Integer.toString(recordCount), appreason);
					((DBTableParent)hHandle).DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(strWriteBarCode, batchLot));
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
