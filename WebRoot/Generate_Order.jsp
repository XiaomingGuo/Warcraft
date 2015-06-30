<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> po_list = null, product_type = null;
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
			String sql = "select * from customer_po where status<5";
			if (hDBHandle.QueryDataBase(sql))
			{
				po_list = hDBHandle.GetAllStringValue("po_name");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			
			String tempOrderName = request.getParameter("OrderName");
			if (tempOrderName != null)
			{
				sql = "select * from product_order_record where Order_Name='" + tempOrderName + "'";
				if (hDBHandle.QueryDataBase(sql))
				{
					recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
				}
				else
				{
					hDBHandle.CloseDatabase();
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
    <jsp:include page="MainMenu.jsp"/>
    <br><br>
    <table align="center">
    	<tr>
    		<td>
		  		<form name="Create_Order" action = "Submit/SubmitCreateOrder.jsp" method = "post">
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
					alert("我的乖乖,你就不能选择个正确的PO单号吗?");
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
							cmdtr.append("<td><input align='middle' type='submit' value='提交生产单'></td>");
							$confirmOrder.append(cmdtr);
						}
					}
				});
			});
		});
		
		function addordercolumn(obj)
		{
			var order_name = $("#OrderHeader").val() + $("#OrderName").val();
			if($("#OrderName").val()==""||$("#product_type").find("option:selected").text().indexOf("请选择")>=0||$("#product_name").find("option:selected").text().indexOf("请选择")>=0||$("#delivery_date").val().length != 8||$("#order_QTY").val()==""||parseInt($("#order_QTY").val()) <= 0)
			{
				alert("我说大姐,你这输入信息糊弄谁呢?");
				return;
			}
			$.post("Ajax/Add_Order_Item_Ajax.jsp", {"product_type":$("#product_type").find("option:selected").text(), "product_name":$("#product_name").find("option:selected").text(), "bar_code":$("#bar_code").val(), "delivery_date":$("#delivery_date").val(), "order_QTY":$("#order_QTY").val(), "present":$("#present").val(), "order_name":order_name}, function(data, textStatus)
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
			location.href ="Generate_PO.jsp?OrderName="+order_name;
		}
	</script>
  </body>
</html>
<%
		}
	}
%>