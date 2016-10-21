<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.Product_Storage" %>
<%@ page import="com.DB.operation.Semi_Product_Storage" %>
<%@ page import="com.DB.operation.Material_Storage" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.PageParentClass" %>
<%
	String rtnRst = "remove$";
	String pro_name = (String)request.getParameter("product_name").replace(" ", "");
	String pro_type = (String)request.getParameter("product_type").replace(" ", "");
	String po_name = (String)request.getParameter("po_name").replace(" ", "");
	
	PageParentClass hPageHandle = new PageParentClass();
	DBTableParent hPIHandle = new DatabaseStore("Product_Info");
	hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "product_type"), Arrays.asList(pro_name, pro_type));
	String strBarcode = hPIHandle.getDBRecordList("Bar_Code").get(0);
	
	Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
	Semi_Product_Storage hSPSHandle = new Semi_Product_Storage(new EarthquakeManagement());
	Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
	
	int iProRepertory = hPageHandle.GetAllRepertoryByPOName(hPageHandle.GetUsedBarcode(strBarcode, "Product_Storage"), po_name);
	int iSemiProductRepertory = hSPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(hSPSHandle.GetUsedBarcode(strBarcode, "semi_pro_storage"), "Material_Supply", "1"));
	int iMatRepertory = hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(hMSHandle.GetUsedBarcode(strBarcode, "material_storage"), "Material_Supply", "1"));
	rtnRst += strBarcode + "$";
	rtnRst += Integer.toString(iProRepertory) + "$";
	rtnRst += Integer.toString(iSemiProductRepertory) + "$";
	rtnRst += Integer.toString(iMatRepertory) + "$";
	out.write(rtnRst);
%>
