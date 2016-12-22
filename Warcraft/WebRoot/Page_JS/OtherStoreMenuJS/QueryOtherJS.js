/**
 * 
 */
function OnloadDisplay(obj)
{
    DisplayTableContent(obj);
}

function QueryOtherStoreRecord()
{
    var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
    DisplayTableContent(addDate);
}

function DisplayTableContent(addDate)
{
    alert(addDate);
    var $displayOrder = $("#display_info");
    $.post("Ajax/OtherStoreMenu/QueryOtherApproveAjax.jsp", {"submitDate": addDate}, function(data, textStatus)
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
                    var th = $("<th>" + data_list[iHead + 2] + "</th>");
                    tr.append(th);
                }
                $displayOrder.append(tr);
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var Barcode = data_list[iRow*iColCount + 5];
                        var td = $("<td></td>");
                        if (12 == iCol)
                        {
                            if(data_list[iRow*iColCount + iCol + 2] == "0")
                            {
                                td.append("<input type='button' value='确认' id='" + data_list[iRow*iColCount + iCol + 2] + "Sure' name='" + data_list[iRow*iColCount + iCol + 2] + "$" + Barcode +"' onclick='SubmitQty(this)'>&nbsp;");//
                                td.append("<input type='button' value='删除' id='" + data_list[iRow*iColCount + iCol + 2] + "Rej' name='" + data_list[iRow*iColCount + iCol + 2] + "$" + Barcode +"' onclick='RejectQty(this)'>");//
                            }
                            else
                            {
                                td.append("<input type='button' value='修改' id='" + data_list[iRow*iColCount + iCol + 2] + "Rej' name='" + data_list[iRow*iColCount + iCol + 2] + "#" + iRow +"' onclick='ModifyRecord(this)'>");//
                            }
                        }
                        else
                            td.append(data_list[iRow*iColCount + iCol + 2]);
                        tr.append(td);
                    }
                    $displayOrder.append(tr);
                }
            }
        }
    });
}

function SubmitQty(obj)
{
    var valueList = obj.name.split("$");
    DisableButton(valueList[0]+"Rej");
    DisableButton(valueList[0]+"Sure");
    $.post("Ajax/Submit_Other_Qty_Ajax.jsp", {"Batch_Lot":valueList[0], "Bar_Code":valueList[1]}, function(data, textStatus)
    {
        if (!CheckAjaxResult(textStatus, data))
        {
            alert("确认入库错误!");
            return;
        }
        location.reload();
    });
}

function RejectQty(obj)
{
    var valueList = obj.name.split("$");
    DisableButton(valueList[0]+"Rej");
    DisableButton(valueList[0]+"Sure");
    $.post("Ajax/Reject_Storage_Qty_Ajax.jsp", {"Batch_Lot":valueList[0], "Bar_Code":valueList[1]}, function(data, textStatus)
    {
        if (!CheckAjaxResult(textStatus, data))
        {
            alert("删除库存错误!");
            return;
        }
        location.reload();
    });
}

function ModifyRecord(obj)
{
    var tempVal = obj.name.split("#");
    var iRow = parseInt(tempVal[1]);
    var modifytab = document.getElementById('modify_info');
    var displaytab = document.getElementById('display_info');
    modifytab.rows[1].cells[0].innerText=tempVal[0];
    modifytab.rows[1].cells[1].innerText=displaytab.rows[iRow].cells[1].innerText;
    $("#barcode").val(displaytab.rows[iRow].cells[2].innerText);
    modifytab.rows[1].cells[3].innerText=displaytab.rows[iRow].cells[3].innerText;
    modifytab.rows[1].cells[4].innerText=displaytab.rows[iRow].cells[4].innerText;
    modifytab.rows[1].cells[5].innerText=displaytab.rows[iRow].cells[5].innerText;
    modifytab.rows[1].cells[6].innerText=displaytab.rows[iRow].cells[6].innerText;
    modifytab.rows[1].cells[7].innerText=displaytab.rows[iRow].cells[7].innerText;
    modifytab.rows[1].cells[8].innerText=displaytab.rows[iRow].cells[8].innerText;
    var index = 0;
    $("#Vendor option").each(function()
    {
        if($(this).text()==displaytab.rows[iRow].cells[9].innerText)
        {
            Vendor.options[index].selected = true;
        }
        index++;
    });
    modifytab.rows[1].cells[10].innerText=displaytab.rows[iRow].cells[10].innerText;
}

function InputBarcode(obj)
{
    var checkedBarcode = $("#barcode").val();
    if(checkedBarcode == null || checkedBarcode.length != 8)
    {
        $("#barcode").val("");
        return;
    }
    if (parseInt(checkedBarcode) < 10000000 || parseInt(checkedBarcode) >= 20000000)
    {
        alert("只能输入八码(大于等于10000000或小于20000000)!");
        $("#barcode").val("");
        return;
    }
    var modifytab = document.getElementById('modify_info');
    $.post("Ajax/Get_ProductInfo_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
    {
        //"id", "Bar_Code", "name", "product_type", "weight", "sample_price", "sample_vendor", "process_name", "capacity", "description"
        if (CheckAjaxResult(textStatus, data))
        {
            var proInfoList = data.split("$");
            modifytab.rows[1].cells[1].innerText=proInfoList[3];
            modifytab.rows[1].cells[7].innerText=proInfoList[6];
            var totalPrice = parseFloat(proInfoList[6])*parseFloat(modifytab.rows[1].cells[4].innerText);
            modifytab.rows[1].cells[8].innerText=(Number(totalPrice)).toFixed(1);
            var index = 0;
            $("#Vendor option").each(function()
            {
                if($(this).text()==proInfoList[7])
                {
                    Vendor.options[index].selected = true;
                }
                index++;
            });
            modifytab.rows[1].cells[10].innerText=proInfoList[10];
        }
    });
}

function ExecModify()
{
    var vendorName = GetSelectedContent("Vendor");
    var barCode = $("#barcode").val();
    var modifytab = document.getElementById('modify_info');
    if(modifytab.rows[1].cells[0].innerText == "..."||modifytab.rows[1].cells[1].innerText == "..."||
            barCode == ""||modifytab.rows[1].cells[3].innerText == "..."||
            modifytab.rows[1].cells[4].innerText == "..."||modifytab.rows[1].cells[5].innerText == "..."||
            modifytab.rows[1].cells[6].innerText == "..."||modifytab.rows[1].cells[7].innerText == "..."||
            modifytab.rows[1].cells[8].innerText == "..."||vendorName.indexOf("请选择") >= 0||
            modifytab.rows[1].cells[9].innerText == "...")
    {
        $("#barcode").val("");
        return;
    }
    $.post("Ajax/Update_OtherStorage_Record_Ajax.jsp", {"ID":modifytab.rows[1].cells[0].innerText, "Barcode":$("#barcode").val(),
                                                            "SinglePrice": modifytab.rows[1].cells[7].innerText, "TotalPrice": modifytab.rows[1].cells[8].innerText,
                                                            "VendorName":GetSelectedContent("Vendor")}, function(data, textStatus)
    {
        if (!CheckAjaxResult(textStatus, data))
        {
            alert("Update Error!");
        }
    });
    location.href ="OtherStoreMenu/QueryOther.jsp";
}