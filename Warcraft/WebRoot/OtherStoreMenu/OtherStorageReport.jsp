<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryOtherStorageReport" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	QueryOtherStorageReport hPageHandle = new QueryOtherStorageReport();

	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("../tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&64;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("../tishi.jsp");
		}
		else
		{
			message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			request.setCharacterEncoding("UTF-8");
			String[] selectKeyList = {"库名", "类别", "名称", "供应商", "到货日期", "操作"};
			String currentDate = hPageHandle.GenYearMonthString("-");
			String beginDate = String.format("%s%s", currentDate, "01");
			String endDate = String.format("%s%s", currentDate, "31");
			
			List<String> store_nameList = hPageHandle.GetStoreName("TOOLS");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>库房报表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
	<script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
	<script language="javascript" src="Page_JS/OtherStoreMenuJS/OtherStorageReportJS.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
  <body>
	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
    <jsp:include page="../Menu/MainMenu.jsp"/>
    <br>
    <form action="ReportPage/SaveStorageReport.jsp" method="post">
    <table align="center" border="1">
		<caption><b>库房报表</b></caption>
			<tr>
<%
				for(int iCol = 1; iCol <= selectKeyList.length; iCol++)
				{
%>
				<th><%= selectKeyList[iCol-1]%></th>
<%
				}
%>
			</tr>
			<tr>
				<td align="right">
					<select name="store_name" id="store_name" style="width:120px">
						<option value = "--请选择--">--请选择--</option>
<%
				for(int i = 0; i < store_nameList.size(); i++)
				{
%>
					<option value = <%=store_nameList.get(i) %>><%=store_nameList.get(i)%></option>
<%
				}
%>
					</select>
				</td>
				<td align="right">
					<select name="product_type" id="product_type" style="width:100px">
						<option value = "--请选择--">--请选择--</option>
					</select>
				</td>
				<td align="right">
					<select name="product_name" id="product_name" style="width:150px">
						<option value = "--请选择--">--请选择--</option>
					</select>
				</td>
				<td align="right">
					<select name="supplier_name" id="supplier_name" style="width:120px">
						<option value = "--请选择--">--请选择--</option>
					</select>
				</td>
				<td align="center">
					<label>交货时间:</label>
					<input dojoType="dropdowndatepicker" id="SubmitDate" name="SubmitDate" displayFormat="yyyy-MM-dd" value="<%=hPageHandle.GenYearMonthDayString("-") %>">
				</td>
				<td align="center">
					<input align="middle" id="confirm_button" type="button" value="查询" onclick="SubmitDateChange()">
				</td>
			</tr>
		</table>
		<table align="center">
			<tr>
				<td align="center">
					<b><font  size="3">查询起止时间:</font></b>
				</td>
			</tr>
			<tr>
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
		<br>
		<table id="display_add" border='1' align="center"></table>
		<br>
		<table align="center">
		<tr>
			<td align="center">
				<input type="submit" value="下载报表" style='width:80px'/>
			</td>
		</tr>
		</table>
		<table id="hidden_table" style="visibility:hidden"></table>
	</form>
  </body>
</html>
<%
		}
	}
%>