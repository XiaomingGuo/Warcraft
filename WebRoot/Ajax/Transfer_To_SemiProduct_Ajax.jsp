<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Mb_Material_Po" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Transfer_To_SemiProduct_Ajax" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String rtnRst = "remove$";
	if(session.getAttribute("logonuser")==null)
	{
		rtnRst += "error:未登陆";
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String barcode = request.getParameter("Bar_Code").replace(" ", "");
		String POName = request.getParameter("PO_Name").replace(" ", "");
		String PutQty = request.getParameter("TransQty").replace(" ", "");
		Transfer_To_SemiProduct_Ajax hPageHandle = new Transfer_To_SemiProduct_Ajax();
		
		if (barcode != null && !barcode.isEmpty() && POName != null && !POName.isEmpty() &&  PutQty != null && !PutQty.isEmpty())
		{
			rtnRst += hPageHandle.ExecuteTransferMaterialToSemiProduct(barcode, POName, PutQty) + "$";
		}
		else
		{
			rtnRst += "error:你输入的是什么啊,赶紧重新输入!";
		}
		out.write(rtnRst);
	}
%>
