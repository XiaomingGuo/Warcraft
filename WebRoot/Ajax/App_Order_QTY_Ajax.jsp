<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.Product_Storage" %>
<%@ page import="com.DB.operation.Material_Storage" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String pro_name = (String)request.getParameter("product_name").replace(" ", "");
	String pro_type = (String)request.getParameter("product_type").replace(" ", "");
	Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
	hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "product_type"), Arrays.asList(pro_name, pro_type));
	String strBarcode = hPIHandle.getDBRecordList("Bar_Code").get(0);
	
	Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
	Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
	
	int iProRepertory = hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
	int iMatRepertory = hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
	rtnRst += strBarcode + "$";
	rtnRst += Integer.toString(iProRepertory) + "$";
	rtnRst += Integer.toString(iMatRepertory) + "$";
	out.write(rtnRst);
%>
