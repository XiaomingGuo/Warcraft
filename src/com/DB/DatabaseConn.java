package com.DB;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.DB.core.DataBaseCore;

public class DatabaseConn extends DataBaseCore
{
	public List<String> GetProductInfo(String str)
	{
		//product_info Database query
		List<String> product_info = null;
		String sql = "select * from product_info where product_type='" + str +"'";
		if (QueryDataBase(sql))
		{
			product_info = GetAllStringValue("name");
		}
		return product_info;
	}
	

	public int GetRepertoryByBarCode(String barcode, String storage_name)
	{
		return GetIN_QTYByBarCode(barcode, storage_name) - GetOUT_QTYByBarCode(barcode, storage_name);
	}

	public String GetUsedBarcode(String barcode, String storage_name)
	{
		String rtnRst = barcode;
		if (Integer.parseInt(barcode) > 50000000 && Integer.parseInt(barcode) < 70000000)
		{
			if (storage_name.indexOf("material") >= 0)
			{
				rtnRst = (Integer.parseInt(barcode) >= 60000000)?Integer.toString(Integer.parseInt(barcode)-10000000):barcode;
			}
			else if(storage_name.indexOf("product") >= 0)
			{
				rtnRst = (Integer.parseInt(barcode) >= 60000000)?barcode:Integer.toString(Integer.parseInt(barcode)+10000000);
			}
		}
		return rtnRst;
	}
	
