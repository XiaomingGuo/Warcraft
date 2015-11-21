package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.OtherStorage;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Other_Storage extends DBTableParent implements ITableInterface
{
	private List<OtherStorage> resultList = null;
	private OtherStorage aWriteRecord = null;
	
	public Other_Storage(IEQManagement hEQMHandle)
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
		Iterator<OtherStorage> it = resultList.iterator();
		while(it.hasNext())
		{
			OtherStorage tempRecord = (OtherStorage)it.next();
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
				rtnRst.add(tempRecord.getVendorName());
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
		String hql = String.format("from OtherStorage cpr where cpr.%s='%s' order by cpr.%s asc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	
	/*
	private void execQueryDesc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from OtherStorage cpr where cpr.%s='%s' order by cpr.%s desc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	*/
	
	public void AddARecord(String poName)
	{
		aWriteRecord = new OtherStorage();
		//aWriteRecord.setPoName(poName);
		getEQMHandle().addANewRecord();
	}

	@Override
	public double GetDblSumOfValue(String getValue, String keyword, String keyValue)
	{
		return super.GetDblSumOfValue("OtherStorage", getValue, keyword, keyValue);
	}
	
	@Override
	public int GetIntSumOfValue(String getValue, String keyword, String keyValue)
	{
		return super.GetIntSumOfValue("OtherStorage", getValue, keyword, keyValue);
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
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}
}
