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
    <jsp:include page="Menu/ManufactureMenu.jsp"/>
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
				$.post("Ajax/Generate_Order_Item_Ajax.jsp", {"po_name":po_name, "status":"0"}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						$displayOrder.empty();
						$confirmOrder.empty();
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
										if(data_list[iRow*iColCount + iCol + 2] != "0")
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
			});
		});
	</script>
  </body>
</html>
<%
		}
	}
%>