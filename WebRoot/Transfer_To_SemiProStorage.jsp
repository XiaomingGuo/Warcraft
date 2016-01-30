<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Material_Storage" %>
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
			Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
			String curPOName = request.getParameter("POName");
			if(null == curPOName)
				curPOName = "";
			hMSHandle.QueryRecordByKeyListGroupByList(Arrays.asList("po_name"));
			List<String> POList = hMSHandle.getDBRecordList("po_name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>原材料转半成品</title>
    
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
  	<script language="javascript" src="Page_JS/TempPoSelectPublicFunJS.js"></script>
  	<script language="javascript" src="Page_JS/Transfer_To_SemiProStorageJS.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
  <body onload="changePOName()">
   	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
    <jsp:include page="Menu/ManufactureMenu.jsp"/>
    <br><br>
    <table align="center">
    	<tr>
    		<td>
		  		<table align="center">
		  			<tr><td align="center"><h2>原材料转半成品</h2></td></tr>
		  			<tr>
				  		<td align="right">
				  			<h1>
						  		<label>PO号:</label>
							  	<select name="POName" id="POName" style="width:200px">
								  	<option value = "--请选择--">--请选择--</option>
<%
									for(int i = 0; i < POList.size(); i++)
									{
										if(curPOName.contains(POList.get(i)))
										{
%>
								  	<option value = <%=POList.get(i) %> selected><%=POList.get(i)%></option>
<%
										}
										else
										{
%>
								  	<option value = <%=POList.get(i) %>><%=POList.get(i)%></option>
<%											
										}
									}
%>
							  	</select>
					  		</h1>
					  	</td>
			  		</tr>
		  		</table>
	 		   	<table id="display_po" border='1' align="center"></table>
			</td>
		</tr>
   	</table>
  </body>
</html>
<%
		}
	}
%>