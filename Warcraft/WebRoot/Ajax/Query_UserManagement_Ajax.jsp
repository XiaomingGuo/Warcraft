<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.UserManagement" %>
<%
    String rtnRst = "";
    int BeginPage = Integer.parseInt(request.getParameter("BeginPage").replace(" ", ""));
    UserManagement hPageHandle = new UserManagement();
    rtnRst += hPageHandle.GenerateReturnString(BeginPage);
    out.write(rtnRst);
%>
