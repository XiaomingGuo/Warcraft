<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> product_type = null;
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
			String sql = "select * from product_type";
			if (hDBHandle.QueryDataBase(sql))
			{
				product_type = hDBHandle.GetAllStringValue("name");
			}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>录入</title>
    
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
    <jsp:include page="MainPage.jsp"/>
   	<center>
    	<table border="1">
    	<tr>
   			<th align="center">添加产品类型</th>
   		</tr>
   		<tr>
   			<td>
	    		<label>请输入产品类型:</label>
	    		<input type="text" name="producttype" id="producttype" style='width:140px'>
	    		<input type="button" value="Add" onclick="changeAddType(this)" style='width:50px'>
    		</td>
    	</tr>
    	</table>
    	<br><br><br>
   		<form name="AddMaterial" action = "SubmitMaterial.jsp" method = "post">
	    	<table border="1">
	    	<tr>
	   			<th>添加产品</th>
    		</tr>
    		<tr>
    			<td align="right">
		    		<label>请选择产品类型:</label>
				  	<select name="product_type" id="product_type" style='width:180px'>
					  	<option value = "--请选择类别--">--请选择类别--</option>
<%
for(int i = 0; i < product_type.size(); i++)
{
%>
		  				<option value = <%=product_type.get(i)%>><%=product_type.get(i)%></option>
<%
}
%>
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
   	</center>
	  	<script type="text/javascript">
		$(function()
		{
			var $product_type = $('#product_type');
			var $product_name = $('#product_name');
			var $bar_code = $('#bar_code');
			
			$product_type.change(function()
			{
				$product_name.empty();
				$product_name.append('<option value="请选择">--请选择--</option>');
				$.post("AppAjax.jsp", {"FilterKey1":$("#product_type").find("option:selected").text()}, function(data, textStatus)
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
				$.post("Pro_QTY_Ajax.jsp", {"product_name":$("#product_name").find("option:selected").text()}, function(data, textStatus)
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
			alert($('#producttype').val());
			$.post("AddProTypeAjax.jsp", {"pro_type":$('#producttype').val()}, function(data, textStatus)
			{
				if (!(textStatus == "success" && data.indexOf("产品类型") < 0))
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