package com.Warcraft.SupportUnit;
import java.util.List;

import com.DB.core.DatabaseConn;

public class SubmitDiscardMaterial
{
	DatabaseConn hDBHandle;
	public SubmitDiscardMaterial()
	{
		hDBHandle = new DatabaseConn();
	}
	
	public int CheckPOStatus(String appBarcode, String appOrderName)
	{
		String POName = hDBHandle.GetPONameFromOrderRecord(appBarcode, appOrderName);
		return hDBHandle.GetInProcessQty(appBarcode, POName) - hDBHandle.GetCompleteQty(appBarcode, POName);
	}
	
	public boolean ExcuteDiscardMaterial(String appBarcode, String appOrderName, String appProduct_QTY, String appreason, int used_count)
	{
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
		int repertory_count = hDBHandle.GetRepertoryByBarCode(appBarcode, "material_storage");
		if (repertory_count >= used_count)
		{
			String sql = "select * from material_storage where Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, "material_storage") +"'";
			if (hDBHandle.QueryDataBase(sql))
			{
				List<List<String>> material_info_List = hDBHandle.GetAllDBColumnsByList(keyArray);
				for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
				{
					String batchLot =  material_info_List.get(0).get(iCol);
					int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
					int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
					int recordCount = sql_in_count - sql_out_count;
					if (recordCount >= used_count)
					{
						sql= "INSERT INTO discard_material_record (Order_Name, Bar_Code, Batch_Lot, QTY, reason) VALUE ('" + appOrderName + "', '" + hDBHandle.GetUsedBarcode(appBarcode, "discard_material_record") + "', '" + batchLot + "', '" + appProduct_QTY + "', '" + appreason +"')";
						hDBHandle.execUpate(sql);
						break;
					}
					else
					{
						sql= "INSERT INTO discard_material_record (Order_Name, Bar_Code, Batch_Lot, QTY, reason) VALUE ('" + appOrderName + "', '" + hDBHandle.GetUsedBarcode(appBarcode, "discard_material_record") + "', '" + batchLot + "', '"  + recordCount + "', '" + appreason +"')";
						hDBHandle.execUpate(sql);
					}
				}
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
		}
		else
		{
			return false;
		}
		return true;
	}
}
