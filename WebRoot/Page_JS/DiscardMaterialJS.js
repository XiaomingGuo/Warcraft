/**
 * 
 */
/*$(function()
{
	var $product_name = $('#product_name');
	var $bar_code = $('#bar_code');
	var $Total_QTY = $('#Total_QTY');
	
	$product_order.change(function()
	{
		$product_name.empty();
		$bar_code.empty();
		$product_name.append('<option value="请选择">--请选择--</option>');
		$bar_code.append('<option value="请选择">--请选择--</option>');
		$.post("Ajax/Query_Pro_Name_From_Order_Ajax.jsp", {"product_order":GetSelectedContent("product_order")}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var pro_list = data.split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					if(i%2 > 0)
						AddNewSelectItem("product_type", pro_list[i]);
					else
						AddNewSelectItem("product_name", pro_list[i]);
				}
			}
		});
	});
	
	$product_name.change(function()
	{
		$bar_code.empty();
		var tempValue = $("#product_name").find("option:selected").text().split("-");
		$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_type":tempValue[0], "product_name":tempValue[1]+"-"+tempValue[2], "storage":"material_storage"}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var code_list = data.split("$");
				AddNewSelectItem("bar_code", code_list[1]);
			}
		});
	});
});*/

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