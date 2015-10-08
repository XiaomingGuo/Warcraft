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

}
