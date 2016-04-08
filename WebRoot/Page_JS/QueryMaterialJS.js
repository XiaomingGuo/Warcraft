/**
 * 
 */
function SubmitMaterialQty(obj)
{
	var valueList = obj.name.split("$");
	DisableButton(valueList[0]+"Rej");
	DisableButton(valueList[0]+"Sure");
	$.post("Ajax/Submit_Material_Qty_Ajax.jsp", {"recordId":valueList[0], "Bar_Code":valueList[1]}, function(data, textStatus)
	{
		if (!CheckAjaxResult(textStatus, data))
		{
			alert("确认入库错误!");
			return;
		}
		location.reload();
	});
}

