<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.UserManagement" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
    String message="";
    UserManagement hPageHandle = new UserManagement();
    if(session.getAttribute("logonuser")==null)
    {
        response.sendRedirect("tishi.jsp");
    }
    else
    {
        int temp = mylogon.getUserRight()&2;
        if(temp == 0)
        {
            session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
            response.sendRedirect("tishi.jsp");
        }
        else
        {
            message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            int PageRecordCount = 20;
            int BeginPage = Integer.parseInt(request.getParameter("BeginPage"));
            
            String[] keyList = {"ID", "考勤工号", "考勤类型", "姓名", "创建时间", "部门", "密码", "用户权限", "操作"};
            String[] codeKeyList = {"ID", "check_in_id", "isFixWorkGroup", "name", "create_date", "department", "password", "permission", "submit"};
            int recordCount = hPageHandle.GetUserCount();
            List<List<String>> recordList = hPageHandle.GetUserInfo(PageRecordCount, BeginPage);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

  </head>
    <script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
    <script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
    <script language="javascript" src="Page_JS/UserManagementJS.js"></script>
  <body onload="DisplayUserTable(<%=BeginPage%>)">
    <jsp:include page="Menu/MainMenu.jsp"/>
    <table border="1" align="center">
        <tr>
<%
            for(int iCol = 1; iCol <= keyList.length; iCol++)
            {
%>
            <th><%= keyList[iCol-1]%></th>
<%
            }
%>
        </tr>

<%
            if (!recordList.isEmpty())
            {
                int iRow = 0;
%>
        <tr>
<%
                for(int iCol = 1; iCol <= keyList.length; iCol++)
                {
                    if(keyList[iCol-1] == "用户权限")
                    {
%>
        <td>
            <center>
                <input type="checkbox" name="AddPermission" value="2048">仓库管理员
                <input type="checkbox" name="AddPermission" value="1024">计划员
                <input type="checkbox" name="AddPermission" value="512">计划审核
                <input type="checkbox" name="AddPermission" value="256">生产管理
                <input type="checkbox" name="AddPermission" value="128">质量检验员
                <br>
                <input type="checkbox" name="AddPermission" value="64">出货管理员
                <input type="checkbox" name="AddPermission" value="32">会计员
                <input type="checkbox" name="AddPermission" value="4092">管理员
                <input type="checkbox" name="AddPermission" value="4095">超级管理员
            </center>
        </td>
<%
                    }
                    else if(keyList[iCol-1] == "操作")
                    {
%>
        <td>
            <center><input type="button" value="添加" name="<%=iRow %>" id="<%=iRow %>" onclick="Add(this)"></center>
        </td>
<%
                    }
                    else if(keyList[iCol-1] == "ID")
                    {
%>
        <td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
                    }
                    else if(keyList[iCol-1] == "创建时间")
                    {
%>
        <td></td>
<%
                    }
                    else
                    {
%>
        <td>
            <center><input type="text" name="<%=codeKeyList[iCol-1] %>" id="<%=codeKeyList[iCol-1] %>" style="width:100px"></center>
        </td>
<%
                    }
                }
%>
        </tr>
<%
            }
%>
    </table>
    <br>
    <table id="display_user" border='1' align="center"></table>
    <br><br>
    <jsp:include page="PageNum.jsp">
        <jsp:param value="<%=recordCount %>" name="recordCount"/>
        <jsp:param value="<%=PageRecordCount %>" name="PageRecordCount"/>
        <jsp:param value="<%=BeginPage %>" name="BeginPage"/>
        <jsp:param value="UserManagement.jsp" name="PageName"/>
    </jsp:include>
  </body>
</html>
<%
        }
    }
%>