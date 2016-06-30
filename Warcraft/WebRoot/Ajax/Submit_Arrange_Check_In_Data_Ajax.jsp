<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.ArrangeCheckInTime" %>
<%
    String rtnRst = "";
    ArrangeCheckInTime hPageHandle = new ArrangeCheckInTime();
    String userId = request.getParameter("userId").replace(" ", "");
    String workGroup = request.getParameter("WorkGroup").replace(" ", "");
    
    if(userId.length() > 0&&workGroup.length() > 0)
        rtnRst += hPageHandle.SubmitArrangeCheckInData(userId, workGroup);
    
    out.write(rtnRst);
%>
