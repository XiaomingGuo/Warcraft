<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.OtherInformation" %>
<%
    String rtnRst = "";
    String proName = (String)request.getParameter("Pro_Name").replace(" ", "");
    String barCode = (String)request.getParameter("Bar_Code").replace(" ", "");
    String description = (String)request.getParameter("Description").replace(" ", "");
    
    if(proName.length() > 0&&barCode.length() == 8&&description.length() > 0)
    {
        OtherInformation hPageHandle = new OtherInformation();
        rtnRst += hPageHandle.UpdateOtherInformationRecord(proName, barCode, description);
    }
    out.write(rtnRst);
%>
