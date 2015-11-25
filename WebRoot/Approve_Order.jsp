<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	String[] displayKeyList = {"产品类型", "产品名称", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "余量", "操作"};
	String[] sqlKeyList = {"product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "percent", "status"};
	String displayName = null;
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
			//product_type Database query
 			Product_Order hSNHandle = new Product_Order(new EarthquakeManagement());
			hSNHandle.GetRecordByStatus(0);
			List<String> orderList = hSNHandle.getDBRecordList("Order_Name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产单审核</title>
    
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
    <table width="61.8%" height="80%" align="center">
    	<tr>
    		<td height="3%"></td>
    		<td height="3%" bgcolor="grey"></td>
    		<td height="3%" align="center"><b><font size="5"><label id="TitleName">生产单号</label></font></b></td>
    	</tr>
		<tr>
			<td valign="top" align="center" width="19%">
				<table align="center" border="1" width="100%">
					<tr><th>生产单号:</th></tr>
				</table>
<%
					if (orderList != null)
					{
%>
				<h5>
					<ul>
<%
						for(int iRow = 0; iRow < orderList.size(); iRow++)
						{
							displayName = orderList.get(iRow);
%>
						<li><%=displayName %></li>
<%
						}
%>
	   				</ul>
	   			</h5>
<%
					}
%>
   			</td>
			<td width="0.5%" height="80%" bgcolor="grey"></td>
   			<td width="80.5%" valign="top" align="center">
   				<table width="100%" border="1">
   					<tr><th>生产单内容：</th></tr>
	   			</table>
	   			<table id="OrderBlock" border="1"></table>
	   			<br><br>
	   			<input align="middle" type="button" onclick="ApproveProduceOrder(this)" value="Approve">
	   			<input align="middle" type="button" onclick="RejectProduceOrder(this)" value="Reject">
   			</td>
		</tr>
   	</table>
	<script type="text/javascript">
		$(function()
		{
			$("li").click(function()
			{
				var order_name=$(this).html();
				var $OrderBlock = $("#OrderBlock");
				$("#TitleName").html(order_name);
				$.post("Ajax/Query_Approve_Order_Item_Ajax.jsp", {"order_name":order_name}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						$OrderBlock.empty();
						var data_list = data.split("$");
						var iColCount = data_list[1], iRowCount = data_list[2];
						
						var tr = $("<tr></tr>");
						for (var iHead = 1; iHead < iColCount; iHead++)
						{
							var th = $("<th>" + data_list[iHead + 3] + "</th>");
							tr.append(th);
						}
						$OrderBlock.append(tr);
						for(var iRow = 1; iRow <= iRowCount; iRow++)
						{
							var tr = $("<tr></tr>");
							for (var iCol = 1; iCol < iColCount; iCol++)
							{
								var td = $("<td></td>");
								if (iCol == iColCount - 1)
								{
									td.append("<label>待审核</label>");
								}
								else
								{
									td.append(data_list[iRow*iColCount + iCol + 3]);
								}
								tr.append(td);
							}
							$OrderBlock.append(tr);
						}
					}
				});
			});
		});
		
		function ApproveProduceOrder(obj)
		{
			var value = $("#TitleName").html();
			$.post("Ajax/Approve_Order_Ajax.jsp", {"order_name":value}, function(data, textStatus)
			{
				if (!(textStatus == "success")||data.indexOf("error") >= 0)
				{
					alert(data.split("$")[1]);
				}
				location.reload();
			});
		}
		
		function RejectProduceOrder(obj)
		{
			var value = $("#TitleName").html();
			$.post("Ajax/Reject_Order_Ajax.jsp", {"order_name":value}, function(data, textStatus)
			{
				if (!(textStatus == "success")||data.indexOf("error") >= 0)
				{
					alert(data.split("$")[1]);
				}
				location.reload();
			});
		}
	</script>
  </body>
</html>
<%
		}
	}
%>