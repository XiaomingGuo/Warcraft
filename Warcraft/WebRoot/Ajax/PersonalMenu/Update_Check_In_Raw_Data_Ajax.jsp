<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.PersonalInfo" %>
<%
	String rtnRst = "";
    String id = (String)request.getParameter("ID").replace(" ", "");
    String workGroup = (String)request.getParameter("workGroup").replace(" ", "");
    if(id.length() > 0&&workGroup.length() > 0)
    {
        PersonalInfo hPageHandle = new PersonalInfo();
        rtnRst += hPageHandle.UpdateCheckInRawDataRecord(id, workGroup);
    }
    out.write(rtnRst);
%>
