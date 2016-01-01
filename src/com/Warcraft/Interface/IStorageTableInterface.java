package com.Warcraft.Interface;

import java.util.List;

public interface IStorageTableInterface
{
	public String GetUsedBarcode(String strBarcode, String storageName);
	public int GetIntSumOfValue(String getValue, List<String> keyList, List<String> valueList);
	public double GetDblSumOfValue(String getValue, String keyword, String keyValue);
	public int RecordDBCount();
	public List<String> getDBRecordList(String keyWord);
	public void QueryRecordByFilterKeyList(List<String> keyList, List<String> valueList);
	public void QueryRecordByFilterKeyListAndBetweenDateSpan(List<String> keyList, List<String> valueList, String beginDate, String endDate);
	public void AddARecord(String appBarcode, String batch_lot,
			String appProductQTY, String appPriceUnit, String appTotalPrice,
			String appSupplier_name, String appInStoreDate);
	public void AddAExRecord(String id, String appBarcode, String batch_lot,
			String appProductQTY, String outQty, String appPriceUnit, String appTotalPrice,
			String appOrderName, String appInStoreDate, String isEnsure, String createDate);
	public void UpdateRecordByFilterKeyList(String setKeyWord, String setValue, List<String> keyList, List<String> valueList);
}
