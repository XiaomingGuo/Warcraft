<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> product_type = null;
	String[] displayKeyList = {"产品类型", "产品名称", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "余量", "操作"};
	String[] sqlKeyList = {"product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "percent", "status"};
	List<List<String>> recordList = null;
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
			String sql = "select * from product_type where storeroom='成品库'";
			if (hDBHandle.QueryDataBase(sql))
			{
				product_type = hDBHandle.GetAllStringValue("name");
			}
			
			String tempOrderName = request.getParameter("OrderName");
			if (tempOrderName != null)
			{
				sql = "select * from product_order_record where Order_Name='" + tempOrderName + "'";
				hDBHandle.QueryDataBase(sql);
				if (hDBHandle.QueryDataBase(sql))
				{
					recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
				}
			}
			Calendar mData = Calendar.getInstance();
			String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
			String DeliveryDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生成订单</title>
    
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
    <br><br>
    <table align="center">
    	<tr>
    		<td>
		  		<form name="Create_Order" action = "SubmitCreateOrder.jsp" method = "post">
			  		<table align="center">
			  			<tr>
			  				<td>
				  				<h1>
							  		<label>订单号:</label>
							  		<input type="text" name="OrderHeader" id="OrderHeader" value="MB-" style="width:30px" readonly>
							  		<input type="text" name="OrderName" id="OrderName" onblur="changeOrderName(this)" value="<%=createDate %>-P015-05-06157" style="width:200px">
						  		</h1>
					  		</td>
				  		</tr>
			  		</table>
			    	<br><br>
		 		   	<table id="order_display"></table>
				</form>
			</td>
		</tr>
   	</table>
  	<script type="text/javascript">
				
		function changeOrderName(obj)
		{
			var $orderdisplay = $("#order_display");
			$orderdisplay.attr("border", 1);
			$orderdisplay.attr("align", "center");
			var order_name = $("#OrderHeader").val() + $("#OrderName").val();
			$.post("Ajax/Query_Order_Item_Ajax.jsp", {"order_name":order_name}, function(data, textStatus)//Query_Order_Item_Ajax
			{
				if (textStatus == "success")
				{
					$orderdisplay.empty();
					var data_list = data.split("$");
					var iColCount = data_list[1], iRowCount = data_list[2];
					var tr = $("<tr></tr>");
					for (var iHead = 1; iHead < iColCount; iHead++)
					{
						var th = $("<th>" + data_list[iHead + 3] + "</th>");
						tr.append(th);
					}
					$orderdisplay.append(tr);
					for(var iRow = 1; iRow <= iRowCount; iRow++)
					{
						var tr = $("<tr></tr>");
						for (var iCol = 1; iCol < iColCount; iCol++)
						{
							var td = $("<td></td>");
							if (iCol == iColCount - 1)
							{
								td.append("<label>未完成</label");
							}
							else
							{
								td.append(data_list[iRow*iColCount + iCol + 3]);
							}
							tr.append(td);
						}
						$orderdisplay.append(tr);
					}
				}
			});
		}
		
		function deleteRecord(obj)
		{
			var delID = obj.name;
			alert(delID);
			$.post("Ajax/Del_Order_Item_Ajax.jsp", {"product_id":delID}, function(data, textStatus)
			{
				if (!(textStatus == "success"))
				{
					alert(data);
				}
				changeOrderName();
			});
		}
		
		function Qty_Calc(obj)
		{
			var orderCount = parseInt($("#order_QTY").val());
			var proCount = parseInt($("#product_QTY").val());
			var matCount = parseInt($("#material_QTY").val());
			var PO_QTY = (proCount + matCount) - orderCount;
			if (PO_QTY >= 0)
			{
				$("#PO_QTY").attr("value", 0);
			}
			else
			{
				$("#PO_QTY").attr("value", -PO_QTY);
			}
		}
	</script>
  </body>
</html>
<%
		}
	}
%>