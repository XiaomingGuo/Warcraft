<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> product_order = null, product_info = null;
%>
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
			
			//storeroom name Database query
			String sql = "select * from product_order";
			if (hDBHandle.QueryDataBase(sql))
			{
				product_order = hDBHandle.GetAllStringValue("Order_Name");
			}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>材料报废</title>
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
  	<br><br><br>
   	<form name="AppContent" action = "Submit/SubmitDiscardMaterial.jsp" method = "post">
  	<table align="center" border="1">
	  	<tr>
	  		<th align="center">材料报废</th>
	  	</tr>
		<tr>
	  		<td align="right">
		  		<label>生产单:</label>
			  	<select name="product_order" id="product_order" style="width:200px">
				  	<option value = "--请选择--">--请选择--</option>
<%
			if (product_order != null)
			{
				for(int i = 0; i < product_order.size(); i++)
				{
%>
				  	<option value = <%=product_order.get(i)%>><%=product_order.get(i)%></option>
<%
				}
			}
%>
			  	</select>
		  	</td>
	  	</tr>
		<tr>
			<td align="right">
				<label>名称:</label>
				<select name="product_name" id="product_name" style="width:200px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>Bar Code:</label>
				<select name="bar_code" id="bar_code" style="width:200px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>报废数量:</label>
				<select name="QTY" id="QTY" style="width:200px">
<%
					for(int i = 1; i <= 20; i++)
					{
%>
			  		<option value = <%=i%>><%=i%></option>
<%
					}
%>		
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>库存数量:</label>
				<input name="Total_QTY" id="Total_QTY" style="width:200px" readonly>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>报废原因:</label>
				<input name="discard_reason" id="discard_reason" style="width:200px">
			</td>
		</tr>
		<tr>
			<td align="center">
				<input name="commit" type=submit value="提交" style="width:100">
			</td>
		</tr>
	</table>
  	</form>
  	<script type="text/javascript">
		$(function()
		{
			var $product_order = $('#product_order');
			var $product_name = $('#product_name');
			var $bar_code = $('#bar_code');
			var $Total_QTY = $('#Total_QTY');
			
			$product_order.change(function()
			{
				$product_name.empty();
				$bar_code.empty();
				$product_name.append('<option value="请选择">--请选择--</option>');
				$bar_code.append('<option value="请选择">--请选择--</option>');
				$.post("Ajax/Query_Pro_Name_From_Order_Ajax.jsp", {"FilterKey1":$("#product_order").find("option:selected").text()}, function(data, textStatus)
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
				$bar_code.empty();
				var tempValue = $("#product_name").find("option:selected").text().split("-");
				$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_type":tempValue[0], "product_name":tempValue[1]+"-"+tempValue[2], "storage":"material_storage"}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						var code_list = data.split("$");
						for (var i = 1; i < code_list.length - 1; i++)
						{
							var newOption = $("<option>" + code_list[i] + "</option>");
							$(newOption).val(code_list[i]);
							$bar_code.append(newOption);
						}
						$Total_QTY.attr("value", code_list[i]);
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