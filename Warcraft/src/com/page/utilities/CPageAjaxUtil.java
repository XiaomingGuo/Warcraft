package com.page.utilities;

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
    
    @Override
    public String PrepareHeader(List<List<String>> recordList)
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
}
