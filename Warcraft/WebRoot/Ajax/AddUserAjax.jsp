<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.UserManagement" %>
<%
    String rtnRst = "";
    String checkInId = (String)request.getParameter("check_in_id").replace(" ", "");
    String workGroup = (String)request.getParameter("work_group").replace(" ", "");
    String name = (String)request.getParameter("name").replace(" ", "");
    String department = (String)request.getParameter("department").replace(" ", "");
    String password = (String)request.getParameter("password").replace(" ", "");
    String permission = (String)request.getParameter("Permission").replace(" ", "");
    UserManagement hPageHandle = new UserManagement();
    if (checkInId != null&&workGroup != null&&name != null&&department != null&&password != null&&permission != null&&
        !checkInId.isEmpty()&&!workGroup.isEmpty()&&!name.isEmpty()&&!department.isEmpty()&&!password.isEmpty()&&!permission.isEmpty())
        rtnRst = hPageHandle.DoUserInfoManagement(checkInId, workGroup, name, department, password, permission);
    out.write(rtnRst);
%>
