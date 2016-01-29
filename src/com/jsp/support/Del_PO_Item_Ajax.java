package com.jsp.support;

import java.util.Arrays;

import com.Warcraft.Interface.*;

public class Del_PO_Item_Ajax extends PageParentClass
{
	public void RollBackTransferStorage(String barcode, String POName)
	{
		RollbackAStorage(barcode, POName, "Product_Storage");
		RollbackAStorage(barcode, POName, "Semi_Storage");
		RollbackAStorage(barcode, POName, "Material_Storage");
	}

	private void RollbackAStorage(String barcode, String POName, String storeName)
	{

	}
}
