/**
 * 
 */
$(function()
{
	$('#POName').change(function()
	{
		changePOName();
	});
});

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
	$.post("Ajax/Query_Add_Material_PO_Item_Ajax.jsp", {"po_name":po_name, "status":"0"}, function(data, textStatus)
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
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						if (0 == iColCount - iCol)
						{
							if(parseInt(data_list[iRow*iColCount + 12]) > 0)
							{
								td.append("<input type='button' value='入库' name='" + data_list[iRow*iColCount + iCol + 2] + "$" + data_list[iRow*iColCount + 3] + "' id='" +data_list[iRow*iColCount + 3] + "_Button' onclick=AddToMaterialStorage(this)>");
							}
							else
							{
								td.append("<label>已完成</label>");
							}
						}
						else if(1 == iColCount - iCol)
						{
							td.append("<input type='text' value='0' name='" + data_list[iRow*iColCount + 10] + "$" + data_list[iRow*iColCount + 11] + "$" + data_list[iRow*iColCount + 3] + "' id='" + data_list[iRow*iColCount + 3] + "' style='width:70px' onblur='CheckQTY(this)'>");
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

function AddToMaterialStorage(obj)
{
	var tempList = obj.name.split('$');
	var storeQTY = $("#"+tempList[1]).val();

	if(parseInt(storeQTY) <= 0)
	{
		alert("物料数量怎么能小于等于零呢？");
		return;
	}
	var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
	$.post("Ajax/AddMFGMaterial_ReferTo_PO_Ajax.jsp", {"mb_material_po_id":tempList[0], "PutInQTY":storeQTY, "AddDate":addDate}, function(data, textStatus)
	{
		if (!(textStatus == "success"))
		{
			alert(data);
		}
		location.href ="AddMFGMaterial_ReferTo_PO.jsp?POName="+data.split('$')[1];
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
