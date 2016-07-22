<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Process_Detail_Info" %>
<%
	String pro_type=(String)request.getParameter("FilterKey1").replace(" ", "");
	String rtnRst = "remove$";
	
	Query_Process_Detail_Info hPageHandle = new Query_Process_Detail_Info();
	List<String> proInfo = hPageHandle.GetProductInfoByProType(pro_type);
	
	if (proInfo != null)
	{
		for(int i = 0; i < proInfo.size(); i++)
			rtnRst += proInfo.get(i) + '$';
	}
	out.write(rtnRst);
%>
