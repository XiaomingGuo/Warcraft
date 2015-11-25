/**
 * 
 */
$(function()
{
	var $product_type = $('#product_type');
	var $product_name = $('#product_name');
	var $bar_code = $('#bar_code');
	
	$product_type.change(function()
	{
		$product_name.empty();
		$bar_code.empty();
		$product_name.append('<option value="请选择">--请选择--</option>');
		$.post("Ajax/App_Pro_Name_Ajax.jsp", {"FilterKey1":GetSelectedContent("product_type")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var pro_list = data.split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("product_name", pro_list[i]);
				}
			}
		});
	});
	
	$product_name.change(function()
	{
		$.post("Ajax/App_Order_QTY_Ajax.jsp", {"product_name":GetSelectedContent("product_name"), "product_type":GetSelectedContent("product_type")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var code_list = data.split("$");
				$bar_code.attr("value", code_list[1]);
				$("#product_QTY").attr("value", code_list[2]);
				$("#material_QTY").attr("value", code_list[3]);
				Qty_Calc();
			}
		});
	});	
});

function changePOName(obj)
{
	var $displayOrder = $("#display_po");
	var $confirmOrder = $("#confirm_po");
	var po_name = $.trim($("#POName").val());
	if (po_name.length < 6)
	{
		alert("你就不能取个长点儿的PO单号吗?");
		return;
	}
	$.post("Ajax/Query_PO_Item_Ajax.jsp", {"po_name":po_name, "status":"0"}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			$displayOrder.empty();
			$confirmOrder.empty();
			var Count = 0;
			var data_list = data.split("$");
			var status = data_list[1], iColCount = data_list[2], iRowCount = data_list[3];
			if (status != "null")
			{
				$("#confirm_button").attr("disabled", "disabled");
			}
			else
			{
				$("#confirm_button").removeAttr("disabled");
			}
			if (iColCount > 0&&iRowCount > 0)
			{
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					var th = $("<th>" + data_list[iHead + 3] + "</th>");
					tr.append(th);
				}
				$displayOrder.append(tr);
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						if (0 == iColCount - iCol)
						{
							if(status == "null")
							{
								td.append("<input type='button' value='删除' name=" + data_list[iRow*iColCount + iCol + 3] + " onclick=deleteRecord(this)>");
							}
							else
							{
								td.append("<label>已录入</label>");
							}
						}
						else
						{
							td.append(data_list[iRow*iColCount + iCol + 3]);
						}
						if(3 == iColCount - iCol)
						{
							Count += parseInt(data_list[iRow*iColCount + iCol + 3]);
						}
						tr.append(td);
					}
					$displayOrder.append(tr);
				}
				
				var cmdtr = $("<tr></tr>");
				if (status == "null")
				{
					cmdtr.append("<td><input align='middle' type='submit' value='录入订单'></td>");
				}
				if (Count > 0)
				{
					cmdtr.append("<td><input align='middle' type='button' onclick=CreatePO(this) value='生成采购单'></td>");
				}
				$confirmOrder.append(cmdtr);
			}
		}
	});
}

function addpoitem(obj)
{
	var po_name = $("#POName").val();
	if(po_name==""||$("#bar_code").val() == null||$("#bar_code").val() == ""||$("#delivery_date").val().length != 8||parseInt($("#order_QTY").val()) <= 0||$("#vendor_name").find("option:selected").text().indexOf("请选择") >= 0)
	{
		alert("能输入点儿正常值不?");
		return;
	}
	$.post("Ajax/Add_PO_Item_Ajax.jsp", {"bar_code":$("#bar_code").val(), "delivery_date":$("#delivery_date").val(), "cpo_QTY":$("#cpo_QTY").val(), "percent":$("#percent").val(), "vendor_name":$("#vendor_name").find("option:selected").text(), "po_name":po_name}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			changePOName();
		}
	});
}

function deleteRecord(obj)
{
	var delID = obj.name;
	$.post("Ajax/Del_PO_Item_Ajax.jsp", {"product_id":delID}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			changePOName();
		}
	});
}

function Qty_Calc(obj)
{
	var poCount = parseInt($("#cpo_QTY").val());
	var proCount = parseInt($("#product_QTY").val());
	var matCount = parseInt($("#material_QTY").val());
	var tempQTY = (proCount + matCount) - poCount;
	if (poCount > 0&&proCount >= 0&&matCount >= 0)
	{
		if (tempQTY >= 0)
		{
			$("#Need_QTY").val(0);
		}
		else
		{
			$("#Need_QTY").val(-tempQTY);
		}
	}
	else
	{
		$("#cpo_QTY").val("");
	}
}

function CreatePO(obj)
{
	var po_name = $("#POName").val();
	location.href ="List_Purchase.jsp?PO_Name="+po_name;
}

function InputBarcode(obj)
{
	var barcode = $("#bar_code").val();
	if(barcode == null||barcode.length != 8)
	{
		alert("输入八码不对吧!");
		$("#bar_code").val("");
		return;
	}
	if(!IsProductionMaterial(barcode))
	{
		alert("这里只能输入产品八码?");
		return;
	}
	var tempBarcode = ReplaceInputWithProductBarcode(barcode);
	if(barcode != tempBarcode)
	{
		$("#bar_code").val(tempBarcode);
		barcode = tempBarcode;
	}
	$("#product_name").empty();
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":barcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#product_type").val(proInfoList[2]);
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}

