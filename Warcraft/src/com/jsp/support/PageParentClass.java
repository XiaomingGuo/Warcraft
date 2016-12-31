package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DBTableParent;
import com.Warcraft.SupportUnit.DateAdapter;
import com.page.utilities.CRecordsQueryUtil;

public class PageParentClass
{
    public int CalcOrderQty(String po_Num, String percent)
    {
        return Integer.parseInt(po_Num) * (100 + Integer.parseInt(percent))/100;
    }
    
    public int CalcOrderQty(int po_Num, String percent)
    {
        return po_Num * (100 + Integer.parseInt(percent))/100;
    }
    
    public int CalcOrderQty(String po_Num, int percent)
    {
        return Integer.parseInt(po_Num) * (100 + percent)/100;
    }
    
    public int CalcOrderQty(int po_Num, int percent)
    {
        return po_Num * (100 + percent)/100;
    }
    
    public boolean IsOtherBarcode(String barcode)
    {
        if (Integer.parseInt(barcode) < 50000000 || Integer.parseInt(barcode) >= 80000000)
            return true;
        return false;
    }
    
    public boolean IsMaterialBarcode(String barcode)
    {
        if (Integer.parseInt(barcode) >= 50000000 && Integer.parseInt(barcode) < 60000000)
            return true;
        return false;
    }
    
    public boolean IsProductBarcode(String barcode)
    {
        if (Integer.parseInt(barcode) >= 60000000 && Integer.parseInt(barcode) < 70000000)
            return true;
        return false;
    }
    
    public boolean IsSemiProBarcode(String barcode)
    {
        if (Integer.parseInt(barcode) >= 70000000 && Integer.parseInt(barcode) < 80000000)
            return true;
        return false;
    }
    
    public String GetUsedBarcode(String barcode, String storage_name)
    {
        return new DatabaseStore("Product_Info").GetUsedBarcode(barcode, storage_name);
    }
    
    public String GetStorageNameByBarCode(String Bar_Code, boolean isExhausted)
    {
        String rtnRst = "";
        int barcode = Integer.parseInt(Bar_Code);
        if(barcode >= 50000000 && barcode < 60000000) {
            rtnRst = isExhausted?"ExhaustedMaterial":"MaterialStorage";
        }
        else if(barcode >= 60000000 && barcode < 70000000) {
            rtnRst = isExhausted?"ExhaustedProduct":"ProductStorage";
        }
        else if(barcode >= 70000000 && barcode < 80000000) {
            rtnRst = isExhausted?"ExhaustedSemiProduct":"SemiProductStorage";
        }
        else {
            rtnRst = isExhausted?"ExhaustedOther":"OtherStorage";
        }
        return rtnRst;
    }
    
    public int GetQTYByBarCode(String qtyType, String barcode, List<String> keyList, List<String> valueList)
    {
        DBTableParent hHandle = GenStorageHandle(barcode);
        return hHandle.GetIntSumOfValue(qtyType, keyList, valueList);
    }
    
    public int GetStorageRepertory(String barcode, List<String> keyList, List<String> valueList)
    {
        return GetQTYByBarCode("IN_QTY", barcode, keyList, valueList) -
                GetQTYByBarCode("OUT_QTY", barcode, keyList, valueList);
    }
    
    public String GetStoreNameBySelectName(String selectName)
    {
        if(selectName.contains("五金库"))
        	return "Other";
        else if(selectName.contains("原材料库"))
        	return "Material";
        else if(selectName.equals("半成品库"))
        	return "Manu";
        else if(selectName.equals("成品库"))
        	return "Product";
        return "Other";
    }
    
    public DBTableParent GenStorageHandleByStorageName(String storageName)
    {
        if(storageName.toLowerCase().equals("other"))
            return new DatabaseStore("Other_Storage");
        else if(storageName.toLowerCase().equals("product"))
            return new DatabaseStore("Product_Storage");
        else if(storageName.toLowerCase().equals("semi")||storageName.toLowerCase().equals("material"))
            return new DatabaseStore("Manu_Storage");
        else if(storageName.toLowerCase().equals("exother"))
            return new DatabaseStore("Exhausted_Other");
        else if(storageName.toLowerCase().equals("exproduct"))
            return new DatabaseStore("Exhausted_Product");
        else if(storageName.toLowerCase().equals("exsemi")||storageName.toLowerCase().equals("exmaterial"))
            return new DatabaseStore("Exhausted_Manu");
        return null;    
    }
    
