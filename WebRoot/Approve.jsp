<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] keyList = {"id", "proposer", "material_name", "QTY", "create_date", "isApprove"};
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
		int temp = mylogon.getUserRight()&512;
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
			String sql = "select * from material_record where isApprove=0";
			if (hDBHandle.QueryDataBase(sql))
			{
				recordList = hDBHandle.GetAllDBColumnsByList(keyList);
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
  	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
  <body>
    <jsp:include page="MainPage.jsp"/>
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
if (!recordList.isEmpty())
{
	for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
	{
	%>
	  			<tr>
	  	<%
		for(int iCol = 1; iCol <= recordList.size(); iCol++)
		{
			if(keyList[iCol-1] != "isApprove")
			{
		%>
	    			<td><%= recordList.get(iCol-1).get(iRow-1)%></td>
	    <%
	    	}
	    	else
	    	{
	    		if(!recordList.get(iCol-1).get(iRow-1).equalsIgnoreCase("1"))
	    		{
		%>
	    			<td>
	    				<center><input type="button" value="领取" name=<%=recordList.get(0).get(iRow-1)%> id=<%=recordList.get(0).get(iRow-1)%> Pro_Name=<%=recordList.get(2).get(iRow-1)%> Pro_Qty=<%=recordList.get(3).get(iRow-1)%>  onclick="change(this)"></center>
	    			</td>
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
    </center>
		<script type="text/javascript">
			function change(obj)
			{
				$.post("ApproveAjax.jsp", {"material_id":obj.name, "OUT_QTY":obj.Pro_Qty, "Pro_Name":obj.Pro_Name}, function(data, textStatus){
					if (textStatus = "success") {
						location.reload();
					}
				});
			}
		</script>
  </body>
</html>
<%
		}
	}
%>