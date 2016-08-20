/**
 * 
 */
function delappitem(obj)
{
    var tab = document.getElementById('check_in_list');
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        if(tab.rows[iRow].cells[0].innerText == obj.name)
        {
            tab.deleteRow(iRow);
            if(tab.rows.length == 1)
                tab.deleteRow(0);
            break;
        }
    }
    initRows(tab);
}

function initRows(tab)
{
    var tabRows = tab.rows.length;  
    for(var i = 1; i<tabRows; i++)
    {
        tab.rows[i].cells[0].innerText=i;
        tab.rows[i].cells[tab.rows[i].cells.length-1].innerHTML="<input align='middle' type='button' name='"+ i +"' value='删除' onclick='delappitem(this)'>";  
    }
}

function GenSelectBoxString(strName, strSelectItem)
{
    var selectArray = strSelectItem.split("#");
    var appendString = "<select name='"+strName+"' id='"+strName+"' style='width:100px'><option value = '--请选择--'>--请选择--</option>";
    for(var idx = 0; idx < selectArray.length; idx++)
        appendString += "<option value = " + selectArray[idx] +">" + selectArray[idx] + "</option>";
    appendString += "</select>";
    return appendString;
}

function GenUserNameSelectBox(strName, strSelectItem)
{
    var selectItem = strSelectItem.split("#");
    var appendString = "<select name='"+strName+"' id='"+strName+"' style='width:100px' onchange='changeUserName(this.options[this.options.selectedIndex].value)'><option value='--请选择--'>--请选择--</option>";
    for(var idx = 0; idx < selectItem.length; idx++)
        appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
    appendString += "</select>";
    return appendString;
}

function GenPersonMenuInputTableHeadTitle(data_list)
{
    var tr = $("<tr></tr>");
    for (var iHead = 1; iHead <= parseInt(data_list[1]); iHead++)
    {
        var th = $("<th>" + data_list[iHead + 2] + "</th>");
        tr.append(th);
    }
    return tr;
}

function GenPersonMenuInputTableBody(data_list, iRow)
{
    var iColCount = parseInt(data_list[1]);
    var tr = $("<tr></tr>");
    for (var iCol = 1; iCol <= iColCount; iCol++)
    {
        var td = $("<td></td>");
        if(iColCount - iCol == 0)
            td.append("<input type='button' value='确认' name='" + data_list[iRow*iColCount + iCol + 2] + "' onclick=EnsureCheckInData(this)>");
        else if(iColCount - iCol == 1)
            td.append(GenSelectBoxString("WorkGroup", data_list[iRow*iColCount + iCol + 2]));
        else if(iColCount - iCol == 2)
            td.append(GenSelectBoxString("department", data_list[iRow*iColCount + iCol + 2]));
        else if(iColCount - iCol == 3)
            td.append("<input name='UserID' id='UserID' style='width:100' onblur='InputUserID()'>");
        else if(iColCount - iCol == 4)
            td.append(GenUserNameSelectBox("UserName", data_list[iRow*iColCount + iCol + 2]));
        else if(iColCount - iCol == 5)
            td.append(data_list[iRow*iColCount + iCol + 2]);
        tr.append(td);
    }
    return tr;
}

function PersonMenuOnloadDisplay(strAjaxFilePath)
{
    var $displayInfo = $("#display_info");
    $.post(strAjaxFilePath, {}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $displayInfo.empty();
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
            if (iColCount > 0&&iRowCount > 0)
            {
                $displayInfo.append(GenPersonMenuInputTableHeadTitle(data_list));
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                    $displayInfo.append(GenPersonMenuInputTableBody(data_list, iRow));
            }
        }
    });
}

function GenDisplayHeadRowContent(displayHead)
{
    var myHeadRow = document.createElement("tr");
    myHeadRow.setAttribute("align", "center");
    for(var iCol=0; iCol < displayHead.length; iCol++)
        myHeadRow.appendChild(CreateTabCellContext("th", displayHead[iCol]));
    return myHeadRow;
}

function GenDisplayRowContent(rowContent)
{
    var myCurrentRow = document.createElement("tr");
    for(var iCol=0; iCol < rowContent.length; iCol++)
        myCurrentRow.appendChild(CreateTabCellContext("td", rowContent[iCol]));
    myCurrentRow.appendChild(CreateTabCellContext("td", "<input align='middle' type='button' name='"+ rowContent[0] +"' value='删除' onclick='delappitem(this)'>"));
    return myCurrentRow;
}

function InputUserID(obj)
{
    var userID = $("#UserID").val();
    if(userID == null||userID.length == 0)
        return;
    $.post("Ajax/PersonalMenu/Get_Name_By_CheckInId_Ajax.jsp", {"UserID":userID}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $("#UserName").val(data.split("$")[1]);
            changeUserName(data.split("$")[1]);
        }
    });
}

$(function()
{
    $('#UserName').change(function()
    {
        var userName = GetSelectedContent("UserName");
        if(userName.indexOf("请选择") >= 0)
        {
            $("#UserID").val("");
            return;
        }
        $.post("Ajax/PersonalMenu/Get_CheckInId_By_Name_Ajax.jsp", {"User_Name":userName}, function(data, textStatus)
        {
            if (CheckAjaxResult(textStatus, data))
                $("#UserID").val(data.split('$')[1]);
        });
    });
});
