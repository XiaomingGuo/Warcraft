<%@ page contentType="text/html;charset=UTF-8"%>
<%
  session.invalidate();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Login.jsp</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
	<style>
	body{ color:#464646; font:12px normal Verdana, Arial, Helvetica, sans-serif; background:#ECF5FF url(/tpl/user/tpl1/images/bg.jpg) center top repeat-x;}
	</style>
  </head>
  
  <body>
  	<center>
  		<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
  		<form action="DoLogin.jsp" method="post">
  			<fieldset style='width: 500'>
  				<legend><font SIZE="+2" style="font-weight:bold;">登陆</font></legend><br><br>
	  			账号:<input type='text' name='name' align='left' size='18' style='width:200px;'/><br><br>
	  			密码:<input type='password' name='key' align='left' size='18' style='width:200px;'/><br><br>
	  			<input type='submit' value='登陆'>
  			</fieldset>
  		</form>
  	</center>
  </body>
</html>
