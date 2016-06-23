/**
 * 
 */
$(function()
{
	var $Pro_Order_select = $('#Pro_Order_select');
	$Pro_Order_select.change(function()
	{
		var $displayOrder = $("#display_order");
		var $confirmOrder = $("#confirm_order");
		var order_name = $Pro_Order_select.find("option:selected").text();
		if (order_name.indexOf("请选择") >= 0)
		{
			$displayOrder.empty();
			$confirmOrder.empty();
			return;
		}
		$.post("Ajax/Product_Order_Close_Item_Ajax.jsp", {"order_name":order_name}, function(data, textStatus)
		{
			alert(data);
			if (textStatus == "success")
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
								if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 0)
								{
									td.append("<label>待审核</label>");
								}
								else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 1)
								{
									td.append("<label>待加工</label>");
								}
								else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 2)
								{
									td.append("<label>加工中...</label>");
								}
								else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 3)
								{
									td.append("<label>待检验</label>");
								}
								else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 4)
								{
									td.append("<label>检验中...</label>");
								}
								else if(parseInt(data_list[iRow*iColCount + iCol + 2]) == 5)
								{
									td.append("<label>已完成</label>");
								}
								else
								{
									td.append("<label>未知状态</label>");
								}
							}
							else if (0 == iColCount - iCol)
							{
								if (parseInt(data_list[iRow*iColCount + iCol + 3]) < 0)
								{
									td.append("<label>已关闭</label>");
								}
								else
								{
									td.append("<input type='button' value='关闭' name='" + data_list[iRow*iColCount + 6] + "$" + data_list[iRow*iColCount + 7] + "$" + iRow + "' onclick='CloseOrder(this)'>");
								}
							}
							else if(iCol == 1)
							{
								td.append(iRow);
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
		if (textStatus == "success")
		{
			if (parseInt(data.split('$')[1]) > 0)
			{
				$("#barcode").val("");
			}
			$('#po_select').change();
		}
	});
}
		
function CloseOrder(obj)
{
	return;
}

function ShowSalePage(obj)
{
	var po_name = $("#po_select").find("option:selected").text();
	if (po_name.indexOf("请选择") >= 0)
	{
		$displayOrder.empty();
		$confirmOrder.empty();
		return;
	}
	$.post("Ajax/Add_Sale_Order_Ajax.jsp", {"POName": po_name}, function(data, textStatus)
	{
		if (!(textStatus == "success") || data.indexOf("error") >= 0)
		{
			alert(data.split("$")[1]);
		}
		location.href ="List_SaleOrder.jsp?PO_Name="+po_name;
	});
}
