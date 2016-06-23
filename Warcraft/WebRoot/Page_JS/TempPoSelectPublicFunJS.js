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

function CheckQTY(obj)
{
    if (parseInt(obj.value)>parseInt(obj.name))
    {
        alert("入库数量不能大于生产单量!");
        obj.value = 0;
    }
}
