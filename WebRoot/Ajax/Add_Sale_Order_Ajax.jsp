<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String date_of_delivery = (String)request.getParameter("date_of_delivery");
	String po_name = (String)request.getParameter("POName");
	if (po_name != null && date_of_delivery != null)
	{
		Calendar mData = Calendar.getInstance();
		String currentDate = String.format("%04d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1);
		String sql = "select * from shipping_record where customer_po='" + po_name + "' and print_mark='00000000'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			List<String> idList = hDBHandle.GetAllStringValue("id");
			for (int index = 0; index < idList.size(); index++)
			{
				sql = "update shipping_record set print_mark='" + date_of_delivery + "' where id='" + idList.get(index) + "'";
				hDBHandle.execUpate(sql);
			}
			
			sql = "select * from shipping_no where shipping_no > " + currentDate + "0000";
			if (hDBHandle.QueryDataBase(sql))
			{
 				String ship_no = String.format("%s%04d", currentDate, hDBHandle.GetRecordCount() + 1);
				sql = "INSERT INTO shipping_no (customer_po, print_mark, shipping_no) VALUES ('" + po_name + "','" + date_of_delivery + "','" + ship_no + "')";
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
