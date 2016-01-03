package com.DB.operation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ExhaustedProduct;
import com.DB.support.MaterialStorage;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Exhausted_Product extends DBTableParent implements ITableInterface, IStorageTableInterface
{
	private List<ExhaustedProduct> resultList = null;
	private ExhaustedProduct aWriteRecord = null;
	
	public Exhausted_Product(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public String GetTableName()
	{
		return "ExhaustedProduct";
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
		Iterator<ExhaustedProduct> it = resultList.iterator();
		while(it.hasNext())
		{
			ExhaustedProduct tempRecord = (ExhaustedProduct)it.next();
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
			case "Order_Name":
				rtnRst.add(tempRecord.getOrderName());
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
	
	public void GetAllRecord()
	{
		String hql = String.format("from ExhaustedProduct");
		getEQMHandle().EQQuery(hql);
	}

	@Override
	public double GetDblSumOfValue(String getValue, String keyword, String keyValue)
	{
		return super.GetDblSumOfValue("ExhaustedProduct", getValue, keyword, keyValue);
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
		else if(keyword.toLowerCase().indexOf("order_name") >= 0) {
			rtnRst = "orderName";
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
		else if(keyword.toLowerCase().indexOf("isensure") >= 0) {
			rtnRst = "isEnsure";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}

	@Override
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AddAExRecord(String id, String appBarcode, String batch_lot,
			String appProductQTY, String outQty, String appPriceUnit, String appTotalPrice,
			String appOrderName, String appInStoreDate, String isEnsure, String createDate)
	{
		aWriteRecord = new ExhaustedProduct();
		aWriteRecord.setId(Integer.parseInt(id));
		aWriteRecord.setBarCode(appBarcode);
		aWriteRecord.setBatchLot(batch_lot);
		aWriteRecord.setOrderName(appOrderName);
		aWriteRecord.setInQty(Integer.parseInt(appProductQTY));
		aWriteRecord.setOutQty(Integer.parseInt(outQty));
		aWriteRecord.setPricePerUnit(Float.parseFloat(appPriceUnit));
		aWriteRecord.setTotalPrice(Double.parseDouble(appTotalPrice));
		aWriteRecord.setIsEnsure(Integer.parseInt(isEnsure));
		aWriteRecord.setCreateDate(Timestamp.valueOf(createDate));
		getEQMHandle().addANewRecord();
	}

	@Override
	public void QueryRecordByFilterKeyList(List<String> keyList,
			List<String> valueList)
	{
		String hql = "from ExhaustedProduct ep where ";
		for(int idx=0; idx<keyList.size()-1; idx++)
		{
			hql += String.format("ep.%s='%s' and ", GetDatabaseKeyWord(keyList.get(idx)), valueList.get(idx));
		}
		hql+= String.format("ep.%s='%s'", GetDatabaseKeyWord(keyList.get(keyList.size()-1)), valueList.get(valueList.size()-1));
		getEQMHandle().EQQuery(hql);
		
	}

	@Override
	public void QueryRecordByFilterKeyListAndBetweenDateSpan(
			List<String> keyList, List<String> valueList, String beginDate,
			String endDate)
	{
		String hql = "from ExhaustedProduct ep where ";
		for(int idx=0; idx<keyList.size(); idx++)
		{
			hql += String.format("ep.%s='%s' and ", GetDatabaseKeyWord(keyList.get(idx)), valueList.get(idx));
		}
		hql+= String.format("ep.createDate>='%s' and ep.createDate<='%s'", beginDate, endDate);
		getEQMHandle().EQQuery(hql);
	}

	@Override
	public void AddARecord(String appBarcode, String batch_lot,
			String appProductQTY, String appPriceUnit, String appTotalPrice,
			String appSupplier_name, String appInStoreDate) {
		// TODO Auto-generated method stub
		
	}
}
