<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryMonthReportAjax" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%!
	String[] sqlKeyList = {"name", "Bar_Code", "product_type"};
	String[] displayKeyList = {"ID", "名称", "八码", "批号", "申请人", "数量", "单价", "使用者", "申请日期", "领取确认"};
%>
<%
	String rtnRst = "remove$";
	String storage_name = request.getParameter("storage_name");
	String product_type = request.getParameter("product_type");
	String product_name = request.getParameter("product_name");
	String user_name = request.getParameter("user_name");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	
	QueryMonthReportAjax hPageHandle = new QueryMonthReportAjax();
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
	
	List<String> recordList = null;

	recordList = hPageHandle.GetResultByStartEndDate(bar_code_List, user_name, beginDate, endDate);
	rtnRst += displayKeyList.length + "$";
	rtnRst += recordList.size()/displayKeyList.length+1 + "$";
	for(int idx = 0; idx < displayKeyList.length; idx++)
		rtnRst += displayKeyList[idx] + "$";
	
	double totalRepertoryPrice = 0.0;
	int inSum=0;
	for(int idx = 0; idx < recordList.size(); idx++)
	{
		rtnRst += recordList.get(idx) + "$";
		if(idx%displayKeyList.length == 5)
			inSum += Integer.parseInt(recordList.get(idx));
		else if(idx%displayKeyList.length == 6)
			totalRepertoryPrice += Double.parseDouble(recordList.get(idx)) * Integer.parseInt(recordList.get(idx-1));
	}
	
	for(int idx = 0; idx < displayKeyList.length-6; idx++)
	{
		rtnRst += "$";
	}
	
	//{"ID", "名称", "八码", "批号", "申请人", "数量", "单价", "使用者", "申请日期", "领取确认"};
	NumberFormat formatter = new DecimalFormat("#.###");
	rtnRst += "汇总$"+Integer.toString(inSum)+"$";
	rtnRst += "总价值$";
	rtnRst += formatter.format(totalRepertoryPrice)+"$";
	rtnRst += "$";
	out.write(rtnRst);
%>
