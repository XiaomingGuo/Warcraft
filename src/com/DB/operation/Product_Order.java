package com.DB.operation;

import com.Warcraft.Interface.IDBExecute;

public class Product_Order implements IDBExecute
{
	private String[] tableKeyList = {"id", "Order_Name", "status"};
	private final String tableName = "product_order";
	
	public Product_Order()
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
