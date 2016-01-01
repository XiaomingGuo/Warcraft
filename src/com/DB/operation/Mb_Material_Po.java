package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.MbMaterialPo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Mb_Material_Po extends DBTableParent implements ITableInterface
{
	private List<MbMaterialPo> resultList = null;
	private MbMaterialPo aWriteRecord = null;
	
	public Mb_Material_Po(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
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
			case "vendor":
				rtnRst.add(tempRecord.getVendor());
				break;
			case "po_name":
				rtnRst.add(tempRecord.getPoName());
				break;
			case "PO_QTY":
				rtnRst.add(tempRecord.getPoQty().toString());
				break;
			case "date_of_delivery":
				rtnRst.add(tempRecord.getDateOfDelivery());
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
		String hql = String.format("from MbMaterialPo cpr where cpr.%s='%s' order by cpr.%s asc", GetDatabaseKeyWord(keyWord), value, GetDatabaseKeyWord(orderKey));
		getEQMHandle().EQQuery(hql);
	}
	
	public void QueryRecordByFilterKeyList(List<String> keyList,
			List<String> valueList)
	{
		String hql = "from MbMaterialPo mmp where ";
		for(int idx=0; idx<keyList.size()-1; idx++)
		{
			hql += String.format("mmp.%s='%s' and ", GetDatabaseKeyWord(keyList.get(idx)), valueList.get(idx));
		}
		hql+= String.format("mmp.%s='%s'", GetDatabaseKeyWord(keyList.get(keyList.size()-1)), valueList.get(valueList.size()-1));
		getEQMHandle().EQQuery(hql);
	}
	
	public void AddARecord(String poName)
	{
		aWriteRecord = new MbMaterialPo();
		aWriteRecord.setPoName(poName);
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
		else if(keyword.toLowerCase().indexOf("vendor") >= 0) {
			rtnRst = "vendor";
		}
		else if(keyword.toLowerCase().indexOf("po_name") >= 0) {
			rtnRst = "poName";
		}
		else if(keyword.toLowerCase().indexOf("po_qty") >= 0) {
			rtnRst = "poQty";
		}
		else if(keyword.toLowerCase().indexOf("date_of_delivery") >= 0) {
			rtnRst = "dateOfDelivery";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}
	
	public void DeleteRecordKeyWord(String keyWord, List<String> delPoList)
	{
		for (String poName : delPoList)
		{
			String hql = String.format("delete MbMaterialPo mmp where mmp.%s='%s'", GetDatabaseKeyWord(keyWord), poName);
			getEQMHandle().DeleteAndUpdateRecord(hql);
		}
	}

	@Override
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateRecordByKeyWord(String setKeyWord, String setValue,
			String keyWord, String keyValue) {
		// TODO Auto-generated method stub
		
	}

}
