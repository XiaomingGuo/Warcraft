<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.ShippingSummary" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	ShippingSummary hPageHandle = new ShippingSummary();
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&128;
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
			String displayName = null;
			String currentDate = hPageHandle.GenYearMonthString("-");
			String beginDate = String.format("%s%s", currentDate, "01");
			String endDate = String.format("%s%s", currentDate, "31");
			//product_type Database query
			Product_Order hSNHandle = new Product_Order(new EarthquakeManagement());
			hSNHandle.GetRecordByStatus(1);
			List<String> shipNoList = hPageHandle.GetShippingNoList(beginDate, endDate);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>出货报表</title>
    
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
  	<script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
  	<script language="javascript" src="Page_JS/ShippingSummaryJS.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
  <body>
   	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
    <jsp:include page="Menu/MFGReportMenu.jsp"/>
    <table width="61.8%" height="80%" align="center">
    	<tr>
    		<td height="3%"></td>
    		<td height="3%" bgcolor="grey"></td>
    		<td height="3%" align="center">
   		  	  	<table align="center">
				  	<tr>
				  		<td align="center">
					  		<b><font  size="3">查询起止时间:</font></b>
						</td>
					  	<td>
						  	<table border="1" align="center">
							  	<tr>
							  		<td>
						    			<label>开始日期:</label>
						    			<div dojoType="dropdowndatepicker" id="BeginDate" name="BeginDate" displayFormat="yyyy-MM-dd" value="<%=beginDate %>"></div>
					    			</td>
					    			<td>
						    			<label>截止日期:</label>
						    			<div dojoType="dropdowndatepicker" id="EndDate" name="EndDate" displayFormat="yyyy-MM-dd" value="<%=endDate %>"></div>
							  		</td>
							  	</tr>
						  	</table>
					  	</td>
				  	</tr>
				</table>
			</td>
    	</tr>
		<tr>
			<td valign="top" align="center" width="19%">
				<table align="center" border="1" width="100%">
					<tr><th>生产单号:</th></tr>
				</table>
				<h5>
					<ul>
<%
					if (shipNoList != null)
					{
						for(int iRow = 0; iRow < shipNoList.size(); iRow++)
						{
							displayName = shipNoList.get(iRow);
%>
						<li><%=displayName %></li>
<%
						}
					}
%>
	   				</ul>
	   			</h5>
   			</td>
			<td width="0.5%" height="80%" bgcolor="grey"></td>
   			<td width="80.5%" valign="top" align="center">
   				<table width="100%" border="1">
   					<tr><th>生产单内容：</th></tr>
	   			</table>
	   			<table id="OrderBlock" border="1"></table>
   			</td>
		</tr>
   	</table>
  </body>
</html>
<%
		}
	}
%>