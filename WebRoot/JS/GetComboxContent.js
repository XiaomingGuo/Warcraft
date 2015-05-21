/**
 * 
 */
var xmlHttp;
function searchComboxOption(str)
{
	xmlHttp=GetXmlHttpObject();
	if(xmlHttp==null)
	{
		alert("Browser does not support HTTP Request");
		return ;
	}
	var url="ajax.jsp";
	url=url+"?q="+str;
	url=url+"&sid="+Math.random();
	xmlHttp.onreadystatechange=stateChanged;
	xmlHttp.open("GET",url,true);
	xmlHttp.send(null);
}
function stateChanged()
{
	if(xmlHttp.readyState==4||xmlHttp.readyState=="complete")
	{
		document.getElementById("pro_info").innerHTML=xmlHttp.responseText;
		//var proInfo = document.getElementsByName("product_info");
	}
}
function GetXmlHttpObject()
{
	var objXMLHttp=null;
	if(window.XMLHttpRequest)
	{
		objXMLHttp=new XMLHttpRequest();
	}
	else if(window.ActiveXObject)
	{
		objXMLHttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	return objXMLHttp;
}