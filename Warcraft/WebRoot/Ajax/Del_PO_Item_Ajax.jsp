<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Customer_Po_Record" %>
<%@ page import="com.DB.operation.Mb_Material_Po"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Del_PO_Item_Ajax" %>
<%
	String rtnRst = "remove$";
	String pro_id = (String)request.getParameter("product_id").replace(" ", "");
	
	if (pro_id != null)
	{
		Del_PO_Item_Ajax hPageHandle = new Del_PO_Item_Ajax();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(pro_id));
		hCPRHandle.DeleteRecordByKeyList(Arrays.asList("id"), Arrays.asList(pro_id));
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.DeleteRecordByKeyList(Arrays.asList("Bar_Code", "po_name", "vendor"), 
				Arrays.asList(hCPRHandle.getDBRecordList("Bar_Code").get(0), 
						hCPRHandle.getDBRecordList("po_name").get(0), 
						hCPRHandle.getDBRecordList("vendor").get(0)));
	}
	out.write(rtnRst);
%>
