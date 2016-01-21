<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Other_Record" %>
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
		Other_Record hORHandle = new Other_Record(new EarthquakeManagement());
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		String[] displayKeyList = {"ID", "物料名称", "八码", "批号", "申请人", "数量", "使用人", "申请时间", "是否领取"};
		
		hORHandle.QueryAllRecord();
		int recordCount = hORHandle.RecordDBCount();
		int BeginPage = tempBP!=null?Integer.parseInt(tempBP):1;
		hORHandle.QueryRecordByFilterKeyListWithOrderAndLimit(null, null, Arrays.asList("id"), PageRecordCount*(BeginPage-1), PageRecordCount);
		if (hORHandle.RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "proposer", "QTY", "user_name", "create_date", "isApprove"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				recordList.add(hORHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
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
					String Barcode = recordList.get(0).get(iRow-1);
					hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(Barcode));
%>
  			<tr>
<%
					for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
					{
						if(displayKeyList[iCol-1] == "是否领取")
						{
%>
    			<td><%= (recordList.get(iCol-3).get(iRow-1).equalsIgnoreCase("1")) ? "已领取" :"未领取" %></td>
<%
				    	}
				    	else if (displayKeyList[iCol-1] == "物料名称")
				    	{
%>
    			<td><%= hPIHandle.getDBRecordList("name").get(0) %></td>
<%
				    	}
				    	else if (displayKeyList[iCol-1] == "ID")
				    	{
%>
    			<td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
				    	}
				    	else
				    	{
%>
    			<td><%= recordList.get(iCol-3).get(iRow-1)%></td>
<%
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
   	    	<jsp:param value="Query.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
  </body>
</html>
<%
	}
%>