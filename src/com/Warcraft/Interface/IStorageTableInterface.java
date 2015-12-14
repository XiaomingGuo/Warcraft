package com.Warcraft.Interface;

public interface IStorageTableInterface
{
	public int RecordDBCount();
	public void QueryRecordByBarcodeAndBatchLot(String strBarcode, String batch_lot);
	public void AddARecord(String appBarcode, String batch_lot, String appProductQTY, String appPriceUnit, String appTotalPrice, String appSupplier_name, String appInStoreDate);
}