	public int GetIN_QTYByBarCode(String barcode, String storage_name)
	{
		int rtnRst = 0;
		String sql = "select IN_QTY from "+storage_name+" where Bar_Code='" + GetUsedBarcode(barcode, storage_name) +"'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				List<String> in_Qty_List = GetAllStringValue("IN_QTY");
				for (int i = 0; i < in_Qty_List.size(); i++)
				{
					rtnRst += Integer.parseInt(in_Qty_List.get(i));
				}
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public int GetOUT_QTYByBarCode(String barcode, String storage_name)
	{
		int rtnRst = 0;
		String sql = "select OUT_QTY from "+storage_name+" where Bar_Code='" + GetUsedBarcode(barcode, storage_name) +"'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				List<String> out_Qty_List = GetAllStringValue("OUT_QTY");
				for (int i = 0; i < out_Qty_List.size(); i++)
				{
					rtnRst += Integer.parseInt(out_Qty_List.get(i));
				}
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GetStoreroomByType(String proType)
	{
		String rtnRst = "";
		String sql = "select storeroom from product_type where name='" + proType +"'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				rtnRst = GetSingleString("storeroom");
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GetNameByBarcode(String barcode)
	{
		String rtnRst = "";
		String sql = "select name from product_info where Bar_Code='" + GetUsedBarcode(barcode, "product_storage") +"'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				rtnRst = GetSingleString("name");
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public double GetPerWeigthByBarcode(String barcode)
	{
		double rtnRst = 0.0;
		String sql = "select weight from product_info where Bar_Code='" + GetUsedBarcode(barcode, "product_storage") +"'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				rtnRst = GetSingleDouble("weight");
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GetMergeMark(String id)
	{
		String rtnRst = "";
		String sql = "select Merge_Mark from material_record where id='" + id +"'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				rtnRst = GetSingleString("Merge_Mark");
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public double GetPrice_Pre_Unit(String bar_code, String Batch_Lot)
	{
		double rtnRst = 0.0;
		String sql = "select Price_Per_Unit from material_storage where Bar_Code='" + GetUsedBarcode(bar_code, "material_storage") +"' and Batch_Lot='" + Batch_Lot + "'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				rtnRst = GetSingleDouble("Price_Per_Unit");
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public double GetProductRepertoryPrice(String barcode, String storage)
	{
		double rtnRst = 0.0;
		String sql = "select * from " + storage + " where Bar_Code='" + GetUsedBarcode(barcode, storage) +"' and IN_QTY != OUT_QTY";
		String[] keyWord = {"IN_QTY", "OUT_QTY", "Price_Per_Unit"};
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				List<List<String>> Qty_List = GetAllDBColumnsByList(keyWord);
				for (int i = 0; i < Qty_List.get(0).size(); i++)
				{
					rtnRst += (Integer.parseInt(Qty_List.get(0).get(i)) - Integer.parseInt(Qty_List.get(1).get(i))) * Float.parseFloat(Qty_List.get(2).get(i));
				}
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public boolean MoveToExhaustedTable(String barcode, String batchLot, String fromTable, String toTable)
	{
		boolean rtnRst = true;
		String sql = null;
		sql = "INSERT INTO " + toTable + " SELECT * FROM " + fromTable + " WHERE Bar_Code='" + GetUsedBarcode(barcode, toTable) +"' AND Batch_Lot='" + batchLot +"'";
		rtnRst &= execUpate(sql);
		sql = "DELETE FROM " + fromTable + " WHERE Bar_Code='" + GetUsedBarcode(barcode, fromTable) +"' AND Batch_Lot='" + batchLot +"'";
		rtnRst &= execUpate(sql);
		return rtnRst;
	}

	public int TransferMaterialToProduct(String barcode, String batchLot, String OrderName, int used_count)
	{
		int rtnRst = 0;
		String sql = "select * from product_storage where Bar_Code='" + GetUsedBarcode(barcode, "product_storage") + "' and Batch_Lot='" + batchLot + "' and Order_Name='" + OrderName + "'";
		if (QueryDataBase(sql) && GetRecordCount() > 0)
		{
			int storageQTY = GetSingleInt("IN_QTY");
			sql= "UPDATE product_storage SET IN_QTY='" + Integer.toString(storageQTY+used_count) + "' WHERE Bar_Code='" + GetUsedBarcode(barcode, "product_storage") + "' and Batch_Lot='" + batchLot + "' and Order_Name='" + OrderName + "'";
		}
		else
		{
			CloseDatabase();
			sql = "INSERT INTO product_storage (Bar_Code, Batch_Lot, Order_Name, IN_QTY, Price_Per_Unit, Total_Price) VALUES ('" + GetUsedBarcode(barcode, "product_storage") + "', '" + batchLot + "', '" + OrderName + "', '" + Integer.toString(used_count) + "', '0', '0')";
		}
		execUpate(sql);
		return rtnRst;
	}
	
	public String GetTypeByBarcode(String barcode)
	{
		String rtnRst = "";
		String sql = "select * from product_info where Bar_Code='" + GetUsedBarcode(barcode, "product_storage") + "'";
		if (QueryDataBase(sql) && GetRecordCount() > 0)
		{
			rtnRst = GetSingleString("product_type");
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GetBarcodeByName(String pro_name, String flag)
	{
		String rtnRst = "";
		String sql = "select * from product_info where name='" + pro_name + "'";
		if (QueryDataBase(sql) && GetRecordCount() > 0)
		{
			List<String> tempList = GetAllStringValue("Bar_Code");
			for (int idx = 0; idx < tempList.size(); idx++)
			{
				String curBarcode = tempList.get(idx);
				if (flag.indexOf("Mat") >= 0)
				{
					rtnRst = Integer.parseInt(curBarcode) > 60000000&&Integer.parseInt(curBarcode) < 70000000?Integer.toString(Integer.parseInt(curBarcode)-10000000):curBarcode;
					break;
				}
				else if(flag.indexOf("Pro") >= 0)
				{
					rtnRst = Integer.parseInt(curBarcode) > 50000000&&Integer.parseInt(curBarcode) < 60000000?Integer.toString(Integer.parseInt(curBarcode)+10000000):curBarcode;
					break;
				}
				else
				{
					rtnRst = curBarcode;
					continue;	
				}
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public int GetMBMaterialPOQTY(String barcode, String po_name)
	{
		int rtnRst = 0;
		String sql = "select PO_QTY from mb_material_po where Bar_Code='" + GetUsedBarcode(barcode, "mb_material_po") +"' and po_name='" + po_name + "'";
		if (QueryDataBase(sql)&&GetRecordCount() > 0)
		{
			List<String> po_Qty_List = GetAllStringValue("PO_QTY");
			for (int i = 0; i < po_Qty_List.size(); i++)
			{
				rtnRst += Integer.parseInt(po_Qty_List.get(i));
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public int GetDiscardMaterialQTY(String barcode, String order_name)
	{
		int rtnRst = 0;
		String sql = "select QTY from discard_material_record where Bar_Code='" + GetUsedBarcode(barcode, "discard_material_record") +"' and Order_Name='" + order_name + "'";
		if (QueryDataBase(sql)&&GetRecordCount() > 0)
		{
			List<String> Qty_List = GetAllStringValue("QTY");
			for (int i = 0; i < Qty_List.size(); i++)
			{
				rtnRst += Integer.parseInt(Qty_List.get(i));
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GetPOInfo(String barcode, String po_name, String keyWord)
	{
		String rtnRst = "";
		String sql = "select * from customer_po_record where Bar_Code='" + GetUsedBarcode(barcode, "customer_po_record") + "' and po_name='" + po_name + "'";
		if (QueryDataBase(sql) && GetRecordCount() > 0)
		{
			rtnRst = GetSingleString(keyWord);
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public int GetInProcessQty(String barcode, String po_name)
	{
		int rtnRst = 0;
		String sql = "select QTY from product_order_record where Bar_Code='" + GetUsedBarcode(barcode, "product_order_record") +"' and po_name='" + po_name + "'";
		if (QueryDataBase(sql)&&GetRecordCount() > 0)
		{
			List<String> po_Qty_List = GetAllStringValue("QTY");
			for (int i = 0; i < po_Qty_List.size(); i++)
			{
				rtnRst += Integer.parseInt(po_Qty_List.get(i));
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public int GetCompleteQty(String barcode, String po_name)
	{
		int rtnRst = 0;
		String sql = "select QTY from product_order_record where Bar_Code='" + GetUsedBarcode(barcode, "product_order_record") +"' and po_name='" + po_name + "'";
		if (QueryDataBase(sql)&&GetRecordCount() > 0)
		{
			List<String> po_Qty_List = GetAllStringValue("OQC_QTY");
			for (int i = 0; i < po_Qty_List.size(); i++)
			{
				rtnRst += Integer.parseInt(po_Qty_List.get(i));
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GetPONameFromOrderRecord(String barcode, String orderName)
	{
		String rtnRst = "";
		String sql = "select po_name from product_order_record where Bar_Code='" + GetUsedBarcode(barcode, "product_order_record") +"' and Order_Name='" + orderName + "'";
		if (QueryDataBase(sql)&&GetRecordCount() > 0)
		{
			rtnRst = GetSingleString("po_name");
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GetDescByBarcode(String barcode)
	{
		String rtnRst = "";
		String sql = "select description from product_info where Bar_Code='" + GetUsedBarcode(barcode, "other_storage") +"'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				rtnRst = GetSingleString("description");
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public int GetShipQTYByBarcode(String po_name, String barcode, String Delivery_Date)
	{
		int rtnRst = 0;
		String sql = "select ship_QTY from shipping_record where customer_po='" + po_name + "' and Bar_Code='" + GetUsedBarcode(barcode, "shipping_record") +"' and print_mark='" + Delivery_Date + "'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				List<String> in_Qty_List = GetAllStringValue("ship_QTY");
				for (int i = 0; i < in_Qty_List.size(); i++)
				{
					rtnRst += Integer.parseInt(in_Qty_List.get(i));
				}
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GetShipNOByPrintMark(String po_name, String PrintMark)
	{
		String rtnRst = "";
		String sql = "select shipping_no from shipping_no where customer_po='" + po_name + "' and print_mark='" + PrintMark +"'";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				rtnRst = GetSingleString("shipping_no");
			}
			else
			{
				CloseDatabase();
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public String GenOrderName(String OrderHeader)
	{
		String orderName = "";
		int iCount = 1;
		do
		{
			orderName = OrderHeader + "_" + Integer.toString(iCount);
			String sql = "select * from product_order where Order_Name='" + orderName + "'";
			if (QueryDataBase(sql)&&GetRecordCount() <= 0)
			{
				CloseDatabase();
				break;
			}
			CloseDatabase();
			iCount += 1;
		}while(true);
		return orderName;
	}

	public List<List<String>> GetCustomerPORecord(String PO_Name, String[] KeyList)
	{
		List<List<String>> rtnRst = null;
		String sql = "select * from customer_po_record where po_name='" + PO_Name + "' order by id asc";
		if (QueryDataBase(sql)&&GetRecordCount() > 0)
		{
			rtnRst = GetAllDBColumnsByList(KeyList);
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public List<List<String>> GetProductOrderRecord(String id, String[] KeyList)
	{
		List<List<String>> rtnRst = null;
		String sql = "select * from product_order_record where id='" + id + "'";
		if (QueryDataBase(sql) && GetRecordCount() > 0)
		{
			rtnRst = GetAllDBColumnsByList(KeyList);
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public List<List<String>> GetMaterialStorage(String barcode, String[] KeyList)
	{
		List<List<String>> rtnRst = null;
		String sql = "select * from material_storage where Bar_Code='" + GetUsedBarcode(barcode, "material_storage") + "'";
		if (QueryDataBase(sql) && GetRecordCount() > 0)
		{
			rtnRst = GetAllDBColumnsByList(KeyList);
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}

	public boolean GetMaterialStorage(int count, String barcode)
	{
		String[] materialKey = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
		String usedBarcode = GetUsedBarcode(barcode, "material_storage");
		List<List<String>> material_info_List = GetMaterialStorage(usedBarcode, materialKey);
		if (material_info_List != null)
		{
			int used_count = count;
			for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
			{
				String batchLot =  material_info_List.get(0).get(iCol);
				int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
				int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
				int recordCount = sql_in_count - sql_out_count;
			
				String sql = "";
				if (recordCount >= used_count)
				{
					sql= "UPDATE material_storage SET OUT_QTY='" + Integer.toString(sql_out_count+used_count) + "' WHERE Bar_Code='" + usedBarcode +"' and Batch_Lot='" + batchLot +"'";
					execUpate(sql);
					if (recordCount == used_count)
					{
						MoveToExhaustedTable(usedBarcode, batchLot, "material_storage", "exhausted_material");
					}
					break;
				}
				else
				{
					sql= "UPDATE material_storage SET OUT_QTY='" + Integer.toString(sql_in_count) + "' WHERE Bar_Code='" + usedBarcode +"' and Batch_Lot='" + batchLot +"'";
					execUpate(sql);
					if (!MoveToExhaustedTable(usedBarcode, batchLot, "material_storage", "exhausted_material"))
						continue;
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
	
	public int GetUncompleteOrderRecord(String barcode)
	{
		int rtnRst = 0;
		String sql = "select * from product_order_record where Bar_Code='" + GetUsedBarcode(barcode, "product_order_record") +"' and status<5";
		if (QueryDataBase(sql)&&GetRecordCount() > 0)
		{
			String[] keyList = {"QTY", "OQC_QTY"};
			List<List<String>> po_Qty_List = GetAllDBColumnsByList(keyList);
			for (int i = 0; i < po_Qty_List.get(0).size(); i++)
			{
				rtnRst += Integer.parseInt(po_Qty_List.get(0).get(i))-Integer.parseInt(po_Qty_List.get(1).get(i));
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}
	
	public int GetTotalOrderQTY(String barcode, String po_name)
	{
		int rtnRst = 0;
		String sql = "select * from customer_po_record where Bar_Code='" + GetUsedBarcode(barcode, "customer_po_record") +"' and po_name='" + po_name + "'";
		if (QueryDataBase(sql)&&GetRecordCount() > 0)
		{
			String[] keyList = {"QTY", "percent"};
			List<List<String>> po_Qty_List = GetAllDBColumnsByList(keyList);
			for (int i = 0; i < po_Qty_List.get(0).size(); i++)
			{
				rtnRst += Integer.parseInt(po_Qty_List.get(0).get(i))*(100+Integer.parseInt(po_Qty_List.get(1).get(i)))/100;
			}
		}
		else
		{
			CloseDatabase();
		}
		return rtnRst;
	}

}
