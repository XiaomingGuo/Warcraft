<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	int sql_out_count = 0, sql_in_count = 0, used_count = 0;
	String[] keyArray = {"IN_QTY", "OUT_QTY"};
%>
<%
	String rtnRst = "";
	String product_type = (String)request.getParameter("product_type");
	String sql = "select * from product_type where name='" + product_type +"'" ;
	if (!product_type.isEmpty()&&hDBHandle.QueryDataBase(sql))
	{	
		if(hDBHandle.GetRecordCount() > 0)
		{
			rtnRst = "产品类型已经存在!";
		}
		else
		{
			sql = "INSERT INTO product_type (name) VALUES ('" + product_type + "')";
			hDBHandle.execUpate(sql);
		}
	}
	else
	{
		rtnRst = "产品类型为空或查询数据库出错!";
	}
	
	out.write(rtnRst);
%>
