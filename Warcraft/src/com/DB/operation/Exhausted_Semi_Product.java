package com.DB.operation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ExhaustedSemiProduct;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.Interface.ITableInterface;

public class Exhausted_Semi_Product implements ITableInterface, IStorageTableInterface
{
	private List<ExhaustedSemiProduct> resultList = null;
	private ExhaustedSemiProduct aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Exhausted_Semi_Product(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
	@Override
	public String GetTableName()
	{
		return "ExhaustedSemiProduct";
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
		Iterator<ExhaustedSemiProduct> it = resultList.iterator();
		while(it.hasNext())
		{
			ExhaustedSemiProduct tempRecord = (ExhaustedSemiProduct)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "Batch_Lot":
				rtnRst.add(tempRecord.getBatchLot());
				break;
			case "IN_QTY":
				rtnRst.add(tempRecord.getInQty().toString());
				break;
			case "OUT_QTY":
				rtnRst.add(tempRecord.getOutQty().toString());
				break;
			case "Price_Per_Unit":
				rtnRst.add(tempRecord.getPricePerUnit().toString());
				break;
			case "Total_Price":
				rtnRst.add(tempRecord.getTotalPrice().toString());
				break;
			case "Order_Name":
				rtnRst.add(tempRecord.getOrderName());
				break;
			case "po_name":
				rtnRst.add(tempRecord.getPoName());
				break;
			case "vendor_name":
				rtnRst.add(tempRecord.getVendorName());
				break;
			case "in_store_date":
				rtnRst.add(tempRecord.getVendorName());
				break;
			case "isEnsure":
				rtnRst.add(tempRecord.getIsEnsure().toString());
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
	
	@Override
	public String GetDatabaseKeyWord(String keyword)
	{
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("bar_code") >= 0) {
			rtnRst = "barCode";
		}
		else if(keyword.toLowerCase().indexOf("batch_lot") >= 0) {
			rtnRst = "batchLot";
		}
		else if(keyword.toLowerCase().indexOf("in_qty") >= 0) {
			rtnRst = "inQty";
		}
		else if(keyword.toLowerCase().indexOf("out_qty") >= 0) {
			rtnRst = "outQty";
		}
		else if(keyword.toLowerCase().indexOf("price_per_unit") >= 0) {
			rtnRst = "pricePerUnit";
		}
		else if(keyword.toLowerCase().indexOf("total_price") >= 0) {
			rtnRst = "totalPrice";
		}
		else if(keyword.toLowerCase().indexOf("order_name") >= 0) {
			rtnRst = "orderName";
		}
		else if(keyword.toLowerCase().indexOf("po_name") >= 0) {
			rtnRst = "poName";
		}
		else if(keyword.toLowerCase().indexOf("vendor_name") >= 0) {
			rtnRst = "vendorName";
		}
		else if(keyword.toLowerCase().indexOf("in_store_date") >= 0) {
			rtnRst = "inStoreDate";
		}
		else if(keyword.toLowerCase().indexOf("isensure") >= 0) {
			rtnRst = "isEnsure";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}
	
	public void AddAExRecord(String id, String appBarcode, String batch_lot,
			String appProductQTY, String outQty, String appPriceUnit, String appTotalPrice, String appOrderName,
			String poName, String appSupplier_name, String appInStoreDate, String isEnsure, String createDate)
	{
		aWriteRecord = new ExhaustedSemiProduct();
		aWriteRecord.setId(Integer.parseInt(id));
		aWriteRecord.setBarCode(appBarcode);
		aWriteRecord.setBatchLot(batch_lot);
		aWriteRecord.setOrderName(appOrderName);
		aWriteRecord.setInQty(Integer.parseInt(appProductQTY));
		aWriteRecord.setOutQty(Integer.parseInt(outQty));
		aWriteRecord.setPricePerUnit(Float.parseFloat(appPriceUnit));
		aWriteRecord.setTotalPrice(Double.parseDouble(appTotalPrice));
		aWriteRecord.setOrderName(appOrderName);
		aWriteRecord.setPoName(poName);
		aWriteRecord.setVendorName(appSupplier_name);
		aWriteRecord.setInStoreDate(appInStoreDate);
		aWriteRecord.setIsEnsure(Integer.parseInt(isEnsure));
		aWriteRecord.setCreateDate(Timestamp.valueOf(createDate));
		gEQMHandle.addANewRecord();
	}
}
