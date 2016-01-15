<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Other_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"ID", "物料名称", "八码", "批号", "申请人", "数量", "使用人", "申请时间", "领取"};
	String[] sqlKeyList = {"id", "Bar_Code", "Batch_Lot", "proposer", "QTY", "user_name", "create_date", "isApprove"};
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
			Other_Record hORHandle = new Other_Record(new EarthquakeManagement());
			hORHandle.QueryRecordByFilterKeyList(Arrays.asList("isApprove"), Arrays.asList("0"));
			String sql = "select * from other_record where isApprove=0";
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
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>批准</title>
    
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
					for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
					{
						if(displayKeyList[iCol-1] == "领取")
						{
				    		if(!recordList.get(iCol-2).get(iRow-1).equalsIgnoreCase("1"))
				    		{
%>
    			<td>
	    				<center><input type="button" value="领取" name=<%=recordList.get(0).get(iRow-1)+"$"+recordList.get(1).get(iRow-1)+"$"+recordList.get(4).get(iRow-1)%> id=<%=recordList.get(0).get(iRow-1)%> onclick="change(this)"></center>
    			</td>
<%
							}
				    	}
				    	else if(displayKeyList[iCol-1] == "物料名称")
				    	{
%>
    			<td><%= hDBHandle.GetNameByBarcode(recordList.get(1).get(iRow-1)) %></td>
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
    			<td><%= recordList.get(iCol-2).get(iRow-1)%></td>
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
   	    	<jsp:param value="Approve.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
		<script type="text/javascript">
			function change(obj)
			{
				var tempList = obj.name.split("$");
				$("#"+tempList[0]).attr("disabled","disabled");
				$.post("Ajax/ApproveAjax.jsp", {"material_id":tempList[0], "Barcode":tempList[1], "OUT_QTY":tempList[2]}, function(data, textStatus)
				{
					if (!(textStatus == "success" && data.indexOf(tempList[1]) < 0))
					{
						alert(data);
					}
					location.reload();
				});
			}
		</script>
  </body>
</html>
<%
		}
	}
%>