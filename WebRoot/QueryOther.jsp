<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Other_Storage" %>
<%@ page import="com.DB.operation.Product_Info" %>
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
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		int PageRecordCount = 20;
		String tempBP = request.getParameter("BeginPage");
		List<List<String>> recordList = new ArrayList<List<String>>();
		String[] displayKeyList = {"ID", "产品名称", "八码", "批号", "总进货量", "已消耗", "库存", "单价", "总进货价", "供应商", "备注", "操作"};
		Other_Storage hOSHandle = new Other_Storage(new EarthquakeManagement());
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		
		hOSHandle.QueryAllRecord();
		int recordCount = hOSHandle.RecordDBCount();
		int BeginPage = tempBP!=null?Integer.parseInt(tempBP):1;
		
		hOSHandle.QueryRecordByFilterKeyListWithOrderAndLimit(null, null, Arrays.asList("id"), PageRecordCount*(BeginPage-1), PageRecordCount);
		if (hOSHandle.RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Total_Price", "vendor_name", "id", "isEnsure"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				recordList.add(hOSHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>其他库状态</title>
    
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
  	<script language="javascript" src="Page_JS/QueryOtherJS.js"></script>
  <body>
    <jsp:include page="Menu/MFGToolsMenu.jsp"/>
    <center>
    	<table border="1">
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
 
<%
		if (!recordList.isEmpty())
		{
			for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
			{
%>
  			<tr>
<%
				String Barcode = recordList.get(0).get(iRow-1);
				hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(Barcode));
				for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
				{
					if (displayKeyList[iCol-1] == "ID")
			    	{
%>
    			<td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "产品名称")
			    	{
%>
    			<td><%= hPIHandle.getDBRecordList("name").get(0) %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "八码")
			    	{
%>
    			<td><%= Barcode %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "批号")
			    	{
%>
    			<td><%= recordList.get(1).get(iRow-1) %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "总进货量")
			    	{
%>
    			<td><%= recordList.get(2).get(iRow-1)%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "已消耗")
			    	{
%>
    			<td><%= recordList.get(3).get(iRow-1) %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "库存")
			    	{
%>
    			<td><%= (Integer.parseInt(recordList.get(2).get(iRow-1)) - Integer.parseInt(recordList.get(3).get(iRow-1)))%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "单价")
			    	{
%>
    			<td><%= recordList.get(4).get(iRow-1)%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "总进货价")
			    	{
%>
    			<td><%= recordList.get(5).get(iRow-1)%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "供应商")
			    	{
%>
    			<td><%= recordList.get(6).get(iRow-1)%></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "备注")
			    	{
%>
    			<td><%= hPIHandle.getDBRecordList("description").get(0) %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "操作")
			    	{
			    		if(recordList.get(8).get(iRow-1).equals("0"))
			    		{
%>
    			<td>
    				<input type='button' value='确认' id='<%=recordList.get(7).get(iRow-1) %>Sure' name='<%=recordList.get(7).get(iRow-1) %>$<%=Barcode %>' onclick='SubmitQty(this)'>
    				&nbsp;
    				<input type='button' value='删除' id='<%=recordList.get(7).get(iRow-1) %>Rej' name='<%=recordList.get(7).get(iRow-1) %>$<%=Barcode %>' onclick='RejectQty(this)'>
    			</td>
<%
			    		}
			    		else
			    		{
			    			%>
    			<td><label>已确认</label></td>
			    			<%
			    		}
			    	}
		    	}
%>
			</tr>
<%
			}
		}
%>
    	</table>
    	<br><br>
   	    <jsp:include page="PageNum.jsp">
   	    	<jsp:param value="<%=recordCount %>" name="recordCount"/>
   	    	<jsp:param value="<%=PageRecordCount %>" name="PageRecordCount"/>
   	    	<jsp:param value="<%=BeginPage %>" name="BeginPage"/>
   	    	<jsp:param value="QueryOther.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
  </body>
</html>
<%
	}
%>