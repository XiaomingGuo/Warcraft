<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.ArrangeCheckInTime" %>
<%
    String rtnRst = "remove$";
    ArrangeCheckInTime hPageHandle = new ArrangeCheckInTime();
    String userName = request.getParameter("User_Name").replace(" ", "");
    
    if(userName.length() > 0)
        rtnRst += hPageHandle.GetUserNameByKeyWord("name", userName, "check_in_id") + "$";
    
    out.write(rtnRst);
%>
