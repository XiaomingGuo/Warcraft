package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.ProductInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Info extends DBTableParent implements ITableInterface
{
	private List<ProductInfo> resultList = null;
	
	public Product_Info(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}

	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<ProductInfo> it = getResultList().iterator();
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
	public void setResult(Query query)
	{
		this.setResultList(query.list());
	}

	public List<ProductInfo> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<ProductInfo> resultList)
	{
		this.resultList = resultList;
	}
	
	public void GetBarCodeByName(String name)
	{
		String hql = String.format("from ProductInfo pi where pi.name='%s'", name);
		getEQMHandle().EQQuery(hql);
	}

}
