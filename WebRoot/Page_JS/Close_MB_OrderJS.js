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
		$.post("Ajax/Close_MB_Order_Item_Ajax.jsp", {"po_name":po_name, "status":"0"}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				$displayOrder.empty();
				$confirmOrder.empty();
				var data_list = data.split("$");
				var iColCount = data_list[1], iRowCount = data_list[2], iJudgeCount = 0;
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
							if (0 == iColCount - iCol)
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
						iJudgeCount += parseInt(data_list[iRow*iColCount + 10]) - parseInt(data_list[iRow*iColCount + 11]);
					}
					var cmdtr = $("<tr></tr>");
					if(iJudgeCount <= 0)
						cmdtr.append("<td><input align='middle' type='submit' value='关闭订单'></td>");
					$confirmOrder.append(cmdtr);
				}
			}
		});
	});
});

