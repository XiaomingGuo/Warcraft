/**
 * 
 */
function changePOName(obj)
{
	var po_name = $.trim($("#POName").val());
	if(po_name.length == 0)
		return;
	var $displayOrder = $("#display_po");
	var $confirmOrder = $("#confirm_po");
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
				DisableButton("confirm_button");
			}
			else
			{
				EnableButton("confirm_button");
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
							tempList = data_list[iRow*iColCount + iCol + 3].split("#");
							if(parseInt(tempList[1]) <= 0)
							{
								td.append("<input type='button' value='删除' name=" + tempList[0] + " onclick=deleteRecord(this)>");
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
					//cmdtr.append("<td><input align='middle' type='submit' value='录入订单'></td>");
				}
				cmdtr.append("<td><input align='middle' type='button' onclick=CreatePO(this) value='提交或生成采购单'></td>");
				$confirmOrder.append(cmdtr);
			}
		}
	});
}

function addpoitem(obj)
{
	var po_name = $("#POName").val();
	if(po_name==""||$("#barcode").val() == null||$("#barcode").val() == ""||$("#delivery_date").val().length != 8||parseInt($("#Input_QTY").val()) <= 0||GetSelectedContent("vendor_name").indexOf("请选择") >= 0)
	{
		alert("能输入点儿正常值不?");
		return;
	}
	$.post("Ajax/Add_PO_Item_Ajax.jsp", {"bar_code":$("#barcode").val(), "delivery_date":$("#delivery_date").val(), "cpo_QTY":$("#Input_QTY").val(), "percent":$("#percent").val(), "vendor_name":GetSelectedContent("vendor_name"), "po_name":po_name}, function(data, textStatus)
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

function CreatePO(obj)
{
	var po_name = $("#POName").val();
	$.post("Ajax/Transfer_Storage_Ajax.jsp", {"PO_Name":po_name}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			location.href ="List_Purchase.jsp?PO_Name="+po_name;
		}
	});
}


