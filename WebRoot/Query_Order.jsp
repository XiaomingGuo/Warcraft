<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
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
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String sql = "select * from product_info";
		hDBHandle.QueryDataBase(sql);
		int recordCount = hDBHandle.GetRecordCount();
		String tempBP = request.getParameter("BeginPage");
		int BeginPage = tempBP!=null?Integer.parseInt(tempBP):1;
		String limitSql = String.format("%s order by id desc limit %d,%d", sql, PageRecordCount*(BeginPage-1), PageRecordCount);
		if (hDBHandle.QueryDataBase(limitSql))
		{
			recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产订单查询</title>
    
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
    <jsp:include page="MainMenu.jsp"/>
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
%>
  			<tr>
<%
				String bar_code = recordList.get(1).get(iRow-1);
				int sql_in_qty = hDBHandle.GetIN_QTYByBarCode(bar_code);
				int sql_out_qty = hDBHandle.GetOUT_QTYByBarCode(bar_code);
				double dblPro_Price = hDBHandle.GetProductRepertoryPrice(bar_code);
				totalPrice += dblPro_Price;
				String pro_Price = String.format("%.3f", hDBHandle.GetProductRepertoryPrice(bar_code));
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
%>