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
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		int PageRecordCount = 20;
		List<List<String>> recordList = new ArrayList<List<String>>();
		String tempBP = request.getParameter("BeginPage");
		DBTableParent hORHandle = new DatabaseStore("Other_Record");
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		String[] displayKeyList = {"ID", "name", "Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
		
		hORHandle.QueryRecordByFilterKeyList(Arrays.asList("proposer"), Arrays.asList(mylogon.getUsername()));
		int recordCount = hORHandle.getTableInstance().RecordDBCount();
		int BeginPage = tempBP!=null?Integer.parseInt(tempBP):1;
		hORHandle.QueryRecordByFilterKeyListWithOrderAndLimit(Arrays.asList("proposer"), Arrays.asList(mylogon.getUsername()), Arrays.asList("id"), PageRecordCount*(BeginPage-1), PageRecordCount);
		if (hORHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
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
    
    <title>个人报表</title>
    
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
    <jsp:include page="Menu/MainMenu.jsp"/>
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
					if(displayKeyList[iCol-1] == "isApprove")
					{
%>
    			<td><%= (recordList.get(iCol-3).get(iRow-1).equalsIgnoreCase("1")) ? "已领取" :"未领取" %></td>
<%
			    	}
			    	else if (displayKeyList[iCol-1] == "name")
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
   	    	<jsp:param value="PersonReport.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
  </body>
</html>
<%
	}
%>