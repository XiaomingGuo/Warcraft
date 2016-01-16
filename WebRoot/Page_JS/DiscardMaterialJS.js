/**
 * 
 */
function InputBarcode(obj)
{
	var checkedBarcode = $("#inputBarcode").val();
	if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
	{
		$("#inputBarcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) < 50000000 || parseInt(checkedBarcode) > 80000000)
	{
		alert("你输入的不是生产物料, 五金和工具不能报废!");
		return;
	}
	if (parseInt(checkedBarcode) >= 50000000 && parseInt(checkedBarcode) < 60000000)
	{
		checkedBarcode = parseInt(checkedBarcode) + 10000000;
	}
	else if (parseInt(checkedBarcode) >= 70000000 && parseInt(checkedBarcode) < 80000000)
	{
		checkedBarcode = parseInt(checkedBarcode) - 10000000;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#bar_code").empty();
			AddNewSelectItem("bar_code", checkedBarcode);
			$("#product_type").empty();
			var keyWord = proInfoList[2];
			AddNewSelectItem("product_type", keyWord);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}