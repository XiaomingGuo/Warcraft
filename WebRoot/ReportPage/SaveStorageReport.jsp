<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%@ page import="com.jspsmart.upload.*"  %>
<%@ page import="com.office.util.ExcelOperationUtil"  %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	ExcelOperationUtil excelUtil = new ExcelOperationUtil();
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&64;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("../tishi.jsp");
		}
		else
		{
			message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			request.setCharacterEncoding("UTF-8");
			for(int iRow = 0; ;iRow++)
			{
				String breakFlag = request.getParameter(Integer.toString(iRow*10));
				if (breakFlag == null)
				{
					break;
				}
				for(int iCol = 0; ;iCol++)
				{
					String tempValue = request.getParameter(Integer.toString(iRow*10+iCol));
					if (tempValue == null)
					{
						break;
					}
					excelUtil.WriteDataToExcel("d:\\tempExcel.xls", tempValue);
				}
			}
		}
		response.sendRedirect("../Storeroom_Summary.jsp");
	}
%>