<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"name", "Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
	String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
	List<List<String>> recordList = null;
	int pageNum = 0;
	int PageRecordCount = 3;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&512;
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
			String sql = "select * from material_record";
			if (hDBHandle.QueryDataBase(sql))
			{
				int recordCount = hDBHandle.GetRecordCount();
				pageNum = (recordCount%PageRecordCount == 0)?recordCount/PageRecordCount:1 + recordCount/PageRecordCount;
			}
			int BeginPage = Integer.parseInt(request.getParameter("BeginPage"));
			sql = String.format("select * from material_record order by id desc limit %d,%d", PageRecordCount*(BeginPage-1), PageRecordCount);
			if (hDBHandle.QueryDataBase(sql))
			{
				recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
			}
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
for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
{
%>
	   			<th><%= displayKeyList[iCol-1]%></th>
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
	for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
	{
		if(displayKeyList[iCol-1] == "isApprove")
		{
%>
    			<td><%= (recordList.get(iCol-2).get(iRow-1).equalsIgnoreCase("1")) ? "已领取" :"未领取" %></td>
<%
    	}
    	else if (displayKeyList[iCol-1] == "name")
    	{
%>
    			<td><%= hDBHandle.GetNameByBarcode(recordList.get(0).get(iRow-1)) %></td>
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
%>
    	</table>
    	<br><br>
    	<h2>
    	<a href="Query.jsp?BeginPage=1">首页</a>
<%
		if (BeginPage > 1)
		{
%>
    		<a href="Query.jsp?BeginPage=<%=BeginPage-1 %>">上一页</a>
<%
		}
		if (BeginPage - 2 > 1)
		{
%>
			<a>...</a>
<%
		}
		int begin = (BeginPage-2)>1?BeginPage-2:1;
		int end = (begin+4)<pageNum?(begin+4):pageNum;
		if (end == pageNum)
		{
			begin = end - 4;
		}
		for (int iPage = begin; iPage <= end; iPage++)
		{
%>
			<a href="Query.jsp?BeginPage=<%=iPage %>"><%=iPage %></a>
<%
		}
		if ((BeginPage + 2) < pageNum)
		{
%>
			<a>...</a>
<%
		}
		if (BeginPage < pageNum)
		{
%>
    		<a href="Query.jsp?BeginPage=<%=BeginPage+1 %>">下一页</a>
<%
    	}
%>
    	<a href="Query.jsp?BeginPage=<%=pageNum %>">末页</a>
    	</h2>
    </center>
  </body>
</html>
<%
		}
	}
%>