<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.SummarizeCheckInTime" %>
<%
    String rtnRst = "";
    String username = request.getParameter("user_name").replace(" ", "");
    String queryDate = request.getParameter("queryDate").replace(" ", "");
    
    if(username.length() > 0&&queryDate.length() > 0)
    {
        SummarizeCheckInTime hPageHandle = new SummarizeCheckInTime();
        rtnRst += hPageHandle.GenerateReturnString(username, queryDate);
    }
    else
    {
        rtnRst += "remove$error:名字能靠谱点bu?";
    }
    out.write(rtnRst);
%>
