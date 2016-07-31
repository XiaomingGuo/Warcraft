/**
 * 
 */
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
            {
                $("#UserID").val(data.split('$')[1]);
            }
        });
    });
});

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

