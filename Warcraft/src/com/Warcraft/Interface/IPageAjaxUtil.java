package com.Warcraft.Interface;

import java.util.List;

public interface IPageAjaxUtil
{
    public String PrepareHeader(List<List<String>> recordList);
    public void setTableHandle(IPageInterface hHandle);
    public IPageInterface getTableHandle();
}
