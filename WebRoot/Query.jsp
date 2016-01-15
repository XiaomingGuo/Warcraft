<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"ID", "物料名称", "八码", "批号", "申请人", "数量", "使用人", "申请时间", "是否领取"};
	String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "proposer", "QTY", "user_name", "create_date", "isApprove"};
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
		String sql = "select * from other_record";
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
    <jsp:include page="Menu/MFGToolsMenu.jsp"/>
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
					for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
					{
						if(displayKeyList[iCol-1] == "是否领取")
						{
%>
    			<td><%= (recordList.get(iCol-3).get(iRow-1).equalsIgnoreCase("1")) ? "已领取" :"未领取" %></td>
<%
				    	}
				    	else if (displayKeyList[iCol-1] == "物料名称")
				    	{
%>
    			<td><%= hDBHandle.GetNameByBarcode(recordList.get(0).get(iRow-1)) %></td>
<%
				    	}
				    	else if (displayKeyList[iCol-1] == "ID")
				    	{
%>
    			<td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
				    	}
				    	else
				    	{
%>
    			<td><%= recordList.get(iCol-3).get(iRow-1)%></td>
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
   	    	<jsp:param value="Query.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
  </body>
</html>
<%
	}
%>