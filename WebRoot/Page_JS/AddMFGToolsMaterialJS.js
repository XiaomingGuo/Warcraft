/**
 * 
 */
var inputHead = ["库名", "类别", "产品名称", "八码", "入库数量", "单重", "单价", "备注", "供应商"];
function InputBarcode(obj)
{
	var checkedBarcode = $("#barcode").val();
	if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
	{
		$("#barcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) >= 50000000 && parseInt(checkedBarcode) < 80000000)
	{
		alert("只能输入五金库八码(小于50000000或大于等于80000000)!");
		$("#barcode").val("");
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			if(parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 80000000)
			{
				$("#store_name_addproduct").val("原材料库");
			}
			else
			{
				$("#store_name_addproduct").val(proInfoList[1]);
			}
			$("#store_name_addproduct").change();
			$("#product_type").empty();
			var keyWord = proInfoList[2];
			if(parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 80000000)
			{
				if (keyWord.indexOf("原锭") < 0)
				{
					keyWord += "原锭";
				}
				else if (keyWord.indexOf("半成品"))
				{
					keyWord.replace("半成品", "原锭");
				}
			}
			AddNewSelectItem("product_type", keyWord);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}

function AddMFGToolsMaterialFun()
{
	var tab = document.getElementById('display_add');
	if(tab.rows.length < 2)
	{
		alert("申领数量超出库存数量或申领信息填写不完整!");
		return;
	}
	
	var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
	for(var iRow=1; iRow < tab.rows.length; iRow++)
	{
		$.post("Ajax/Submit_Material_Ajax.jsp", {"barcode":tab.rows[iRow].cells[4].innerText,
			"QTY":tab.rows[iRow].cells[5].innerText,"PriceUnit":tab.rows[iRow].cells[8].innerText,
			"supplier_name":tab.rows[iRow].cells[10].innerText,"SubmitDate":addDate},
			function(data, textStatus)
		{
			if (!CheckAjaxResult(textStatus, data))
			{
				return;
			}
		});
	}
	while(tab.rows.length > 0)
	{
		tab.deleteRow(0);
	}
}
