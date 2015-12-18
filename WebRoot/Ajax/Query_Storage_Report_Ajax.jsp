<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryStorageReportAjax" %>
<%!
	String[] sqlKeyList = {"name", "Bar_Code", "product_type"};
	String[] displayKeyList = {"ID", "八码", "名称", "库名", "规格", "批号", "进货数量", "消耗数量", "单价", "进货总价", "供应商", "进货单时间"};
%>
<%
	String rtnRst = "remove$";
	String storage_name = request.getParameter("storage_name");
	String product_type = request.getParameter("product_type");
	String product_name = request.getParameter("product_name");
	String supplier_name = request.getParameter("supplier_name");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	String submitDate = request.getParameter("submitDate");
	
	QueryStorageReportAjax hPageHandle = new QueryStorageReportAjax();
	List<String> bar_code_List = new ArrayList<String>();
	if (storage_name.indexOf("请选择") < 0 && product_type.indexOf("请选择") >= 0 &&product_name.indexOf("请选择") >= 0 )
	{
		List<String> pro_Type_List = hPageHandle.QueryProTypeStorage(storage_name);
		for(int idx = 0; idx < pro_Type_List.size(); idx++)
		{
			List<String> tempList = hPageHandle.QueryProNameByProType(pro_Type_List.get(idx));
			for(int iRecordIdx = 0; iRecordIdx < tempList.size(); iRecordIdx++)
				bar_code_List.add(hPageHandle.QueryBarCodeByProNameAndType(pro_Type_List.get(idx), tempList.get(iRecordIdx)));
		}
	}
	else if(storage_name.indexOf("请选择") < 0&&product_type.indexOf("请选择") < 0&&product_name.indexOf("请选择") >= 0)
	{
		List<String> tempList = hPageHandle.QueryProNameByProType(product_type);
		for(int iRecordIdx = 0; iRecordIdx < tempList.size(); iRecordIdx++)
			bar_code_List.add(hPageHandle.QueryBarCodeByProNameAndType(product_type, tempList.get(iRecordIdx)));
	}
	else if(storage_name.indexOf("请选择") < 0&&product_type.indexOf("请选择") < 0&&product_name.indexOf("请选择") < 0)
	{
		bar_code_List.add(hPageHandle.QueryBarCodeByProNameAndType(product_type, product_name));
	}
	else
		bar_code_List = hPageHandle.QueryAllBarcode();
	
	Calendar mData = Calendar.getInstance();
	String currentDate = String.format("%04d-", mData.get(Calendar.YEAR)) + String.format("%02d-", mData.get(Calendar.MONDAY)+1) + String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
	if(currentDate.contains(submitDate))
	{
		;
	}
	else
	{
		;
	}
	rtnRst += displayKeyList.length + "$";
	rtnRst += bar_code_List.size()+1 + "$";
	for(int idx = 0; idx < displayKeyList.length; idx++)
		rtnRst += displayKeyList[idx] + "$";
	
	List<String> recordList = hPageHandle.GetAllRecordByBarCodeList(bar_code_List);
	double totalPrice = 0.0;
	int inSum=0, outSum=0, repertorySum=0;
	for(int idx = 0; idx < recordList.size(); idx++)
	{
		rtnRst += recordList.get(idx) + "$";
		if(idx%displayKeyList.length == 7)
			totalPrice+=Double.parseDouble(recordList.get(idx));
		else if(idx%displayKeyList.length == 6)
			repertorySum += Integer.parseInt(recordList.get(idx));
		else if(idx%displayKeyList.length == 5)
			outSum += Integer.parseInt(recordList.get(idx));
		else if(idx%displayKeyList.length == 4)
			inSum += Integer.parseInt(recordList.get(idx));
	}
	
	for(int idx = 0; idx < displayKeyList.length-5; idx++)
	{
		rtnRst += "$";
	}
	
	rtnRst += "汇总$"+Integer.toString(inSum)+"$";
	rtnRst += Integer.toString(outSum)+"$";
	rtnRst += Integer.toString(repertorySum)+"$";
	rtnRst += Double.toString(totalPrice)+"$";
	out.write(rtnRst);
%>
