/**
 * 
 */
function change(obj)
{
    var iPermission = 0;
    $("input:checkbox[name=permission]:checked").each(function(i)
    {
        iPermission += Number($(this).val());
    });
    $.post("Ajax/UpdateUserAjax.jsp", {"Index":obj.name, "Permission":iPermission}, function(data, textStatus){
        if (CheckAjaxResult(textStatus, data))
        {
            location.reload();
        }
    });
}

function Add(obj)
{
    var iPermission = 0;
    $("input:checkbox[name=AddPermission]:checked").each(function(i)
    {
        iPermission += Number($(this).val());
    });
    //"ID", "考勤工号", "姓名", "创建时间", "部门", "密码", "用户权限", "操作"
    $.post("Ajax/AddUserAjax.jsp", {"name":$('#name').val(), "check_in_id":$('#check_in_id').val(), "password":$('#password').val(), "department":$('#department').val(), "Permission":iPermission}, function(data, textStatus){
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
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var td = $("<td></td>");
                        td.append(data_list[iRow*iColCount + iCol + 2]);
                        tr.append(td);
                    }
                    $displayUser.append(tr);
                }
            }
        }
    });
}
