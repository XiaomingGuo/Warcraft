package com.Warcraft.Interface;

import java.util.List;

public interface IDiscard_Material_Record
{
	public void addANewRecord(int id, String strOrderName, String strBarcode, String strBatchLot, int iQty, String strReason);
	//public void addANewRecord(String strOrderName, String strBarcode, String strBatchLot, int iQty, String strReason);
	public void DeleteRecord(int id);
	public void getRecord();
	public void updateRecord(int id);
	public List<String> QueryARecord(String hql);
}
