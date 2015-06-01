<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] sqlKeyList = {"id", "name", "Bar_Code", "product_type"};
	String[] keyList = {"id", "name", "Bar_Code", "product_type", "IN_QTY", "OUT_QTY", "repertory"};
	List<List<String>> recordList = null;
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
		if (hDBHandle.QueryDataBase(sql))
		{
			recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
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
    <jsp:include page="MainPage.jsp"/>
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
for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
{
%>
  			<tr>
  	<%
	int sql_in_qty = hDBHandle.GetIN_QTYByBarCode(recordList.get(2).get(iRow-1));
	int sql_out_qty = hDBHandle.GetOUT_QTYByBarCode(recordList.get(2).get(iRow-1));
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
    	else
    	{
	%>
    			<td><%= recordList.get(iCol-1).get(iRow-1)%></td>
    <%
   		}
    }
    %>
			</tr>
<%
}
%>
    	</table>
    </center>
  </body>
</html>
<%
	}
%>