<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
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
			message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			//product_type Database query
			Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
			hPOHandle.GetRecordMoreThanStatus(1);
			List<String> po_list = hPOHandle.getDBRecordList("Order_Name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>关闭生产单</title>
    
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
    <br><br>
    <table align="center">
    	<tr>
    		<td>
		  		<form name="Create_Order" action = "Submit/SubmitCloseOrder.jsp" method = "post">
			  		<table align="center">
			  			<tr>
			  				<td>
				  				<h1>
							  		<label>生产单号:</label>
								  	<select name="Pro_Order_select" id="Pro_Order_select" style="width:300px">
									  	<option value = "--请选择--">--请选择--</option>
<%
							if (po_list != null)
							{
								for(int i = 0; i < po_list.size(); i++)
								{
%>
									  	<option value = <%= po_list.get(i) %>><%=po_list.get(i)%></option>
<%
								}
							}
%>
								  	</select>
						  		</h1>
					  		</td>
				  		</tr>
			  		</table>
		  		
			    	<br>
		 		   	<table id="display_order" border="1" align="center"></table>
		 		   	<br><br>
		 		   	<table id="confirm_order" align="center"></table>
				</form>
			</td>
		</tr>
   	</table>
  	<script type="text/javascript">
		$(function()
		{
			var $Pro_Order_select = $('#Pro_Order_select');
			$Pro_Order_select.change(function()
			{
				var $displayOrder = $("#display_order");
				var $confirmOrder = $("#confirm_order");
				var order_name = $Pro_Order_select.find("option:selected").text();
				if (order_name.indexOf("请选择") >= 0)
				{
					$displayOrder.empty();
					$confirmOrder.empty();
					return;
				}
				$.post("Ajax/Product_Order_Close_Item_Ajax.jsp", {"order_name":order_name}, function(data, textStatus)
				{
					alert(data);
					if (textStatus == "success")
					{
						$displayOrder.empty();
						$confirmOrder.empty();
						var data_list = data.split("$");
						var iColCount = data_list[1], iRowCount = data_list[2];
						if (iColCount > 0&&iRowCount > 0)
						{
							var tr = $("<tr></tr>");
							for (var iHead = 1; iHead <= iColCount; iHead++)
							{
								var th = $("<th>" + data_list[iHead + 2] + "</th>");
								tr.append(th);
							}
							$displayOrder.append(tr);
							for(var iRow = 1; iRow <= iRowCount; iRow++)
							{
								var tr = $("<tr></tr>");
								for (var iCol = 1; iCol <= iColCount; iCol++)
								{
									var td = $("<td></td>");
									if (1 == iColCount - iCol)
									{
										if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 0)
										{
											td.append("<label>待审核</label>");
										}
										else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 1)
										{
											td.append("<label>待加工</label>");
										}
										else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 2)
										{
											td.append("<label>加工中...</label>");
										}
										else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 3)
										{
											td.append("<label>待检验</label>");
										}
										else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 4)
										{
											td.append("<label>检验中...</label>");
										}
										else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 5)
										{
											td.append("<label>已完成</label>");
										}
										else
										{
											td.append("<label>未知状态</label>");
										}
									}
									else if (0 == iColCount - iCol)
									{
										if (parseInt(data_list[iRow*iColCount + iCol + 3]) < 0)
										{
											td.append("<label>已关闭</label>");
										}
										else
										{
											td.append("<input type='button' value='关闭' name='" + data_list[iRow*iColCount + 6] + "$" + data_list[iRow*iColCount + 7] + "$" + iRow + "' onclick='CloseOrder(this)'>");
										}
									}
									else if(iCol == 1)
									{
										td.append(iRow);
									}
									else
									{
										td.append(data_list[iRow*iColCount + iCol + 2]);
									}
									tr.append(td);
								}
								$displayOrder.append(tr);
							}
							var cmdtr = $("<tr></tr>");
							cmdtr.append("<td><input align='middle' type='submit' value='关闭订单'></td>");
							$confirmOrder.append(cmdtr);
						}
					}
				});
			});
		});
		
		function PutOutQtyInCPO(obj)
		{
			var splitList = obj.name.split("$");
			var Barcode = splitList[0], po_name = splitList[1];
			$.post("Ajax/Update_Customer_OUT_QTY_Ajax.jsp", {"Bar_Code":Barcode, "po_name":po_name, "OUT_QTY":$("#"+splitList[2]+"_QTY").val()}, function(data, textStatus)
			{
				if (textStatus == "success")
				{
					if (parseInt(data.split('$')[1]) > 0)
					{
						$("#barcode").val("");
					}
					$('#po_select').change();
				}
			});
		}
		
		function CloseOrder(obj)
		{
			return;
		}
		
		function ShowSalePage(obj)
		{
			var po_name = $("#po_select").find("option:selected").text();
			if (po_name.indexOf("请选择") >= 0)
			{
				$displayOrder.empty();
				$confirmOrder.empty();
				return;
			}
			$.post("Ajax/Add_Sale_Order_Ajax.jsp", {"POName": po_name}, function(data, textStatus)
			{
				if (!(textStatus == "success") || data.indexOf("error") >= 0)
				{
					alert(data.split("$")[1]);
				}
				location.href ="List_SaleOrder.jsp?PO_Name="+po_name;
			});
		}
	</script>
  </body>
</html>
<%
		}
	}
%>