<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null){
		response.sendRedirect("tishi.jsp");
	}
	else{
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String sql = "select * from material_record";
		hDBHandle.QueryDataBase(sql);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查询</title>
    
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
for(int iCol = 1; iCol < 10; iCol++)
{
%>
	   	<th>Header<%= iCol%><th>
<%
}
%>
    	</tr>
<%
for(int iRow = 1; iRow < 10; iRow++)
{
%>
    		<tr>
	<%
	for(int iCol = 1; iCol < 10; iCol++)
	{
	%>
    			<td>Row<%= iRow%>, cell<%= iCol%></td>
    <%
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