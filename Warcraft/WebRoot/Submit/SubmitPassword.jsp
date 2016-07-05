<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.User_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String cur_PWD = request.getParameter("cur_password").replace(" ", "");
		String new_PWD = request.getParameter("new_password").replace(" ", "");
		String confirm_PWD = request.getParameter("confirm_password").replace(" ", "");
		String user_name = mylogon.getUsername();
		if (!cur_PWD.isEmpty() && !new_PWD.isEmpty() && !confirm_PWD.isEmpty() && new_PWD.equals(confirm_PWD))
		{
			User_Info hUIHandle = new User_Info(new EarthquakeManagement());
			hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(user_name));
			if(hUIHandle.RecordDBCount() == 1)
			{
				String sql_PWD = hUIHandle.getDBRecordList("password").get(0);
				if(sql_PWD.equals(cur_PWD))
				{
					hUIHandle.UpdateRecordByKeyList("password", new_PWD, Arrays.asList("name"), Arrays.asList(user_name));
					session.setAttribute("error", "恭喜你,密码修改成功,重新登录后生效!");
				}
				else
					session.setAttribute("error", "当前密码错误,请重新输入!");
			}
			else
				session.setAttribute("error", "用户不存在或用户名重复!");
		}
		else
		{
			session.setAttribute("error", "密码为空或新密码两次输入密码不一致!");
		}
		response.sendRedirect("../tishi.jsp");
	}
%>
