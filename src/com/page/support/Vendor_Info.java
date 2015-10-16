package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.VendorInfo;
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
}
