package com.page.utilities;

import java.util.ArrayList;
import java.util.List;

import com.Warcraft.Interface.IPageAjaxUtil;
import com.Warcraft.Interface.IPageInterface;

public class CPageAjaxUtil  implements IPageAjaxUtil
{
    IPageInterface m_PageHandle;
    
    @Override
    public void setTableHandle(IPageInterface hHandle)
    {
        this.m_PageHandle = hHandle;
    }
    
    @Override
    public IPageInterface getTableHandle()
    {
        return this.m_PageHandle;
    }
    
    private String PrepareHeader(List<List<String>> recordList)
    {
        String rtnRst = "remove$";
        String[] displayArray = m_PageHandle.GetDisplayArray();
        if (recordList.size() > 0)
        {
            rtnRst += Integer.toString(displayArray.length) + "$";
            rtnRst += Integer.toString(recordList.get(0).size()) + "$";
            for(int i = 0; i < displayArray.length; i++)
            {
                rtnRst += displayArray[i] + "$";
            }
        }
        return rtnRst;
    }
    
    @Override
    public List<List<String>> GenDisplayResultList()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        for(int idx = 0; idx < m_PageHandle.GetDisplayArray().length; idx++)
            rtnRst.add(new ArrayList<String>());
        return rtnRst;
    }
    
    @Override
    public String GenerateAjaxString(List<List<String>> recordList)
    {
        String rtnRst = PrepareHeader(recordList);
        
        if (recordList.size() > 0)
        {
            for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
            {
                for(int iCol = 0; iCol < m_PageHandle.GetDisplayArray().length; iCol++)
                {
                    rtnRst += recordList.get(iCol).get(iRow) + "$";
                }
            }
        }
        return rtnRst;
    }
}
