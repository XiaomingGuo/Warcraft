/**
 * 
 */
function SubmitQty(obj)
{
	var valueList = obj.name.split("$");
	$("#"+valueList[0]+"Rej").attr("disabled", "disabled");
	$("#"+valueList[0]+"Sure").attr("disabled", "disabled");
	$.post("Ajax/Submit_Storage_Qty_Ajax.jsp", {"recordId":valueList[0], "storage_name":valueList[1]}, function(data, textStatus)
	{
		if (!CheckAjaxResult(textStatus, data))
		{
			alert("确认入库错误!");
			return;
		}
	});
	location.reload();
}

function RejectQty(obj)
{
	var valueList = obj.name.split("$");
	$("#"+valueList[0]+"Rej").attr("disabled", "disabled");
	$("#"+valueList[0]+"Sure").attr("disabled", "disabled");
	$.post("Ajax/Reject_Storage_Qty_Ajax.jsp", {"recordId":valueList[0], "storage_name":valueList[1]}, function(data, textStatus)
	{
		if (!CheckAjaxResult(textStatus, data))
		{
			alert("删除库存错误!");
			return;
		}
		location.reload();
	});
}
