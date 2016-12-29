<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Process_Detail_Info" %>
<%
	String rtnRst = "";
	String storeroom = request.getParameter("storeroom").replace(" ", "");
	String pro_type = request.getParameter("pro_type").replace(" ", "");
	if (!storeroom.isEmpty()&&!pro_type.isEmpty())
	{
		Query_Process_Detail_Info hPageHandle = new Query_Process_Detail_Info();
		hPageHandle.AddProductTypeToDatabase(pro_type, storeroom);
	}
	else
		rtnRst = "error:产品类型或库名为空或查询数据库出错!";
	out.write(rtnRst);
%>
