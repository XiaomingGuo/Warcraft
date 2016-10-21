package com.DB.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.VendorInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Vendor_Info implements ITableInterface
{
	private List<VendorInfo> resultList = null;
	private VendorInfo aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Vendor_Info(){}
	
	@Override
	public void setEQManagement(IEQManagement hEQHandle)
	{
		gEQMHandle = hEQHandle;
	}
	
	@Override
	public String GetTableName()
	{
		return "VendorInfo";
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
		Iterator<VendorInfo> it = resultList.iterator();
		while(it.hasNext())
		{
			VendorInfo tempRecord = (VendorInfo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "vendor_name":
				rtnRst.add(tempRecord.getVendorName());
				break;
			case "storeroom":
				rtnRst.add(tempRecord.getStoreroom());
				break;
			case "vendor_fax":
				rtnRst.add(tempRecord.getVendorFax());
				break;
			case "vendor_tel":
				rtnRst.add(tempRecord.getVendorTel());
				break;
			case "vendor_e_mail":
				rtnRst.add(tempRecord.getVendorEMail());
				break;
			case "vendor_address":
				rtnRst.add(tempRecord.getVendorAddress());
				break;
			case "description":
				rtnRst.add(tempRecord.getDescription());
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
	
	public void GetRecordExceptStoreroom(List<String> keyList)
	{
		String hql = "from VendorInfo vi where ";
		for(int idx=0; idx<keyList.size()-1; idx++)
		{
			hql += String.format("vi.storeroom!='%s' and ", keyList.get(idx));
		}
		hql+= String.format("vi.storeroom!='%s'", keyList.get(keyList.size()-1));
		gEQMHandle.EQQuery(hql);
	}
	
	public void AddARecord(String vendor_name, String storeroom, String vendor_fax, String vendor_tel, String vendor_e_mail, String vendor_address, String description)
	{
		aWriteRecord = new VendorInfo();
		aWriteRecord.setVendorName(vendor_name);
		aWriteRecord.setStoreroom(storeroom);
		aWriteRecord.setVendorFax(vendor_fax);
		aWriteRecord.setVendorTel(vendor_tel);
		aWriteRecord.setVendorEMail(vendor_e_mail);
		aWriteRecord.setVendorAddress(vendor_address);
		aWriteRecord.setDescription(description);
		gEQMHandle.addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword)
	{
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("vendor_name") >= 0) {
			rtnRst = "vendorName";
		}
		else if(keyword.toLowerCase().indexOf("storeroom") >= 0) {
			rtnRst = "storeroom";
		}
		else if(keyword.toLowerCase().indexOf("vendor_fax") >= 0) {
			rtnRst = "vendorFax";
		}
		else if(keyword.toLowerCase().indexOf("vendor_tel") >= 0) {
			rtnRst = "vendorTel";
		}
		else if(keyword.toLowerCase().indexOf("vendor_e_mail") >= 0) {
			rtnRst = "vendorEMail";
		}
		else if(keyword.toLowerCase().indexOf("vendor_address") >= 0) {
			rtnRst = "vendorAddress";
		}
		else if(keyword.toLowerCase().indexOf("description") >= 0) {
			rtnRst = "description";
		}
		return rtnRst;
	}
}
