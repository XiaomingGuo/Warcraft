<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.ArrangeCheckInTime" %>
<%
    String rtnRst = "error:添加假期信息失败！";
    ArrangeCheckInTime hPageHandle = new ArrangeCheckInTime();
    String userId = request.getParameter("userId").replace(" ", "");
    String HolidayType = request.getParameter("HolidayType").replace(" ", "");
    String beginDate = request.getParameter("BeginDate").replace(" ", "");
    String endDate = request.getParameter("EndDate").replace(" ", "");
    
    if(userId.length() > 0&&HolidayType.length() > 0&&beginDate.length() > 0&&endDate.length() > 0)
        rtnRst = hPageHandle.SubmitAddBatchHolidaysDate(userId, HolidayType, beginDate, endDate);
    out.write(rtnRst);
%>