    public DBTableParent GenStorageHandle(String barcode)
    {
        if(IsOtherBarcode(barcode))
            return new DatabaseStore("Other_Storage");
        else if(IsSemiProBarcode(barcode)||IsMaterialBarcode(barcode)||IsProductBarcode(barcode))
            return new DatabaseStore("Manu_Storage");
        return null;
    }
    
    public DBTableParent GenProcessStorageHandle(String barcode)
    {
        if(IsSemiProBarcode(barcode))
            return new DatabaseStore("Semi_Product_Storage");
        else if(IsMaterialBarcode(barcode))
            return new DatabaseStore("Material_Storage");
        else if(IsProductBarcode(barcode))
            return new DatabaseStore("Product_Storage");
        else
            return new DatabaseStore("Other_Storage");
    }
    
    public void AddSingleRecordToStorage(DBTableParent hStorageHandle, String barcode, String batch_lot, String storeQty, String sPrice, String tPrice, String orderName, String poName, String vendor, String addDate)
    {
        if(IsSemiProBarcode(barcode))
            ((Semi_Product_Storage)hStorageHandle.getTableInstance()).AddARecord(barcode, batch_lot, storeQty, sPrice, tPrice, orderName, poName, vendor, addDate);
        else if(IsMaterialBarcode(barcode))
            ((Material_Storage)hStorageHandle.getTableInstance()).AddARecord(barcode, batch_lot, storeQty, sPrice, tPrice, orderName, poName, vendor, addDate);
        else if(IsProductBarcode(barcode))
            ((Product_Storage)hStorageHandle.getTableInstance()).AddARecord(barcode, batch_lot, storeQty, sPrice, tPrice, orderName, poName, vendor, addDate);
        else
            ((Other_Storage)hStorageHandle.getTableInstance()).AddARecord(barcode, batch_lot, storeQty, sPrice, tPrice, orderName, poName, vendor, addDate);
    }
    
    public void AddSingleOtherManuRecordToStorage(DBTableParent hStorageHandle, String barcode, String batch_lot, String appProductQTY, String sPrice, String tPrice, 
            String orderName, String poName, String vendor, String storeDate)
    {
        if(IsOtherBarcode(barcode))
            ((Other_Storage)hStorageHandle.getTableInstance()).AddARecord(barcode, batch_lot, appProductQTY, sPrice, tPrice, orderName, poName, vendor, storeDate);
        else if(IsSemiProBarcode(barcode)||IsMaterialBarcode(barcode)||IsProductBarcode(barcode))
            ((Manu_Storage_Record)hStorageHandle.getTableInstance()).AddARecord(barcode, batch_lot, appProductQTY, sPrice, tPrice, orderName, poName, vendor, storeDate);
    }
    
    public void AddSingleExRecordToStorage(DBTableParent hExStorageHandle, String id, String barcode, String batch_lot, String inQty, String outQty, String sPrice, String tPrice, 
                                                String orderName, String poName, String vendor, String storeDate, String isEnsure, String createDate)
    {
        if(IsOtherBarcode(barcode))
            ((Exhausted_Other)hExStorageHandle.getTableInstance()).AddAExRecord(id, barcode, batch_lot, inQty, outQty, sPrice, tPrice, orderName, poName, vendor, storeDate, isEnsure, createDate);
        else if(IsSemiProBarcode(barcode)||IsMaterialBarcode(barcode)||IsProductBarcode(barcode))
            ((Exhausted_Manu_Storage_Record)hExStorageHandle.getTableInstance()).AddAExRecord(id, barcode, batch_lot, inQty, outQty, sPrice, tPrice, orderName, poName, vendor, storeDate, isEnsure, createDate);
    }
    
    public DBTableParent GenExStorageHandle(String barcode)
    {
        if(IsOtherBarcode(barcode))
            return new DatabaseStore("Exhausted_Other");
        else if(IsSemiProBarcode(barcode)||IsMaterialBarcode(barcode)||IsProductBarcode(barcode))
            return new DatabaseStore("Exhausted_Manu");
        return null;
    }
    
