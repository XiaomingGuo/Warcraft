<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	String[] displayKeyList = {"ID", "产品名称", "八码", "批号", "总进货量", "已消耗", "库存", "单价", "总进货价", "供应商", "备注"};
	String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Total_Price", "vendor_name"};
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
		Calendar mData = Calendar.getInstance();
		String currentDate = String.format("%04d-", mData.get(Calendar.YEAR)) + String.format("%02d-", mData.get(Calendar.MONDAY)+1);
		String beginDate = String.format("%s%s", currentDate, "01");
		String endDate = String.format("%s%s", currentDate, "31");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>物料录入查询</title>
    
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
  <body>
	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
    <jsp:include page="Menu/QueryMenu.jsp"/>
    <br><br>
    <form action="ReportPage/StorageReport.jsp" method="post">
		<table align="center" id="ShowContent">
			<tr>
		  		<td align="center">
			  		<label>选择库名:</label>
				  	<select name="store_name" id="store_name" style="width:180px">
					  	<option value = "--请选择--">--请选择--</option>
					  	<option value = "material">原材料库</option>
					  	<option value = "other">其他库</option>
				  	</select>
			  	</td>
		  	</tr>
	  	</table>
	  	<br>
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
			    			<div dojoType="dropdowndatepicker" name="BeginDate" displayFormat="yyyy-MM-dd" value="<%=beginDate %>"></div>
		    			</td>
		    			<td>
			    			<label>截止日期:</label>
			    			<div dojoType="dropdowndatepicker" name="EndDate" displayFormat="yyyy-MM-dd" value="<%=endDate %>"></div>
				  		</td>
				  	</tr>
			  	</table>
			  	<table align="center">
				  	<tr>
					  	<td align="center">
					  		<input type="submit" value="查询" />
							<!-- <input type="button" value="生成报表并下载" /> -->
					  	</td>
				  	</tr>
			  	</table>
		  	</td>
		  	</tr>
		</table>
   	</form>
  </body>
</html>
<%
	}
%>