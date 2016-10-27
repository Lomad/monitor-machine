/**
 * Created by InnerPeace on 2016/10/26.
 */

var fTable;
var index=0;
var json=[];
var index2=0;
var json2=[];
$(document).ready(function () {
    global_Object.serverAppName=$("#serverAppName").val();
    global_Object.transactionTypeName=$("#transactionTypeName").val();
    global_Object.serverIpAddress=$("#serverIpAddress").val();
    global_Object.clientAppName=$("#clientAppName").val();
    global_Object.clientIpAddress=$("#clientIpAddress").val();
    global_Object.status=$("#status").val();
    global_Object.type = $("#type").val();
    global_Object.time = $("#time").val();
    global_Object.initDomEvent();
    fTable = $("#fTable").winningTable({
        "pageLength": 10,
        "processing": false,
        "ordering": true, //排序功能
        "order": [[ 6, "desc" ]],
        "columns": [
            {"title": "时间", "data": "startTime"},
            {
                "title": "服务方系统名称", "data": "transactionTypeName", "orderable": false,
                "render":function(data, type, full, meta)
                {
                    json.push(full.children);
                    //var html = '<i class="fa  icon cp fa-chevron-down"  onclick="global_Object.bzClick(this,'+index+')"></i> '+data;
                    var html = '' + data;
                    index++;
                    return html;
                }
            },
            {"title": "服务方IP地址", "data": "serverIpAddress", "orderable": false},
            {"title": "消费方系统名称", "data": "clientAppName", "orderable": false},
            {"title": "消费方IP地址", "data": "clientIpAddress", "orderable": false},
            {"title": "耗时", "data": "useTime"},
            {"title": "状态", "data": "status","orderable": false},
            {
                "title": "详细参数", "data": "startTime", "orderable": false,
                "render": function (data, type, full, meta) {
                    json2.push(full.datas);
                    var html = '<i class="fa icon cp fa-exchange"  onclick=global_Object.detail(this,'+JSON.stringify(full.datas)+')></i> ';
                    index2++;
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
    //console.log(global_Object)
    fTable.queryDataInPage("/paas/queryLastHourTransactionMessageList", {serverAppName:global_Object.serverAppName,transactionTypeName:global_Object.transactionTypeName,serverIpAddress:global_Object.serverIpAddress,clientAppName:global_Object.clientAppName,clientIpAddress:global_Object.clientIpAddress,status:global_Object.status});
});
var global_Object = {
    serverAppName:$("serverAppName").val(),
    transactionTypeName:$("transactionTypeName").val(),
    serverIpAddress:$("serverIpAddress").val(),
    clientAppName:$("clientAppName").val(),
    clientIpAddress:$("clientIpAddress").val(),
    status:$("status").val(),
    type:"",
    time:"",
    initDomEvent:function(){
        $("#statusselect a").on("click", function () {
            $("#statusvalue").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
            global_Object.status = $(this).text();
            if(global_Object.status=="请选择状态"){
                global_Object.status="";
            }
            global_Object.queryTableData();
        });
    },
    queryTableData:function(){
        index=0;
        json=[];
        index2=0;
        json2=[];
        fTable.queryDataInPage("/paas/queryLastHourTransactionMessageList", {serverAppName:global_Object.serverAppName,transactionTypeName:global_Object.transactionTypeName,serverIpAddress:global_Object.serverIpAddress,clientAppName:global_Object.clientAppName,clientIpAddress:global_Object.clientIpAddress,status:global_Object.status});
    },

    detail: function (obj,json) {
        $("#xqEdit").modal("show");
        //console.log(json);
        var html ="";
        for(var key in json){
            html +="<tr><td>"+key+"</td><td>"+json[key]+"</td></tr>";
            //name.push(key);
            //json.push(data.durations[key]);
        }
        $("#xqTable tbody").html(html);
    }
}