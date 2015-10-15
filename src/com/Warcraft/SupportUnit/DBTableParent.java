package com.Warcraft.SupportUnit;

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
}
