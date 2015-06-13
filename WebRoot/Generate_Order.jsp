<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> product_type = null;
	String[] displayKeyList = {"产品类型", "产品名称", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "进货余量", "确认录入"};
	List<List<String>> recordList = null;
	int PageRecordCount = 20;
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
			String sql = "select * from product_type where storeroom='成品库'";
			if (hDBHandle.QueryDataBase(sql))
			{
				product_type = hDBHandle.GetAllStringValue("name");
			}
			Calendar mData = Calendar.getInstance();
			String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
			String DeliveryDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1);
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
		  		<form name="Create_Order" action = "SubmitCreateOrder.jsp" method = "post">
			  		<table align="center">
			  			<tr>
			  				<td>
				  				<h1>
							  		<label>订单号:</label>
							  		<input type="text" name="OrderHeader" id="OrderHeader" value="MB-<%=createDate %>-" readonly>
							  		<input type="text" name="OrderName" id="OrderName" onclick="change(this)" value="P015-05-06157">
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
									for(int i = 0; i < product_type.size(); i++)
									{
%>
								  	<option value = <%= product_type.get(i) %>><%=product_type.get(i)%></option>
<%
									}
%>
							  	</select>
						  	</td>
						  	<td align="right">
								<select name="product_name" id="product_name" style="width:100px">
								  	<option value = "--请选择--">--请选择--</option>
								</select>
							</td>
							<td align="center"><input name="bar_code" id="bar_code" style="width:100px" readonly></td>
							<td align="center"><input name="Delivery_Date" id="Delivery_Date" value=<%=DeliveryDate %>></td>
							<td align="center"><input name="QTY" id="QTY"></td>
							<td align="center"><input name="Product_QTY" id="Product_QTY" style="width:60px" readonly></td>
							<td align="center"><input name="Material_QTY" id="Material_QTY" style="width:60px" readonly></td>
							<td align="center"><input name="Need_Material" id="Need_Material" style="width:60px" readonly></td>
							<td align="center"><input name="Present" id="Present" style="width:60px"><label>%</label></td>
	    					<td align="center"><input align="middle" type="button" value="确认" onclick="addordercolumn(this)"></td>
					  	</tr>
			    	</table>
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
				$bar_code.append('<option value="请选择">--请选择--</option>');
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
				$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":$product_name.find("option:selected").text(), "product_type":$product_type.find("option:selected").text()}, function(data, textStatus)
				{
					if (textStatus == "success")
					{
						var code_list = data.split("$");
						for (var i = 1; i < code_list.length - 1; i++)
						{
							$bar_code.attr("value", code_list[i]);
						}
					}
				});
			});	
		});
		
		function addordercolumn(obj)
		{
			//String[] sqlKeyList = {"id", "Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
			//<!-- "产品类型", "产品名称", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "进货余量" -->
			alert("1");
			$.post("Ajax/Add_Order_Item_Ajax.jsp", {"product_type":$("#product_type").find("option:selected").text(), "product_name":$("#product_name").find("option:selected").text()}, function(data, textStatus)
			{
				alert("2");
				if (!(textStatus == "success"))
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