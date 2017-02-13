<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.UploadCheckInTime" %>
<%
	String rtnRst = "";
    UploadCheckInTime hPageHandle = new UploadCheckInTime();
    String userId = request.getParameter("userId").replace(" ", "");
    String workGroup = request.getParameter("WorkGroup").replace(" ", "");
    String beginDate = request.getParameter("BeginDate").replace(" ", "");
    String endDate = request.getParameter("EndDate").replace(" ", "");
    
    if(userId.length() > 0&&workGroup.length() > 0)
        rtnRst += hPageHandle.SubmitUploadCheckInData(userId, workGroup, beginDate, endDate);
    
    out.write(rtnRst);
%>
