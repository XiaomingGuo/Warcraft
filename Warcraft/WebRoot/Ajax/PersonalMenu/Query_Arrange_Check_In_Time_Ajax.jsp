<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.UploadCheckInTime" %>
<%
	String rtnRst = "";
    UploadCheckInTime hPageHandle = new UploadCheckInTime();
    rtnRst += hPageHandle.GenerateReturnString();
    out.write(rtnRst);
%>
