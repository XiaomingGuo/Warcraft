package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProductInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Info extends DBTableParent implements ITableInterface
{
	private List<ProductInfo> resultList = null;
	private ProductInfo aWriteRecord = null;
	
	public Product_Info(IEQManagement hEQMHandle)
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
		Iterator<ProductInfo> it = resultList.iterator();
		while(it.hasNext())
		{
			ProductInfo tempRecord = (ProductInfo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "name":
				rtnRst.add(tempRecord.getName());
				break;
			case "product_type":
				rtnRst.add(tempRecord.getProductType());
				break;
			case "weight":
				rtnRst.add(tempRecord.getWeight().toString());
				break;
			case "description":
				rtnRst.add(tempRecord.getDescription());
				break;
			case "picture":
				rtnRst.add(tempRecord.getPicture());
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

	public void GetRecordByBarcode(String bar_code)
	{
		String hql = String.format("from ProductInfo pi where pi.barCode='%s'", bar_code);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordByName(String name)
	{
		String hql = String.format("from ProductInfo pi where pi.name='%s'", name);
		getEQMHandle().EQQuery(hql);
	}

	public void GetRecordByProType(String product_type)
	{
		String hql = String.format("from ProductInfo pi where pi.productType='%s'", product_type);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordByNameAndProType(String product_name, String product_type)
	{
		String hql = String.format("from ProductInfo pi where pi.name='%s' and pi.productType='%s'", product_name, product_type);
		getEQMHandle().EQQuery(hql);
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
