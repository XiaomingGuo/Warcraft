/**
 * 
 */
function SaveToExcel(obj)
{
	$.post("Ajax/Save_MB_Po_Item_Ajax.jsp", {"poName":obj.name}, function(data, textStatus)
	{
		if (!CheckAjaxResult(textStatus, data))
		{
			alert("生成po报表错误!");
			return;
		}
	});
}

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
