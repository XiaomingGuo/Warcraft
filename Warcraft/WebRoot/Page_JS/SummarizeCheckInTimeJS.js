/**
 * 
 */
/*$(function()
{
    $('#UserName').change(function()
    {
        changeUserName();
    });
});*/

function changeUserName()
{
    var $displayOrder = $("#display_po");
    var user_name = $.trim($("#UserName").val());
    if(user_name.length == 0)
    {
        alert("你就不能起个靠谱的名字吗?");
        return;
    }
    var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
    $.post("Ajax/Query_Summarize_Check_In_Data_Ajax.jsp", {"user_name":user_name, "queryDate": addDate}, function(data, textStatus)
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
                        var td = $("<td></td>");
                        td.append(data_list[iRow*iColCount + iCol + 2]);
                        tr.append(td);
                    }
                    $displayOrder.append(tr);
                }
            }
        }
    });
}


