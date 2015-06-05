<%@page import="com.mysql.fabric.xmlrpc.base.Data"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String cur_PWD = request.getParameter("cur_password");
		String new_PWD = request.getParameter("new_password");
		String confirm_PWD = request.getParameter("confirm_password");
		String user_name = mylogon.getUsername();
		String sql_PWD = "";
		if (!cur_PWD.isEmpty() && !new_PWD.isEmpty() && !confirm_PWD.isEmpty() && new_PWD.equals(confirm_PWD))
		{
			String sql = "select * from user_info where name='" + user_name + "'";
			hDBHandle.QueryDataBase(sql);
			if (hDBHandle.GetRecordCount() == 1)
			{
				sql_PWD = hDBHandle.GetSingleString("password");
				if (sql_PWD.equals(cur_PWD))
				{
					//product_type Database query
					sql = "UPDATE user_info SET password='" + new_PWD + "' WHERE name='" + user_name +"'";
					hDBHandle.execUpate(sql);
				}
				else
				{
					session.setAttribute("error", "当前密码错误,请重新输入!");
					response.sendRedirect("tishi.jsp");
				}
			}
			else
			{
				session.setAttribute("error", "用户不存在或用户名重复!");
				response.sendRedirect("tishi.jsp");
			}
			session.setAttribute("error", "恭喜你,密码修改成功,重新登录后生效!");
			response.sendRedirect("tishi.jsp");
		}
		else
		{
			session.setAttribute("error", "密码为空或新密码两次输入密码不一致!");
			response.sendRedirect("tishi.jsp");
		}
	}
%>
