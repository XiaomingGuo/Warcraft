<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.ArrangeCheckInTime" %>
<%

    String rtnRst = "";
    ArrangeCheckInTime hPageHandle = new ArrangeCheckInTime();
    String userId = request.getParameter("userId").replace(" ", "");
    String workGroup = request.getParameter("WorkGroup").replace(" ", "");
    String AddDate = request.getParameter("AddDate").replace(" ", "");
    
    if(userId.length() > 0&&workGroup.length() > 0&&AddDate.length() > 10)
        rtnRst += hPageHandle.SubmitAddCheckInData(userId, workGroup, AddDate);
    
    out.write(rtnRst);
%>