    public DBTableParent GenProcessExStorageHandle(String barcode)
    {
        if(IsSemiProBarcode(barcode))
            return new DatabaseStore("Exhausted_Semi_Product");
        else if(IsMaterialBarcode(barcode))
            return new DatabaseStore("Exhausted_Material");
        else if(IsProductBarcode(barcode))
            return new DatabaseStore("Exhausted_Product");
        else
            return new DatabaseStore("Exhausted_Other");
    }
    
    public void CheckMoveToExhaustedTable(String barcode, String batchLot)
    {
        DBTableParent hStorageHandle = GenStorageHandle(barcode);
        hStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
        if (hStorageHandle.getDBRecordList("IN_QTY").equals(hStorageHandle.getDBRecordList("OUT_QTY")))
        {
            String orderKeyWord = "Order_Name", PoKeyWord = "po_name";
            if (GetStorageNameByBarCode(barcode, true).contains("Other"))
            {
                orderKeyWord = "vendor_name";
                PoKeyWord = "vendor_name";
            }
            DBTableParent hExStorageHandle = GenExStorageHandle(barcode);
            hExStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
            if(hExStorageHandle.getTableInstance().RecordDBCount() > 0)
            {
                int setValue = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(0)) + Integer.parseInt(hExStorageHandle.getDBRecordList("IN_QTY").get(0));
                hExStorageHandle.UpdateRecordByKeyList("IN_QTY", Integer.toString(setValue), Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
                hExStorageHandle.UpdateRecordByKeyList("OUT_QTY", Integer.toString(setValue), Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
            }
            else
                AddSingleExRecordToStorage(hExStorageHandle, hStorageHandle.getDBRecordList("id").get(0), hStorageHandle.getDBRecordList("Bar_Code").get(0), 
                        hStorageHandle.getDBRecordList("Batch_Lot").get(0), hStorageHandle.getDBRecordList("IN_QTY").get(0),
                        hStorageHandle.getDBRecordList("OUT_QTY").get(0), hStorageHandle.getDBRecordList("Price_Per_Unit").get(0),
                        hStorageHandle.getDBRecordList("Total_Price").get(0), hStorageHandle.getDBRecordList(orderKeyWord).get(0),
                        hStorageHandle.getDBRecordList(PoKeyWord).get(0), hStorageHandle.getDBRecordList("vendor_name").get(0),
                        hStorageHandle.getDBRecordList("in_store_date").get(0), hStorageHandle.getDBRecordList("isEnsure").get(0),
                        hStorageHandle.getDBRecordList("create_date").get(0));
            hStorageHandle.DeleteRecordByKeyList(Arrays.asList("id"), Arrays.asList(hStorageHandle.getDBRecordList("id").get(0)));
        }
    }
    
    public double GetPrice_Pre_Unit(String bar_code, String Batch_Lot)
    {
        DBTableParent hHandle = GenStorageHandle(bar_code);
        DBTableParent hExHandle = GenExStorageHandle(bar_code);
        hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(bar_code, Batch_Lot));
        hExHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(bar_code, Batch_Lot));
        List<String> tempList = new ArrayList<String>();
        tempList.addAll(hHandle.getDBRecordList("Price_Per_Unit"));
        tempList.addAll(hExHandle.getDBRecordList("Price_Per_Unit"));
        return Double.parseDouble(tempList.get(0));
    }
    
    public int GetAStorageRepertoryByPOName(String barcode, String po_name)
    {
        DBTableParent hHandle = GenStorageHandle(barcode);
        return hHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, po_name, "1"));
    }
    
    public int GetRepertoryByBarcodePo(String strBarcode)
    {
        int rtnRst = 0;
        DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
        DBTableParent hHandle = GenStorageHandle(strBarcode);
        ((DBTableParent)hHandle).QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(strBarcode, "1"));
        List<String> loopList = hHandle.getDBRecordList("po_name");
        for (String poName : loopList)
        {
            hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(poName));
            if(Integer.parseInt(hCPHandle.getDBRecordList("status").get(0)) >= 5)
            {
                rtnRst += GetAStorageRepertoryByPOName(strBarcode, poName);
            }
        }
        return rtnRst;
    }

    public int GetAllRepertoryByPOName(String barcode, String po_name)
    {
        int rtnRst = 0;
        rtnRst += GetRepertoryByBarcodePo(barcode);
        rtnRst += GetAStorageRepertoryByPOName(GetUsedBarcode(barcode, "Semi_Pro_Storage"), po_name);
        rtnRst += GetAStorageRepertoryByPOName(GetUsedBarcode(barcode, "Material_Storage"), po_name);
        return rtnRst;
    }
    
    public String GenYearMonthDayString()
    {
        Calendar mData = Calendar.getInstance();
        return String.format("%04d%02d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1, mData.get(Calendar.DAY_OF_MONTH));
    }

    public String GenYearMonthDayString(String strSpan)
    {
        Calendar mData = Calendar.getInstance();
        return String.format("%04d%s%02d%s%02d", mData.get(Calendar.YEAR), strSpan, mData.get(Calendar.MONDAY)+1, strSpan, mData.get(Calendar.DAY_OF_MONTH));
    }
    
    public String GenYearMonthString()
    {
        Calendar mData = Calendar.getInstance();
        return String.format("%04d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1);
    }

    public String GenYearMonthString(String strSpan)
    {
        Calendar mData = Calendar.getInstance();
        return String.format("%04d%s%02d%s", mData.get(Calendar.YEAR), strSpan, mData.get(Calendar.MONDAY)+1, strSpan);
    }
    
    public String GetEndDayOfMonth(String yearMonth)
    {
        return Integer.toString(DateAdapter.getDayCountOfAMonth(yearMonth));
    }
    
    public String GenYearString()
    {
        Calendar mData = Calendar.getInstance();
        return String.format("%04d", mData.get(Calendar.YEAR));
    }

    public String GenYearString(String strSpan)
    {
        Calendar mData = Calendar.getInstance();
        return String.format("%04d%s", mData.get(Calendar.YEAR), strSpan);
    }
    
    private int GetExAndStorageRecordCount(String strBarcode, String Batch_Lot)
    {
        DBTableParent hHandle = GenStorageHandle(strBarcode);
        DBTableParent hExHandle = GenExStorageHandle(strBarcode);
        hHandle.QueryRecordByFilterKeyList(Arrays.asList("Batch_Lot"), Arrays.asList(Batch_Lot));
        hExHandle.QueryRecordByFilterKeyList(Arrays.asList("Batch_Lot"), Arrays.asList(Batch_Lot));
        return hHandle.getTableInstance().RecordDBCount() + hExHandle.getTableInstance().RecordDBCount();
    }
    
    private int GetExAndProcessStorage(String strBarcode, String Batch_Lot)
    {
        DBTableParent hHandle = GenProcessStorageHandle(strBarcode);
        DBTableParent hExHandle = GenProcessExStorageHandle(strBarcode);
        hHandle.QueryRecordByFilterKeyList(Arrays.asList("Batch_Lot"), Arrays.asList(Batch_Lot));
        hExHandle.QueryRecordByFilterKeyList(Arrays.asList("Batch_Lot"), Arrays.asList(Batch_Lot));
        return hHandle.getTableInstance().RecordDBCount() + hExHandle.getTableInstance().RecordDBCount();
    }
    
    public String GenBatchLot(String strBarcode)
    {
        return GenBatchLot(GenYearMonthDayString(), strBarcode);
    }
    
    public String GenBatchLot(String batch_lot_Head, String strBarcode)
    {
        String rtnRst = "";
        int loopNum = 1;
        do
        {
            rtnRst = batch_lot_Head + "-" + String.format("%02d", loopNum);
            if ((GetExAndProcessStorage(GetUsedBarcode(strBarcode, "Product_Storage"), rtnRst) + GetExAndProcessStorage(GetUsedBarcode(strBarcode, "Semi_Pro_Storage"), rtnRst) +
                    GetExAndProcessStorage(GetUsedBarcode(strBarcode, "Material_Storage"), rtnRst) + GetExAndStorageRecordCount(GetUsedBarcode(strBarcode, "Other_Storage"), rtnRst) +
                    GetExAndStorageRecordCount(strBarcode, rtnRst)) <= 0)
                break;
            loopNum ++;
        }while(true);
        return rtnRst;
    }
    
    public int GetHasFinishPurchaseNum(String barcode, String POName)
    {
        DBTableParent hStorageHandle = GenProcessStorageHandle(barcode);
        DBTableParent hExStorageHandle = GenProcessExStorageHandle(barcode);
        return hStorageHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, POName, "1")) + 
                hExStorageHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, POName));
    }
    
    public int GetHasFinishPurchaseNumWithoutEnsure(String barcode, String POName)
    {
        DBTableParent hStorageHandle = GenProcessStorageHandle(barcode);
        DBTableParent hExStorageHandle = GenProcessExStorageHandle(barcode);
        return hStorageHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, POName)) + 
                hExStorageHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, POName));
    }
    
    public String CheckRequestPara(String checkValue)
    {
        if (checkValue != null)
            checkValue.replace(" ", "");
        return checkValue;
    }
    
    public boolean CheckParamValidityMoreThanLength(String chekcVal, int valLen)
    {
        if(chekcVal != null&&chekcVal.length() > valLen)
            return true;
        return false;
    }
    
    public boolean CheckParamValidityEqualsLength(String chekcVal, int valLen)
    {
        if(chekcVal != null&&chekcVal.length() == valLen)
            return true;
        return false;
    }
    
    public boolean CheckParamValidityMoreThanValue(String chekcVal, int val)
    {
        if(chekcVal != null&&Integer.parseInt(chekcVal) > val)
            return true;
        return false;
    }
    
    public List<String> GetStoreName(String storeType)
    {
        List<String> rtnRst = new ArrayList<String>();
        List<String> displayStoreName = Arrays.asList("成品库", "半成品库", "原材料库");
        DBTableParent hSNHandle = new DatabaseStore("Storeroom_Name");
        hSNHandle.QueryAllRecord();
        List<String> tempList = hSNHandle.getDBRecordList("name");
        
        for(String storeName : tempList)
        {
            if((storeType.contains("MFG")&&!displayStoreName.contains(storeName))||
                    (!storeType.contains("MFG")&&displayStoreName.contains(storeName)))
                continue;
            rtnRst.add(storeName);
        }
        return rtnRst;
    }
    
    public boolean CheckBarcodeStatus(String barcode)
    {
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        String[] storageList = new String[] {"Material_Storage", "Product_Storage", "Semi_Pro_Storage", "Other_Storage"};
        int recordCount = 0;
        for(String storageName : storageList)
        {
            hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(GetUsedBarcode(barcode, storageName)));
            recordCount += hPIHandle.getTableInstance().RecordDBCount();
        }
        return recordCount > 0?false:true;
    }
    
    public String GetProductInfoByBarcode(String barcode, String keyWord)
    {
        String rtnRst = null;
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
        if(hPIHandle.getTableInstance().RecordDBCount() > 0)
            rtnRst = hPIHandle.getDBRecordList(keyWord).get(0);
        return rtnRst;
    }
    
    public boolean IsCustomerPoClose(String poname)
    {
        DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
        hCPHandle.QueryRecordByFilterKeyListAndMoreThanKeyValue(Arrays.asList("po_name"), Arrays.asList(poname), "status", "0");
        if (hCPHandle.getTableInstance().RecordDBCount() > 0||"Material_Supply" == poname)
            return true;
        return false;
    }
    
    public List<String> GetAllUserRecordByName(String queryKeyVal, String getKeyWord)
    {
        DBTableParent hUIHandle = new DatabaseStore("User_Info");
        if(queryKeyVal.equals("AllRecord"))
            hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("isAbsense"), Arrays.asList("1"));
        else
            hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "isAbsense"), Arrays.asList(queryKeyVal, "1"));
        List<String> rtnRst = hUIHandle.getDBRecordList(getKeyWord);
        if(getKeyWord.equals("name"))
            rtnRst.remove("root");
        else if(getKeyWord.equals("check_in_id"))
            rtnRst.remove("99999");
        return rtnRst;
    }
    
    public List<String> GetAllProductNameList()
    {
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        hPIHandle.QueryAllRecord();
        List<String> rtnRst = hPIHandle.getDBRecordList("name");
        return rtnRst;
    }
    
    public String GetDisplayMonth()
    {
        String tempDate = DateAdapter.getPrecedingMonthString(GenYearMonthDayString(""));
        return String.format("%s-%s-%s", tempDate.substring(0, 4), tempDate.substring(4, 6), tempDate.substring(6, 8));
    }
    
    public boolean CheckUserName(String userName)
    {
        DBTableParent hUIHandle = new DatabaseStore("User_Info");
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(userName));
        if(hUIHandle.getTableInstance().RecordDBCount() > 0)
        	return true;
        return false;
    }
}
