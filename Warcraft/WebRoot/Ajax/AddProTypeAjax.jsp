<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Process_Detail_Info" %>
<%
	String rtnRst = "";
	String storeroom = (String)request.getParameter("storeroom").replace(" ", "");
	String pro_type = (String)request.getParameter("pro_type").replace(" ", "");
	if (!storeroom.isEmpty()&&!pro_type.isEmpty())
	{
		Query_Process_Detail_Info hPageHandle = new Query_Process_Detail_Info();
		if(hPageHandle.IsProductTypeExist(pro_type))
			rtnRst = "产品类型已经存在!";
		else
			hPageHandle.AddProductTypeToDatabase(pro_type, storeroom);
	}
	else
		rtnRst = "产品类型或库名为空或查询数据库出错!";
	out.write(rtnRst);
%>
