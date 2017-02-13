<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.UploadCheckInTime" %>
<%
	String rtnRst = "remove$";
    UploadCheckInTime hPageHandle = new UploadCheckInTime();
    String userName = request.getParameter("User_Name").replace(" ", "");
    
    if(userName.length() > 0)
        rtnRst += hPageHandle.GetUserNameByKeyWord("name", userName, "check_in_id") + "$";
    
    out.write(rtnRst);
%>
