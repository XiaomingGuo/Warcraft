<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%
	String rtnRst = "remove$";
	String ordername = (String)request.getParameter("Order_Name").replace(" ", "");
	
	if (ordername != null)
	{
		DBTableParent hPOHandle = new DatabaseStore("Product_Order");
		DBTableParent hPORHandle = new DatabaseStore("Product_Order_Record");
		hPOHandle.UpdateRecordByKeyList("status", "3", Arrays.asList("Order_Name"), Arrays.asList(ordername));
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(ordername));
		if (hPORHandle.getTableInstance().RecordDBCount() > 0)
		{
			List<String> idList = hPORHandle.getDBRecordList("id");
			for (int index = 0; index < idList.size(); index++)
			{
				hPORHandle.UpdateRecordByKeyList("status", "3", Arrays.asList("id"), Arrays.asList(idList.get(index)));
			}
		}
	}
	out.write(rtnRst);
%>
