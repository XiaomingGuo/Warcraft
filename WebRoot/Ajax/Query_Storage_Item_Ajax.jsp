<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryStorageItemAjax" %>
<%!
	String[] sqlKeyList = {"name", "Bar_Code", "product_type"};
	String[] displayList = {"ID", "产品名称", "八码", "产品类型", "进货数量", "出库数量", "库存", "总价值"};
%>
<%
	String rtnRst = "remove$";
	String storage_name = request.getParameter("storage_name");
	String product_type = request.getParameter("product_type");
	String product_name = request.getParameter("product_name");
	String bar_code = request.getParameter("bar_code");
	QueryStorageItemAjax hPageHandle = new QueryStorageItemAjax();
	List<String> bar_code_List = new ArrayList<String>();
	if (storage_name.indexOf("请选择") < 0 && product_type.indexOf("请选择") >= 0 &&product_name.indexOf("请选择") >= 0 )
	{
		List<String> pro_Type_List = hPageHandle.QueryProTypeStorage(storage_name);
		for(int idx = 0; idx < pro_Type_List.size(); idx++)
		{
			List<String> tempList = hPageHandle.QueryProNameByProType(pro_Type_List.get(idx));
			for(int iRecordIdx = 0; iRecordIdx < tempList.size(); iRecordIdx++)
				bar_code_List.add(hPageHandle.QueryBarCodeByProName(tempList.get(iRecordIdx)));
		}
	}
	else if(storage_name.indexOf("请选择") < 0&&product_type.indexOf("请选择") < 0&&product_name.indexOf("请选择") >= 0)
	{
		List<String> tempList = hPageHandle.QueryProNameByProType(product_type);
		for(int iRecordIdx = 0; iRecordIdx < tempList.size(); iRecordIdx++)
			bar_code_List.add(hPageHandle.QueryBarCodeByProName(tempList.get(iRecordIdx)));
	}
	else if(storage_name.indexOf("请选择") < 0&&product_type.indexOf("请选择") < 0&&product_name.indexOf("请选择") < 0)
	{
		bar_code_List.add(bar_code);
	}
	rtnRst += displayList.length + "$";
	rtnRst += bar_code_List.size()+1 + "$";
	for(int idx = 0; idx < displayList.length; idx++)
		rtnRst += displayList[idx] + "$";
	
	List<String> recordList = hPageHandle.GetAllRecordByBarCodeList(bar_code_List);
	double totalPrice = 0.0;
	int inSum=0, outSum=0, repertorySum=0;
	for(int idx = 0; idx < recordList.size(); idx++)
	{
		rtnRst += recordList.get(idx) + "$";
		if(idx%displayList.length == 7)
			totalPrice+=Double.parseDouble(recordList.get(idx));
		else if(idx%displayList.length == 6)
			repertorySum += Integer.parseInt(recordList.get(idx));
		else if(idx%displayList.length == 5)
			outSum += Integer.parseInt(recordList.get(idx));
		else if(idx%displayList.length == 4)
			inSum += Integer.parseInt(recordList.get(idx));
	}
	
	for(int idx = 0; idx < displayList.length-5; idx++)
	{
		rtnRst += "$";
	}
	
	rtnRst += "汇总$"+Integer.toString(inSum)+"$";
	rtnRst += Integer.toString(outSum)+"$";
	rtnRst += Integer.toString(repertorySum)+"$";
	rtnRst += Double.toString(totalPrice)+"$";
	out.write(rtnRst);
%>
