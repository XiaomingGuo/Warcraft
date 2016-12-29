<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%@ page import="com.DB.operation.Storeroom_Name" %>
<%
	String rtnRst = "";
	String storeroom = (String)request.getParameter("storeroom").replace(" ", "");

	if (storeroom != null&&!storeroom.isEmpty())
	{	
		DBTableParent hSNHandle = new DatabaseStore("Storeroom_Name");
		((Storeroom_Name)hSNHandle.getTableInstance()).AddARecord(storeroom);
	}
	else
		rtnRst = "error:库名为空或查询数据库出错!";
	out.write(rtnRst);
%>
