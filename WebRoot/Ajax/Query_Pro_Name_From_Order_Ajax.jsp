<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<List<String>> pro_info = null;
	List<String> pro_type = null;
%>
<%
	String pro_order=(String)request.getParameter("FilterKey1");
	String rtnRst = "remove$";
	//product_info Database query
	String sql = "select * from product_order_record where Order_Name='" + pro_order +"'";
	if (hDBHandle.QueryDataBase(sql))
	{
		String[] keyWord = {"product_name", "Bar_Code"};
		pro_info = hDBHandle.GetAllDBColumnsByList(keyWord);
	}
	else
	{
		hDBHandle.CloseDatabase();
	}
	if (pro_info != null)
	{
		pro_type = new ArrayList<String>();
		for(int i = 0; i < pro_info.get(0).size(); i++)
		{
			String temp_type = "";
			String temp_Code = Integer.toString(Integer.parseInt(pro_info.get(1).get(i)) - 10000000);
			sql = "select product_type from product_info where Bar_Code='" + temp_Code +"'";
			if (hDBHandle.QueryDataBase(sql))
			{
				temp_type = hDBHandle.GetSingleString("product_type");
				pro_type.add(temp_type);
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
		}
		for(int i = 0; i < pro_info.get(0).size(); i++)
		{
			rtnRst += pro_type.get(i) + "-" + pro_info.get(0).get(i);
			rtnRst += '$';
		}
	}
	out.write(rtnRst);
%>
