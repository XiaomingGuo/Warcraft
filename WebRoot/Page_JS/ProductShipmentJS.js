/**
 * 
 */
$(function()
{
	$('#po_select').change(function()
	{
		var $displayOrder = $("#display_order");
		var $confirmOrder = $("#confirm_order");
		var po_name = GetSelectedContent("po_select");
		if (po_name.indexOf("请选择") >= 0)
		{
			$displayOrder.empty();
			$confirmOrder.empty();
			return;
		}
		$.post("Ajax/PO_Shipment_Item_Ajax.jsp", {"po_name":po_name, "status":"0"}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				$displayOrder.empty();
				$confirmOrder.empty();
				var data_list = data.split("$");
				var iColCount = data_list[1], iRowCount = data_list[2];
				if (iColCount > 0&&iRowCount > 0)
				{
					var tr = $("<tr></tr>");
					for (var iHead = 1; iHead <= iColCount; iHead++)
					{
						var th = $("<th>" + data_list[iHead + 2] + "</th>");
						tr.append(th);
					}
					$displayOrder.append(tr);
					for(var iRow = 1; iRow <= iRowCount; iRow++)
					{
						var tr = $("<tr></tr>");
						for (var iCol = 1; iCol <= iColCount; iCol++)
						{
							var td = $("<td></td>");
							if (1 == iColCount - iCol)
							{
								if(parseInt(data_list[iRow*iColCount + 9]) > parseInt(data_list[iRow*iColCount + 10]))
								{
									td.append("<input type='text' style='width:68px' name='" + data_list[iRow*iColCount + 9] + "$" + data_list[iRow*iColCount + 10] + "$" + data_list[iRow*iColCount + 11] + "' id='" + iRow + "_QTY' value=" + data_list[iRow*iColCount + iCol + 2] + " onblur='CheckQTY(this)'>");
								}
								else
								{
									td.append("<label>已完成</label>");
								}
							}
							else if (0 == iColCount - iCol)
							{
								if (parseInt(data_list[iRow*iColCount + iCol + 2]) == 0)
								{
									td.append("<input type='button' value='出库' disabled>");
								}
								else
								{
									td.append("<input type='button' value='出库' name='" + data_list[iRow*iColCount + 6] + "$" + data_list[iRow*iColCount + 7] + "$" + iRow + "' onclick='PutOutQtyInCPO(this)'>");
								}
							}
							else
							{
								td.append(data_list[iRow*iColCount + iCol + 2]);
							}
							tr.append(td);
						}
						$displayOrder.append(tr);
					}
					var cmdtr = $("<tr></tr>");
					cmdtr.append("<td><input align='middle' type='button' value='打印销售单' onclick='ShowSalePage(this)'></td>");
					cmdtr.append("<td><input align='middle' type='submit' value='关闭订单'></td>");
					$confirmOrder.append(cmdtr);
				}
			}
		});
	});
});

function PutOutQtyInCPO(obj)
{
	var splitList = obj.name.split("$");
	var Barcode = splitList[0], po_name = splitList[1];
	$.post("Ajax/Update_Customer_OUT_QTY_Ajax.jsp", {"Bar_Code":Barcode, "po_name":po_name, "OUT_QTY":$("#"+splitList[2]+"_QTY").val()}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			if (parseInt(data.split('$')[1]) > 0)
			{
				$("#barcode").val("");
			}
			$('#po_select').change();
		}
	});
}

function CheckQTY(obj)
{
	var splitList = obj.name.split("$");
	if(parseInt(splitList[2]) <= 0)
	{
		alert("成品库无库存,请加紧生产!!!!!");
		obj.value = 0;
	}
	else
	{
		if (parseInt(splitList[0]) < parseInt(splitList[1]) + parseInt(obj.value))
		{
			alert("出货量不能大于客户PO数量!!!!!");
			if(parseInt(splitList[0]) - parseInt(splitList[1]) >= parseInt(splitList[2]))
				obj.value = parseInt(splitList[2]);
			else if(parseInt(splitList[0]) - parseInt(splitList[1]) < parseInt(splitList[2]))
				obj.value = parseInt(splitList[0]) - parseInt(splitList[1]);
		}
	}
}

function ShowSalePage(obj)
{
	var po_name = GetSelectedContent("po_select");
	if (po_name.indexOf("请选择") >= 0)
	{
		$displayOrder.empty();
		$confirmOrder.empty();
		return;
	}
	$.post("Ajax/Add_Sale_Order_Ajax.jsp", {"POName": po_name}, function(data, textStatus)
	{
		if (!CheckAjaxResult(textStatus, data))
		{
			alert(data.split("$")[1]);
		}
		location.href ="List_SaleOrder.jsp?PO_Name="+po_name;
	});
}

