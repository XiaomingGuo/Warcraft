<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String date_of_delivery = (String)request.getParameter("date_of_delivery");
	String ordername = (String)request.getParameter("Order_Name");
	if (ordername != null)
	{
		String sql = "select * from shipping_record where customer_po='" + ordername + "' and print_mark='00000000'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			List<String> idList = hDBHandle.GetAllStringValue("id");
			for (int index = 0; index < idList.size(); index++)
			{
				sql = "update shipping_record set print_mark='" + date_of_delivery + "' where id='" + idList.get(index) + "'";
				hDBHandle.execUpate(sql);
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	out.write(rtnRst);
%>
