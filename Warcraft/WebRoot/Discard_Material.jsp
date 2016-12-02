<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
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
			
			//storeroom name Database query
			DBTableParent hSPSHandle = new DatabaseStore("Semi_Product_Storage");
			hSPSHandle.QueryRecordGroupByList(Arrays.asList("po_name"));
			List<String> product_order = hSPSHandle.getDBRecordList("po_name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>材料报废</title>
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
  	<script language="javascript" src="Page_JS/DiscardMaterialJS.js"></script>
  <body>
    <jsp:include page="Menu/MainMenu.jsp"/>
  	<br><br><br>
   	<form name="AppContent" action = "Submit/SubmitDiscardMaterial.jsp" method = "post">
  	<table align="center" border="1">
	  	<tr>
	  		<th align="center">材料报废</th>
	  	</tr>
		<tr>
	  		<td align="right">
		  		<label>生产单:</label>
			  	<select name="product_order" id="product_order" style="width:260px">
				  	<option value = "--请选择--">--请选择--</option>
<%
			if (product_order != null)
			{
				for(int i = 0; i < product_order.size(); i++)
				{
%>
				  	<option value = <%=product_order.get(i)%>><%=product_order.get(i)%></option>
<%
				}
			}
%>
			  	</select>
		  	</td>
	  	</tr>
		<tr>
			<td align="right">
				<label>类型:</label>
				<select name="product_type" id="product_type" style="width:260px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>名称:</label>
				<select name="product_name" id="product_name" style="width:260px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>Bar Code:</label>
				<select name="bar_code" id="bar_code" style="width:260px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
   			<td align="right">
	   			<label id=label_barcode>Bar Code:</label>
				<input type="text" name="inputBarcode" id="inputBarcode" style='width:260px' onblur="InputBarcode()">
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>报废人:</label>
				<input type="text" name="Operator" id="Operator" style='width:260px'>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>报废数量:</label>
				<input type="text" name="QTY" id="QTY" style='width:260px'>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>报废原因:</label>
			  	<select name="discard_reason" id="discard_reason" style="width:260px">
				  	<option value = "--请选择--">--请选择--</option>
				  	<option value = "报废">报废</option>
				  	<option value = "料废">料废</option>
			  	</select>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input name="commit" type=submit value="提交" style="width:100">
			</td>
		</tr>
	</table>
  	</form>
  </body>
</html>
<%
		}
	}
%>