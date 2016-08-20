<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.SummaryHoliday" %>
<%
    String rtnRst = "";
    String userID = request.getParameter("check_in_id").replace(" ", "");
    String userName = request.getParameter("UserName").replace(" ", "");
    String queryDate = request.getParameter("queryDate").replace(" ", "");
    String holidayType = request.getParameter("Holiday_Type").replace(" ", "");
    
    if(queryDate.length() > 0)
    {
        SummaryHoliday hPageHandle = new SummaryHoliday();
        rtnRst += hPageHandle.GenerateReturnString(userID, userName, queryDate, holidayType);
    }
    else
    {
        rtnRst += "remove$error:名字能靠谱点不?";
    }
    out.write(rtnRst);
%>
