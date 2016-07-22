package com.DB.operation;

import java.util.ArrayList;
import java.util.Arrays;
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
	public String GetTableName()
	{
		return "ProductOrderRecord";
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
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("bar_code") >= 0) {
			rtnRst = "barCode";
		}
		else if(keyword.toLowerCase().indexOf("delivery_date") >= 0) {
			rtnRst = "deliveryDate";
		}
		else if(keyword.toLowerCase().indexOf("qty") >= 0) {
			rtnRst = "qty";
		}
		else if(keyword.toLowerCase().indexOf("completeqty") >= 0) {
			rtnRst = "completeQty";
		}
		else if(keyword.toLowerCase().indexOf("oqc_qty") >= 0) {
			rtnRst = "oqcQty";
		}
		else if(keyword.toLowerCase().indexOf("po_name") >= 0) {
			rtnRst = "poName";
		}
		else if(keyword.toLowerCase().indexOf("order_name") >= 0) {
			rtnRst = "orderName";
		}
		else if(keyword.toLowerCase().indexOf("status") >= 0) {
			rtnRst = "status";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}

	public int GetQtyByBarcodeAndPOName(String strBarcode, String appPOName, String getKeyValue)
	{
		int rtnRst = 0;
		QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(GetUsedBarcode(strBarcode, "Product_Order_Record"), appPOName));
		
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
	
	public int GetUncompleteOrderRecord(String barCode)
	{
		int rtnRst = 0;
		String hql = String.format("from ProductOrderRecord por where por.barCode='%s' and por.status<5", barCode);
		getEQMHandle().EQQuery(hql);
		if (RecordDBCount() > 0)
		{
			List<String> po_Qty_List = getDBRecordList("QTY");
			List<String> po_Oqc_List = getDBRecordList("OQC_QTY");
			for (int i = 0; i < po_Qty_List.size(); i++)
			{
				rtnRst += Integer.parseInt(po_Qty_List.get(i))-Integer.parseInt(po_Oqc_List.get(i));
			}
		}
		return rtnRst;
	}
}
