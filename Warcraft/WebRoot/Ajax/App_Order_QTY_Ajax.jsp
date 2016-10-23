<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent" %>
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
	
	DBTableParent hPSHandle = new DatabaseStore("Product_Storage");
	DBTableParent hSPSHandle = new DatabaseStore("Semi_Product_Storage");
	DBTableParent hMSHandle = new DatabaseStore("Material_Storage");
	
	int iProRepertory = hPageHandle.GetAllRepertoryByPOName(hPageHandle.GetUsedBarcode(strBarcode, "Product_Storage"), po_name);
	int iSemiProductRepertory = hSPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(hSPSHandle.GetUsedBarcode(strBarcode, "semi_pro_storage"), "Material_Supply", "1"));
	int iMatRepertory = hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(hMSHandle.GetUsedBarcode(strBarcode, "material_storage"), "Material_Supply", "1"));
	rtnRst += strBarcode + "$";
	rtnRst += Integer.toString(iProRepertory) + "$";
	rtnRst += Integer.toString(iSemiProductRepertory) + "$";
	rtnRst += Integer.toString(iMatRepertory) + "$";
	out.write(rtnRst);
%>
