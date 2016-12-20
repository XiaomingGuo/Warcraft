<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryOtherStorageReport" %>
<%
	String rtnRst = "remove$";
	String storage_name = request.getParameter("storage_name");
	String product_type = request.getParameter("product_type");
	String product_name = request.getParameter("product_name");
	String supplier_name = request.getParameter("supplier_name");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	String submitDate = request.getParameter("submitDate");
	
	QueryOtherStorageReport hPageHandle = new QueryOtherStorageReport();
	rtnRst += hPageHandle.GenerateResponseString(storage_name, product_type, product_name, supplier_name, beginDate, endDate, submitDate);
	out.write(rtnRst);
%>
