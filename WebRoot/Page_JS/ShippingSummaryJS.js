/**
 * 
 */
$(function()
{
	$("li").click(function()
	{
		var order_name=$(this).html();
		var $OrderBlock = $("#OrderBlock");
		$("#TitleName").html(order_name);
		$.post("Ajax/Query_Order_Item_Ajax.jsp", {"po_name":order_name}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				$OrderBlock.empty();
				var data_list = data.split("$");
				var iColCount = data_list[2], iRowCount = data_list[3];
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					if (iHead == iColCount)
					{
						var th = $("<th>入库数量</th>");
						tr.append(th);
					}
					else
					{
						var th = $("<th>" + data_list[iHead + 3] + "</th>");
						tr.append(th);
					}
				}
				$OrderBlock.append(tr);
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					var execID = data_list[(iRow)*iColCount + 4];
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						if (1 == iColCount - iCol)
						{
							if (data_list[(iRow)*iColCount + 10] == data_list[(iRow)*iColCount + 13])
							{
								td.append("<input type='button' value='入库' disabled>");
							}
							else
							{
								td.append("<input type='button' value='入库' name='" + data_list[iRow*iColCount + 4] + "$" + execID.toString() + "' onclick='PutInStorage(this)'>");
							}
						}
						else if(0 == iColCount - iCol)
						{
							if (data_list[(iRow)*iColCount + 10] == data_list[(iRow)*iColCount + 13])
							{
								if (data_list[(iRow)*iColCount + 9] == data_list[(iRow)*iColCount + 10])
								{
									td.append("<label>检验已完成</label>");
								}
								else
								{
									td.append("<label>待检验</label>");
								}
							}
							else
							{
								td.append("<input type='text' value='0' name='" + data_list[iRow*iColCount + 9] + "$" + data_list[iRow*iColCount + 13] + "' id='" + execID.toString() + "' style='width:70px' onblur='CheckQTY(this)'>");
							}
						}
						else if(iCol == 1)
						{
							td.append(iRow);
						}
						else
						{
							td.append(data_list[iRow*iColCount + iCol + 3]);
						}
						tr.append(td);
					}
					$OrderBlock.append(tr);
				}
			}
		});
	});
});

function PutInStorage(obj)
{
	var tempList = obj.name.split('$');
	var storeQTY = $("#"+tempList[1]).val();
	$.post("Ajax/Out_Quality_Control_Ajax.jsp", {"product_id":tempList[0], "PutInQTY":storeQTY}, function(data, textStatus)
	{
		if (!(textStatus == "success") || data.indexOf("error") >= 0)
		{
			alert(data.split("$")[1]);
		}
		location.reload();
	});
}

function CheckQTY(obj)
{
	var tempList = obj.name.split('$');
	if (parseInt(obj.value)+parseInt(tempList[1]) > parseInt(tempList[0]))
	{
		alert("入库数量不能大于生产单量!");
		obj.value = 0;
	}
}
