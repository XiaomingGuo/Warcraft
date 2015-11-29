package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProductOrderRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Order_Record extends DBTableParent implements ITableInterface
{
	private List<ProductOrderRecord> resultList = null;
	private ProductOrderRecord aWriteRecord = null;
	
	public Product_Order_Record(IEQManagement hEQMHandle)
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
		Iterator<ProductOrderRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			ProductOrderRecord tempRecord = (ProductOrderRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "delivery_date":
				rtnRst.add(tempRecord.getDeliveryDate());
				break;
			case "QTY":
				rtnRst.add(tempRecord.getQty().toString());
				break;
			case "completeQTY":
				rtnRst.add(tempRecord.getCompleteQty().toString());
				break;
			case "OQC_QTY":
				rtnRst.add(tempRecord.getOqcQty().toString());
				break;
			case "po_name":
				rtnRst.add(tempRecord.getPoName());
				break;
			case "Order_Name":
				rtnRst.add(tempRecord.getOrderName());
				break;
			case "status":
				rtnRst.add(tempRecord.getStatus().toString());
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
	
	public void GetRecordByOrderName(String pro_order)
	{
		String hql = String.format("from ProductOrderRecord por where por.orderName='%s'", pro_order);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordByPOName(String poName)
	{
		String hql = String.format("from ProductOrderRecord por where por.poName='%s'", poName);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordByKeyWord(String keyWord, String poName)
	{
		String hql = String.format("from ProductOrderRecord por where por.%s='%s'", keyWord, poName);
		getEQMHandle().EQQuery(hql);
	}
	
	public void AddARecord(String barCode, String deliveryDate, int qty, String poName, String orderName)
	{
		aWriteRecord = new ProductOrderRecord();
		aWriteRecord.setBarCode(GetUsedBarcode(barCode, "product_order_record"));
		aWriteRecord.setDeliveryDate(deliveryDate);
		aWriteRecord.setQty(qty);
		aWriteRecord.setPoName(poName);
		aWriteRecord.setOrderName(orderName);
		getEQMHandle().addANewRecord();
	}

	@Override
	public int GetIntSumOfValue(String getValue,
			String keyword, String keyValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double GetDblSumOfValue(String getValue,
			String keyword, String keyValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	public int GetQtyByBarcodeAndPOName(String strBarcode, String appPOName, String getKeyValue)
	{
		int rtnRst = 0;
		String hql = String.format("from ProductOrderRecord por where por.barCode='%s' and por.poName='%s'", GetUsedBarcode(strBarcode, "Product_Order_Record"), appPOName);
		getEQMHandle().EQQuery(hql);
		if (RecordDBCount() > 0)
		{
			List<String> po_Qty_List = getDBRecordList(getKeyValue);
			for (int i = 0; i < po_Qty_List.size(); i++)
			{
				rtnRst += Integer.parseInt(po_Qty_List.get(i));
			}
		}
		return rtnRst;
	}
	
	public void DeleteRecordByKeyWord(String keyWord, List<String> delPoList)
	{
		for (String poName : delPoList)
		{
			String hql = String.format("delete ProductOrderRecord por where por.%s='%s'", keyWord, poName);
			getEQMHandle().DeleteAndUpdateRecord(hql);
		}
	}
	
	public void UpdatetRecordByOrderName(String orderName, String barcode, String keyWord, String value)
	{
		String hql = String.format("update ProductOrderRecord por set por.%s='%s' where por.orderName='%s' and por.barCode='%s'", keyWord, value, orderName, barcode);
		getEQMHandle().DeleteAndUpdateRecord(hql);
	}

	@Override
	public void UpdateRecordByKeyWord(String setKeyWord, String setValue,
			String keyWord, String keyValue) {
		// TODO Auto-generated method stub
		
	}
}
