<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonalMenu.PersonalInfo" %>
<%
	String rtnRst = "";
    String username = request.getParameter("user_name").replace(" ", "");
    String queryDate = request.getParameter("queryDate").replace(" ", "");
    
    if(username.length() > 0&&queryDate.length() > 0)
    {
        PersonalInfo hPageHandle = new PersonalInfo();
        rtnRst += hPageHandle.GenerateReturnString(username, queryDate);
    }
    else
        rtnRst += "remove$error:名字能靠谱点不?";
    out.write(rtnRst);
%>
