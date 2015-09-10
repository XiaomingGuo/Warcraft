<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	int sql_out_count = 0, sql_in_count = 0, used_count = 0;
%>
<%
	String rtnRst = "";
	String storeroom = (String)request.getParameter("storeroom");
	String pro_type = (String)request.getParameter("pro_type");
	String sql = "select * from product_type where name='" + pro_type +"'" ;
	if (!storeroom.isEmpty()&&!pro_type.isEmpty()&&hDBHandle.QueryDataBase(sql))
	{
		if(hDBHandle.GetRecordCount() > 0)
		{
			hDBHandle.CloseDatabase();
			rtnRst = "产品类型已经存在!";
		}
		else
		{
			hDBHandle.CloseDatabase();
			if(storeroom.indexOf("原材料库") == 0)
			{
				sql = "INSERT INTO product_type (name, storeroom) VALUES ('" + pro_type + "','成品库')";
				hDBHandle.execUpate(sql);
				sql = "INSERT INTO product_type (name, storeroom) VALUES ('" + pro_type + "原锭','"+ storeroom +"')";
			}
			else
			{
				sql = "INSERT INTO product_type (name, storeroom) VALUES ('" + pro_type + "','"+ storeroom +"')";
			}
			hDBHandle.execUpate(sql);
		}
	}
	else
	{
		rtnRst = "产品类型或库名为空或查询数据库出错!";
	}
	
	out.write(rtnRst);
%>
