<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.ApprovePage" %>
<%
    String rtnRst = "";
    String recordId = (String)request.getParameter("material_id").replace(" ", "");
    ApprovePage hPageHandle = new ApprovePage();
    if (recordId != null&&!recordId.isEmpty())
        rtnRst = hPageHandle.DeleteOtherApplyRecord(recordId);
    out.write(rtnRst);
%>
