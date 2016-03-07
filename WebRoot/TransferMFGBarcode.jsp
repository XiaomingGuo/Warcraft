<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PageParentClass" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	PageParentClass hPageHandle = new PageParentClass();
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
			String[] selectKeyList = {"库名", "类别", "名称", "八码"};
			String[] from_inputKeyList = {"八码", "产品名称", "转出数量", "库存数量", "原材料单重", "成品单重", "备注"};
			String[] to_inputKeyList = {"八码", "产品名称", "转换数量", "原材料单重", "成品单重", "备注"};
			//product_type Database query
			List<String> store_name = hPageHandle.GetStoreName("MFG");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产物料八码转换</title>
    
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
  	<script language="javascript" src="Page_JS/TransferMFGBarcodeJS.js"></script>
<!-- <script language="javascript" src="Page_JS/AddMaterialJS.js"></script>
  	<script language="javascript" src="Page_JS/AddMFGMaterialJS.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
-->
  <body>
  	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
    <jsp:include page="Menu/DataEnterMenu.jsp"/>
    <br>
    <table width="65%" align="center">
    	<tr>
    		<td>
		    	<table align="center" border="1">
		    		<caption><b>转换源物料</b></caption>
    				<tr>
<%
				for(int iCol = 1; iCol <= selectKeyList.length; iCol++)
				{
%>
						<th><%= selectKeyList[iCol-1]%></th>
<%
				}
%>
					</tr>
					<tr>
				  		<td align="right">
						  	<select name="from_store_name" id="from_store_name" style="width:120px">
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
				  		<td align="right">
						  	<select name="from_product_type" id="from_product_type" style="width:100px">
							  	<option value = "--请选择--">--请选择--</option>
						  	</select>
					  	</td>
						<td align="right">
							<select name="from_product_name" id="from_product_name" style="width:150px">
							  	<option value = "--请选择--">--请选择--</option>
							</select>
						</td>
						<td align="right">
							<select name="from_bar_code" id="from_bar_code" style="width:100px">
							  	<option value = "--请选择--">--请选择--</option>
							</select>
						</td>
				  	</tr>
			  	</table>
			  	<br>
		    	<table align="center" border="1">
    				<tr>
<%
				for(int iCol = 1; iCol <= from_inputKeyList.length; iCol++)
				{
%>
						<th><%= from_inputKeyList[iCol-1]%></th>
<%
				}
%>
					</tr>
				  	<tr>
			   			<td align="right">
							<input type="text" name="from_barcode" id="from_barcode" style='width:100px' onblur="from_InputBarcode()">
						</td>
			   			<td align="right">
							<input type="text" name="from_productname" id="from_productname" style='width:120px'>
						</td>
			   			<td align="right">
							<input type="text" name="from_QTY" id="from_QTY" style='width:70px'>
						</td>
			   			<td align="right">
							<input type="text" name="store_QTY" id="store_QTY" style='width:70px'>
						</td>
			   			<td align="right">
							<input type="text" name="from_WeightUnit" id="from_WeightUnit" style='width:90px'>
						</td>
			   			<td align="right">
							<input type="text" name="from_ProductWeight" id="from_ProductWeight" style='width:70px'>
						</td>
			   			<td align="right">
							<input type="text" name="from_Description" id="from_Description" value="无备注" style='width:120px'>
						</td>
				  	</tr>
			  	</table>
			  	<br><br>
				<table align="center" border="1">
		    		<caption><b>转换目标物料</b></caption>
    				<tr>
<%
				for(int iCol = 1; iCol <= selectKeyList.length; iCol++)
				{
%>
						<th><%= selectKeyList[iCol-1]%></th>
<%
				}
%>
					</tr>
					<tr>
				  		<td align="right">
						  	<select name="to_store_name" id="to_store_name" style="width:120px">
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
				  		<td align="right">
						  	<select name="to_product_type" id="to_product_type" style="width:100px">
							  	<option value = "--请选择--">--请选择--</option>
						  	</select>
					  	</td>
						<td align="right">
							<select name="to_product_name" id="to_product_name" style="width:150px">
							  	<option value = "--请选择--">--请选择--</option>
							</select>
						</td>
						<td align="right">
							<select name="to_bar_code" id="to_bar_code" style="width:100px">
							  	<option value = "--请选择--">--请选择--</option>
							</select>
						</td>
				  	</tr>
			  	</table>
			  	<br>
		    	<table align="center" border="1">
    				<tr>
<%
				for(int iCol = 1; iCol <= to_inputKeyList.length; iCol++)
				{
%>
						<th><%= to_inputKeyList[iCol-1]%></th>
<%
				}
%>
					</tr>
				  	<tr>
			   			<td align="right">
							<input type="text" name="to_barcode" id="to_barcode" style='width:100px' onblur="to_InputBarcode()">
						</td>
			   			<td align="right">
							<input type="text" name="to_productname" id="to_productname" style='width:120px'>
						</td>
			   			<td align="right">
							<input type="text" name="to_QTY" id="to_QTY" style='width:70px'>
						</td>
			   			<td align="right">
							<input type="text" name="to_WeightUnit" id="to_WeightUnit" style='width:90px'>
						</td>
			   			<td align="right">
							<input type="text" name="to_ProductWeight" id="to_ProductWeight" style='width:70px'>
						</td>
			   			<td align="right">
							<input type="text" name="to_Description" id="to_Description" value="无备注" style='width:120px'>
						</td>
				  	</tr>
			  	</table>
			  	<br>
			  	<table align="center">
					<tr>
			   			<td align="center">
							<input type="button" value="提交" style='width:100px' onclick="DoTranferBarcode()">
						</td>
					</tr>
		    	</table>
			</td>
		</tr>
   	</table>
  </body>
</html>
<%
		}
	}
%>