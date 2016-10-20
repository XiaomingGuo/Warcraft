<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PageParentClass" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
    String message="";
    PageParentClass hPageHandle = new PageParentClass();
    if(session.getAttribute("logonuser")==null)
    {
        response.sendRedirect("tishi.jsp");
    }
    else
    {
        message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String[] displayKeyList = {"库名", "类别", "名称", "八码", "使用者", "数量", "库存数量", "操作"};
        
        //storeroom name Database query
        List<String> store_name = hPageHandle.GetStoreName("TOOLS");
        List<String> UserList = hPageHandle.GetAllUserRecordByName("AllRecord", "name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>申请</title>
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
      <script language="javascript" src="Page_JS/ApplicationJS.js"></script>
  <body>
    <jsp:include page="Menu/MFGToolsMenu.jsp"/>
      <br><br>
      <table id="inputTab" align="center" border="1">
      <caption><b>申领物品</b></caption>
        <tr>
<%
            for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
            {
%>
                <th><%= displayKeyList[iCol-1]%></th>
<%
            }
%>
        </tr>
        <tr>
              <td align="right">
                  <select name="store_name" id="store_name" style="width:100px">
                      <option value = "--请选择--">--请选择--</option>
<%
                    for(int i = 0; i < store_name.size(); i++)
                    {
%>
                      <option value = <%= store_name.get(i) %>><%=store_name.get(i)%></option>
<%
                    }
%>
                  </select>
              </td>
              <td align="right">
                  <select name="product_type" id="product_type" style="width:100px">
                      <option value = "--请选择--">--请选择--</option>
                  </select>
              </td>
            <td align="right">
                <select name="product_name" id="product_name" style="width:200px">
                      <option value = "--请选择--">--请选择--</option>
                </select>
            </td>
            <td align="right">
                <input name="bar_code" id="bar_code" onblur="InputBarcode()" style="width:100px">
            </td>
            <td align="right">
                <select name="user_name" id="user_name" style="width:100px">
                    <option value = "--请选择--">--请选择--</option>
<%
                    for(int i = 0; i < UserList.size(); i++)
                    {
%>
                    <option value = <%=UserList.get(i) %>><%=UserList.get(i)%></option>
<%
                    }
%>
                </select>
            </td>
            <td align="right">
                <input name="QTY" id="QTY" style="width:80px" onblur="CheckQTY(this)">
            </td>
            <td align="right">
                <input name="Total_QTY" id="Total_QTY" style="width:80px" readonly>
            </td>
            <td align="center"><input align="middle" id="confirm_button" type="button" value="确认" onclick="addappitem(this)"></td>
          </tr>
    </table>
    <br>
    <table id="display_app" border='1' align="center"></table>
    <br>
    <table align="center">
        <tr>
            <td><input name="commit" type="button" value="提交" style="width:100" onclick="submitOtherApp(this)"></td>
        </tr>
    </table>
  </body>
</html>
<%
    }
%>