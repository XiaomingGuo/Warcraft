<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.Product_Type" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&64;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("../tishi.jsp");
		}
		else
		{
			message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			request.setCharacterEncoding("UTF-8");
			String store_name = request.getParameter("store_name").replace(" ", "");
			String beginDate = request.getParameter("BeginDate").replace(" ", "");
			String endDate = request.getParameter("EndDate").replace(" ", "");
			Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
			Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
			//product_type Database query
			if (store_name.indexOf("请选择") < 0)
			{
				List<List<String>> record_list = null;
				String[] displayKeyList = {"ID", "八码", "名称", "库名", "规格", "批号", "进货数量", "消耗数量", "单价", "进货总价", "供应商"};
				String[] keyWords = {"id", "Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Total_Price", "vendor_name"};
				String sql = "select * from (select * from " + store_name + "_storage where create_date > '" + beginDate + "' and create_date < '" + endDate
						+ "' UNION ALL select * from exhausted_" + store_name + " where create_date > '" + beginDate + "' and create_date < '" + endDate + "')T order by vendor_name, Bar_Code, id";
				if (hDBHandle.QueryDataBase(sql))
				{
					record_list = hDBHandle.GetAllDBColumnsByList(keyWords);
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
    
    <title>生产单生成</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  	<link href="css/style.css" rel="stylesheet" type="text/css">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
  </head>
  <body>
    <jsp:include page="../Menu/QueryMenu.jsp"/>
    <br><br>
    <form action="ReportPage/SaveStorageReport.jsp" method="post">
	    <table border="1" align="center">
	    	<tr>
<%
				for(int iCol = 0; iCol < displayKeyList.length; iCol++)
				{
%>
				<th><%= displayKeyList[iCol]%></th>
<%
				}
%>
			</tr>
<%
				if (record_list.size() > 0)
				{
					for(int iRow = 0; iRow < record_list.get(0).size(); iRow++)
					{
%>
    		<tr>
<%
						String barcode = record_list.get(1).get(iRow);
						for(int iCol = 0; iCol < displayKeyList.length; iCol++)
						{
							if ("ID" == displayKeyList[iCol])
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= iRow + 1 %> style='width:30px' readonly></td>
<%
							}
							else if ("八码" == displayKeyList[iCol])
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= record_list.get(1).get(iRow) %> style='width:80px' readonly></td>
<%
							}
							else if ("名称" == displayKeyList[iCol])
							{
								hPIHandle.GetRecordByBarcode(barcode);
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= hPIHandle.getDBRecordList("name").get(0) %> style='width:150px' readonly></td>
<%
							}
							else if ("库名" == displayKeyList[iCol])
							{
								hPIHandle.GetRecordByBarcode(barcode);
								hPTHandle.GetRecordByName(hPIHandle.getDBRecordList("product_type").get(0));
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= hPTHandle.getDBRecordList("storeroom").get(0) %> style='width:100px' readonly></td>
<%
							}
							else if ("规格" == displayKeyList[iCol])
							{
								hPIHandle.GetRecordByBarcode(barcode);
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= hPIHandle.getDBRecordList("description").get(0) %> style='width:120px' readonly></td>
<%
							}
							else if ("批号" == displayKeyList[iCol])
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= record_list.get(2).get(iRow) %> style='width:80px' readonly></td>
<%
							}
							else if ("进货数量" == displayKeyList[iCol])
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= record_list.get(3).get(iRow) %> style='width:50px' readonly></td>
<%
							}
							else if ("消耗数量" == displayKeyList[iCol])
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= record_list.get(4).get(iRow) %> style='width:50px' readonly></td>
<%
							}
							else if ("单价" == displayKeyList[iCol])
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= record_list.get(5).get(iRow) %> style='width:80px' readonly></td>
<%
							}
							else if ("进货总价" == displayKeyList[iCol])
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= record_list.get(6).get(iRow) %> style='width:100px' readonly></td>
<%
							}
							else if ("供应商" == displayKeyList[iCol])
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value=<%= record_list.get(7).get(iRow) %> style='width:80px' readonly></td>
<%
							}
							else
							{
%>
				<td><input name="<%=iRow*11+iCol %>" value="无此列" style='width:100px' readonly></td>
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
   		<br>
	  	<table align="center">
		<tr>
	  		<td align="right">
		  		<label>分页列:</label>
			  	<select name="OrderItemSelect" id="OrderItemSelect" style="width:100px">
				  	<option value = "--请选择--">--请选择--</option>
<%
					for(int i = 0; i < displayKeyList.length; i++)
					{
%>
				  	<option value=<%=i%>><%=displayKeyList[i]%></option>
<%
					}
%>
			  	</select>
		  	</td>
		  	<td align="center">
		  		<input type="submit" value="下载报表" style='width:80px'/>
		  	</td>
	  	</tr>
 		</table>
   	</form>
  </body>
</html>
<%
			}
			else
			{
				session.setAttribute("error", "你是要查询那个库的数据!");
				response.sendRedirect("../tishi.jsp");
			}
		}
	}
%>