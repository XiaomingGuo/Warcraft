<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"name", "Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
	String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
	List<List<String>> recordList = null;
%>
<%
	String message="";
	Calendar mData = Calendar.getInstance();
	String currentDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&64;
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
			String sql = "select * from material_record where proposer='" + mylogon.getUsername() + "'";
			if (hDBHandle.QueryDataBase(sql))
			{
				recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
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
    <jsp:include page="MainPage.jsp"/>
    <center>
    	<lable>查询起止时间:</lable>
    	<table border="1">
    		<tr>
    			<td>
	   				<lable>开始日期:</lable>
	   				<input type="text" value=<%=currentDate%> name="DateOfBegin" id="DateOfBegin">
    			</td>
    			<td>
	   				<lable>截止日期:</lable>
	   				<input type="text" value=<%=currentDate%> name="DateOfEnd" id="DateOfBegin">
    			</td>
    			<td>
	    			<input type="button" value=查询  onclick="change(this)">
    			</td>
    		</tr>
    	</table>
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
for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
{
%>
  			<tr>
<%
	for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
	{
		if(displayKeyList[iCol-1] == "isApprove")
		{
%>
    			<td><%= (recordList.get(iCol-2).get(iRow-1).equalsIgnoreCase("1")) ? "已领取" :"未领取" %></td>
<%
    	}
    	else if (displayKeyList[iCol-1] == "name")
    	{
%>
    			<td><%= hDBHandle.GetNameByBarcode(recordList.get(0).get(iRow-1)) %></td>
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
%>
    	</table>
    </center>
	<script type="text/javascript">
		function change(obj)
		{
   			//String[] sqlKeyList = {"id", "Bar_Code", "Batch_Lot", "proposer", "QTY", "create_date", "isApprove"};
			//var tempList = obj.name.split("$");
			alert($("#DateOfBegin").Text());
			alert($("#DateOfEnd").Text());
			$.post("ApproveAjax.jsp", {"material_id":tempList[0], "Barcode":tempList[1], "OUT_QTY":tempList[2]}, function(data, textStatus)
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