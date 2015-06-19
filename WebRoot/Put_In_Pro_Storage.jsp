<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"产品类型", "产品名称", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "余量", "操作"};
	String[] sqlKeyList = {"product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "percent", "status"};
	List<List<String>> recordList = null;
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
			//product_type Database query
 			List<String> orderName = null;
			String sql = "select * from product_order where status='1'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
			{
				orderName = hDBHandle.GetAllStringValue("Order_Name");
			}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订单入库</title>
    
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
    <table width="61.8%" height="80%" align="center">
    	<tr>
    		<td height="3%"></td>
    		<td height="3%" bgcolor="grey"></td>
    		<td height="3%" align="center"><b><font size="5"><label id="TitleName">订单号</label></font></b></td>
    	</tr>
		<tr>
			<td valign="top" align="center" width="19%">
				<table align="center" border="1" width="100%">
					<tr><th>订单号:</th></tr>
				</table>
				<h5>
					<ul>
<%
					if (orderName != null)
					{
						for(int iRow = 0; iRow < orderName.size(); iRow++)
						{
							displayName = orderName.get(iRow);
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
   					<tr><th>订单内容：</th></tr>
	   			</table>
	   			<table id="OrderBlock" border="1"></table>
	   			<br><br>
	   			<input align="middle" type="button" onclick="CloseOrder(this)" value="关闭订单">
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
				$.post("Ajax/Query_Order_Item_Ajax.jsp", {"order_name":order_name}, function(data, textStatus)//Query_Order_Item_Ajax
				{
					if (textStatus == "success")
					{
						$OrderBlock.empty();
						var data_list = data.split("$");
						var iColCount = data_list[1], iRowCount = data_list[2];
						var tr = $("<tr></tr>");
						for (var iHead = 1; iHead <= iColCount; iHead++)
						{
							if (iHead == iColCount)
							{
								var th = $("<th>入库数量</th>");
								tr.append(th);
							}
							else
							{
								var th = $("<th>" + data_list[iHead + 3] + "</th>");
								tr.append(th);
							}
						}
						$OrderBlock.append(tr);
						for(var iRow = 1; iRow <= iRowCount; iRow++)
						{
							var tr = $("<tr></tr>");
							for (var iCol = 1; iCol <= iColCount; iCol++)
							{
								var td = $("<td></td>");
								if (1 == iColCount - iCol)
								{
									td.append("<input type='button' value='入库' name='" + data_list[iRow*iColCount + 3] + "$" + iRow.toString() + "' onclick='PutInStorage(this)'>");
								}
								else if(0 == iColCount - iCol)
								{
									td.append("<input type='text' value='0' name='" + data_list[iRow*iColCount + 8] + "$" + data_list[iRow*iColCount + 9] + "' id='" + iRow.toString() + "' style='width:70px' onblur='CheckQTY(this)'>");
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
		
		function PutInStorage(obj)
		{
			var tempList = obj.name.split('$');
			var storeQTY = $("#"+tempList[1]).val();
			$.post("Ajax/Put_In_Storage_Ajax.jsp", {"product_id":tempList[0], "PutInQTY":storeQTY}, function(data, textStatus)
			{
				if (!(textStatus == "success"))
				{
					alert(data);
				}
				location.reload();
			});
		}
		
		function CheckQTY(obj)
		{
			var tempList = obj.name.split('$');
			if (parseInt(obj.value)+parseInt(tempList[1]) > parseInt(tempList[0]))
			{
				alert("入库数量不能大于订单量!");
				obj.value = 0;
			}
		}
		
		function CloseOrder(obj)
		{
			var ordername = $("#TitleName").html();
			alert(ordername);
			$.post("Ajax/Close_Order_Ajax.jsp", {"Order_Name":ordername}, function(data, textStatus)
			{
				if (!(textStatus == "success"))
				{
					alert(data);
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