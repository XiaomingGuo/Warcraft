<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] sqlKeyList = {"name", "Bar_Code", "product_type"};
	String[] keyList = {"ID", "name", "Bar_Code", "product_type", "IN_QTY", "OUT_QTY", "repertory", "Total_Price"};
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
		int temp = mylogon.getUserRight()&32;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("tishi.jsp");
		}
		else
		{
			message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			String sql = "select * from product_info";
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
    
    <title>库存</title>
    
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
    <jsp:include page="Menu/MFGReportMenu.jsp"/>
    <center>
    	<table border="1">
    		<tr>
<%
			for(int iCol = 1; iCol <= keyList.length; iCol++)
			{
%>
	   			<th><%= keyList[iCol-1]%></th>
<%
			}
%>
    		</tr>
 
<%
			double totalPrice = 0.0;
			if (!recordList.isEmpty())
			{
				for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
				{
					String storageName = "other_storage";
					String bar_code = recordList.get(1).get(iRow-1);
					if (Integer.parseInt(bar_code) >= 60000000 && Integer.parseInt(bar_code) < 70000000)
					{
						storageName = "product_storage";
					}
%>
  			<tr>
<%
					if (Integer.parseInt(bar_code) >= 50000000 && Integer.parseInt(bar_code) < 60000000)
					{
						storageName = "material_storage";
					}
					int sql_in_qty = hDBHandle.GetIN_QTYByBarCode(bar_code, storageName);
					int sql_out_qty = hDBHandle.GetOUT_QTYByBarCode(bar_code, storageName);
					double dblPro_Price = hDBHandle.GetProductRepertoryPrice(bar_code, storageName);
					totalPrice += dblPro_Price;
					String pro_Price = String.format("%.3f", dblPro_Price);
					for(int iCol = 1; iCol <= keyList.length; iCol++)
					{
						if(keyList[iCol-1] == "repertory")
						{
%>
    			<td><%= sql_in_qty - sql_out_qty%></td>
<%
				    	}
				    	else if (keyList[iCol-1] == "IN_QTY")
				    	{
%>
    			<td><%= sql_in_qty%></td>
<%
				    	}
				     	else if (keyList[iCol-1] == "OUT_QTY")
				    	{
%>
    			<td><%= sql_out_qty%></td>
<%
				    	}
				    	else if (keyList[iCol-1] == "ID")
				    	{
%>
	    			<td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
				    	}
				    	else if (keyList[iCol-1] == "Total_Price")
				    	{
%>
	    			<td><%=pro_Price %></td>
<%
				    	}
				    	else
				    	{
%>
    			<td><%= recordList.get(iCol-2).get(iRow-1)%></td>
<%
   						}
    				}
%>
			</tr>
<%
				}
			}
			String strTotalPrice = String.format("%.3f", totalPrice);
%>
			<tr>
<%
			for(int iCol = 1; iCol <= keyList.length-2; iCol++)
			{
%>
				<td><table></table></td>
<%
			}
%>				<td><label>总价值:</label></td>
				<td><%=strTotalPrice %></td>
			</tr>
    	</table>
    	<br><br>
   	    <jsp:include page="PageNum.jsp">
   	    	<jsp:param value="<%=recordCount %>" name="recordCount"/>
   	    	<jsp:param value="<%=PageRecordCount %>" name="PageRecordCount"/>
   	    	<jsp:param value="<%=BeginPage %>" name="BeginPage"/>
   	    	<jsp:param value="Summary.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
  </body>
</html>
<%
		}
	}
%>