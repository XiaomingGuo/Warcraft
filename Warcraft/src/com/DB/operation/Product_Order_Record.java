package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProductOrderRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Product_Order_Record implements ITableInterface
{
	private List<ProductOrderRecord> resultList = null;
	private ProductOrderRecord aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Product_Order_Record(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
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
		aWriteRecord.setBarCode(barCode);
		aWriteRecord.setDeliveryDate(deliveryDate);
		aWriteRecord.setQty(qty);
		aWriteRecord.setPoName(poName);
		aWriteRecord.setOrderName(orderName);
		gEQMHandle.addANewRecord();
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
}
