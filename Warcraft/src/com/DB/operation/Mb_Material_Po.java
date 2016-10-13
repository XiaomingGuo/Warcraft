package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.MbMaterialPo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Mb_Material_Po implements ITableInterface
{
	private List<MbMaterialPo> resultList = null;
	private MbMaterialPo aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Mb_Material_Po(IEQManagement hEQMHandle)
	{
		gEQMHandle = hEQMHandle;
	}
	
	@Override
	public String GetTableName()
	{
		return "MbMaterialPo";
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
		Iterator<MbMaterialPo> it = resultList.iterator();
		while(it.hasNext())
		{
			MbMaterialPo tempRecord = (MbMaterialPo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "po_name":
				rtnRst.add(tempRecord.getPoName());
				break;
			case "date_of_delivery":
				rtnRst.add(tempRecord.getDateOfDelivery());
				break;
			case "vendor":
				rtnRst.add(tempRecord.getVendor());
				break;
			case "PO_QTY":
				rtnRst.add(tempRecord.getPoQty().toString());
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
	
	public void AddARecord(String barCode, String poName, String dateOfDelivery, String vendor, int poQty)
	{
		aWriteRecord = new MbMaterialPo();
		aWriteRecord.setBarCode(barCode);
		aWriteRecord.setPoName(poName);
		aWriteRecord.setDateOfDelivery(dateOfDelivery);
		aWriteRecord.setVendor(vendor);
		aWriteRecord.setPoQty(poQty);
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
		else if(keyword.toLowerCase().indexOf("po_name") >= 0) {
			rtnRst = "poName";
		}
		else if(keyword.toLowerCase().indexOf("date_of_delivery") >= 0) {
			rtnRst = "dateOfDelivery";
		}
		else if(keyword.toLowerCase().indexOf("vendor") >= 0) {
			rtnRst = "vendor";
		}
		else if(keyword.toLowerCase().indexOf("po_qty") >= 0) {
			rtnRst = "poQty";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}
}
