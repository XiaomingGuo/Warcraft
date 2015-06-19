<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String ordername = (String)request.getParameter("Order_Name");
	
	if (ordername != null)
	{
		String sql = "update product_order set status='3' where Order_Name='" + ordername + "'";
		hDBHandle.execUpate(sql);
		sql = "select * from product_order_record  where Order_Name='" + ordername + "'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			List<String> idList = hDBHandle.GetAllStringValue("id");
			for (int index = 0; index < idList.size(); index++)
			{
				sql = "update product_order_record set status='3' where id='" + idList.get(index) + "'";
				hDBHandle.execUpate(sql);
			}
		}
	}
	out.write(rtnRst);
%>
