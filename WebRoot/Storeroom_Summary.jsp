<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx"  uri="/struts-dojo-tags"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"ID", "产品名称", "八码", "批号", "总进货量", "已消耗", "库存", "单价", "总进货价", "供应商", "备注"};
	String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Total_Price", "vendor_name"};
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
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String sql = "select * from material_storage UNION select * from other_storage";
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
    
    <title>物料录入查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<s:head/>
    <sx:head parseContent="true"/>

  </head>
  
  <body>
    <jsp:include page="Menu/QueryMenu.jsp"/>
    <center>
		<table align="center">
			<tr>
		  		<td align="right">
			  		<label>选择库名:</label>
				  	<select name="store_name" id="store_name" style="width:180px">
					  	<option value = "--请选择--">--请选择--</option>
					  	<option value = "product">成品库</option>
					  	<option value = "material">原材料库</option>
					  	<option value = "other">其他库</option>
				  	</select>
			  	</td>
		  	</tr>
		</table>
    	<label>查询起止时间:</label>
    	<table border="1">
    		<tr>
    			<th><label>开始日期:</label></th>
    			<th><label>截止日期:</label></th>
    		</tr>
    		<tr>
				<s:form action="">
					<td><sx:datetimepicker id="DateOfBegin" name="DateOfBegin" displayFormat="yyyy-MM-dd" /></td>
	   				<td><sx:datetimepicker id="DateOfEnd" name="DateOfEnd" displayFormat="yyyy-MM-dd" /></td>
				</s:form>
     		</tr>
    	</table>
    </center>
  </body>
</html>
<%
	}
%>