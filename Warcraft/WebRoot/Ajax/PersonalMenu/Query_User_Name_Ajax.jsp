<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.UploadCheckInTime" %>
<%
	String rtnRst = "";
    UploadCheckInTime hPageHandle = new UploadCheckInTime();
    String userName = request.getParameter("UserName").replace(" ", "");
    
    if(userName.length() > 0)
        rtnRst += hPageHandle.GetUserNameString(userName);
    
    out.write(rtnRst);
%>
