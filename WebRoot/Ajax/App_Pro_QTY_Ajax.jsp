<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<List<String>> proInfo = null;
%>
<%
	int iRepertory = 0;
	String rtnRst = "remove$";
	String pro_name = (String)request.getParameter("product_name").replace(" ", "");
	String pro_type = (String)request.getParameter("product_type").replace(" ", "");
	String storageName = (String)request.getParameter("storage").replace(" ", "");
	if(storageName == null||storageName == "")
	{
		storageName = "other_storage";
	}
	String sql= "select * from product_info where name='"+pro_name+"' and product_type='"+ pro_type + "'";
	if (hDBHandle.QueryDataBase(sql))
	{
		String[] keyWord = {"Bar_Code", "weight", "description"};
		proInfo = hDBHandle.GetAllDBColumnsByList(keyWord);
	}
	else
	{
		hDBHandle.CloseDatabase();
	}
	if (proInfo != null && proInfo.size() > 0)
	{
		for (int i = 0; i < proInfo.get(0).size(); i++)
		{
			String bar_Code = proInfo.get(0).get(i);
			iRepertory += hDBHandle.GetIN_QTYByBarCode(bar_Code, storageName) - hDBHandle.GetOUT_QTYByBarCode(bar_Code, storageName);
			rtnRst += bar_Code + "$" + proInfo.get(1).get(i) + "$" + proInfo.get(2).get(i) + "$" ;
		}
	}
	rtnRst += Integer.toString(iRepertory);
	out.write(rtnRst);
%>
