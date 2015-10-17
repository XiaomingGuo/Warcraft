/**
 * 
 */
$(function()
{
	var $po_select = $('#po_select');
	$po_select.change(function()
	{
		var po_name = $.trim($po_select.find("option:selected").text());
		if (po_name.indexOf("请选择") >= 0)
		{
			$("#display_page_po").hide();
			$("#display_page_order").show();
			$("#display_page_order").load("Generate_Order_Manual.jsp");
		}
		else
		{
			$("#display_page_order").hide();
			$("#display_page_po").show();
			var $displayOrder = $("#display_order_po");
			var $confirmOrder = $("#confirm_order_po");
			$displayOrder.empty();
			$confirmOrder.empty();
			$displayOrder.attr("border", 1);
			$displayOrder.attr("align", "center");
			$confirmOrder.attr("align", "center");
			$.post("Ajax/Generate_Order_Item_Ajax.jsp", {"po_name":$.trim(po_name), "status":"0"}, function(data, textStatus)
			{
				if (textStatus == "success")
				{
					var Count = 0;
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
								if (0 == iColCount - iCol)
								{
									if(data_list[iRow*iColCount + iCol + 2] > 0)
									{
										td.append("<input type='text' name='" + iRow + "_QTY' id='" + iRow + "_QTY' value=" + data_list[iRow*iColCount + iCol + 2] + ">");
									}
									else if (data_list[iRow*iColCount + iCol + 2] < 0)
									{
										td.append("<label>缺料中</label>");
									}
									else
									{
										td.append("<label>已完成</label>");
									}
								}
								else
								{
									td.append(data_list[iRow*iColCount + iCol + 2]);
								}
								if(2 == iColCount - iCol)
								{
									Count += parseInt(data_list[iRow*iColCount + iCol + 2]);
								}
								tr.append(td);
							}
							$displayOrder.append(tr);
						}
						if (Count > 0)
						{
							var cmdtr = $("<tr></tr>");
							cmdtr.append("<td><input align='middle' type='submit' value='提交生产单'></td>");
							$confirmOrder.append(cmdtr);
						}
					}
				}
			});
		}
	});
});