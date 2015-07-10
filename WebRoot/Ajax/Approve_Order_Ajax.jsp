<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name");
	List<String> idList = null;
	
	if (order_name != null||order_name.indexOf("生产单号") < 0)
	{
		String sql= "select id from product_order_record where Order_Name='"+order_name+"'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			idList = hDBHandle.GetAllStringValue("id");
			for (int index = 0; index < idList.size(); index++)
			{
				sql = "UPDATE product_order_record SET status=1 where id='" + idList.get(index) + "'";
				hDBHandle.execUpate(sql);
			}
			sql = "UPDATE product_order SET status=1 where Order_Name='" + order_name + "'";
			hDBHandle.execUpate(sql);
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	out.write(rtnRst);
%>
