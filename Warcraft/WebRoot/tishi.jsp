<%@ page contentType="text/html;charset=GBK" pageEncoding="UTF-8"%>
<%
  String message="";
  Object abc = session.getAttribute("logonuser");
  if(session.getAttribute("logonuser")==null)
  {
	  message="请先<a href='Login.jsp'>[登录]</a>！";
  }
  else
  {
	  message=(String)session.getAttribute("error");
  }
%>
<html>
  <head>
    <title>友情提示！</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
  </head>  
  <body>
    <center>
        <table style="margin-top:200" width="250" border="1" cellpadding="0" cellspacing="0" bordercolor="black" bordercolorlight="black" bordercolordark="white" >
          <tr bgcolor="lightgrey">
            <td align="center">友情提示！</td>
          </tr>
          <tr height="50">
            <td align="center">
              <%=message%>
            </td>
          </tr>
        </table>
<%  
	if(session.getAttribute("logonuser")==null||session.getAttribute("logonuser").equals(""))
	{
		out.println("<a href='Login.jsp'>[登录]</a>");
	}
%>
  </center>
  </body>
</html>
