<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"ID", "产品名称", "八码", "批号", "总进货量", "已消耗", "库存", "单价", "总进货价", "供应商", "备注"};
	String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Total_Price", "vendor_name"};
	List<List<String>> recordList = null;
	int PageRecordCount = 20;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String sql = "select * from material_storage";
		hDBHandle.QueryDataBase(sql);
		int recordCount = hDBHandle.GetRecordCount();
		hDBHandle.CloseDatabase();
		String tempBP = request.getParameter("BeginPage");
		int BeginPage = tempBP!=null?Integer.parseInt(tempBP):1;
		String limitSql = String.format("%s order by id desc limit %d,%d", sql, PageRecordCount*(BeginPage-1), PageRecordCount);
		if (hDBHandle.QueryDataBase(limitSql))
		{
			recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>原材料状态</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <jsp:include page="Menu/DataEnterMenu.jsp"/>
    <center>
    	<table border="1">
    		<tr>
<%
		for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
		{
%>
	   			<th><%= displayKeyList[iCol-1]%></th>
<%
		}
%>
    		</tr>
 
<%
		if (!recordList.isEmpty())
		{
			for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
			{
%>
  			<tr>
<%
				//{"ID", "产品名称", "八码", "批号", "总进货量", "已消耗", "库存", "单价", "总价", "供应商", "备注"};
				//"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Total_Price", "vendor_name"
				for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
				{
					if (displayKeyList[iCol-1] == "ID")
			    	{
%>
    			<td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "产品名称")
			    	{
%>
    			<td><%= hDBHandle.GetNameByBarcode(recordList.get(0).get(iRow-1)) %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "八码")
			    	{
%>
    			<td><%= recordList.get(0).get(iRow-1) %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "批号")
			    	{
%>
    			<td><%= recordList.get(1).get(iRow-1) %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "总进货量")
			    	{
%>
    			<td><%= recordList.get(2).get(iRow-1)%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "已消耗")
			    	{
%>
    			<td><%= recordList.get(3).get(iRow-1) %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "库存")
			    	{
%>
    			<td><%= (Integer.parseInt(recordList.get(2).get(iRow-1)) - Integer.parseInt(recordList.get(3).get(iRow-1)))%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "单价")
			    	{
%>
    			<td><%= recordList.get(4).get(iRow-1)%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "总进货价")
			    	{
%>
    			<td><%= recordList.get(5).get(iRow-1)%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "供应商")
			    	{
%>
    			<td><%= recordList.get(6).get(iRow-1)%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "备注")
			    	{
%>
    			<td><%= hDBHandle.GetDescByBarcode(recordList.get(0).get(iRow-1)) %></td>
<%
			    	}
		    	}
%>
			</tr>
<%
			}
		}
%>
    	</table>
    	<br><br>
   	    <jsp:include page="PageNum.jsp">
   	    	<jsp:param value="<%=recordCount %>" name="recordCount"/>
   	    	<jsp:param value="<%=PageRecordCount %>" name="PageRecordCount"/>
   	    	<jsp:param value="<%=BeginPage %>" name="BeginPage"/>
   	    	<jsp:param value="QueryMaterial.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
  </body>
</html>
<%
	}
%>