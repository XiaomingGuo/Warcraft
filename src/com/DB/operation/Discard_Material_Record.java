package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.DiscardMaterialRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Discard_Material_Record extends DBTableParent implements ITableInterface
{
	private List<DiscardMaterialRecord> resultList = null;
	private DiscardMaterialRecord aWriteRecord = null;
	
	public Discard_Material_Record(IEQManagement hEQMHandle)
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
}
