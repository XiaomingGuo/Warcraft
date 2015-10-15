/**
 * 
 */
$(function()
{
	var $product_order = $('#product_order');
	var $product_name = $('#product_name');
	var $bar_code = $('#bar_code');
	var $Total_QTY = $('#Total_QTY');
	
	$product_order.change(function()
	{
		$product_name.empty();
		$bar_code.empty();
		$product_name.append('<option value="«Î—°‘Ò">--«Î—°‘Ò--</option>');
		$bar_code.append('<option value="«Î—°‘Ò">--«Î—°‘Ò--</option>');
		$.post("Ajax/Query_Pro_Name_From_Order_Ajax.jsp", {"FilterKey1":$("#product_order").find("option:selected").text()}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var pro_list = data.split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					var newOption = $("<option>" + pro_list[i] + "</option>");
					$(newOption).val(pro_list[i]);
					$product_name.append(newOption);
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
				var newOption = $("<option>" + code_list[1] + "</option>");
				$(newOption).val(code_list[1]);
				$bar_code.append(newOption);
				$Total_QTY.attr("value", code_list[4]);
			}
		});
	});				
});