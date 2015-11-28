<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Storeroom_Name" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] sqlKeyList = {"name", "Bar_Code", "product_type"};
	String[] keyList = {"ID", "name", "Bar_Code", "product_type", "IN_QTY", "OUT_QTY", "repertory", "Total_Price"};
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
		int temp = mylogon.getUserRight()&32;
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
			String sql = "select * from product_info where Bar_Code >= 10000000 and Bar_Code < 20000000";
			//if(request.getParameter("curStorage") != null)
			//	sql += String.format(" and ");
			//else if(request.getParameter("curType") != null)
			//	sql += String.format(" and ");
			//else if(request.getParameter("curName") != null)
			//	sql += String.format(" and ");
			//else if(request.getParameter("curBarCode") != null)
			//	sql += String.format(" and ");
			hDBHandle.QueryDataBase(sql);
			int recordCount = hDBHandle.GetRecordCount();
			hDBHandle.CloseDatabase();
			String tempBP = request.getParameter("BeginPage");
			int BeginPage = tempBP!=null?Integer.parseInt(tempBP):1;
			String limitSql = String.format("%s order by id desc limit %d,%d", sql, PageRecordCount*(BeginPage-1), PageRecordCount);
			if (hDBHandle.QueryDataBase(limitSql))
			{
				recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
			hSNHandle.GetAllRecord();
			List<String> store_name = hSNHandle.getDBRecordList("name");
			
			store_name.remove("成品库");
			store_name.remove("原材料库");
			store_name.remove("半成品库");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>其他库存</title>
    
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
  	<script language="javascript" src="Page_JS/OtherSummaryJS.js"></script>
  <body>
    <jsp:include page="Menu/QueryMenu.jsp"/>
    <table align="center" border="1">
		<caption>其他库存筛选</caption>
	  	<tr>
	  		<td align="right">
		  		<label>库名:</label>
			  	<select name="store_name" id="store_name" style="width:180px">
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
		  		<label>类别:</label>
			  	<select name="product_type" id="product_type" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
			  	</select>
		  	</td>
			<td align="right">
				<label>名称:</label>
				<select name="product_name" id="product_name" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
			<td align="right">
	   			<label id=lable_barcode>Bar Code:</label>
				<input type="text" name="bar_code" id="bar_code" style='width:180px' onblur="InputBarcode()">
			</td>
	  	</tr>
   	</table>
   	<br>
   	<center>
    	<table border="1">
    		<tr>
<%
			for(int iCol = 1; iCol <= keyList.length; iCol++)
			{
%>
	   			<th><%= keyList[iCol-1]%></th>
<%
			}
%>
    		</tr>
 
<%
			double totalPrice = 0.0;
			if (!recordList.isEmpty())
			{
				for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
				{
					String storageName = "other_storage";
					String bar_code = recordList.get(1).get(iRow-1);
					if (Integer.parseInt(bar_code) >= 60000000 && Integer.parseInt(bar_code) < 70000000)
					{
						storageName = "product_storage";
					}
%>
  			<tr>
<%
					if (Integer.parseInt(bar_code) >= 50000000 && Integer.parseInt(bar_code) < 60000000)
					{
						storageName = "material_storage";
					}
					int sql_in_qty = hDBHandle.GetIN_QTYByBarCode(bar_code, storageName);
					int sql_out_qty = hDBHandle.GetOUT_QTYByBarCode(bar_code, storageName);
					double dblPro_Price = hDBHandle.GetProductRepertoryPrice(bar_code, storageName);
					totalPrice += dblPro_Price;
					String pro_Price = String.format("%.3f", dblPro_Price);
					for(int iCol = 1; iCol <= keyList.length; iCol++)
					{
						if(keyList[iCol-1] == "repertory")
						{
%>
    			<td><%= sql_in_qty - sql_out_qty%></td>
<%
				    	}
				    	else if (keyList[iCol-1] == "IN_QTY")
				    	{
%>
    			<td><%= sql_in_qty%></td>
<%
				    	}
				     	else if (keyList[iCol-1] == "OUT_QTY")
				    	{
%>
    			<td><%= sql_out_qty%></td>
<%
				    	}
				    	else if (keyList[iCol-1] == "ID")
				    	{
%>
	    			<td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
				    	}
				    	else if (keyList[iCol-1] == "Total_Price")
				    	{
%>
	    			<td><%=pro_Price %></td>
<%
				    	}
				    	else
				    	{
%>
    			<td><%= recordList.get(iCol-2).get(iRow-1)%></td>
<%
   						}
    				}
%>
			</tr>
<%
				}
			}
			String strTotalPrice = String.format("%.3f", totalPrice);
%>
			<tr>
<%
			for(int iCol = 1; iCol <= keyList.length-2; iCol++)
			{
%>
				<td><table></table></td>
<%
			}
%>				<td><label>总价值:</label></td>
				<td><%=strTotalPrice %></td>
			</tr>
    	</table>
    	<br><br>
   	    <jsp:include page="PageNum.jsp">
   	    	<jsp:param value="<%=recordCount %>" name="recordCount"/>
   	    	<jsp:param value="<%=PageRecordCount %>" name="PageRecordCount"/>
   	    	<jsp:param value="<%=BeginPage %>" name="BeginPage"/>
   	    	<jsp:param value="OtherSummary.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
  </body>
</html>
<%
		}
	}
%>