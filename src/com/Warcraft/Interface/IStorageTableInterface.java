package com.Warcraft.Interface;

import java.util.List;

public interface IStorageTableInterface
{
	public int RecordDBCount();
	public List<String> getDBRecordList(String keyWord);
	public void QueryRecordByFilterKeyList(List<String> keyList, List<String> valueList);
	public void QueryRecordByFilterKeyListAndBetweenDateSpan(List<String> keyList, List<String> valueList, String beginDate, String endDate);
	public void AddARecord(String appBarcode, String batch_lot, String appProductQTY, String appPriceUnit, String appTotalPrice, String appSupplier_name, String appInStoreDate);
}
