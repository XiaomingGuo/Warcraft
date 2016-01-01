package com.Warcraft.SupportUnit;

import java.util.Arrays;
import java.util.List;

import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public abstract class DBTableParent
{
	private IEQManagement hEQMHandle = null;

	public DBTableParent(IEQManagement hEQMHandle)
	{
		this.setEQMHandle(hEQMHandle);
		this.getEQMHandle().setTableHandle((ITableInterface)this);
	}

	public IEQManagement getEQMHandle()
	{
		return hEQMHandle;
	}

	public void setEQMHandle(IEQManagement hEQMHandle)
	{
		this.hEQMHandle = hEQMHandle;
	}
	
	public String GetUsedBarcode(String barcode, String storage_name)
	{
		String rtnRst = barcode;
		if (Integer.parseInt(barcode) > 50000000 && Integer.parseInt(barcode) < 70000000)
		{
			if (storage_name.toLowerCase().indexOf("material") >= 0)
			{
				rtnRst = (Integer.parseInt(barcode) >= 60000000)?Integer.toString(Integer.parseInt(barcode)-10000000):barcode;
			}
			else if(storage_name.toLowerCase().indexOf("product") >= 0)
			{
				rtnRst = (Integer.parseInt(barcode) >= 60000000)?barcode:Integer.toString(Integer.parseInt(barcode)+10000000);
			}
		}
		return rtnRst;
	}
	
	public double GetDblSumOfValue(String storage_name, String getValue, String keyword, String keyValue)
	{
		double rtnRst = 0.0;
		String hql = String.format("from %s st where st.%s='%s'", storage_name, ((ITableInterface)this).GetDatabaseKeyWord(keyword), keyValue);
		getEQMHandle().EQQuery(hql);
		if (((ITableInterface)this).RecordDBCount() > 0)
		{
			List<String> in_Qty_List = ((ITableInterface)this).getDBRecordList(getValue);
			for (int i = 0; i < in_Qty_List.size(); i++)
			{
				rtnRst += Double.parseDouble(in_Qty_List.get(i));
			}
		}
		return rtnRst;
	}
	
	public int GetIntSumOfValue(String getValue, List<String> keyList, List<String> valList)
	{
		int rtnRst = 0;
		String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valList);
		getEQMHandle().EQQuery(hql);
		if (((ITableInterface)this).RecordDBCount() > 0)
		{
			List<String> in_Qty_List = ((ITableInterface)this).getDBRecordList(getValue);
			for (int i = 0; i < in_Qty_List.size(); i++)
			{
				rtnRst += Integer.parseInt(in_Qty_List.get(i));
			}
		}
		return rtnRst;
	}

	public double GetDblPriceOfStorage(String storage_name, String getValIN, String getValOUT, String getValPerPrice, String keyword, String keyValue)
	{
		double rtnRst = 0.0;
		String hql = String.format("from %s st where st.%s='%s'", storage_name, ((ITableInterface)this).GetDatabaseKeyWord(keyword), keyValue);
		getEQMHandle().EQQuery(hql);
		if (((ITableInterface)this).RecordDBCount() > 0)
		{
			List<String> in_Qty_List = ((ITableInterface)this).getDBRecordList(getValIN);
			List<String> out_Qty_List = ((ITableInterface)this).getDBRecordList(getValOUT);
			List<String> perPrice = ((ITableInterface)this).getDBRecordList(getValPerPrice);
			for (int i = 0; i < in_Qty_List.size(); i++)
			{
				int repertory = Integer.parseInt(in_Qty_List.get(i)) - Integer.parseInt(out_Qty_List.get(i));
				rtnRst += repertory*Double.parseDouble(perPrice.get(i));
			}
		}
		return rtnRst;
	}
	
	public String GenerateWhereString(List<String> keyList, List<String> valueList)
	{
		String rtnRst = "";
		for(int idx=0; idx<keyList.size()-1; idx++)
		{
			rtnRst += String.format(" tbn.%s='%s' and ", ((ITableInterface)this).GetDatabaseKeyWord(keyList.get(idx)), valueList.get(idx));
		}
		rtnRst+= String.format(" tbn.%s='%s'", ((ITableInterface)this).GetDatabaseKeyWord(keyList.get(keyList.size()-1)), valueList.get(valueList.size()-1));
		return rtnRst;
	}
}
