<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> product_type = null, store_name = null;
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
			String sql = "select * from storeroom_name";
			if (hDBHandle.QueryDataBase(sql))
			{
				store_name = hDBHandle.GetAllStringValue("name");
			}
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
		  		<form name="AddMaterial" action = "SubmitMaterial.jsp" method = "post">
		    	<table align="center" border="1">
			    	<tr>
			   			<th>添加产品</th>
			   		</tr>
					<tr>
				  		<td align="right">
					  		<label>库名:</label>
						  	<select name="store_name_addproduct" id="store_name_addproduct" style="width:180px">
							  	<option value = "--请选择--">--请选择--</option>
<%
								for(int i = 0; i < store_name.size(); i++)
								{
%>
							  	<option value = <%= i + 1 %>><%=store_name.get(i)%></option>
<%
								}
%>
						  	</select>
					  	</td>
				  	</tr>
				  	<tr>
				  		<td align="right">
					  		<label>类别:</label>
						  	<select name="product_type" id="product_type" style="width:180px">
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
							<label>Bar Code:</label>
							<select name="bar_code" id="bar_code" style="width:180px">
							  	<option value = "--请选择--">--请选择--</option>
							</select>
						</td>
					</tr>
			    	<tr>
			   			<td align="right">
				   			<label>请输入产品名称:</label>
							<input type="text" name="productname" id="productname" style='width:180px'>
						</td>
					</tr>
					<tr>
			   			<td align="right">
				   			<label>Bar Code:</label>
							<input type="text" name="barcode" id="barcode" style='width:180px'>
						</td>
					</tr>
					<tr>
			   			<td align="right">
				   			<label>入库数量:</label>
							<input type="text" name="QTY" id="QTY" style='width:180px'>
						</td>
					</tr>
					<tr>
			   			<td align="right">
				   			<label>单价:</label>
							<input type="text" name="PriceUnit" id="PriceUnit" style='width:180px'>
						</td>
					</tr>
					<tr>
			   			<td align="center">
							<input type=submit value="提交" style='width:100px'>
						</td>
					</tr>
		    	</table>
				</form>
			</td>
		</tr>
   	</table>
  	<script type="text/javascript">
		$(function()
		{
			var $store_name_addproduct = $('#store_name_addproduct');
			var $product_type = $('#product_type');
			var $product_name = $('#product_name');
			var $bar_code = $('#bar_code');
			
			$store_name_addproduct.change(function()
			{
				$product_type.empty();
				$product_name.empty();
				$bar_code.empty();
				$product_type.append('<option value="请选择">--请选择--</option>');
				$product_name.append('<option value="请选择">--请选择--</option>');
				$bar_code.append('<option value="请选择">--请选择--</option>');
				$.post("App_Pro_Type_Ajax.jsp", {"FilterKey1":$("#store_name_addproduct").find("option:selected").text()}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						var pro_list = data.split("$");
						for (var i = 1; i < pro_list.length - 1; i++)
						{
							var newOption = $("<option>" + pro_list[i] + "</option>");
							$(newOption).val(pro_list[i]);
							$product_type.append(newOption);
						}
					}
				});
			});
			
			$product_type.change(function()
			{
				$product_name.empty();
				$product_name.append('<option value="请选择">--请选择--</option>');
				$.post("App_Pro_Name_Ajax.jsp", {"FilterKey1":$("#product_type").find("option:selected").text()}, function(data, textStatus)
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
				$bar_code.append('<option value="请选择">--请选择--</option>');
				$.post("App_Pro_QTY_Ajax.jsp", {"product_name":$("#product_name").find("option:selected").text()}, function(data, textStatus)
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
						$("#productname").attr("value", $product_name.find("option:selected").text());
					}
				});
			});	
			
			$bar_code.change(function()
			{
				$('#barcode').attr("value", $bar_code.find("option:selected").text());
			});				
		});
	</script>
	
	<script type="text/javascript">
		function changeAddType(obj)
		{
			$.post("AddProTypeAjax.jsp", {"storeroom":$("#store_name_addtype").find("option:selected").text(), "pro_type":$('#producttype').val()}, function(data, textStatus)
			{
				if (!(textStatus == "success" && data.indexOf("产品类型") < 0))
				{
					alert(data);
				}
				location.reload();
			});
		}
		
		function changeAddStore(obj)
		{
			$.post("AddStoreNameAjax.jsp", {"storeroom":$('#storename').val()}, function(data, textStatus)
			{
				if (!(textStatus == "success" && data.indexOf("库名") < 0))
				{
					alert(data);
				}
				location.reload();
			});
		}
	</script>
  </body>
</html>
<%
		}
	}
%>