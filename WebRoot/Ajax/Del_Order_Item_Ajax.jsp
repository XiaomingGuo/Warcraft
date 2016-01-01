<%@page import="com.DB.operation.Mb_Material_Po"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String pro_id = (String)request.getParameter("product_id").replace(" ", "");
	String Order_Name = (String)request.getParameter("Order_Name").replace(" ", "");
	String Bar_Code = (String)request.getParameter("Bar_Code").replace(" ", "");
	
	if (pro_id != null)
	{
		Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
		hPORHandle.DeleteRecordByKeyWord("id", Arrays.asList(pro_id));
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.DeleteRecordByKeyList(Arrays.asList("po_name", "Bar_Code"), Arrays.asList(Order_Name, hMMPHandle.GetUsedBarcode(Bar_Code, "mb_material_po")));
	}
	out.write(rtnRst);
%>
