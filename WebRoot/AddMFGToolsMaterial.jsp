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
			String[] selectKeyList = {"库名", "类别", "名称", "八码"};
			String[] inputKeyList = {"八码", "产品名称:", "入库数量", "单重", "单价", "备注", "供应商", "操作"};
			//product_type Database query
			Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
			hSNHandle.GetAllRecord();
			List<String> store_name = hSNHandle.getDBRecordList("name");
			store_name.remove(store_name.indexOf("成品库"));
			store_name.remove(store_name.indexOf("半成品库"));
			store_name.remove(store_name.indexOf("原材料库"));
			Calendar mData = Calendar.getInstance();
			String currentDate = String.format("%04d-", mData.get(Calendar.YEAR)) + String.format("%02d-", mData.get(Calendar.MONDAY)+1)+String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>五金录入</title>
    
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
  	<script language="javascript" src="Page_JS/AddMFGToolsMaterialJS.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
  <body>
  	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
    <jsp:include page="Menu/MFGToolsMenu.jsp"/>
    <br>
    <table width="65%" align="center">
    	<tr>
    		<td>
		    	<table align="center" border="1">
		    		<caption><b>添加产品</b></caption>
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
						  	<select name="store_name_addproduct" id="store_name_addproduct" style="width:120px">
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
						  	<select name="product_type" id="product_type" style="width:100px">
							  	<option value = "--请选择--">--请选择--</option>
						  	</select>
					  	</td>
						<td align="right">
							<select name="product_name" id="product_name" style="width:150px">
							  	<option value = "--请选择--">--请选择--</option>
							</select>
						</td>
						<td align="right">
							<select name="bar_code" id="bar_code" style="width:100px">
							  	<option value = "--请选择--">--请选择--</option>
							</select>
						</td>
				  	</tr>
			  	</table>
			  	<br>
		    	<table align="center" border="1">
    				<tr>
<%
				for(int iCol = 1; iCol <= inputKeyList.length; iCol++)
				{
%>
						<th><%= inputKeyList[iCol-1]%></th>
<%
				}
%>
					</tr>
				  	<tr>
			   			<td align="right">
							<input type="text" name="barcode" id="barcode" style='width:100px' onblur="InputBarcode()">
						</td>
			   			<td align="right">
							<input type="text" name="productname" id="productname" style='width:120px'>
						</td>
			   			<td align="right">
							<input type="text" name="QTY" id="QTY" style='width:70px'>
						</td>
			   			<td align="right">
							<input type="text" name="WeightUnit" id="WeightUnit" style='width:70px'>
						</td>
			   			<td align="right">
							<input type="text" name="PriceUnit" id="PriceUnit" style='width:70px'>
						</td>
			   			<td align="right">
							<input type="text" name="Description" id="Description" value="无备注" style='width:120px'>
						</td>
				  		<td align="right">
						  	<select name="supplier_name" id="supplier_name" style="width:120px">
							  	<option value = "--请选择--">--请选择--</option>
						  	</select>
					  	</td>
						<td align="center"><input align="middle" type="button" value="确认" onclick="additem(this)"></td>
				  	</tr>
			  	</table>
			  	<br><br>
		  		<table id="display_add" border='1' align="center"></table>
			  	<br>
			  	<table align="center">
					<tr>
			   			<td align="center">
				   			<label>入库时间:</label>
			    			<div dojoType="dropdowndatepicker" name="SubmitDate" id="SubmitDate" displayFormat="yyyy-MM-dd" value="<%=currentDate %>"></div>
						</td>
			   			<td align="center">
							<input type="button" value="提交" style='width:100px' onclick="AddMFGToolsMaterialFun()">
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