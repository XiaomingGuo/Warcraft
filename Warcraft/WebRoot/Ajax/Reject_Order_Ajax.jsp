<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name").replace(" ", "");
	
	if (order_name != null||order_name.indexOf("生产单号") < 0)
	{
		DBTableParent hPORHandle = new DatabaseStore("Product_Order_Record");
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(order_name));
		List<String> delPoList = hPORHandle.getDBRecordList("po_name");
		String delPoKeyWord = "poName", delOrderKeyWord = "orderName";
		for(int idx = 0; idx < delPoList.size(); idx++)
		{
			if(delPoList.get(idx).toLowerCase().indexOf("internal_po") >= 0)
			{
				delPoList.set(idx, order_name);
				delPoKeyWord = "orderName";
			}
		}

		if(delPoList != null && delPoList.size() > 0)
		{
			hPORHandle.QueryRecordByFilterKeyList(Arrays.asList(delPoKeyWord), Arrays.asList(delPoList.get(0)));
			List<String> delOrderList = hPORHandle.getDBRecordList("Order_Name");
			hPORHandle.DeleteAllRecordListByKeyWord(delPoKeyWord, delPoList);
			DBTableParent hPOHandle = new DatabaseStore("Product_Order");
			hPOHandle.DeleteAllRecordListByKeyWord(delOrderKeyWord, delOrderList);
			DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
			hCPHandle.DeleteAllRecordListByKeyWord("poName", delPoList);
			DBTableParent hMMPRHandle = new DatabaseStore("Mb_Material_Po");
			hMMPRHandle.DeleteAllRecordListByKeyWord("po_name", delPoList);
		}
	}
	out.write(rtnRst);
%>
