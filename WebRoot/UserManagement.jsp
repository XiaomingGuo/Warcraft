<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] keyList = {"ID", "name", "create_date", "department", "password", "permission", "submit"};
	String[] sqlkeyList = {"id", "name", "create_date", "department", "password", "permission"};
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
		int temp = mylogon.getUserRight()&2;
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
			String sql = "select * from user_info";
			hDBHandle.QueryDataBase(sql);
			int recordCount = hDBHandle.GetRecordCount();
			hDBHandle.CloseDatabase();
			int BeginPage = Integer.parseInt(request.getParameter("BeginPage"));
			String limitSql = String.format("%s order by id desc limit %d,%d", sql, PageRecordCount*(BeginPage-1), PageRecordCount);
			if (hDBHandle.QueryDataBase(limitSql))
			{
				recordList = hDBHandle.GetAllDBColumnsByList(sqlkeyList);
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
    
    <title>用户管理</title>
    
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
    <jsp:include page="Menu/MainMenu.jsp"/>
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
				int iRow = 0;
				for(iRow = 1; iRow <= recordList.get(0).size(); iRow++)
				{
					if (recordList.get(1).get(iRow-1).equalsIgnoreCase("root"))
						continue;
%>
  			<tr>
<%
					for(int iCol = 1; iCol <= keyList.length; iCol++)
					{
						String tempValue = recordList.get(0).get(iRow-1);
						if(keyList[iCol-1] == "permission")
						{
%>
    			<td>
    				<center>
	    				<input type="checkbox" name="permission" value="2048">查询
	    				<input type="checkbox" name="permission" value="1024">批准
	    				<input type="checkbox" name="permission" value="512">生产单生成
	    				<input type="checkbox" name="permission" value="256">生产单审核
	    				<input type="checkbox" name="permission" value="128">成品入库
	    				<input type="checkbox" name="permission" value="64">材料报废
	    				<br>
	    				<input type="checkbox" name="permission" value="32">成品出货
	    				<input type="checkbox" name="permission" value="16">物料录入
	    				<input type="checkbox" name="permission" value="8">月报表
	    				<input type="checkbox" name="permission" value="4">库存
	    				<input type="checkbox" name="permission" value="2">用户管理
    				</center>
    			</td>
<%
				    	}
						else if(keyList[iCol-1] == "submit")
						{
%>
    			<td>
					<center><input type="button" value="修改" name=<%=tempValue %> id=<%=tempValue %> onclick="change(this)"></center>
				</td>
<%
						}
						else if(keyList[iCol-1] == "ID")
						{
%>
   				<td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
						}
				    	else
				    	{
%>
   				<td><%= recordList.get(iCol-1).get(iRow-1)%></td>
<%
						}
					}
%>
			</tr>
<%
				}
%>
  			<tr>
<%
				for(int iCol = 1; iCol <= keyList.length; iCol++)
				{
					if(keyList[iCol-1] == "permission")
					{
%>
    			<td>
    				<center>
	    				<input type="checkbox" name="AddPermission" value="2048">查询
	    				<input type="checkbox" name="AddPermission" value="1024">批准
	    				<input type="checkbox" name="AddPermission" value="512">生产单生成
	    				<input type="checkbox" name="AddPermission" value="256">生产单审核
	    				<input type="checkbox" name="AddPermission" value="128">成品入库
	    				<input type="checkbox" name="AddPermission" value="64">材料报废
	    				<br>
	    				<input type="checkbox" name="AddPermission" value="32">成品出货
	    				<input type="checkbox" name="AddPermission" value="16">物料录入
	    				<input type="checkbox" name="AddPermission" value="8">月报表
	    				<input type="checkbox" name="AddPermission" value="4">库存
	    				<input type="checkbox" name="AddPermission" value="2">用户管理
    				</center>
    			</td>
<%
			    	}
					else if(keyList[iCol-1] == "submit")
					{
%>
    			<td>
					<center><input type="button" value="添加" name="<%=iRow %>" id="<%=iRow %>" onclick="Add(this)"></center>
				</td>
<%
					}
					else if(keyList[iCol-1] == "ID")
					{
%>
   				<td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
					}
			    	else if(keyList[iCol-1] == "create_date")
			    	{
%>
				<td></td>
<%
					}
			    	else
			    	{
%>
   				<td>
					<center><input type="text" name="<%=keyList[iCol-1] %>" id="<%=keyList[iCol-1] %>"></center>
				</td>
<%
					}
				}
%>
			</tr>
<%
			}
%>
    	</table>
    	<br><br>
   	    <jsp:include page="PageNum.jsp">
   	    	<jsp:param value="<%=recordCount %>" name="recordCount"/>
   	    	<jsp:param value="<%=PageRecordCount %>" name="PageRecordCount"/>
   	    	<jsp:param value="<%=BeginPage %>" name="BeginPage"/>
   	    	<jsp:param value="UserManagement.jsp" name="PageName"/>
   	    </jsp:include>
    </center>
		<script type="text/javascript">
			function change(obj)
			{
				var iPermission = 0;
				$("input:checkbox[name=permission]:checked").each(function(i){
					iPermission += Number($(this).val());
				});
				$.post("Ajax/UpdateUserAjax.jsp", {"Index":obj.name, "Permission":iPermission}, function(data, textStatus){
					if (textStatus == "success") {
						location.reload();
					}
				});
			}
			
			function Add(obj)
			{
				//	String[] sqlkeyList = {"id", "name", "create_date", "department", "password", "permission"};
				var iPermission = 0;
				$("input:checkbox[name=AddPermission]:checked").each(function(i){
					iPermission += Number($(this).val());
				});
				$.post("Ajax/AddUserAjax.jsp", {"name":$('#name').val(), "password":$('#password').val(), "department":$('#department').val(), "Permission":iPermission}, function(data, textStatus){
					if (textStatus == "success") {
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