<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Customer_Po" %>
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
			Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
			hCPHandle.GetRecordLessThanStatus(1);
			List<String> po_list = hCPHandle.getDBRecordList("po_name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>成品出货</title>
    
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
		  		<form name="Create_Order" action = "Submit/SubmitProductShipment.jsp" method = "post">
			  		<table align="center">
			  			<tr>
			  				<td>
				  				<h1>
							  		<label>客户PO单号:</label>
								  	<select name="po_select" id="po_select" style="width:300px">
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
			var $po_select = $('#po_select');
			$po_select.change(function()
			{
				var $displayOrder = $("#display_order");
				var $confirmOrder = $("#confirm_order");
				var po_name = $po_select.find("option:selected").text();
				if (po_name.indexOf("请选择") >= 0)
				{
					$displayOrder.empty();
					$confirmOrder.empty();
					return;
				}
				$.post("Ajax/PO_Shipment_Item_Ajax.jsp", {"po_name":po_name, "status":"0"}, function(data, textStatus)
				{
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
										if(parseInt(data_list[iRow*iColCount + 9]) > parseInt(data_list[iRow*iColCount + 10]))
										{
											td.append("<input type='text' style='width:68px' name='" + data_list[iRow*iColCount + 9] + "$" + data_list[iRow*iColCount + 10] + "' id='" + iRow + "_QTY' value=" + data_list[iRow*iColCount + iCol + 2] + " onblur='CheckQTY(this)'>");
										}
										else
										{
											td.append("<label>已完成</label>");
										}
									}
									else if (0 == iColCount - iCol)
									{
										if (parseInt(data_list[iRow*iColCount + iCol + 2]) == 0)
										{
											td.append("<input type='button' value='出库' disabled>");
										}
										else
										{
											td.append("<input type='button' value='出库' name='" + data_list[iRow*iColCount + 6] + "$" + data_list[iRow*iColCount + 7] + "$" + iRow + "' onclick='PutOutQtyInCPO(this)'>");
										}
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
							cmdtr.append("<td><input align='middle' type='button' value='打印销售单' onclick='ShowSalePage(this)'></td>");
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
		
		function CheckQTY(obj)
		{
			var splitList = obj.name.split("$");
			if (parseInt(splitList[0]) < parseInt(splitList[1]) + parseInt(obj.value))
			{
				alert("出货量不能大于客户PO数量!!!!!");
				obj.value = parseInt(splitList[0]) - parseInt(splitList[1]);
			}
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