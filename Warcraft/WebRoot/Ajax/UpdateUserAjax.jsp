<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.UserManagement" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
    String rtnRst = "";
    String userId = (String)request.getParameter("UserId").replace(" ", "");
    UserManagement hPageHandle = new UserManagement();
    rtnRst = hPageHandle.GetUpdateReturnString(userId);
    out.write(rtnRst);
%>
