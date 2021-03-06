package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProductInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Product_Info implements ITableInterface
{
    private List<ProductInfo> resultList = null;
    private ProductInfo aWriteRecord = null;
	IEQManagement gEQMHandle;
    
    public Product_Info(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
    @Override
    public String GetTableName()
    {
        return "ProductInfo";
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
            case "sample_price":
                rtnRst.add(tempRecord.getSamplePrice().toString());
                break;
            case "sample_vendor":
                rtnRst.add(tempRecord.getSampleVendor());
                break;
            case "process_name":
                rtnRst.add(tempRecord.getProcessName());
                break;
            case "capacity":
                rtnRst.add(tempRecord.getCapacity().toString());
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
    
    @Override
    public String GetDatabaseKeyWord(String keyword) {
        String rtnRst = "";
        if(keyword.toLowerCase().indexOf("id") >= 0){
            rtnRst = "id";
        }
        else if(keyword.toLowerCase().indexOf("bar_code") >= 0) {
            rtnRst = "barCode";
        }
        else if(keyword.toLowerCase().indexOf("name") >= 0) {
            rtnRst = "name";
        }
        else if(keyword.toLowerCase().indexOf("product_type") >= 0) {
            rtnRst = "productType";
        }
        else if(keyword.toLowerCase().indexOf("weight") >= 0) {
            rtnRst = "weight";
        }
        else if(keyword.toLowerCase().indexOf("sample_price") >= 0) {
            rtnRst = "samplePrice";
        }
        else if(keyword.toLowerCase().indexOf("sample_vendor") >= 0) {
            rtnRst = "sampleVendor";
        }
        else if(keyword.toLowerCase().indexOf("process_name") >= 0) {
            rtnRst = "processName";
        }
        if(keyword.toLowerCase().indexOf("capacity") >= 0){
            rtnRst = "capacity";
        }
        else if(keyword.toLowerCase().indexOf("description") >= 0) {
            rtnRst = "description";
        }
        else if(keyword.toLowerCase().indexOf("picture") >= 0) {
            rtnRst = "picture";
        }
        return rtnRst;
    }
    
    public void AddARecord(String barCode, String name, String productType, String weight, String samplePrice, String sampleVendor,
    		String processName, String capacity, String description)
    {
        aWriteRecord = new ProductInfo();
        aWriteRecord.setBarCode(barCode);
        aWriteRecord.setName(name);
        aWriteRecord.setProductType(productType);
        aWriteRecord.setWeight(Float.parseFloat(weight));
        aWriteRecord.setSamplePrice(Float.parseFloat(samplePrice));
        aWriteRecord.setCapacity(Integer.parseInt(capacity));
        if(null == description)
            aWriteRecord.setDescription("无备注");
        else
            aWriteRecord.setDescription(description);
        gEQMHandle.addANewRecord();
    }
}
