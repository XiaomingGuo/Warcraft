/**
 * 
 */
function changePOName()
{
	var $displayOrder = $("#display_po");
	var po_name = $.trim($("#POName").val());
	if(po_name.length == 0)
		return;
	if (po_name.length < 6)
	{
		alert("你就不能找个靠谱的PO单号吗?");
		return;
	}
	$.post("Ajax/Query_Material_PO_Item_Ajax.jsp", {"po_name":po_name}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			$displayOrder.empty();
			var data_list = data.split("$");
			var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
			if (iColCount > 0&&iRowCount > 0)
			{
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					if (iHead == iColCount-1)
					{
						var th = $("<th>入库数量</th>");
						tr.append(th);
					}
					else
					{
						var th = $("<th>" + data_list[iHead + 2] + "</th>");
						tr.append(th);
					}
				}
				$displayOrder.append(tr);
				//{"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "采购量", "已交数量", "未交数量", "创建时间", "状态", "操作"};
				//{"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "数量", "状态", "操作"};
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						if (0 == iColCount - iCol)
						{
							td.append("<input type='button' value='转出' name='" + data_list[iRow*iColCount + 7] + "$" + data_list[iRow*iColCount + 8] + "$" + data_list[iRow*iColCount + 3] + "' id='" +data_list[iRow*iColCount + 3] + "_Button' onclick=DoTransfer(this)>");
						}
						else if(1 == iColCount - iCol)
						{
							td.append("<input type='text' value='0' name='" + data_list[iRow*iColCount + iCol + 2] + "' id='" + data_list[iRow*iColCount + 3] + "' style='width:70px' onblur='CheckQTY(this)'>");
						}
						else
						{
							td.append(data_list[iRow*iColCount + iCol + 2]);
						}
						tr.append(td);
					}
					$displayOrder.append(tr);
				}
				
			}
		}
	});
}

function DoTransfer(obj)
{
	var tempList = obj.name.split('$');
	var storeQTY = $("#"+tempList[2]).val();

	if(parseInt(storeQTY) <= 0)
	{
		alert("物料数量怎么能小于等于零呢？");
		return;
	}
	DisableButton(tempList[2]+"_Button");
	$.post("Ajax/Transfer_To_SemiProduct_Ajax.jsp", {"Bar_Code":tempList[0], "PO_Name":tempList[1], "TransQty":storeQTY}, function(data, textStatus)
	{
		if (!(textStatus == "success"))
		{
			alert(data);
		}
		location.href ="Transfer_To_SemiProStorage.jsp?POName="+data.split('$')[1];
	});
}
