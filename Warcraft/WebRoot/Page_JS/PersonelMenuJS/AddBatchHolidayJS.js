/**
 * 
 */
var displayHead = ["ID", "姓名", "工号", "部门", "假期类型", "操作"];
function OnloadDisplay()
{
    var $displayInfo = $("#display_info");
    $.post("Ajax/PersonalMenu/Query_AddHoliday_Ajax.jsp", {}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $displayInfo.empty();
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
                $displayInfo.append(tr);
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var td = null;
                        if(iColCount - iCol == 0)
                        {
                            td = $("<td></td>");
                            td.append("<input type='button' value='确认' name='" + data_list[iRow*iColCount + iCol + 2] +
                                    "' onclick=EnsureCheckInData(this)>");
                            //td.append("<input type='button' value='查询' name='" + data_list[iRow*iColCount + iCol + 2] +
                                    //"' onclick=EnsureCheckInData(this)>");
                        }
                        else if(iColCount - iCol == 1)
                        {
                            td = $("<td></td>");
                            var selectItem = data_list[iRow*iColCount + iCol + 2].split("#");
                            var appendString = "<select name='WorkGroup' id='WorkGroup' style='width:100px'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        else if(iColCount - iCol == 2)
                        {
                            td = $("<td></td>");
                            var selectItem = data_list[iRow*iColCount + iCol + 2].split("#");
                            var appendString = "<select name='department' id='department' style='width:100px'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        else if(iColCount - iCol == 3)
                        {
                            td = $("<td></td>");
                            td.append("<input name='UserID' id='UserID' style='width:100' onblur='InputUserID()'>");
                        }
                        else if(iColCount - iCol == 4)
                        {
                            td = $("<td></td>");
                            var selectItem = data_list[iRow*iColCount + iCol + 2].split("#");
                            var appendString = "<select name='UserName' id='UserName' style='width:100px' onchange='changeUserName(this.options[this.options.selectedIndex].value)'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        else if(iColCount - iCol == 5)
                        {
                            td = $("<td></td>");
                            td.append(data_list[iRow*iColCount + iCol + 2]);
                        }
                        tr.append(td);
                    }
                    $displayInfo.append(tr);
                }
            }
        }
    });
}

function changeUserName(obj)
{
    $.post("Ajax/Query_User_Name_Ajax.jsp", {"UserName":obj}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]);
            $('#UserID').val(data_list[iColCount+5]);
            
            var department = document.getElementById('department');
            var index = 0;
            $("#department option").each(function()
            {
                if($(this).text()==data_list[iColCount+6])
                {
                    department.options[index].selected = true;
                }
                index++;
            }); 
        }
    });
}

function EnsureCheckInData(obj)
{
    var workNum = $('#UserID').val();
    var department = GetSelectedContent("department");
    var userName = GetSelectedContent("UserName");
    var workGroup = GetSelectedContent("WorkGroup");
    if(workGroup.indexOf("请选择") >= 0)
    {
        alert("请选择排班班次!");
        return;
    }
    var tab = document.getElementById('check_in_list');
    if(1 > tab.rows.length)
    {
        var myHeadRow = document.createElement("tr");
        myHeadRow.setAttribute("align", "center");
        for(var iCol=0; iCol < displayHead.length; iCol++)
        {
            myHeadRow.appendChild(CreateTabCellContext("th", displayHead[iCol]));
        }
        tab.appendChild(myHeadRow);
    }
    var index = tab.rows.length;
    if(userName.indexOf("请选择") >= 0||department.indexOf("请选择") >= 0)
    {
        $.post("Ajax/Query_AllUserInfo_Ajax.jsp", {"UserName":userName, "Department":department}, function(data, textStatus)
        {
            if (CheckAjaxResult(textStatus, data))
            {
                var data_list = data.split("$");
                var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
                for(var iRow=1; iRow < iRowCount+1; iRow++)
                {
                    var myCurrentRow = document.createElement("tr");
                    var rowNum = index+iRow-1;
                    myCurrentRow.appendChild(CreateTabCellContext("td", rowNum));
                    for(var iCol=1; iCol < iColCount-1; iCol++)
                    {
                        var val = "";
                        if("姓名" == tab.rows[0].cells[iCol].innerText)
                        {
                            val = data_list[iColCount*iRow+4];
                        }
                        else if("工号" == tab.rows[0].cells[iCol].innerText)
                        {
                            val = data_list[iColCount*iRow+5];;
                        }
                        else if("部门" == tab.rows[0].cells[iCol].innerText)
                        {
                            val = data_list[iColCount*iRow+6];
                        }
                        else if("假期类型" == tab.rows[0].cells[iCol].innerText)
                        {
                            val = workGroup;
                        }
                        myCurrentRow.appendChild(CreateTabCellContext("td", val));
                    }
                    myCurrentRow.appendChild(CreateTabCellContext("td", "<input align='middle' type='button' name='"+ rowNum +"' value='删除' onclick='delappitem(this)'>"));
                    tab.appendChild(myCurrentRow);
                }
            }
        });
    }
    else
    {
        var myCurrentRow = document.createElement("tr");
        myCurrentRow.appendChild(CreateTabCellContext("td", index));
        for(var iCol=1; iCol < tab.rows[0].cells.length-1; iCol++)
        {
            var val = "";
            if("姓名" == tab.rows[0].cells[iCol].innerText)
            {
                val = userName;
            }
            else if("工号" == tab.rows[0].cells[iCol].innerText)
            {
                val = workNum;
            }
            else if("部门" == tab.rows[0].cells[iCol].innerText)
            {
                val = department;
            }
            else if("假期类型" == tab.rows[0].cells[iCol].innerText)
            {
                val = workGroup;
            }
            myCurrentRow.appendChild(CreateTabCellContext("td", val));
        }
        myCurrentRow.appendChild(CreateTabCellContext("td", "<input align='middle' type='button' name='"+ index +"' value='删除' onclick='delappitem(this)'>"));
        tab.appendChild(myCurrentRow);
    }
}

function SubmitBatchCheckIn()
{
    var tab = document.getElementById('check_in_list');
    if(tab.rows.length < 2)
    {
        alert("申请人信息填写不完整!");
        return;
    }
    var beginDate = dojo.widget.byId("BeginDate").inputNode.value;
    var endDate = dojo.widget.byId("EndDate").inputNode.value;
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        $.post("Submit/Submit_Add_Batch_Holidays.jsp", {"userId":tab.rows[iRow].cells[2].innerText, "HolidayType":tab.rows[iRow].cells[4].innerText,
                                                        "BeginDate":beginDate, "EndDate":endDate}, function(data, textStatus)
        {
            if (!CheckAjaxResult(textStatus, data))
            {
                alert(data);
            }
        });
    }
    while(tab.rows.length > 0)
    {
        tab.deleteRow(0);
    }
}