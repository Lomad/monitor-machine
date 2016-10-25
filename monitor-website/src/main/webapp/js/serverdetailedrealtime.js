/**
 * Created by Admin on 2016/10/25.
 */
var fTable
$(document).ready(function () {
    fTable = $("#fTable").winningTable({
        "pageLength": 13,
        "processing": false,
        "columns": [

            {"title": "时间", "data": "startTime"},//,sClass:"text-center"
            {"title": "服务名称", "data": "transactionTypeName"},

            {"title": "服务器IP", "data": "serverIpAddress"},
            {"title": "消费方系统名称", "data": "serverIpAddress"},
            {
                "title": "记录状态", "data": "jlzt",
                "render": function (data, type, full, meta) {
                    return data == "1" ? "有效" : "作废";
                }
            },
            {
                "title": "操作", "data": "userid",
                "render": function (data, type, full, meta) {
                    //console.log(full);
                    var html = "";
                    html += '<i data-id="' + data + '" class="fa fa-edit cp" onclick="global_Object.editUser(this)" title="修改"></i>&nbsp;&nbsp;';
                    html += '<i data-id="' + data + '" class="fa  fa-exchange cp" onclick="global_Object.systemUser(this)" title="系统映射"></i>&nbsp;&nbsp;';
                    //html +='<i data-id="'+data+'" class="fa fa-lock cp" onclick="global_Object.Lock(this)" title="上锁"></i>&nbsp;&nbsp;';
                    if (full.jlzt == "0") {
                        html += '<i data-id="' + data + '" onclick="global_Object.enableUser(this)" class="fa fa-link cp" title="启用"></i>&nbsp;&nbsp;'
                    }
                    else {
                        html += '<i data-id="' + data + '" onclick="global_Object.disableUser(this)" class="fa fa-unlink cp" title="禁用"></i>&nbsp;&nbsp;'
                    }
                    html += '<i data-id="' + data + '" class="fa fa-reply cp" onclick="global_Object.updatePassword(this)" title="重置密码"></i>&nbsp;&nbsp;';
                    if (full.loginnum > 4) {
                        html += '<i data-id="' + data + '" class="fa fa-unlock cp" onclick="global_Object.unLock(this)" title="解锁"></i>';
                    }
                    return html;
                }
            }
        ],
        "drawCallback": function (a) {
        },
        "autoWidth": false,
        "responsive": true,
        "width": "100%"
    });
    fTable.queryDataInPage("/paas/queryLastHourTransactionMessageList", {orgid: "00000000"});
});