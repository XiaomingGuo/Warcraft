package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Order_Record extends DBTableParent implements ITableInterface
{
	private List<ProductOrderRecord> resultList = null;
	
	public Product_Order_Record(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<ProductOrderRecord> it = getResultList().iterator();
		while(it.hasNext())
		{
			ProductOrderRecord tempRecord = (ProductOrderRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "delivery_date":
				rtnRst.add(tempRecord.getDeliveryDate());
				break;
			case "QTY":
				rtnRst.add(tempRecord.getQty().toString());
				break;
			case "completeQTY":
				rtnRst.add(tempRecord.getCompleteQty().toString());
				break;
			case "OQC_QTY":
				rtnRst.add(tempRecord.getOqcQty().toString());
				break;
			case "po_name":
				rtnRst.add(tempRecord.getPoName());
				break;
			case "Order_Name":
				rtnRst.add(tempRecord.getOrderName());
				break;
			case "status":
				rtnRst.add(tempRecord.getStatus().toString());
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
	public void setResult(Query query)
	{
		this.setResultList(query.list());
	}

	public List<ProductOrderRecord> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<ProductOrderRecord> resultList)
	{
		this.resultList = resultList;
	}
	
	public List<String> GetBarCodeByOrderName(String pro_order)
	{
		String hql = String.format("from ProductOrderRecord por where por.orderName='%s'", pro_order);
		getEQMHandle().EQQuery(hql);
		return getDBRecordList("Bar_Code");
	}

}
