<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"产品类型", "产品名称", "供应商", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "余量", "操作"};
	String[] sqlKeyList = {"product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "percent", "status"};
	List<List<String>> recordList = null;
%>
<%
	String message="";
	List<String> product_type = null, vendorList = null;
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
			String sql = "select * from product_type where storeroom='成品库'";
			if (hDBHandle.QueryDataBase(sql))
			{
				product_type = hDBHandle.GetAllStringValue("name");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			
			sql = "select * from vendor_info where storeroom='原材料库'";
			if (hDBHandle.QueryDataBase(sql))
			{
				vendorList = hDBHandle.GetAllStringValue("vendor_name");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			
			Calendar mData = Calendar.getInstance();
			String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
			String DeliveryDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产单生成</title>
    
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
    <table align="center">
    	<tr>
    		<td align="center">
		  		<form name="Create_Order" action = "Submit/SubmitManualOrder.jsp" method = "post">
	  				<h1>
				  		<label>生产单号:</label>
				  		<input type="text" name="OrderHeader" id="OrderHeader" value="MB-" style="width:30px" readonly>
				  		<input type="text" name="OrderName" id="OrderName" onblur="changeOrderName(this)" value="<%=createDate %>-" style="width:200px">
			  		</h1>
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
						if (product_type != null)
						{
							for(int i = 0; i < product_type.size(); i++)
							{
%>
								  	<option value = <%= product_type.get(i) %>><%=product_type.get(i)%></option>
<%
							}
						}
%>
							  	</select>
						  	</td>
						  	<td align="right">
								<select name="product_name" id="product_name" style="width:100px">
								  	<option value = "--请选择--">--请选择--</option>
								</select>
							</td>
							<td align="center">
								<select name="vendor_name" id="vendor_name" style="width:100px">
									<option value = "--请选择--">--请选择--</option>
								
<%
						if (vendorList != null)
						{
							for(int i = 0; i < vendorList.size(); i++)
							{
%>
								  	<option value = <%= vendorList.get(i) %>><%=vendorList.get(i)%></option>
<%
							}
						}
%>
								</select>
							</td>
							<td align="center"><input type="text" name="bar_code" id="bar_code" style="width:100px" onkeydown="if (event.keyCode == 13) InputBarcode();"></td>
							<td align="center"><input type="text" name="delivery_date" id="delivery_date" value=<%=DeliveryDate %>></td>
							<td align="center"><input type="text" name="order_QTY" id="order_QTY" onchange="Qty_Calc(this)" style="width:40px"></td>
							<td align="center"><input type="text" name="product_QTY" id="product_QTY" value="0" onchange="Qty_Calc(this)" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="material_QTY" id="material_QTY" value="0" onchange="Qty_Calc(this)" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="PO_QTY" id="PO_QTY" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="present" id="present" style="width:40px" value='8'><label>%</label></td>
	    					<td align="center"><input align="middle" type="button" value="确认" onclick="addordercolumn(this)"></td>
					  	</tr>
			    	</table>
			    	<br>
		 		   	<table id="display_order"></table>
		 		   	<br>
		 		   	<table id="confirm_order"></table>
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
			var $displayOrder = $("#display_order");
			var $confirmOrder = $("#confirm_order");
			$displayOrder.attr("border", 1);
			$displayOrder.attr("align", "center");
			$confirmOrder.attr("align", "center");
			var order_name = $("#OrderHeader").val() + $("#OrderName").val();
			if (order_name.length < 11)
			{
				alert("我的乖乖,你就不能起个长点儿的生产单名吗?");
				return;
			}
			$.post("Ajax/Query_Order_Item_Ajax.jsp", {"order_name":order_name, "status":"0"}, function(data, textStatus)
			{
				if (textStatus == "success")
				{
					$displayOrder.empty();
					$confirmOrder.empty();
					var POCount = 0;
					var data_list = data.split("$");
					var iColCount = data_list[1], iRowCount = data_list[2];
					if (iColCount > 0&&iRowCount > 0)
					{
						var tr = $("<tr></tr>");
						for (var iHead = 1; iHead < iColCount; iHead++)
						{
							var th = $("<th>" + data_list[iHead + 3] + "</th>");
							tr.append(th);
						}
						$displayOrder.append(tr);
						for(var iRow = 1; iRow <= iRowCount; iRow++)
						{
							var tr = $("<tr></tr>");
							for (var iCol = 1; iCol < iColCount; iCol++)
							{
								var td = $("<td></td>");
								if (1 == iColCount - iCol)
								{
									td.append("<input type='button' value='删除' name=" + data_list[iRow*iColCount + 3] + " onclick=deleteRecord(this)>");
								}
								else
								{
									td.append(data_list[iRow*iColCount + iCol + 3]);
								}
								if(5 == iColCount - iCol)
								{
									POCount += parseInt(data_list[iRow*iColCount + 8]) - parseInt(data_list[iRow*iColCount + iCol + 3]);
								}
								tr.append(td);
							}
							$displayOrder.append(tr);
						}
						if (POCount <= 0)
						{
							$confirmOrder.append("<tr><td><input align='middle' type='submit' value='提交生产单'></td></tr>");
						}
						else
						{
							$confirmOrder.append("<tr><td><input align='middle' type='button' onclick=CreatePO(this) value='生成采购单'></td></tr>");
						}
					}
				}
			});
		}
		
		function addordercolumn(obj)
		{
			var order_name = $("#OrderHeader").val() + $("#OrderName").val();
			var vendor = $("#vendor_name").find("option:selected").text();
			if($("#OrderName").val()==""||$("#product_type").find("option:selected").text().indexOf("请选择")>=0||$("#product_name").find("option:selected").text().indexOf("请选择")>=0||$("#delivery_date").val().length != 8||$("#order_QTY").val()==""||parseInt($("#order_QTY").val()) <= 0)
			{
				alert("我说大姐,你这输入信息糊弄谁呢?");
				return;
			}
			$.post("Ajax/Add_Order_Item_Ajax.jsp", {"vendor": vendor, "bar_code":$("#bar_code").val(), "delivery_date":$("#delivery_date").val(), "order_QTY":$("#order_QTY").val(), "present":$("#present").val(), "order_name":order_name}, function(data, textStatus)
			{
				if (textStatus == "success")
				{
					if(data.indexOf('error') >= 0)
					{
						alert(data.split('$')[1]);
						return;
					}
				}
				changeOrderName();
			});
		}
		
		function deleteRecord(obj)
		{
			var order_name = $("#OrderHeader").val() + $("#OrderName").val();
			var delID = obj.name;
			$.post("Ajax/Del_Order_Item_Ajax.jsp", {"product_id":delID, "Order_Name":order_name, "Bar_Code":$("#bar_code").val()}, function(data, textStatus)
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
		
		function CreatePO(obj)
		{
			var order_name = $("#OrderHeader").val() + $("#OrderName").val();
			location.href ="List_Purchase_By_Order.jsp?Order_Name="+order_name;
		}
		
		function InputBarcode(obj)
		{
			$("#product_name").empty();
			var barcode = $("#bar_code").val();
			if(barcode == null||barcode == "" || barcode.length != 8)
			{
				alert("八码的内容和位数不符合要求");
				$("#bar_code").val("");
				return;
			}
			$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":barcode}, function(data, textStatus)
			{
				if (textStatus == "success" && data.indexOf("error") < 0)
				{
					var proInfoList = data.split("$");
					$("#product_type").val(proInfoList[2]);
					var newOption = $("<option>" + proInfoList[3] + "</option>");
					$(newOption).val(proInfoList[3]);
					$("#product_name").append(newOption);
					$("#product_name").change();
				}
				else
				{
					alert(data.split("$")[1]);
				}
			});
		}
	</script>
  </body>
</html>
<%
		}
	}
%>