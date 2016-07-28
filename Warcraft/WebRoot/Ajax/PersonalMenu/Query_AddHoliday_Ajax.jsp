<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.AddHoliday" %>
<%
    String rtnRst = "";
    AddHoliday hPageHandle = new AddHoliday();
    rtnRst += hPageHandle.GenerateReturnString();
    out.write(rtnRst);
%>
