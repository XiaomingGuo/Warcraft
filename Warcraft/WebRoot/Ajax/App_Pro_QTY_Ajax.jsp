<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.App_Pro_QTY_Ajax" %>
<%
	int iRepertory = 0;
	String rtnRst = "remove$";
	String pro_name = (String)request.getParameter("product_name");
	String pro_type = (String)request.getParameter("product_type");
	
	Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
	hPIHandle.GetRecordByNameAndProType(pro_name, pro_type);
	List<String> barcodeList = hPIHandle.getDBRecordList("Bar_Code");
	List<String> weightList = hPIHandle.getDBRecordList("weight");
	List<String> descList = hPIHandle.getDBRecordList("description");
	
	if (barcodeList != null && weightList != null && descList != null)
	{
		App_Pro_QTY_Ajax hPageHandle = new App_Pro_QTY_Ajax();
		for (int i = 0; i < barcodeList.size(); i++)
		{
			String bar_Code = barcodeList.get(i);
			iRepertory += hPageHandle.GetIN_QTYByBarCode(bar_Code) - hPageHandle.GetOUT_QTYByBarCode(bar_Code);
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPIHandle.GetUsedBarcode(bar_Code, "Material_Storage")));
			rtnRst += bar_Code + "$" + hPIHandle.getDBRecordList("weight").get(0) + "$" ;
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPIHandle.GetUsedBarcode(bar_Code, "Product_Storage")));
			rtnRst += descList.get(i) + "$" + hPIHandle.getDBRecordList("weight").get(0) + "$";
		}
	}
	rtnRst += Integer.toString(iRepertory);
	out.write(rtnRst);
%>
