/**
 * 
 */
function ModifyUser(obj)
{
    $.post("Ajax/UpdateUserAjax.jsp", {"UserId":obj.name}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            var data_list = data.split("$");
            $('#check_in_id').val(data_list[1]);
            var index = 0;
            $("#workGroup option").each(function()
            {
                if($(this).text()==data_list[2])
                {
                	workGroup.options[index].selected = true;
                }
                index++;
            }); 
            $('#name').val(data_list[3]);
            $('#department').val(data_list[4]);
            $('#password').val(data_list[5]);
            var perList = data_list[6].split("#");
            $("input:checkbox[name='AddPermission']:checkbox").each(function(i)
            {
                if(contains(perList, $(this).val()))
                    $(this).attr("checked", true);
            });
            //location.reload();
        }
    });
}

function contains(a, obj)
{
    for (var i = 0; i < a.length; i++)
    {
        if (a[i].trim() == obj.trim())
        {
            return true;
        }
    }
    return false;
}

function AddUser(obj)
{
    var strPermission = "";
    $("input:checkbox[name=AddPermission]:checked").each(function(i)
    {
        strPermission += $(this).val() + "#";
    });
    if(strPermission.length <= 0)
    {
        alert("请选择只是一种用户权限!");
        return;
    }
    if($('#check_in_id').val().length <= 0||$('#name').val().length <= 0||
            $('#department').val().length <= 0||$('#password').val().length <= 0)
    {
        alert("请先完善用户信息!");
        return;
    }
    $.post("Ajax/AddUserAjax.jsp", {"check_in_id":$('#check_in_id').val(), "work_group":GetSelectedContent('workGroup'), "name":$('#name').val(),
                                        "department":$('#department').val(), "password":$('#password').val(), "Permission":strPermission}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            location.reload();
        }
    });
}

function DisplayUserTable(beginPage)
{
    var $displayUser = $("#display_user");
    $.post("Ajax/Query_UserManagement_Ajax.jsp", {"BeginPage":beginPage}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $displayUser.empty();
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
                $displayUser.append(tr);
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    if(data_list[iRow*iColCount + 6].indexOf("root")==0)
                        continue;
                    var tr = $("<tr></tr>");
                    for (var iCol = 0; iCol < iColCount; iCol++)
                    {
                        var td = $("<td></td>");
                        if(8 == iCol)
                        {
                            td.append("<input type='button' value='修改' name='" + data_list[iRow*iColCount + iCol + 3] + "' onclick='ModifyUser(this)'>");
                            td.append("<input type='button' value='删除' name='" + data_list[iRow*iColCount + iCol + 3] + "' onclick='DeleteUser(this)'>");
                        }
                        else
                            td.append(data_list[iRow*iColCount + iCol + 3]);
                        tr.append(td);
                    }
                    $displayUser.append(tr);
                }
            }
        }
    });
}

function DeleteUser(obj)
{
    $.post("Ajax/DeleteUserAjax.jsp", {"id":obj.name}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            location.reload();
        }
    });
}

