package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.DiscardMaterialRecord;
import com.DB.support.ProductOrderRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Discard_Material_Record implements ITableInterface
{
	private List<DiscardMaterialRecord> resultList = null;
	private DiscardMaterialRecord aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Discard_Material_Record(IEQManagement hEQMHandle)
	{
		gEQMHandle = hEQMHandle;
	}
	
	@Override
	public String GetTableName()
	{
		return "DiscardMaterialRecord";
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
		Iterator<DiscardMaterialRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			DiscardMaterialRecord tempRecord = (DiscardMaterialRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Order_Name":
				rtnRst.add(tempRecord.getOrderName());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "Batch_Lot":
				rtnRst.add(tempRecord.getBatchLot());
				break;
			case "operator":
				rtnRst.add(tempRecord.getOperator());
				break;
			case "QTY":
				rtnRst.add(tempRecord.getQty().toString());
				break;
			case "reason":
				rtnRst.add(tempRecord.getReason());
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

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("order_name") >= 0) {
			rtnRst = "orderName";
		}
		else if(keyword.toLowerCase().indexOf("bar_code") >= 0) {
			rtnRst = "barCode";
		}
		else if(keyword.toLowerCase().indexOf("batch_lot") >= 0) {
			rtnRst = "batchLot";
		}
		else if(keyword.toLowerCase().indexOf("operator") >= 0) {
			rtnRst = "operator";
		}
		else if(keyword.toLowerCase().indexOf("qty") >= 0) {
			rtnRst = "qty";
		}
		else if(keyword.toLowerCase().indexOf("reason") >= 0) {
			rtnRst = "reason";
		}
		return rtnRst;
	}
	
	public void AddARecord(String orderName, String barCode, String batchLot, String operator, String qty, String reason)
	{
		aWriteRecord = new DiscardMaterialRecord();
		aWriteRecord.setOrderName(orderName);
		aWriteRecord.setBarCode(barCode);
		aWriteRecord.setBatchLot(batchLot);
		aWriteRecord.setOperator(operator);
		aWriteRecord.setQty(Integer.parseInt(qty));
		aWriteRecord.setReason(reason);
		gEQMHandle.addANewRecord();
	}
}
