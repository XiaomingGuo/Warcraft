package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ShippingRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Shipping_Record implements ITableInterface
{
	private List<ShippingRecord> resultList = null;
	private ShippingRecord aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Shipping_Record(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
	@Override
	public String GetTableName()
	{
		return "ShippingRecord";
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
		Iterator<ShippingRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			ShippingRecord tempRecord = (ShippingRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "customer_po":
				rtnRst.add(tempRecord.getCustomerPo());
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
			case "ship_QTY":
				rtnRst.add(tempRecord.getShipQty().toString());
				break;
			case "shipping_no":
				rtnRst.add(tempRecord.getShippingNo().toString());
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
	
	public void AddARecord(String customerPo, String barCode, String batchLot, String orderName, String shipQty)
	{
		aWriteRecord = new ShippingRecord();
		aWriteRecord.setCustomerPo(customerPo);
		aWriteRecord.setBarCode(barCode);
		aWriteRecord.setBatchLot(batchLot);
		aWriteRecord.setOrderName(orderName);
		aWriteRecord.setShipQty(Integer.parseInt(shipQty));
		gEQMHandle.addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("customer_po") >= 0) {
			rtnRst = "customerPo";
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
		else if(keyword.toLowerCase().indexOf("ship_qty") >= 0) {
			rtnRst = "shipQty";
		}
		else if(keyword.toLowerCase().indexOf("shipping_no") >= 0) {
			rtnRst = "shippingNo";
		}
		return rtnRst;
	}
}
