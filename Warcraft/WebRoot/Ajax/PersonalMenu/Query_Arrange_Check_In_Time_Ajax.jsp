<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.ArrangeCheckInTime" %>
<%
    String rtnRst = "";
    ArrangeCheckInTime hPageHandle = new ArrangeCheckInTime();
    rtnRst += hPageHandle.GenerateReturnString();
    out.write(rtnRst);
%>
