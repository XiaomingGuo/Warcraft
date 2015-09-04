<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"ID", "名称", "八码", "批号", "申请人", "数量", "使用者", "申请日期", "领取确认"};
	String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "proposer", "QTY", "user_name", "create_date", "isApprove"};
	List<List<String>> recordList = null;
%>
<%
	String message="";
	Calendar mData = Calendar.getInstance();
	String currentDate = String.format("%04d-", mData.get(Calendar.YEAR)) + String.format("%02d-", mData.get(Calendar.MONDAY)+1);
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
			String sql = "select * from other_record where create_date>'" + beginDate + "' and create_date<'" + endDate + "' and isApprove=1";
			if (hDBHandle.QueryDataBase(sql))
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
    
    <title>月报表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
  </head>
  	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
  <body>
    <jsp:include page="Menu/QueryMenu.jsp"/>
	<form action="ReportPage/Create_Month_Report.jsp" method="post">
	<div align="center"><label>查询起止时间:</label></div>
  	<table border="1" align="center">
	  	<tr>
	  		<td>
    			<label>开始日期:</label>
    			<div dojoType="dropdowndatepicker" id="DateOfBegin" name="DateOfBegin" displayFormat="yyyy-MM-dd" value="<%=beginDate %>"></div>
   			</td>
   			<td>
    			<label>截止日期:</label>
    			<div dojoType="dropdowndatepicker" id="DateOfEnd" name="DateOfEnd" displayFormat="yyyy-MM-dd" value="<%=endDate %>"></div>
	  		</td>
	  	</tr>
  	</table>
	<table align="center">
		<tr><td><input type="button" value="查询" onclick="Query(this)" style='width:80px'></td></tr>
	</table>
   	<table align="center" border="1">
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
					totalPrice += hDBHandle.GetPrice_Pre_Unit("other", recordList.get(0).get(iRow-1), recordList.get(1).get(iRow-1))*Integer.parseInt(recordList.get(3).get(iRow-1));
%>
		<tr>
<%
					for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
					{
						if(displayKeyList[iCol-1] == "领取确认")
						{
%>
   			<td><%= (recordList.get(iCol-3).get(iRow-1).equalsIgnoreCase("1")) ? "已领取" :"未领取" %></td>
<%
			    		}
				    	else if (displayKeyList[iCol-1] == "名称")
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
	<script type="text/javascript">
		function Query(obj)
		{
			var beginData = dojo.widget.byId("DateOfBegin");
			var endData = dojo.widget.byId("DateOfEnd");
			window.location.href="MonthReport.jsp?BeginDate="+beginData.getValue().split("T")[0]+"&EndDate="+endData.getValue().split("T")[0];
		}
		
	</script>
  </body>
</html>
<%
		}
	}
%>