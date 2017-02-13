<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.UploadCheckInTime" %>
<%
	String rtnRst = "";
    UploadCheckInTime hPageHandle = new UploadCheckInTime();
    String userId = request.getParameter("userId").replace(" ", "");
    String AddDate = request.getParameter("AddDate").replace(" ", "");
    String HolidayType = request.getParameter("HolidayType").replace(" ", "");
    String HolidayTime = request.getParameter("HolidayTime").replace(" ", "");
    
    if(userId.length() > 0&&AddDate.length() > 0&&HolidayType.length() > 0)
        rtnRst += hPageHandle.SubmitAddHolidaysDate(userId, AddDate, HolidayType, HolidayTime);
    out.write(rtnRst);
%>
