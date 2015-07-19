<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> po_list = null, product_type = null;
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
			String sql = "select * from customer_po where status = 0";
			if (hDBHandle.QueryDataBase(sql))
			{
				po_list = hDBHandle.GetAllStringValue("po_name");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
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
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
  	<script type="text/javascript">
  		function winload()
  		{
			$("#display_page_order").load("Generate_Order_Manual.jsp");
  		}
  	</script>
  </head>
  <body onload="winload()">
    <jsp:include page="Menu/ManufactureMenu.jsp"/>
    <br><br>
    <table align="center">
    	<tr>
    		<td align="center">
			<form name="Create_Order" action = "Submit/SubmitCreateOrder.jsp" method = "post">
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
	 		   	<div id="display_page_po" align="center">
		 		   	<table id="display_order_po"></table>
		 		   	<table id="confirm_order_po"></table>
				</div>
			</form>
			</td>
		</tr>
		<tr>
			<td>
			   	<div id="display_page_order" align="center"></div>
			</td>
		</tr>
   	</table>
	<script type="text/javascript">
		$(function()
		{
			var $po_select = $('#po_select');
			$po_select.change(function()
			{
				var po_name = $.trim($po_select.find("option:selected").text());
				if (po_name.indexOf("请选择") >= 0)
				{
					$("#display_page_po").hide();
					$("#display_page_order").show();
					$("#display_page_order").load("Generate_Order_Manual.jsp");
				}
				else
				{
					$("#display_page_order").hide();
					$("#display_page_po").show();
					var $displayOrder = $("#display_order_po");
					var $confirmOrder = $("#confirm_order_po");
					$displayOrder.empty();
					$confirmOrder.empty();
					$displayOrder.attr("border", 1);
					$displayOrder.attr("align", "center");
					$confirmOrder.attr("align", "center");
					$.post("Ajax/Generate_Order_Item_Ajax.jsp", {"po_name":$.trim(po_name), "status":"0"}, function(data, textStatus)
					{
						if (textStatus == "success")
						{
							var Count = 0;
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
										if (0 == iColCount - iCol)
										{
											if(parseInt(data_list[iRow*iColCount + 11])-parseInt(data_list[iRow*iColCount + 10])-parseInt(data_list[iRow*iColCount + 12])-parseInt(data_list[iRow*iColCount + 13]) > 0)
											{
												td.append("<input type='text' name='" + iRow + "_QTY' id='" + iRow + "_QTY' value=" + data_list[iRow*iColCount + iCol + 2] + ">");
											}
											else
											{
												td.append("<label>已完成</label>");
											}
										}
										else
										{
											td.append(data_list[iRow*iColCount + iCol + 2]);
										}
										if(2 == iColCount - iCol)
										{
											Count += parseInt(data_list[iRow*iColCount + iCol + 2]);
										}
										tr.append(td);
									}
									$displayOrder.append(tr);
								}
								if (Count > 0)
								{
									var cmdtr = $("<tr></tr>");
									cmdtr.append("<td><input align='middle' type='submit' value='提交生产单'></td>");
									$confirmOrder.append(cmdtr);
								}
							}
						}
					});
				}
			});
		});
	</script>
  </body>
</html>
<%
		}
	}
%>