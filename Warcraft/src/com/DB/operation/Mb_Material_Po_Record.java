package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.MbMaterialPoRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Mb_Material_Po_Record extends DBTableParent implements ITableInterface
{
	private List<MbMaterialPoRecord> resultList = null;
	private MbMaterialPoRecord aWriteRecord = null;
	
	public Mb_Material_Po_Record(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public String GetTableName()
	{
		return "MbMaterialPoRecord";
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
		Iterator<MbMaterialPoRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			MbMaterialPoRecord tempRecord = (MbMaterialPoRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "mb_material_po_id":
				rtnRst.add(tempRecord.getMbMaterialPoId().toString());
				break;
			case "IN_QTY":
				rtnRst.add(tempRecord.getInQty().toString());
				break;
			case "IN_DATE":
				rtnRst.add(tempRecord.getInDate().toString());
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
	
	public void AddARecord(String mbMaterialPoId, String barCode, String batchLot, String inQty, String inDate)
	{
		aWriteRecord = new MbMaterialPoRecord();
		aWriteRecord.setMbMaterialPoId(Integer.parseInt(mbMaterialPoId));
		aWriteRecord.setBarCode(barCode);
		aWriteRecord.setBatchLot(batchLot);
		aWriteRecord.setInQty(Integer.parseInt(inQty));
		aWriteRecord.setInDate(inDate);
		getEQMHandle().addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("mb_material_po_id") >= 0) {
			rtnRst = "mbMaterialPoId";
		}
		else if(keyword.toLowerCase().indexOf("in_qty") >= 0) {
			rtnRst = "inQty";
		}
		else if(keyword.toLowerCase().indexOf("in_date") >= 0) {
			rtnRst = "inDate";
		}
		else if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		return rtnRst;
	}
}
