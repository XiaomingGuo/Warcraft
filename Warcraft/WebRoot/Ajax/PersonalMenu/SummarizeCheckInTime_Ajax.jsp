<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.SummarizeCheckInTime" %>
<%
    String rtnRst = "";
    String userID = request.getParameter("User_ID").replace(" ", "");
    String userName = request.getParameter("User_Name").replace(" ", "");
    String queryDate = request.getParameter("queryDate").replace(" ", "");
    String queryFlagString = request.getParameter("QueryContent").replace(" ", "");
    
    if(queryDate.length() > 0)
    {
        SummarizeCheckInTime hPageHandle = new SummarizeCheckInTime();
        rtnRst += hPageHandle.GenerateResponseString(queryFlagString, userID, userName, queryDate);
    }
    else
        rtnRst += "remove$error:名字能靠谱点不?";
    out.write(rtnRst);
%>
