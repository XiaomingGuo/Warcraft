package com.Warcraft.SupportUnit;
import com.DB.DatabaseConn;;

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
}
