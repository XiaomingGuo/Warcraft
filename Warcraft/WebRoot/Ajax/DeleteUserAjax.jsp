<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.UserManagement" %>
<%
    String rtnRst = "";
    String userId = (String)request.getParameter("id").replace(" ", "");
    UserManagement hPageHandle = new UserManagement();
    if (userId != null&&!userId.isEmpty())
        rtnRst = hPageHandle.DeleteUserInfoAndUserPermission(userId);
    out.write(rtnRst);
%>
