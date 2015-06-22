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
		int temp = mylogon.getUserRight()&16;
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
			else
			{
				hDBHandle.CloseDatabase();
			}
			for (int index = 0; index < store_name.size(); index++)
			{
				if (store_name.get(index).indexOf("成品库") == 0)
				{
					store_name.remove(index);
				}
			}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>物料录入</title>
    
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
    <table width="55%" align="center">
    	<tr>
	    	<td>
				<table width="80%" align="center" border="1">
			    	<tr>
			   			<th align="center">添加库房</th>
			   		</tr>
			   		<tr>
			   			<td align="right">
				    		<label>请输入库房名:</label>
				    		<input type="text" name="storename" id="storename" style='width:140px'>
				    		<input type="button" value="Add" onclick="changeAddStore(this)" style='width:50px'>
			    		</td>
			    	</tr>
		    	</table>
		    	<br><br><br>
		       	<table width="80%" align="center" border="1">
			    	<tr>
			   			<th align="center">添加产品类型</th>
			   		</tr>
					<tr>
				  		<td align="right">
					  		<label>库名:</label>
						  	<select name="store_name_addtype" id="store_name_addtype" style="width:193px">
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
				    		<label>请输入产品类型:</label>
				    		<input type="text" name="producttype" id="producttype" style='width:140px'>
				    		<input type="button" value="Add" onclick="changeAddType(this)" style='width:50px'>
			    		</td>
			    	</tr>
			   	</table>
			</td>
    		<td>
		  		<form name="AddMaterial" action = "Submit/SubmitMaterial.jsp" method = "post">
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
							  	<option value = <%=store_name.get(i) %>><%=store_name.get(i)%></option>
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
							<input type="text" name="productname" id="productname" style='width:180px' onblur="changeProductName(this)">
						</td>
					</tr>
					<tr>
			   			<td align="right">
				   			<label id=lable_barcode>Bar Code:</label>
							<input type="text" name="barcode" id="barcode" style='width:180px' onchange="checkBarcode(this)">
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
				$("#productname").val("");
				$("#barcode").val("");
				$("#QTY").val("");
				$("#PriceUnit").val("");
				$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":$("#store_name_addproduct").find("option:selected").text()}, function(data, textStatus)
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
				$bar_code.empty();
				$product_name.append('<option value="请选择">--请选择--</option>');
				$bar_code.append('<option value="请选择">--请选择--</option>');
				$("#productname").val("");
				$("#barcode").val("");
				$("#QTY").val("");
				$("#PriceUnit").val("");
				$.post("Ajax/App_Pro_Name_Ajax.jsp", {"FilterKey1":$("#product_type").find("option:selected").text()}, function(data, textStatus)
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
				$("#productname").val("");
				$("#barcode").val("");
				$("#QTY").val("");
				$("#PriceUnit").val("");
				$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":$("#product_name").find("option:selected").text(),"product_type":$("#product_type").find("option:selected").text()}, function(data, textStatus)
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
						$("#productname").val($product_name.find("option:selected").text());
					}
				});
			});	
			
			$bar_code.change(function()
			{
				$("#barcode").val("");
				var selectedBarcode = $bar_code.find("option:selected").text();
				//alert(selectedBarcode);
				if (selectedBarcode.indexOf("请选择") > 0)
				{
					//alert("1:");
					$("#barcode").val("");
				}
				else
				{
					//alert("2:"+selectedBarcode);
					$("#barcode").val(selectedBarcode);
				}
			});				
		});
		
		function checkBarcode(obj)
		{
			var checkedBarcode = $("#barcode").val();
			//alert(checkedBarcode);
			if(checkedBarcode == null||checkedBarcode == "" )
			{
				return;
			}
			$.post("Ajax/Check_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
			{
				if (textStatus == "success")
				{
					if (parseInt(data.split('$')[1]) > 0)
					{
						$("#barcode").val("");
					}
				}
			});
		}
		
		function changeProductName(obj)
		{
			$("#barcode").val("");
		}
		
		function changeAddType(obj)
		{
			$.post("Ajax/AddProTypeAjax.jsp", {"storeroom":$("#store_name_addtype").find("option:selected").text(), "pro_type":$('#producttype').val()}, function(data, textStatus)
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
			$.post("Ajax/AddStoreNameAjax.jsp", {"storeroom":$('#storename').val()}, function(data, textStatus)
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