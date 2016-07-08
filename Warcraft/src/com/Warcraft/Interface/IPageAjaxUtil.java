package com.Warcraft.Interface;

import java.util.List;

public interface IPageAjaxUtil
{
    public String GenerateAjaxString(List<List<String>> recordList);
    public List<List<String>> GenDisplayResultList();
    public void setTableHandle(IPageInterface hHandle);
    public IPageInterface getTableHandle();
}
