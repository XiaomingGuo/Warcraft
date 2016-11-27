<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.SummaryHoliday" %>
<%
	String rtnRst = "";
    String id = (String)request.getParameter("ID").replace(" ", "");
    String holidayType = (String)request.getParameter("holidayType").replace(" ", "");
    String holidayDate = (String)request.getParameter("holidayDate").replace(" ", "");
    String holidayTime = (String)request.getParameter("holidayTime").replace(" ", "");
    
    if(id.length() > 0&&holidayType.length() > 0&&holidayDate.length() > 0&&holidayTime.length() > 0)
    {
        SummaryHoliday hPageHandle = new SummaryHoliday();
        rtnRst += hPageHandle.UpdateHoldayMarkRecord(id, holidayType, holidayDate, holidayTime);
    }
    out.write(rtnRst);
%>
