/**
 * 
 */
function findBarcode(obj)
{
	if(!checkInput(obj))
		return;
	$.post("Ajax/Get_Barcode_By_ProName_Ajax.jsp", {"search_name":$("#search_name").val()}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			$("#disBarcode").val(data.split("$")[1]);
		}
		else
		{
			alert(data.split("$")[1]);
		}
	});
}

function checkInput()
{
	var proName = $("#search_name").val();
	if(proName == null||proName == "")
	{
		alert("产品类型不能为空!");
		return false;
	}
	return true;
}
