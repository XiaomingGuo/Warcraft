<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%
	String rtnRst = "remove$";
	String pro_id = (String)request.getParameter("product_id").replace(" ", "");
	String Order_Name = (String)request.getParameter("Order_Name").replace(" ", "");
	String Bar_Code = (String)request.getParameter("Bar_Code").replace(" ", "");
	
	if (pro_id != null)
	{
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(pro_id));
		hCPRHandle.DeleteRecordByKeyList(Arrays.asList("id"), Arrays.asList(pro_id));
		DBTableParent hMMPHandle = new DatabaseStore("Mb_Material_Po");
		hMMPHandle.DeleteRecordByKeyList(Arrays.asList("Bar_Code", "po_name", "vendor"), 
				Arrays.asList(hCPRHandle.getDBRecordList("Bar_Code").get(0), 
						hCPRHandle.getDBRecordList("po_name").get(0), 
						hCPRHandle.getDBRecordList("vendor").get(0)));
	}
	out.write(rtnRst);
%>
