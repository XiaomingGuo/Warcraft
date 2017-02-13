<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.UploadCheckInTime" %>
<%
	String rtnRst = "remove$";
    UploadCheckInTime hPageHandle = new UploadCheckInTime();
    String userID = request.getParameter("UserID").replace(" ", "");
    
    if(userID.length() > 0)
        rtnRst += hPageHandle.GetUserNameByKeyWord("check_in_id", userID, "name") + "$";
    
    out.write(rtnRst);
%>
