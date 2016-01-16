/**
 * 
 */
var inputHead = ["库名", "类别", "产品名称", "八码", "入库数量", "原材料单重", "成品单重", "单价", "备注", "供应商"];

function InputBarcode(obj)
{
	var checkedBarcode = $("#barcode").val();
	if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
	{
		$("#barcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) < 50000000 || parseInt(checkedBarcode) >= 80000000)
	{
		alert("只能输入八码(大于等于50000000或小于80000000)!");
		$("#barcode").val("");
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#store_name_addproduct").val(proInfoList[1]);
			$("#store_name_addproduct").change();
			$("#product_type").empty();
			AddNewSelectItem("product_type", proInfoList[2]);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}
