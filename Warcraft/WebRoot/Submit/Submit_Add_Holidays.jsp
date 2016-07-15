<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.ArrangeCheckInTime" %>
<%

    String rtnRst = "";
    ArrangeCheckInTime hPageHandle = new ArrangeCheckInTime();
    String userId = request.getParameter("userId").replace(" ", "");
    String AddDate = request.getParameter("AddDate").replace(" ", "");
    
    if(userId.length() > 0&&AddDate.length() > 0)
        rtnRst += hPageHandle.SubmitAddHolidaysDate(userId, AddDate);
    out.write(rtnRst);
%>
