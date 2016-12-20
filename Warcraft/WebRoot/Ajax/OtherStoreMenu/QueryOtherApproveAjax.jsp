<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryOther" %>
<%
	String rtnRst = "";
	String curDate = request.getParameter("submitDate");
	
	QueryOther hPageHandle = new QueryOther();
	rtnRst += hPageHandle.GenerateResponseString(curDate);
	out.write(rtnRst);
%>
