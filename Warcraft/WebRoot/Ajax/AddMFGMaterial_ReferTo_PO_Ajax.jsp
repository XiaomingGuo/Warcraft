<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.AddMFGMaterial_ReferTo_PO_Ajax" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String rtnRst = "remove$";
	AddMFGMaterial_ReferTo_PO_Ajax hPageHandle = new AddMFGMaterial_ReferTo_PO_Ajax();
	if(session.getAttribute("logonuser")==null)
	{
		rtnRst += "error:未登陆";
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String mbMaterialId = request.getParameter("mb_material_po_id").replace(" ", "");
		String storeQty = request.getParameter("PutInQTY").replace(" ", "");
		String addDate = request.getParameter("AddDate").replace(" -", "");
		
		if (mbMaterialId != null && !mbMaterialId.isEmpty() && storeQty != null && !storeQty.isEmpty())
		{
			rtnRst += hPageHandle.AddMaterialToSuitedStorage(mbMaterialId, storeQty, addDate) + "$";
		}
		else
		{
			rtnRst += "error:你输入的是什么啊,赶紧重新输入!";
		}
		out.write(rtnRst);
	}
%>
