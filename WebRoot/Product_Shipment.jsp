<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> po_list = null, product_type = null, product_info = null;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&32;
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
  	<br><br><br>
   	<form name="AppContent" action = "Submit/SubmitProductShipment.jsp" method = "post">
  	<table align="center" border="1">
	  	<tr>
	  		<th align="center">成品出货</th>
	  	</tr>
		<tr>
			<td align="right">
				<label>出库单号:</label>
			  	<select name="po_select" id="po_select" style="width:180px">
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
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>Bar Code:</label>
				<select name="bar_code" id="bar_code" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>名称:</label>
				<select name="product_name" id="product_name" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>出库数量:</label>
				<input name="QTY" id="QTY" style="width:180px">
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>订单数量:</label>
				<input name="CPO_QTY" id="CPO_QTY" style="width:180px" readonly>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>库存数量:</label>
				<input name="Total_QTY" id="Total_QTY" style="width:180px" readonly>
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
			var $po_select = $('#po_select');
			var $product_name = $('#product_name');
			var $bar_code = $('#bar_code');
			var $CPO_QTY = $('#CPO_QTY');
			var $Total_QTY = $('#Total_QTY');
			
			$po_select.change(function()
			{
				$product_name.empty();
				$bar_code.empty();
				$product_name.append('<option value="请选择">--请选择--</option>');
				$bar_code.append('<option value="请选择">--请选择--</option>');
				$.post("Ajax/Get_PO_Product_Ajax.jsp", {"po_name":$("#po_select").find("option:selected").text()}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						var pro_list = data.split("$");
						for (var i = 1; i < (pro_list.length - 1); i++)
						{
							var newOption = $("<option>" + pro_list[i] + "</option>");
							$(newOption).val(pro_list[i]);
							$bar_code.append(newOption);
						}
					}
				});
			});
			
			$bar_code.change(function()
			{
				$product_name.empty();
				$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":$("#bar_code").find("option:selected").text(), "po_name":$("#po_select").find("option:selected").text()}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						alert(data);
						var code_list = data.split("$");
						var newOption = $("<option>" + code_list[1] + "</option>");
						$product_name.append(newOption);
						$CPO_QTY.val(code_list[2]);
						$Total_QTY.val(code_list[3]);
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