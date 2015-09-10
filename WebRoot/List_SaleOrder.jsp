<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String message="";
	List<String> print_mark = null;
	String POName = request.getParameter("PO_Name");
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
			String sql = "select * from shipping_record where customer_po='" + POName + "' group by print_mark";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
			{
				print_mark = hDBHandle.GetAllStringValue("print_mark");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			Calendar mData = Calendar.getInstance();
			String DeliveryDate = String.format("%04d", mData.get(Calendar.YEAR));
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
    <jsp:include page="Menu/ManufactureMenu.jsp"/>
    <br><br><br>
    <table align="center" width="20%">
<%
			for (int iRow = 0; iRow < print_mark.size(); iRow++)
			{
				if(print_mark.get(iRow).indexOf("00000000") >= 0)
				{
%>
			<tr>
				<td align="center"><label>销售日期:</label></td>
				<td align="center"><input type="text" id="date_of_delivery" name="date_of_delivery" value="<%=DeliveryDate %>"></td>
				<td align="center" width="10%"><input type="button" id="add" value="添加销售单" name="<%=POName %>" onclick="addSaleOrder(this)"></td>
			</tr>
<%			
				}
				else
				{
%>
			<tr>
				<td align="center"><label>销售日期:</label></td>
				<td align="center"><h1><a onclick="func(this)" name="<%=POName %>$<%=print_mark.get(iRow) %>$<%=iRow %>" href="javascript:void(0)"><%=print_mark.get(iRow) %></a></h1></td>
				<td align="center"><label></label></td>
			</tr>
<%
				}
			}
%>
   	</table>
  </body>
   	<script type="text/javascript">
		function func(obj)
		{
			var paramList = obj.name.split("$");
			var vPOName = paramList[0];
			var print_mark = paramList[1];
			if(vPOName==""||vPOName == null||print_mark == ""||print_mark == null)
			{
				alert("我说大姐,你这PO号和供应商名称我没见过啊?");
				return;
			}
			location.href = "SaleOrder.jsp?PO_Name=" + vPOName + "&Delivery_Date=" + print_mark;
		}
		
		function addSaleOrder(obj)
		{
			var date_of_delivery = $("#date_of_delivery").val();
			if (date_of_delivery.length != 8)
			{
				alert("我说大姐,日期要写全啊!");
				return;
			}
			$.post("Ajax/Add_Sale_Order_Ajax.jsp", {"POName": obj.name, "date_of_delivery":date_of_delivery}, function(data, textStatus)
			{
				if (!(textStatus == "success") || data.indexOf("error") >= 0)
				{
					alert(data.split("$")[1]);
				}
				location.href = "List_SaleOrder.jsp?PO_Name=" + obj.name;
			});
		}
	</script>
</html>
<%
		}
	}
%>