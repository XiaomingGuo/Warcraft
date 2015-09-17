package com.DB.operation;

import com.Warcraft.Interface.IDBExecute;

public class Discard_Material_Record implements IDBExecute
{
	private String[] tableKeyList = {"id", "Order_Name", "Bar_Code", "Batch_Lot", "QTY", "reason"};
	private final String tableName = "discard_material_record";
	
	public Discard_Material_Record()
	{
		
	}
	
	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public String[] getTableKeyList()
	{
		return tableKeyList;
	}
}
