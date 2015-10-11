/**
 * 
 */
function findBarcode(obj)
{
	var proName = $("#search_name").val();
	if(proName == null||proName == "")
	{
		alert("产品类型不能为空!");
		$("#search_name").val("");
		return;
	}
	$.post("Ajax/Get_Barcode_By_ProName_Ajax.jsp", {"search_name":proName}, function(data, textStatus)
	{
		if (textStatus == "success" && data.indexOf("error") < 0)
		{
			var proInfoList = data.split("$");
			$("#disBarcode").val(proInfoList[1]);
		}
		else
		{
			alert(data.split("$")[1]);
		}
	});
}
