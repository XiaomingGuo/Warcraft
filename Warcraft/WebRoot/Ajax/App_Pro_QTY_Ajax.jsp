<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Process_Detail_Info" %>
<%
	String rtnRst = "remove$";
	String pro_name = (String)request.getParameter("product_name");
	String pro_type = (String)request.getParameter("product_type");
	Query_Process_Detail_Info hPageHandle = new Query_Process_Detail_Info();
	List<List<String>> recordList = hPageHandle.GetProductInfoByProNameAndProType(pro_name, pro_type);
	
	int iRepertory = 0;
	if (recordList.size() > 0)
	{
		List<String> barcodeList = recordList.get(0);
		List<String> weightList = recordList.get(1);
		List<String> descList = recordList.get(2);
		
		for (int i = 0; i < barcodeList.size(); i++)
		{
			String bar_Code = barcodeList.get(i);
			iRepertory += hPageHandle.GetIN_QTYByBarCode(bar_Code) - hPageHandle.GetOUT_QTYByBarCode(bar_Code);
			rtnRst += bar_Code + "$" + hPageHandle.GetWeightByBarcode(bar_Code, "Material_Storage") + "$";
			rtnRst += descList.get(i) + "$" + hPageHandle.GetWeightByBarcode(bar_Code, "Product_Storage") + "$";
		}
	}
	rtnRst += Integer.toString(iRepertory);
	out.write(rtnRst);
%>
