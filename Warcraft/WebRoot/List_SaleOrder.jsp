<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	List<String> shipping_no = null;
	String POName = request.getParameter("PO_Name").replace(" ", "");
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&64;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("tishi.jsp");
		}
		else
		{
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			DBTableParent hSRHandle = new DatabaseStore("Shipping_Record");
			hSRHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("customer_po"), Arrays.asList(POName), Arrays.asList("shipping_no"));
			if (hSRHandle.getTableInstance().RecordDBCount() > 0)
			{
				shipping_no = hSRHandle.getDBRecordList("shipping_no");
			}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产单查询</title>
    
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
    <jsp:include page="Menu/MainMenu.jsp"/>
    <br><br><br>
    <table align="center" width="20%">
<%
			for (int iRow = 0; iRow < shipping_no.size(); iRow++)
			{
%>
			<tr>
				<td align="center"><label>销售单号:</label></td>
				<td align="center"><h1><a onclick="func(this)" name="<%=POName %>$<%=shipping_no.get(iRow) %>$<%=iRow %>" href="javascript:void(0)">MOB<%=shipping_no.get(iRow) %></a></h1></td>
				<td align="center"><label></label></td>
			</tr>
<%
			}
%>
   	</table>
  </body>
   	<script type="text/javascript">
		function func(obj)
		{
			var paramList = obj.name.split("$");
			var vPOName = paramList[0];
			var shipping_no = paramList[1];
			if(vPOName==""||vPOName == null||shipping_no == ""||shipping_no == null)
			{
				alert("我说大姐,你这PO号和供应商名称我没见过啊?");
				return;
			}
			location.href = "SaleOrder.jsp?PO_Name=" + vPOName + "&shipping_no=" + shipping_no;
		}
	</script>
</html>
<%
		}
	}
%>