/**
 * 
 */

function changeUserName()
{
    var $displayOrder = $("#display_po");
    var user_name = $.trim($("#UserName").val());
    if(user_name.indexOf("请选择") >= 0)
    {
        alert("需要你指定要查询人员的名字?");
        return;
    }
    var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
    $.post("Ajax/PersonalMenu/Query_Personal_Check_In_Data_Ajax.jsp", {"user_name":user_name, "queryDate": addDate}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $displayOrder.empty();
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
            if (iColCount > 0&&iRowCount > 0)
            {
                $displayOrder.append(GenPersonMenuInputTableHeadTitle(data_list));
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var td = $("<td align='center'></td>");
                        if(iCol == iColCount)
                            td.append("<input style='width:50' type='button' value='修改' name='" + data_list[iRow*iColCount + iCol + 2] + "#" + iRow +
                                      "' onclick=ModifyRecord(this)>");
                        else
                            td.append(data_list[iRow*iColCount + iCol + 2]);
                        tr.append(td);
                    }
                    $displayOrder.append(tr);
                }
                if(parseInt(data_list[iRow*iColCount + 3]) != iRowCount)
                {
                	$("#button_ensure").empty();
                    $("#button_ensure").append($("<tr></tr>").append($("<td></td>").append("<input type='button' value='数据锁定' style='width:68' onclick='EnsureAllData()'>")));
                }
                if(parseInt(data_list[iRow*iColCount + 4]) > 0)
                    alert("为了保证考勤数据安全,请先确认上月数据!!!");
            }
        }
    });
}

function ModifyRecord(obj)
{
    var tempVal = obj.name.split("#");
    var iRow = parseInt(tempVal[1]);
    var modifytab = document.getElementById('modify_info');
    var displaytab = document.getElementById('display_po');
    modifytab.rows[1].cells[0].innerText=tempVal[0];
    modifytab.rows[1].cells[1].innerText=displaytab.rows[iRow].cells[1].innerText;
    modifytab.rows[1].cells[2].innerText=displaytab.rows[iRow].cells[2].innerText;
    modifytab.rows[1].cells[3].innerText=displaytab.rows[iRow].cells[3].innerText;
    modifytab.rows[1].cells[4].innerText=displaytab.rows[iRow].cells[4].innerText;
    modifytab.rows[1].cells[5].innerText=displaytab.rows[iRow].cells[5].innerText;
    var index = 0;
    $("#workGroup option").each(function()
    {
        if($(this).text()==displaytab.rows[iRow].cells[6].innerText)
        {
            workGroup.options[index].selected = true;
        }
        index++;
    });
}

function ExecModify()
{
    var modifytab = document.getElementById('modify_info');
    $.post("Ajax/PersonalMenu/Update_Check_In_Raw_Data_Ajax.jsp", {"ID":modifytab.rows[1].cells[0].innerText, "workGroup": GetSelectedContent('workGroup')}, function(data, textStatus)
    {
        if (!CheckAjaxResult(textStatus, data))
        {
            alert("更新记录出错!");
        }
        changeUserName();
    });
}

function EnsureAllData()
{
    var userName = GetSelectedContent('UserName');
    var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
    $.post("Ajax/PersonalMenu/Ensure_AllCheckInData_Ajax.jsp", {"UserName":userName, "SubmitDate":addDate}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            alert("完成考勤数据确认!");
        }
        location.reload();
    });
}


