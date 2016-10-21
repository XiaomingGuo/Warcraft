package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.*;
import com.Warcraft.Interface.*;

public class Trans_Barcode_Record implements ITableInterface
{
	private List<TransBarcodeRecord> resultList = null;
	private TransBarcodeRecord aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Trans_Barcode_Record(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    	
	@Override
	public String GetTableName()
	{
		return "TransBarcodeRecord";
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
		Iterator<TransBarcodeRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			TransBarcodeRecord tempRecord = (TransBarcodeRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "from_Bar_Code":
				rtnRst.add(tempRecord.getFromBarCode());
				break;
			case "from_Batch_Lot":
				rtnRst.add(tempRecord.getFromBatchLot());
				break;
			case "from_QTY":
				rtnRst.add(tempRecord.getFromQty().toString());
				break;
			case "to_Bar_Code":
				rtnRst.add(tempRecord.getFromBarCode());
				break;
			case "to_Batch_Lot":
				rtnRst.add(tempRecord.getFromBatchLot());
				break;
			case "to_QTY":
				rtnRst.add(tempRecord.getToQty().toString());
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
	
	public void AddARecord(String fromBarcode, String fromBatchLot, String fromQty, String toBarcode, String toBatchLot, String toQty)
	{
		aWriteRecord = new TransBarcodeRecord();
		aWriteRecord.setFromBarCode(fromBarcode);
		aWriteRecord.setFromBatchLot(fromBatchLot);
		aWriteRecord.setFromQty(Integer.parseInt(fromQty));
		aWriteRecord.setToBarCode(toBarcode);
		aWriteRecord.setToBatchLot(toBatchLot);
		aWriteRecord.setToQty(Integer.parseInt(toQty));
		gEQMHandle.addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("from_bar_code") >= 0) {
			rtnRst = "fromBarCode";
		}
		else if(keyword.toLowerCase().indexOf("from_batch_lot") >= 0) {
			rtnRst = "fromBatchLot";
		}
		else if(keyword.toLowerCase().indexOf("from_qty") >= 0) {
			rtnRst = "fromQty";
		}
		else if(keyword.toLowerCase().indexOf("to_bar_code") >= 0) {
			rtnRst = "toBarCode";
		}
		else if(keyword.toLowerCase().indexOf("to_batch_lot") >= 0) {
			rtnRst = "toBatchLot";
		}
		else if(keyword.toLowerCase().indexOf("to_qty") >= 0) {
			rtnRst = "toQty";
		}
		return rtnRst;
	}
}
