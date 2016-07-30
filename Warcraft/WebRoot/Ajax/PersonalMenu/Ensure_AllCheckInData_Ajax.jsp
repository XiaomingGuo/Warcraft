<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.ArrangeCheckInTime" %>
<%
    String rtnRst = "";
    ArrangeCheckInTime hPageHandle = new ArrangeCheckInTime();
    String userName = request.getParameter("UserName").replace(" ", "");
    String submitDate = request.getParameter("SubmitDate").replace(" ", "");
    
    if(userName.length() > 0&&submitDate.length() > 0)
        rtnRst += hPageHandle.EnsureAllArrangeCheckInData(userName, submitDate);
    
    out.write(rtnRst);
%>
