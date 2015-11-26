<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Type" %>
<%@ page import="com.DB.operation.Vendor_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Generate_Order" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	String[] displayKeyList = {"产品类型", "产品名称", "供应商", "八码", "交货日期", "数量", "成品库存", "原材料库存", "缺料数量", "进货余量(%)", "操作"};
	String[] sqlKeyList = {"product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "percent", "status"};
	List<List<String>> recordList = null;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&1024;
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
			Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
			hPTHandle.GetRecordByStoreroom("成品库");
			List<String> product_type = hPTHandle.getDBRecordList("name");
			
			Vendor_Info hVIHandle = new Vendor_Info(new EarthquakeManagement());
			hVIHandle.GetRecordByStoreroom("原材料库");
			List<String> vendorList = hVIHandle.getDBRecordList("vendor_name");
			
			Generate_Order hPageSupport = new Generate_Order();
			Calendar mData = Calendar.getInstance();
			String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
			String DeliveryDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1);
			String OrderName = hPageSupport.GenOrderName(createDate);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产单生成</title>
    
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
  	<script language="javascript" src="Page_JS/Generate_OrderJS.js"></script>
  <body>
    <jsp:include page="Menu/ManufactureMenu.jsp"/>
    <br><br>
    <table align="center">
    	<tr>
    		<td align="center">
		  		<form name="Create_Order" action = "Submit/SubmitManualOrder.jsp" method = "post">
	  				<h1>
				  		<label>生产单号:</label>
				  		<input type="text" name="OrderHeader" id="OrderHeader" value="MB_" style="width:30px" readonly>
				  		<input type="text" name="OrderName" id="OrderName" onblur="changeOrderName(this)" value="<%=OrderName %>" style="width:200px">
			  		</h1>
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
						if (product_type != null)
						{
							for(int i = 0; i < product_type.size(); i++)
							{
%>
								  	<option value = <%= product_type.get(i) %>><%=product_type.get(i)%></option>
<%
							}
						}
%>
							  	</select>
						  	</td>
						  	<td align="right">
								<select name="product_name" id="product_name" style="width:100px">
								  	<option value = "--请选择--">--请选择--</option>
								</select>
							</td>
							<td align="center">
								<select name="vendor_name" id="vendor_name" style="width:100px">
									<option value = "--请选择--">--请选择--</option>
								
<%
						if (vendorList != null)
						{
							for(int i = 0; i < vendorList.size(); i++)
							{
%>
								  	<option value = <%= vendorList.get(i) %>><%=vendorList.get(i)%></option>
<%
							}
						}
%>
								</select>
							</td>
							<td align="center"><input type="text" name="bar_code" id="bar_code" style="width:100px" onblur="InputBarcode()"></td>
							<td align="center"><input type="text" name="delivery_date" id="delivery_date" value=<%=DeliveryDate %>></td>
							<td align="center"><input type="text" name="corder_QTY" id="corder_QTY" onblur="Qty_Calc(this)" style="width:40px"></td>
							<td align="center"><input type="text" name="product_QTY" id="product_QTY" value="0" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="material_QTY" id="material_QTY" value="0" onchange="Qty_Calc(this)" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="Need_QTY" id="Need_QTY" style="width:60px" readonly></td>
							<td align="center"><input type="text" name="percent" id="percent" style="width:100%" value='8' readonly></td>
	    					<td align="center"><input align="middle" id="confirm_button" type="button" value="确认" onclick="addorderitem(this)" disabled></td>
					  	</tr>
			    	</table>
			    	<br><br>
		 		   	<table id="display_po" border='1' align="center"></table>
		 		   	<br><br>
		 		   	<table id="confirm_po" align="center"></table>
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