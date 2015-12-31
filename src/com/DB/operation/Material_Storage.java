package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.MaterialStorage;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Material_Storage extends DBTableParent implements ITableInterface, IStorageTableInterface
{
	private List<MaterialStorage> resultList = null;
	private MaterialStorage aWriteRecord = null;
	
	public Material_Storage(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public int RecordDBCount()
	{
		int rtnRst = 0;
		if (resultList != null)
			rtnRst = resultList.size();
		return rtnRst;
	}

	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<MaterialStorage> it = resultList.iterator();
		while(it.hasNext())
		{
			MaterialStorage tempRecord = (MaterialStorage)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "Batch_Lot":
				rtnRst.add(tempRecord.getBatchLot());
				break;
			case "IN_QTY":
				rtnRst.add(tempRecord.getInQty().toString());
				break;
			case "OUT_QTY":
				rtnRst.add(tempRecord.getOutQty().toString());
				break;
			case "Price_Per_Unit":
				rtnRst.add(tempRecord.getPricePerUnit().toString());
				break;
			case "Total_Price":
				rtnRst.add(tempRecord.getTotalPrice().toString());
				break;
			case "vendor_name":
				rtnRst.add(tempRecord.getVendorName().toString());
				break;
			case "in_store_date":
				rtnRst.add(tempRecord.getInStoreDate());
				break;
			case "isEnsure":
				rtnRst.add(tempRecord.getIsEnsure().toString());
				break;
			case "create_date":
				rtnRst.add(tempRecord.getCreateDate().toString());
				break;
			default:
				break;
			}
		}
		return rtnRst;
	}

	@Override
	public void setResultList(Query query)
	{
		this.resultList = query.list();
	}
	
	@Override
	public Object getAWriteRecord()
	{
		return aWriteRecord;
	}

	public void GetRecordByPoName(String poName)
	{
		execQueryAsc("poName", poName, "id");
	}
	
	private void execQueryAsc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from MaterialStorage cpr where cpr.%s='%s' order by cpr.%s asc", GetDatabaseKeyWord(keyWord), value, GetDatabaseKeyWord(orderKey));
		getEQMHandle().EQQuery(hql);
	}
	
	/*
	private void execQueryDesc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from MaterialStorage cpr where cpr.%s='%s' order by cpr.%s desc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	*/
	
	@Override
	public double GetDblSumOfValue(String getValue, String keyword, String keyValue)
	{
		return super.GetDblSumOfValue("MaterialStorage", getValue, keyword, keyValue);
	}
	
	@Override
	public int GetIntSumOfValue(String getValue, String keyword, String keyValue)
	{
		return super.GetIntSumOfValue("MaterialStorage", getValue, keyword, keyValue);
	}
	
	@Override
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList)
	{
		for (String item : delList)
		{
			String hql = String.format("delete MaterialStorage por where por.%s='%s'", GetDatabaseKeyWord(keyWord), item);
			getEQMHandle().DeleteAndUpdateRecord(hql);
		}
	}
	
	@Override
	public void UpdateRecordByKeyWord(String setKeyWord, String setValue, String keyWord, String keyValue)
	{
		String hql = String.format("update MaterialStorage por set por.%s='%s' where por.%s='%s'", GetDatabaseKeyWord(setKeyWord), setValue,
				GetDatabaseKeyWord(keyWord), keyValue);
		getEQMHandle().DeleteAndUpdateRecord(hql);
	}

	@Override
	public String GetDatabaseKeyWord(String keyword)
	{
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("bar_code") >= 0) {
			rtnRst = "barCode";
		}
		else if(keyword.toLowerCase().indexOf("batch_lot") >= 0) {
			rtnRst = "batchLot";
		}
		else if(keyword.toLowerCase().indexOf("in_qty") >= 0) {
			rtnRst = "inQty";
		}
		else if(keyword.toLowerCase().indexOf("out_qty") >= 0) {
			rtnRst = "outQty";
		}
		else if(keyword.toLowerCase().indexOf("price_per_unit") >= 0) {
			rtnRst = "pricePerUnit";
		}
		else if(keyword.toLowerCase().indexOf("total_price") >= 0) {
			rtnRst = "totalPrice";
		}
		else if(keyword.toLowerCase().indexOf("vendor_name") >= 0) {
			rtnRst = "vendorName";
		}
		else if(keyword.toLowerCase().indexOf("in_store_date") >= 0) {
			rtnRst = "inStoreDate";
		}
		else if(keyword.toLowerCase().indexOf("isensure") >= 0) {
			rtnRst = "isEnsure";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}

	@Override
	public void AddARecord(String appBarcode, String batch_lot,
			String appProductQTY, String appPriceUnit, String appTotalPrice,
			String appSupplier_name, String appInStoreDate)
	{
		aWriteRecord = new MaterialStorage();
		aWriteRecord.setBarCode(appBarcode);
		aWriteRecord.setBatchLot(batch_lot);
		aWriteRecord.setInQty(Integer.parseInt(appProductQTY));
		aWriteRecord.setPricePerUnit(Float.parseFloat(appPriceUnit));
		aWriteRecord.setTotalPrice(Double.parseDouble(appTotalPrice));
		aWriteRecord.setVendorName(appSupplier_name);
		aWriteRecord.setInStoreDate(appInStoreDate);
		getEQMHandle().addANewRecord();
	}

	@Override
	public void QueryRecordByFilterKeyList(List<String> keyList,
			List<String> valueList)
	{
		String hql = "from MaterialStorage ms where ";
		for(int idx=0; idx<keyList.size()-1; idx++)
		{
			hql += String.format("ms.%s='%s' and ", GetDatabaseKeyWord(keyList.get(idx)), valueList.get(idx));
		}
		hql+= String.format("ms.%s='%s'", GetDatabaseKeyWord(keyList.get(keyList.size()-1)), valueList.get(valueList.size()-1));
		getEQMHandle().EQQuery(hql);
	}

	@Override
	public void QueryRecordByFilterKeyListAndBetweenDateSpan(
			List<String> keyList, List<String> valueList, String beginDate,
			String endDate)
	{
		String hql = "from MaterialStorage ms where ";
		for(int idx=0; idx<keyList.size(); idx++)
		{
			hql += String.format("ms.%s='%s' and ", GetDatabaseKeyWord(keyList.get(idx)), valueList.get(idx));
		}
		hql+= String.format("ms.createDate>='%s' and ms.createDate<='%s'", beginDate, endDate);
		getEQMHandle().EQQuery(hql);
	}

	@Override
	public void AddAExRecord(String id, String appBarcode, String batch_lot,
			String appProductQTY, String outQty, String appPriceUnit,
			String appTotalPrice, String appOrderName, String appInStoreDate,
			String isEnsure, String createDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateRecordByFilterKeyList(String setKeyWord, String setValue,
			List<String> keyList, List<String> valueList) {
		// TODO Auto-generated method stub
		
	}
}
