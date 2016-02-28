<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.SubmitDiscardMaterial" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		SubmitDiscardMaterial hPageHandle = new SubmitDiscardMaterial();
		String userName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appPOName = request.getParameter("product_order").replace(" ", "");
		String appBarcode = request.getParameter("inputBarcode").replace(" ", "");
		String appOperator = request.getParameter("Operator").replace(" ", "");
		String appProduct_QTY = request.getParameter("QTY").replace(" ", "");
		String appreason = request.getParameter("discard_reason").replace(" ", "");
		int used_count = Integer.parseInt(appProduct_QTY);
		//product_type Database query
		if (used_count > 0&&appBarcode.length() == 8&&appPOName.indexOf("请选择") < 0&&!appreason.isEmpty()&&!appOperator.isEmpty())
		{
			if (hPageHandle.CheckPOStatus(appBarcode, appPOName) < used_count)
			{
				session.setAttribute("error", "("+ appBarcode + "): 你这报废的也太狠了吧, 加上报废数量都比客户生产单数量大了!");
				response.sendRedirect("../tishi.jsp");
				return;
			}
			if (!hPageHandle.ExcuteDiscardMaterial(appBarcode, appPOName, appOperator, appreason, used_count))
			{
				session.setAttribute("error", "("+ appBarcode + "): 库存数量不足,都不够你报废的!");
				response.sendRedirect("../tishi.jsp");
				return;
			}
		}
		else
		{
			session.setAttribute("error", "你输入的是什么啊,赶紧重新输入!");
			response.sendRedirect("../tishi.jsp");
			return;
		}
		response.sendRedirect("../Discard_Material.jsp");
	}
%>
