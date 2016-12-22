<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%@ page import="com.DB.operation.Other_Record" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String rtnRst = "remove$";
	if(session.getAttribute("logonuser")==null)
	{
		rtnRst += "error:未登陆";
	}
	else
	{
		String proposerName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appProduct_type = (String)request.getParameter("product_type").replace(" ", "");
		String appProduct_name = (String)request.getParameter("product_name").replace(" ", "");
		String appBarcode = (String)request.getParameter("bar_code").replace(" ", "");
		String appUserName = (String)request.getParameter("user_name").replace(" ", "");
		String appProduct_QTY = (String)request.getParameter("QTY").replace(" ", "");
		String appApplyDate = (String)request.getParameter("Apply_Date").replace(" ", "");
		
		//product_type Database query
		if (appProduct_type.indexOf("请选择") < 0 && appProduct_name.indexOf("请选择") < 0 &&
				!appUserName.isEmpty() && !appProduct_QTY.isEmpty() && !appProduct_QTY.isEmpty())
		{
			DBTableParent hORHandle = new DatabaseStore("Other_Record");
			((Other_Record)hORHandle.getTableInstance()).AddARecord(hORHandle.GetUsedBarcode(appBarcode, "other_record"), proposerName, appProduct_QTY, appUserName, appApplyDate, "0");
		}
		else
		{
			rtnRst += "error:你输入的是什么啊,赶紧重新输入!";
		}
	}
	out.write(rtnRst);
%>
