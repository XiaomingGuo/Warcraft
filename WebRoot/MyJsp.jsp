<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>无标题页</title>
    
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
<style type="text/css">
	.sub{display:none;}
</style>
<script type="text/javascript">
	function testjquery()
	{
		var user_name=$("#test").attr("value");
		alert(user_name);
	}
</script>
<body>
    <select id="sel1">
        <option>--请选择--</option>
        <option>北京</option>
        <option>安徽省</option>
    </select>
    <select class="sub">
        <option>--请选择--</option>
    </select>    
    <select class="sub">
        <option>中关村</option>
        <option>朝阳区</option>
    </select>    
    <select class="sub">
        <option>合肥</option>
        <option>安庆</option>
    </select>
	<input id="test" value="jquery">
	<input type="button" value="click me!" onclick="testjquery();">
    <script type="text/javascript">
        $(document).ready(function(){
            $("#sel1").change(function(){
                $("#sel1 option").each(function(i,o){
                   	alert($(this).prop("selected"));
                    if($(this).prop("selected"))
                    {
                    	alert("123");
                        $(".sub").hide();
                        $(".sub").eq(i).show();
                    }
                });
            });
            $("#sel1").change();
        });
    </script>
</body>
</html>