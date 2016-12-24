<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.MonthReport" %>
<%
	String rtnRst = "remove$";
	String storage_name = request.getParameter("storage_name");
	String product_type = request.getParameter("product_type");
	String product_name = request.getParameter("product_name");
	String user_name = request.getParameter("user_name");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	
	MonthReport hPageHandle = new MonthReport();
	rtnRst += hPageHandle.GenerateResponseString(storage_name, product_type, product_name, user_name, beginDate.replace("-", ""), endDate.replace("-", ""));
	out.write(rtnRst);
%>
