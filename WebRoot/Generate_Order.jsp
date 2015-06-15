<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> product_type = null;
	String[] displayKeyList = {"产品类型", "产品名称", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "余量", "操作"};
	String[] sqlKeyList = {"product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "precent", "status"};
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
				sql = "select * from product_order where Order_Name='" + tempOrderName + "'";
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
	 					<tr>
					  		<td align="right">
							  	<select name="product_type" id="product_type" style="width:100px">
								  	<option value = "--请选择--">--请选择--</option>
<%
									for(int i = 0; i < product_type.size(); i++)
									{
%>
								  	<option value = <%= product_type.get(i) %>><%=product_type.get(i)%></option>
<%
									}
%>
							  	</select>
						  	</td>
						  	<td align="right">
								<select name="product_name" id="product_name" style="width:100px">
								  	<option value = "--请选择--">--请选择--</option>
								</select>
							</td>
							<td align="center"><input type="text" name="bar_code" id="bar_code" style="width:100px" readonly></td>
							<td align="center"><input type="text" name="delivery_date" id="delivery_date" value=<%=DeliveryDate %>></td>
							<td align="center"><input type="text" name="order_QTY" id="order_QTY" onchange="Qty_Calc(this)" style="width:40px"></td>
							<td align="center"><input type="text" name="product_QTY" id="product_QTY" value="0" onchange="Qty_Calc(this)" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="material_QTY" id="material_QTY" value="0" onchange="Qty_Calc(this)" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="PO_QTY" id="PO_QTY" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="present" id="present" style="width:40px" value='8'><label>%</label></td>
	    					<td align="center"><input align="middle" type="button" value="确认" onclick="addordercolumn(this)"></td>
					  	</tr>
			    	</table>
			    	<br><br>
		 		   	<table id="order_display" align="center" border="1"></table>
				</form>
			</td>
		</tr>
   	</table>
  	<script type="text/javascript">
		$(function()
		{
			var $product_type = $('#product_type');
			var $product_name = $('#product_name');
			var $bar_code = $('#bar_code');
			
			$product_type.change(function()
			{
				$product_name.empty();
				$bar_code.empty();
				$product_name.append('<option value="请选择">--请选择--</option>');
				$bar_code.append('<option value="请选择">--请选择--</option>');
				$.post("Ajax/App_Pro_Name_Ajax.jsp", {"FilterKey1":$product_type.find("option:selected").text()}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						var pro_list = data.split("$");
						for (var i = 1; i < pro_list.length - 1; i++)
						{
							var newOption = $("<option>" + pro_list[i] + "</option>");
							$(newOption).val(pro_list[i]);
							$product_name.append(newOption);
						}
					}
				});
			});
			
			$product_name.change(function()
			{
				$.post("Ajax/App_Order_QTY_Ajax.jsp", {"product_name":$product_name.find("option:selected").text(), "product_type":$product_type.find("option:selected").text()}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						var code_list = data.split("$");
						$bar_code.attr("value", code_list[1]);
						$("#product_QTY").attr("value", code_list[2]);
						$("#material_QTY").attr("value", code_list[3]);
					}
				});
			});	
		});
		
		function changeOrderName(obj)
		{
			//var orderdisplay = $("#order_display");
			var order_name = $("#OrderHeader").val() + $("#OrderName").val();
			$.post("Ajax/Query_Order_Item_Ajax.jsp", {"order_name":order_name}, function(data, textStatus)//Query_Order_Item_Ajax
			{
				if (textStatus == "success")
				{
					var data_list = data.split("$");
					var iColCount = data_list[1], iRowCount = data_list[2];
					alert(data);
					alert(iColCount);
					alert(iRowCount);
					for(var iRow = 1; iRow < iRowCount; iRow++)
					{
						for (var iCol = 0; iCol < iColCount; iCol++)
						{
							alert(data_list[iRow*iColCount + iCol + 3]);
						}
					}
					
				}
			});
		}
		
		function addordercolumn(obj)
		{
			var order_name = $("#OrderHeader").val() + $("#OrderName").val();
			$.post("Ajax/Add_Order_Item_Ajax.jsp", {"product_type":$("#product_type").find("option:selected").text(), "product_name":$("#product_name").find("option:selected").text(), "bar_code":$("#bar_code").val(), "delivery_date":$("#delivery_date").val(), "order_QTY":$("#order_QTY").val(), "present":$("#present").val(), "order_name":order_name}, function(data, textStatus)
			{
				if (!(textStatus == "success"))
				{
					alert(data);
				}
				location.reload();
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