<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String po_name = (String)request.getParameter("POName").replace(" ", "");
	if (po_name != null)
	{
		Calendar mData = Calendar.getInstance();
		String currentDate = String.format("%04d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1);
		String sql = "select * from shipping_record where customer_po='" + po_name + "' and shipping_no='0'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			List<String> idList = hDBHandle.GetAllStringValue("id");
			String ship_no = null;
			sql = "select * from shipping_no where shipping_no > " + currentDate + "0000";
			if (hDBHandle.QueryDataBase(sql))
			{
 				ship_no = String.format("%s%04d", currentDate, hDBHandle.GetRecordCount() + 1);
				sql = "INSERT INTO shipping_no (customer_po, shipping_no) VALUES ('" + po_name + "','" + ship_no + "')";
				hDBHandle.execUpate(sql);
			}

			for (int index = 0; index < idList.size(); index++)
			{
				sql = "update shipping_record set shipping_no='" + ship_no + "' where id='" + idList.get(index) + "'";
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
