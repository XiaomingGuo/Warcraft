<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"ID", "name", "Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
	String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
	List<List<String>> recordList = null;
%>
<%
	String message="";
	Calendar mData = Calendar.getInstance();
	String currentDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1);
	String beginDate = String.format("%s%s", currentDate, "01");
	String endDate = String.format("%s%s", currentDate, "31");
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&8;
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
			String tempBeginDate = request.getParameter("BeginDate");
			String tempEndDate = request.getParameter("EndDate");
			if (tempBeginDate != null&&tempEndDate != null)
			{
				beginDate = tempBeginDate;
				endDate = tempEndDate;
			}
			String sql = "select * from material_record where create_date>'" + beginDate + "' and create_date<'" + endDate + "' and isApprove=1";
			if (hDBHandle.QueryDataBase(sql))
			{
				recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
			}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>月报表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
  <body>
    <jsp:include page="MainMenu.jsp"/>
    <center>
    	<label>查询起止时间:</label>
    	<table border="1">
    		<tr>
    			<th><label>开始日期:</label></th>
    			<th><label>截止日期:</label></th>
    		</tr>
    		<tr>
    			<td><input type="text" name="DateOfBegin" id="DateOfBegin" style='width:100px' value="<%=beginDate%>"></td>
    			<td><input type="text" name="DateOfEnd" id="DateOfEnd" style='width:100px' value="<%=endDate%>"></td>
    		</tr>
    	</table>
		<input type="button" value="查询" onclick="Query(this)" style='width:80px'>
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
			double totalPrice = 0;
			if (!recordList.isEmpty())
			{
				for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
				{
					hDBHandle.GetPrice_Pre_Unit(recordList.get(0).get(iRow-1), recordList.get(1).get(iRow-1));
					totalPrice += hDBHandle.GetPrice_Pre_Unit(recordList.get(0).get(iRow-1), recordList.get(1).get(iRow-1))*Integer.parseInt(recordList.get(3).get(iRow-1));
%>
  			<tr>
<%
					for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
					{
						if(displayKeyList[iCol-1] == "isApprove")
						{
%>
    			<td><%= (recordList.get(iCol-3).get(iRow-1).equalsIgnoreCase("1")) ? "已领取" :"未领取" %></td>
<%
			    		}
				    	else if (displayKeyList[iCol-1] == "name")
				    	{
%>
    			<td><%= hDBHandle.GetNameByBarcode(recordList.get(0).get(iRow-1)) %></td>
<%
				    	}
				    	else if (displayKeyList[iCol-1] == "ID")
				    	{
%>
	    			<td><%=iRow %></td>
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
			String strTotalPrice = String.format("%.3f", totalPrice);
%>
			<tr>
<%
			for(int iCol = 1; iCol <= displayKeyList.length-2; iCol++)
			{
%>
				<td><table></table></td>
<%
			}
%>
				<td><table>总价值：</table></td>
				<td><%=strTotalPrice %></td>
			</tr>
    	</table>
    	<br><br>
    </center>
	<script type="text/javascript">
		function Query(obj)
		{	
			window.location.href="MonthReport.jsp?BeginDate="+$('#DateOfBegin').val()+"&EndDate="+$('#DateOfEnd').val();
		}
	</script>
  </body>
</html>
<%
		}
	}
%>