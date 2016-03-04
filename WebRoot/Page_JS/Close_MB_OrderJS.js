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
							if (2 == iColCount - iCol)
							{
								td.append(data_list[iRow*iColCount + iCol + 1]);
							}
							else if (1 == iColCount - iCol)
							{
								td.append(data_list[iRow*iColCount + iCol + 1]);
							}
							else if (0 == iColCount - iCol)
							{
								td.append("<label>无需操作</label>");
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
