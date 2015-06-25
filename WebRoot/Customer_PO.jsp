<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	List<List<String>> recordList = null;
	String[] displayKeyList = {"产品类型", "产品名称", "供应商", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "进货余量(%)", "操作"};
	String[] sqlKeyList = {"product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "percent", "status"};
	List<String> product_type = null, vendorList = null;
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
			String sql = "select * from product_type where storeroom='成品库'";
			if (hDBHandle.QueryDataBase(sql))
			{
				product_type = hDBHandle.GetAllStringValue("name");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			
			sql = "select * from vendor_info";
			if (hDBHandle.QueryDataBase(sql))
			{
				vendorList = hDBHandle.GetAllStringValue("vendor_name");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			
			/*String tempOrderName = request.getParameter("OrderName");
			if (tempOrderName != null)
			{
				sql = "select * from product_order_record where po_name='" + tempOrderName + "'";
				if (hDBHandle.QueryDataBase(sql))
				{
					recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
				}
				else
				{
					hDBHandle.CloseDatabase();
				}
			}*/
			Calendar mData = Calendar.getInstance();
			String DeliveryDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>客户PO信息录入</title>
    
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
		  		<form name="Create_Order" action = "Submit/SubmitCustomerPO.jsp" method = "post">
			  		<table align="center">
			  			<tr>
			  				<td>
				  				<h1>
							  		<label>客户PO号:</label>
							  		<input type="text" name="POName" id="POName" onblur="changePOName(this)" value="P015-05-06157" style="width:200px">
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
							<td align="center"><input type="text" name="bar_code" id="bar_code" style="width:100px" readonly></td>
							<td align="center"><input type="text" name="delivery_date" id="delivery_date" value=<%=DeliveryDate %>></td>
							<td align="center"><input type="text" name="cpo_QTY" id="cpo_QTY" onblur="Qty_Calc(this)" style="width:40px"></td>
							<td align="center"><input type="text" name="product_QTY" id="product_QTY" value="0" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="material_QTY" id="material_QTY" value="0" onchange="Qty_Calc(this)" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="Need_QTY" id="Need_QTY" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="percent" id="percent" style="width:100%" value='8'></td>
	    					<td align="center"><input align="middle" id="confirm_button" type="button" value="确认" onclick="addpoitem(this)"></td>
					  	</tr>
			    	</table>
			    	<br><br>
		 		   	<table id="display_po"" border='1' align="center"></table>
		 		   	<br><br>
		 		   	<table id="confirm_po" align="center"></table>
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
						Qty_Calc();
					}
				});
			});	
		});
		
		function changePOName(obj)
		{
			var $displayOrder = $("#display_po");
			var $confirmOrder = $("#confirm_po");
			var po_name = $("#POName").val();
			if (po_name.length < 6)
			{
				alert("我的乖乖,你就不能起个长点儿的PO单名吗?");
				return;
			}
			$.post("Ajax/Query_PO_Item_Ajax.jsp", {"po_name":po_name, "status":"0"}, function(data, textStatus)
			{
				if (textStatus == "success")
				{
					$displayOrder.empty();
					$confirmOrder.empty();
					var Count = 0;
					var data_list = data.split("$");
					var status = data_list[1], iColCount = data_list[2], iRowCount = data_list[3];
					if (status != "null")
					{
						$("#confirm_button").attr("disabled", "disabled");
					}
					else
					{
						$("#confirm_button").removeAttr("disabled");
					}
					if (iColCount > 0&&iRowCount > 0)
					{
						var tr = $("<tr></tr>");
						for (var iHead = 1; iHead <= iColCount; iHead++)
						{
							var th = $("<th>" + data_list[iHead + 3] + "</th>");
							tr.append(th);
						}
						$displayOrder.append(tr);
						for(var iRow = 1; iRow <= iRowCount; iRow++)
						{
							var tr = $("<tr></tr>");
							for (var iCol = 1; iCol <= iColCount; iCol++)
							{
								var td = $("<td></td>");
								if (0 == iColCount - iCol)
								{
									if(status == "null")
									{
										td.append("<input type='button' value='删除' name=" + data_list[iRow*iColCount + iCol + 3] + " onclick=deleteRecord(this)>");
									}
									else
									{
										td.append("<label>已提交</label>");
									}
								}
								else
								{
									td.append(data_list[iRow*iColCount + iCol + 3]);
								}
								if(3 == iColCount - iCol)
								{
									Count += parseInt(data_list[iRow*iColCount + iCol + 3]);
								}
								tr.append(td);
							}
							$displayOrder.append(tr);
						}
						
						var cmdtr = $("<tr></tr>");
						if (status == "null")
						{
							cmdtr.append("<td><input align='middle' type='submit' value='录入订单'></td>");
						}
						if (Count > 0)
						{
							cmdtr.append("<td><input align='middle' type='button' onclick=CreatePO(this) value='生成采购单'></td>");
						}
						$confirmOrder.append(cmdtr);
					}
				}
			});
		}
		
		function addpoitem(obj)
		{
			var po_name = $("#POName").val();
			if(po_name==""||$("#bar_code").val() == ""||$("#delivery_date").val().length != 8||parseInt($("#order_QTY").val()) <= 0)
			{
				alert("我说大姐,你这输入信息糊弄谁呢?");
				return;
			}
			$.post("Ajax/Add_PO_Item_Ajax.jsp", {"bar_code":$("#bar_code").val(), "delivery_date":$("#delivery_date").val(), "cpo_QTY":$("#cpo_QTY").val(), "percent":$("#percent").val(), "vendor_name":$("#vendor_name").find("option:selected").text(), "po_name":po_name}, function(data, textStatus)
			{
				if (!(textStatus == "success"))
				{
					alert(data);
				}
				changePOName();
			});
		}
		
		function deleteRecord(obj)
		{
			var delID = obj.name;
			$.post("Ajax/Del_PO_Item_Ajax.jsp", {"product_id":delID}, function(data, textStatus)
			{
				if (!(textStatus == "success"))
				{
					alert(data);
				}
				changePOName();
			});
		}
		
		function Qty_Calc(obj)
		{
			var poCount = parseInt($("#cpo_QTY").val());
			var proCount = parseInt($("#product_QTY").val());
			var matCount = parseInt($("#material_QTY").val());
			var tempQTY = (proCount + matCount) - poCount;
			if (poCount > 0&&proCount >= 0&&matCount >= 0)
			{
				if (tempQTY >= 0)
				{
					$("#Need_QTY").val(0);
				}
				else
				{
					$("#Need_QTY").val(-tempQTY);
				}
			}
		}
		
		function CreatePO(obj)
		{
			var po_name = $("#OrderHeader").val() + $("#OrderName").val();
			location.href ="List_Purchase.jsp?PO_Name="+po_name;
		}
	</script>
  </body>
</html>
<%
		}
	}
%>