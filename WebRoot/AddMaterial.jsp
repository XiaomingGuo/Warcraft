<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
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
		int temp = mylogon.getUserRight()&2048;
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
    <jsp:include page="Menu/DataEnterMenu.jsp"/>
    <br><br>
    <table width="55%" align="center">
    	<tr>
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
					  		<label>供应商名:</label>
						  	<select name="supplier_name" id="supplier_name" style="width:180px">
							  	<option value = "--请选择--">--请选择--</option>
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
							<input type="text" name="barcode" id="barcode" style='width:180px' onblur="InputBarcode()">
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
				   			<label>单重:</label>
							<input type="text" name="WeightUnit" id="WeightUnit" style='width:180px'>
						</td>
					</tr>
					<tr>
			   			<td align="right">
				   			<label>单价:</label>
							<input type="text" name="PriceUnit" id="PriceUnit" style='width:180px'>
						</td>
					</tr>
					<tr>
			   			<td align="right">
				   			<label>备注:</label>
							<input type="text" name="Description" id="Description" style='width:180px'>
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
			var $supplier_name = $('#supplier_name');
			var $product_type = $('#product_type');
			var $product_name = $('#product_name');
			var $bar_code = $('#bar_code');
			
			$store_name_addproduct.change(function()
			{
				$supplier_name.empty();
				$product_type.empty();
				$product_name.empty();
				$bar_code.empty();
				$supplier_name.append('<option value="请选择">--请选择--</option>');
				$product_type.append('<option value="请选择">--请选择--</option>');
				$product_name.append('<option value="请选择">--请选择--</option>');
				$bar_code.append('<option value="请选择">--请选择--</option>');
				$("#productname").val("");
				$("#barcode").val("");
				$("#QTY").val("");
				$("#WeightUnit").val("");
				$("#PriceUnit").val("");
				$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":$("#store_name_addproduct").find("option:selected").text()}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						var data_list = data.split("#");
						var vendor_list = data_list[0].split("$");
						for (var i = 1; i < vendor_list.length - 1; i++)
						{
							var newOption = $("<option>" + vendor_list[i] + "</option>");
							$(newOption).val(vendor_list[i]);
							$supplier_name.append(newOption);
						}
						var pro_list = data_list[1].split("$");
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
				$("#WeightUnit").val("");
				$("#WeightUnit").removeAttr("readonly");
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
				$("#productname").val("");
				$("#barcode").val("");
				$("#QTY").val("");
				$("#WeightUnit").val("");
				$("#WeightUnit").removeAttr("readonly");
				$("#Description").removeAttr("readonly");
				$("#PriceUnit").val("");
				$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":$("#product_name").find("option:selected").text(),"product_type":$("#product_type").find("option:selected").text()}, function(data, textStatus)
				{
					alert(data);
					if (textStatus == "success")
					{
						var code_list = data.split("$");
						if (code_list.length == 5)
						{
							var newOption = $("<option>" + code_list[1] + "</option>");
							$(newOption).val(code_list[1]);
							$bar_code.append(newOption);
							$("#productname").val($product_name.find("option:selected").text());
							$("#barcode").val(code_list[1]);
							$("#WeightUnit").val(code_list[2]);
							$("#WeightUnit").attr("readonly", "readonly");
							$("#Description").val(code_list[3]);
							$("#Description").attr("readonly", "readonly");
						}
						else
						{
							$bar_code.append('<option value="请选择">--请选择--</option>');
							$("#barcode").val("");
							$("#WeightUnit").val(code_list[2]);
						}
					}
				});
			});	
			
			$bar_code.change(function()
			{
				$("#barcode").val("");
				var selectedBarcode = $bar_code.find("option:selected").text();
				if (selectedBarcode.indexOf("请选择") >= 0)
				{
					$("#barcode").val("");
				}
				else
				{
					$("#barcode").val(selectedBarcode);
				}
			});				
		});
		
		function checkBarcode(obj)
		{
			var checkedBarcode = $("#barcode").val();
			if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
			{
				alert("八码的内容和位数不符合要求");
				$("#barcode").val("");
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
		
		function InputBarcode(obj)
		{
			var checkedBarcode = $("#barcode").val();
			if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
			{
				$("#barcode").val("");
				return;
			}
			if (parseInt(checkedBarcode) > 60000000 && parseInt(checkedBarcode) < 70000000)
			{
				checkedBarcode = parseInt(checkedBarcode)-10000000;
			}
			$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
			{
				if (textStatus == "success" && data.indexOf("error") < 0)
				{
					var proInfoList = data.split("$");
					if(parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 70000000)
					{
						$("#store_name_addproduct").val("原材料库");
					}
					else
					{
						$("#store_name_addproduct").val(proInfoList[1]);
					}
					$("#store_name_addproduct").change();
					$("#product_type").empty();
					if(parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 70000000)
					{
						var keyWord = proInfoList[2];
						if (keyWord.indexOf("原锭") < 0)
						{
							keyWord += "原锭";
						}
						var newOption = $("<option>" + keyWord + "</option>");
						$(newOption).val(keyWord);
						$("#product_type").append(newOption);
					}
					else
					{
						var newOption = $("<option>" + proInfoList[2] + "</option>");
						$(newOption).val(proInfoList[2]);
						$("#product_type").append(newOption);
					}
					$("#product_name").empty();
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