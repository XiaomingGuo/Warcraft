<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	int PageRecordCount = Integer.parseInt(request.getParameter("PageRecordCount").replace(" ", ""));
	int recordCount = Integer.parseInt(request.getParameter("recordCount").replace(" ", ""));
	int pageNum = (recordCount%PageRecordCount == 0)?recordCount/PageRecordCount:1 + recordCount/PageRecordCount;
	int BeginPage = Integer.parseInt(request.getParameter("BeginPage").replace(" ", ""));
	String JSPPage = request.getParameter("PageName").replace(" ", "");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
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
   	<h3>
   	<a href="<%=JSPPage %>?BeginPage=1">首页</a>
<%
	if (BeginPage > 1)
	{
%>
		<a href="<%=JSPPage %>?BeginPage=<%=BeginPage-1 %>">上一页</a>
<%
	}
	int begin = (BeginPage-2)>1?BeginPage-2:1;
	int end = (begin+4)<pageNum?(begin+4):pageNum;
	if (pageNum > 5&&end == pageNum)
	{
		begin = end - 4;
	}
	if (begin > 1)
	{
%>
		<a>...</a>
<%
	}
	for (int iPage = begin; iPage <= end; iPage++)
	{
%>
		<a href="<%=JSPPage %>?BeginPage=<%=iPage %>"><%=iPage %></a>
<%
	}
	if (end < pageNum)
	{
%>
		<a>...</a>
<%
	}
	if (BeginPage < pageNum)
	{
%>
   		<a href="<%=JSPPage %>?BeginPage=<%=BeginPage+1 %>">下一页</a>
<%
   	}
%>
   	<a href="<%=JSPPage %>?BeginPage=<%=pageNum %>">末页</a>
   	</h3>
  </body>
</html>
