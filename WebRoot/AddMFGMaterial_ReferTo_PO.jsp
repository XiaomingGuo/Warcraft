<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&1024;
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
			String curPOName = request.getParameter("POName");
			if(null == curPOName)
				curPOName = "";
			Calendar mData = Calendar.getInstance();
			String DeliveryDate = String.format("%04d", mData.get(Calendar.YEAR));
			String currentDate = String.format("%04d-", mData.get(Calendar.YEAR)) + String.format("%02d-", mData.get(Calendar.MONDAY)+1)+String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>PO物料入库</title>
    
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
  	<script language="javascript" src="Page_JS/AddMFGMaterial_ReferTo_POJS.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
  <body onload="changePOName()">
   	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
    <jsp:include page="Menu/DataEnterMenu.jsp"/>
    <br><br>
    <table align="center">
    	<tr>
    		<td>
		  		<table align="center">
		  			<tr>
		  				<td>
			  				<h1>
						  		<label>PO号:</label>
						  		<input type="text" value="<%=curPOName %>" name="POName" id="POName" onchange="changePOName()" style="width:200px">
					  		</h1>
				  		</td>
			  		</tr>
			  		<tr>
			   			<td align="center">
				   			<label>入库时间:</label>
			    			<div dojoType="dropdowndatepicker" name="SubmitDate" id="SubmitDate" displayFormat="yyyyMMdd" value="<%=currentDate %>"></div>
						</td>
			  		</tr>
		  		</table>
	 		   	<table id="display_po" border='1' align="center"></table>
			</td>
		</tr>
   	</table>
  </body>
</html>
<%
		}
	}
%>