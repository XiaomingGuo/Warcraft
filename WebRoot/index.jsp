<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sx"  uri="/struts-dojo-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>向Excel单元格中添加不同类型的数据</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		table{
			font-size: 12px;
			color: navy;		
			border:1px solid maroon;
		}
		input{
			font-size: 12px;
		}
	</style>
	<script type="text/javascript">
	function getFilePath()
	{
		var file = document.getElementById("filePath");
		var filePath;
		file.select();
		try
		{
			filePath = document.selection.createRange().text;//获得文件的本地路径
		} 
		finally
		{
			document.selection.empty();
		}
		document.getElementById("path").value = filePath;
	}
	
	function getData()
	{
		var beginData = dojo.widget.byId("begin_date");
		alert(beginData.getValue().split("T")[0]);
	}
</script>
	<sx:head/>
  </head>
  
  <body>
	<sx:datetimepicker id="begin_date" name="begin_date" displayFormat="yyyy/MM/dd" label="请选择日期" value="%{'today'}"/>
  	<form id="form1" action="save.jsp" method="post">
  	<input type="hidden" id="path" name="path" />
    <table  background="bg.jpg"  width="280">
    	<tr>
    		<td align="center" colspan="3" height="30"></td>
    	</tr>
    	<tr>
    		<td align="center" colspan="3">【向单元格中添加不同类型的数据】</td>
    	</tr>
    	<tr>
    		<td align="center" colspan="3" height="10"></td>
    	</tr>
    	<tr>
    		<td width="5"></td>
    		<td align="right" valign="bottom">输入文件名：</td>
    		<td valign="bottom">
    			<input type="text" name="fileName" id="fileName" value="Summary-other.xls" />
    		</td>
    	</tr>   
    	<tr>
    		<td width="5"></td>
    		<td align="right" valign="bottom">地址：</td>
    		<td valign="bottom">
    			<input type="text" name="filePath" id="filePath" value="d:\"/>
    		</td>
    	</tr>  	   	
    	<tr>
    		<td width="5"></td>
    		<td align="center" colspan="2" valign="top">
    			<input type="submit" value="上传" onclick="getFilePath()" name="submit">
    			<input type="button" value="获取日期" onclick="getData()" name="Data_Info">
    		</td>
    	</tr>
    	<tr>
    		<td align="center" colspan="3" height="8"></td>
    	</tr>
    </table>
    </form>
  </body>
</html>
