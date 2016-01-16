/**
 * 
 */
var inputHead = ["库名", "类别", "产品名称", "八码", "入库数量", "单重", "单价", "备注", "供应商"];
function InputBarcode(obj)
{
	var checkedBarcode = $("#barcode").val();
	if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
	{
		$("#barcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) >= 50000000 && parseInt(checkedBarcode) < 80000000)
	{
		alert("只能输入五金库八码(小于50000000或大于等于80000000)!");
		$("#barcode").val("");
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			if(parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 80000000)
			{
				$("#store_name_addproduct").val("原材料库");
			}
			else
			{
				$("#store_name_addproduct").val(proInfoList[1]);
			}
			$("#store_name_addproduct").change();
			$("#product_type").empty();
			var keyWord = proInfoList[2];
			if(parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 80000000)
			{
				if (keyWord.indexOf("原锭") < 0)
				{
					keyWord += "原锭";
				}
				else if (keyWord.indexOf("半成品"))
				{
					keyWord.replace("半成品", "原锭");
				}
			}
			AddNewSelectItem("product_type", keyWord);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}

