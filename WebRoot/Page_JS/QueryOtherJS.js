/**
 * 
 */
function SubmitQty(obj)
{
	var valueList = obj.name.split("$");
	DisableButton(valueList[0]+"Rej");
	DisableButton(valueList[0]+"Sure");
	$.post("Ajax/Submit_Other_Qty_Ajax.jsp", {"Batch_Lot":valueList[0], "Bar_Code":valueList[1]}, function(data, textStatus)
	{
		if (!CheckAjaxResult(textStatus, data))
		{
			alert("确认入库错误!");
			return;
		}
		location.reload();
	});
}

function RejectQty(obj)
{
	var valueList = obj.name.split("$");
	DisableButton(valueList[0]+"Rej");
	DisableButton(valueList[0]+"Sure");
	$.post("Ajax/Reject_Storage_Qty_Ajax.jsp", {"Batch_Lot":valueList[0], "Bar_Code":valueList[1]}, function(data, textStatus)
	{
		if (!CheckAjaxResult(textStatus, data))
		{
			alert("删除库存错误!");
			return;
		}
		location.reload();
	});
}
