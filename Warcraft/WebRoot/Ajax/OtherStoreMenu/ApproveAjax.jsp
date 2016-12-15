<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.ApprovePage" %>
<%
	String rtnRst = "";
	String barcode = request.getParameter("Barcode").replace(" ", "");
	String recordID = request.getParameter("material_id").replace(" ", "");
	String usedCount = request.getParameter("OUT_QTY").replace(" ", "");
	ApprovePage hPageHandle = new ApprovePage();
	
	if(!hPageHandle.ApproveApplication(barcode, recordID, usedCount))
		rtnRst = "("+ barcode + "): 库存数量不足!";
	out.write(rtnRst);
%>
