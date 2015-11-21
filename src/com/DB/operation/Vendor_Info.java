package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.VendorInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Vendor_Info extends DBTableParent implements ITableInterface
{
	private List<VendorInfo> resultList = null;
	private VendorInfo aWriteRecord = null;
	
	public Vendor_Info(IEQManagement hEQMHandle)
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

	public void GetRecordByStoreroom(String storeroom)
	{
		String hql = String.format("from VendorInfo vi where vi.storeroom='%s'", storeroom);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordExceptStoreroom(String storeroom)
	{
		String hql = String.format("from VendorInfo vi where vi.storeroom!='%s'", storeroom);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordByNameAndStoreroom(String supplier, String storeroom)
	{
		String hql = String.format("from VendorInfo vi where vi.vendor_name='%s' and vi.storeroom = '%s'", supplier, storeroom);
		getEQMHandle().EQQuery(hql);
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

}
