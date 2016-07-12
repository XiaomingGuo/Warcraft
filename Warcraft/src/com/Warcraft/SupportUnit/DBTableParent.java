package com.Warcraft.SupportUnit;

import java.util.List;

import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public abstract class DBTableParent
{
    private IEQManagement hEQMHandle = null;

    public DBTableParent(IEQManagement hEQMHandle)
    {
        this.setEQMHandle(hEQMHandle);
        this.getEQMHandle().setTableHandle((ITableInterface)this);
    }

    public IEQManagement getEQMHandle()
    {
        return hEQMHandle;
    }

    public void setEQMHandle(IEQManagement hEQMHandle)
    {
        this.hEQMHandle = hEQMHandle;
    }
    
    public String GetUsedBarcode(String barcode, String storage_name)
    {
        String rtnRst = barcode;
        if (Integer.parseInt(barcode) >= 50000000 && Integer.parseInt(barcode) < 60000000)
        {
            if (storage_name.toLowerCase().indexOf("semi") >= 0)
                rtnRst = Integer.toString(Integer.parseInt(barcode)+20000000);
            else if(storage_name.toLowerCase().indexOf("product") >= 0)
                rtnRst = Integer.toString(Integer.parseInt(barcode)+10000000);
        }
        else if(Integer.parseInt(barcode) >= 60000000 && Integer.parseInt(barcode) < 70000000)
        {
            if (storage_name.toLowerCase().indexOf("semi") >= 0)
                rtnRst = Integer.toString(Integer.parseInt(barcode)+10000000);
            else if(storage_name.toLowerCase().indexOf("material") >= 0)
                rtnRst = Integer.toString(Integer.parseInt(barcode)-10000000);
        }
        else if(Integer.parseInt(barcode) >= 70000000 && Integer.parseInt(barcode) < 80000000)
        {
            if (storage_name.toLowerCase().indexOf("product") >= 0)
                rtnRst = Integer.toString(Integer.parseInt(barcode)-10000000);
            else if(storage_name.toLowerCase().indexOf("material") >= 0)
                rtnRst = Integer.toString(Integer.parseInt(barcode)-20000000);
        }
        return rtnRst;
    }
    
    public double GetDblSumOfValue(String storage_name, String getValue, String keyword, String keyValue)
    {
        double rtnRst = 0.0;
        String hql = String.format("from %s st where st.%s='%s'", storage_name, ((ITableInterface)this).GetDatabaseKeyWord(keyword), keyValue);
        getEQMHandle().EQQuery(hql);
        if (((ITableInterface)this).RecordDBCount() > 0)
        {
            List<String> in_Qty_List = ((ITableInterface)this).getDBRecordList(getValue);
            for (int i = 0; i < in_Qty_List.size(); i++)
            {
                rtnRst += Double.parseDouble(in_Qty_List.get(i));
            }
        }
        return rtnRst;
    }
    
    public int GetIntSumOfValue(String getValue, List<String> keyList, List<String> valList)
    {
        int rtnRst = 0;
        String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valList);
        getEQMHandle().EQQuery(hql);
        if (((ITableInterface)this).RecordDBCount() > 0)
        {
            List<String> in_Qty_List = ((ITableInterface)this).getDBRecordList(getValue);
            for (int i = 0; i < in_Qty_List.size(); i++)
            {
                rtnRst += Integer.parseInt(in_Qty_List.get(i));
            }
        }
        return rtnRst;
    }

    public int GetRepertoryByKeyList(List<String> keyList, List<String> valList)
    {
        return GetIntSumOfValue("IN_QTY", keyList, valList) - GetIntSumOfValue("OUT_QTY", keyList, valList);
    }
    
    public double GetDblPriceOfStorage(String storage_name, String getValIN, String getValOUT, String getValPerPrice, String keyword, String keyValue)
    {
        double rtnRst = 0.0;
        String hql = String.format("from %s st where st.%s='%s'", storage_name, ((ITableInterface)this).GetDatabaseKeyWord(keyword), keyValue);
        getEQMHandle().EQQuery(hql);
        if (((ITableInterface)this).RecordDBCount() > 0)
        {
            List<String> in_Qty_List = ((ITableInterface)this).getDBRecordList(getValIN);
            List<String> out_Qty_List = ((ITableInterface)this).getDBRecordList(getValOUT);
            List<String> perPrice = ((ITableInterface)this).getDBRecordList(getValPerPrice);
            for (int i = 0; i < in_Qty_List.size(); i++)
            {
                int repertory = Integer.parseInt(in_Qty_List.get(i)) - Integer.parseInt(out_Qty_List.get(i));
                rtnRst += repertory*Double.parseDouble(perPrice.get(i));
            }
        }
        return rtnRst;
    }
    
    public void UpdateRecordByKeyList(String setKeyWord, String setValue,
            List<String> keyList, List<String> valueList)
    {
        String hql = String.format("update %s tbn set tbn.%s='%s' where", ((ITableInterface)this).GetTableName(), ((ITableInterface)this).GetDatabaseKeyWord(setKeyWord), setValue) + GenerateWhereString(keyList, valueList);
        getEQMHandle().DeleteAndUpdateRecord(hql);
    }
    
    public void DeleteRecordByKeyList(List<String> keyList, List<String> valueList)
    {
        String hql = String.format("delete %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valueList);
        getEQMHandle().DeleteAndUpdateRecord(hql);
    }
    
    public void QueryRecordByFilterKeyList(List<String> keyList, List<String> valueList)
    {
        String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valueList);
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryRecordByFilterKeyListGroupByList(List<String> keyList, List<String> valueList, List<String> groupList)
    {
        String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valueList) + " group by "+ GenerateGroupAndOrderString(groupList);
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryRecordGroupByList(List<String>groupList)
    {
        String hql = String.format("from %s tbn", ((ITableInterface)this).GetTableName()) + " group by "+ GenerateGroupAndOrderString(groupList);
        getEQMHandle().EQQuery(hql);
    }

    public void QueryRecordByFilterKeyListWithOrderAndLimit(List<String> keyList, List<String> valueList, List<String> orderList, int iStart, int iCount)
    {
        String hql = "";
        if(null != keyList||null != valueList)
            hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valueList) + " order by " + GenerateGroupAndOrderString(orderList) + " desc";
        else
            hql = String.format("from %s tbn", ((ITableInterface)this).GetTableName()) + " order by " + GenerateGroupAndOrderString(orderList) + " desc";
        getEQMHandle().EQQueryWithLimit(hql, iStart, iCount);
    }
    
    public void QueryRecordByFilterKeyListAndBetweenDateSpan(List<String> keyList, List<String> valueList, String betweenKeyWord, String beginDate, String endDate)
    {
        String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valueList);
        hql += String.format(" and tbn.%s>'%s' and tbn.%s<'%s'", ((ITableInterface)this).GetDatabaseKeyWord(betweenKeyWord), beginDate,
                ((ITableInterface)this).GetDatabaseKeyWord(betweenKeyWord), endDate);
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryRecordByFilterKeyListAndBetweenDateSpanOrderByListASC(List<String> keyList, List<String> valueList, 
    		String betweenKeyWord, String beginDate, String endDate, List<String> orderList)
    {
        String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valueList);
        hql += String.format(" and tbn.%s>'%s' and tbn.%s<'%s'", ((ITableInterface)this).GetDatabaseKeyWord(betweenKeyWord), beginDate,
                ((ITableInterface)this).GetDatabaseKeyWord(betweenKeyWord), endDate);
        hql += " order by " + GenerateGroupAndOrderString(orderList) + " asc";
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryRecordByFilterKeyList_GroupByList_BetweenDateSpan(List<String> keyList, List<String> valueList, List<String>groupList, String betweenKeyWord, String beginDate, String endDate)
    {
        String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valueList);
        hql += String.format(" and tbn.%s>'%s' and tbn.%s<'%s'", ((ITableInterface)this).GetDatabaseKeyWord(betweenKeyWord), beginDate,
                ((ITableInterface)this).GetDatabaseKeyWord(betweenKeyWord), endDate);
        hql += " group by "+ GenerateGroupAndOrderString(groupList);
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryRecordByFilterKeyListOrderbyListASC(List<String> keyList, List<String> valueList, List<String> orderList)
    {
        String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName()) + GenerateWhereString(keyList, valueList)
                + " order by " + GenerateGroupAndOrderString(orderList) + " asc";
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryRecordByDateSpan(String beginDate, String endDate)
    {
        String hql = String.format("from %s tbn where", ((ITableInterface)this).GetTableName());
        hql += String.format(" tbn.createDate>'%s' and tbn.createDate<'%s'", beginDate, endDate);
        getEQMHandle().EQQuery(hql);
    }
    
    public void QueryAllRecord()
    {
        String hql = String.format("from %s tbn", ((ITableInterface)this).GetTableName());
        getEQMHandle().EQQuery(hql);
    }
    
    public String GenerateWhereString(List<String> keyList, List<String> valueList)
    {
        String rtnRst = "";
        for(int idx=0; idx<keyList.size()-1; idx++)
        {
            rtnRst += String.format(" tbn.%s='%s' and ", ((ITableInterface)this).GetDatabaseKeyWord(keyList.get(idx)), valueList.get(idx));
        }
        rtnRst+= String.format(" tbn.%s='%s'", ((ITableInterface)this).GetDatabaseKeyWord(keyList.get(keyList.size()-1)), valueList.get(valueList.size()-1));
        return rtnRst;
    }
    
    public String GenerateGroupAndOrderString(List<String> orderList)
    {
        String rtnRst = "";
        for(int idx=0; idx < orderList.size() - 1; idx++)
        {
            rtnRst += String.format("tbn.%s, ", ((ITableInterface)this).GetDatabaseKeyWord(orderList.get(idx)));
        }
        rtnRst += String.format("tbn.%s", ((ITableInterface)this).GetDatabaseKeyWord(orderList.get(orderList.size()-1)));
        return rtnRst;
    }
}