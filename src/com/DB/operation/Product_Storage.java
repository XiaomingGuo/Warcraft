package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProductStorage;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Storage extends DBTableParent implements ITableInterface
{
	private List<ProductStorage> resultList = null;
	private ProductStorage aWriteRecord = null;
	
	public Product_Storage(IEQManagement hEQMHandle)
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
		Iterator<ProductStorage> it = resultList.iterator();
		while(it.hasNext())
		{
			ProductStorage tempRecord = (ProductStorage)it.next();
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
		String hql = String.format("from ProductStorage ps where ps.%s='%s' order by ps.%s asc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	
	//String sql = "select IN_QTY from "+storage_name+" where Bar_Code='" + GetUsedBarcode(barcode, storage_name) +"'";
	public double GetDblSumOfValue(String storage_name, String getValue, String keyword, String keyValue)
	{
		double rtnRst = 0.0;
		String hql = String.format("from %s st where st.%s='%s'", storage_name, keyword, keyValue);
		getEQMHandle().EQQuery(hql);
		if (RecordDBCount() > 0)
		{
			List<String> in_Qty_List = getDBRecordList(getValue);
			for (int i = 0; i < in_Qty_List.size(); i++)
			{
				rtnRst += Double.parseDouble(in_Qty_List.get(i));
			}
		}
		return rtnRst;
	}
	
	public int GetIntSumOfValue(String storage_name, String getValue, String keyword, String keyValue)
	{
		int rtnRst = 0;
		String hql = String.format("from %s st where st.%s='%s'", storage_name, keyword, keyValue);
		getEQMHandle().EQQuery(hql);
		if (RecordDBCount() > 0)
		{
			List<String> in_Qty_List = getDBRecordList(getValue);
			for (int i = 0; i < in_Qty_List.size(); i++)
			{
				rtnRst += Integer.parseInt(in_Qty_List.get(i));
			}
		}
		return rtnRst;
	}

	/*
	private void execQueryDesc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from ProductStorage cpr where cpr.%s='%s' order by cpr.%s desc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	*/
	
	public void AddARecord(String poName)
	{
		aWriteRecord = new ProductStorage();
		//aWriteRecord.setPoName(poName);
		getEQMHandle().addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
