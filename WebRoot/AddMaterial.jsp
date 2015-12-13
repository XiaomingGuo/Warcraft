<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Storeroom_Name" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
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
			Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
			hSNHandle.GetAllRecord();
			List<String> store_name = hSNHandle.getDBRecordList("name");

			for (int index = 0; index < store_name.size(); index++)
			{
				if (store_name.get(index).indexOf("成品库") == 0)
				{
					store_name.remove(index);
				}
			}
			Calendar mData = Calendar.getInstance();
			String currentDate = String.format("%04d-", mData.get(Calendar.YEAR)) + String.format("%02d-", mData.get(Calendar.MONDAY)+1)+String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
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
  	<script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
  	<script language="javascript" src="Page_JS/AddMaterialJS.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
  <body>
  	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
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
				   			<label>入库时间:</label>
			    			<div dojoType="dropdowndatepicker" name="SubmitDate" id="SubmitDate" displayFormat="yyyy-MM-dd" value="<%=currentDate %>"></div>
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
  </body>
</html>
<%
		}
	}
%>