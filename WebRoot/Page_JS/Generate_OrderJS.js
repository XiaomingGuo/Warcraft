/**
 * 
 */

function changeOrderName(obj)
{
	var $displayOrder = $("#display_po");
	var $confirmOrder = $("#confirm_po");
	var order_name = $.trim($("#OrderHeader").val()) + $.trim($("#OrderName").val());
	if (order_name.length < 6)
	{
		alert("我的乖乖,你就不能起个长点儿的PO单名吗?");
		return;
	}
	$.post("Ajax/Query_PO_Item_Ajax.jsp", {"po_name":order_name, "status":"0"}, function(data, textStatus)
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
							tempList = data_list[iRow*iColCount + iCol + 3].split("#");
							if(parseInt(tempList[1]) <= 0)
							{
								td.append("<input type='button' value='删除' name=" + tempList[0] + "#" + data_list[iRow*iColCount + 8] + " onclick=deleteRecord(this)>");
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
				if (Count > 0)
				{
					cmdtr.append("<td><input align='middle' type='button' onclick=CreateOrder(this) value='生成采购单'></td>");
				}
				$confirmOrder.append(cmdtr);
			}
		}
	});
}

function addorderitem(obj)
{
	var order_name = $.trim($("#OrderHeader").val()) + $.trim($("#OrderName").val());
	if(order_name==""||$("#barcode").val() == null||$("#barcode").val() == ""||$("#delivery_date").val().length != 8||parseInt($("#Input_QTY").val()) <= 0||GetSelectedContent("vendor_name").indexOf("请选择") >= 0)
	{
		alert("我说大姐,你这输入信息糊弄谁呢?");
		return;
	}
	$.post("Ajax/Add_PO_Item_Ajax.jsp", {"bar_code":$("#barcode").val(), "delivery_date":$("#delivery_date").val(), "cpo_QTY":$("#Input_QTY").val(), "percent":$("#percent").val(), "vendor_name":GetSelectedContent("vendor_name"), "po_name":order_name}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			changeOrderName();
		}
	});
}

function deleteRecord(obj)
{
	var tempList = obj.name.split("#");
	var delID = tempList[0];
	var barcode = tempList[1];
	var order_name = $.trim($("#OrderHeader").val()) + $.trim($("#OrderName").val());

	$.post("Ajax/Del_Order_Item_Ajax.jsp", {"product_id":delID, "Order_Name":order_name, "Bar_Code":barcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			changeOrderName();
		}
	});
}

function CreateOrder(obj)
{
	var order_name = $.trim($("#OrderHeader").val()) + $.trim($("#OrderName").val());
	location.href ="List_Purchase.jsp?PO_Name="+order_name;
}


