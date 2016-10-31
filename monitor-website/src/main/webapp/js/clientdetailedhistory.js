/**
 * Created by InnerPeace on 2016/10/31.
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
    global_Object.value = $("#value").val();
    global_Object.initDomEvent();

    fTable = $("#fTable").winningTable({
        "pageLength": 10,
        "processing": false,
        "ordering": true, //排序功能
        "order": [[ 6, "desc" ]],
        "columns": [

            {"title": "服务名称", "data": "transactionTypeName", "orderable": false,
                "render":function(data, type, full, meta){
                    json.push(full.children);
                    var html = '<i class="fa  icon cp fa-chevron-down"  onclick="global_Object.bzClick(this,'+index+')"></i> '+data;
                    index++;
                    return html;
                }
            },
            {"title": "服务器IP", "data": "serverIpAddress", "orderable": false},
            {"title": "消费方系统名称", "data": "clientAppName", "orderable": false},
            {"title": "消费方IP地址", "data": "clientIpAddress", "orderable": false},
            {"title": "耗时", "data": "useTime"},
            {"title": "状态", "data": "status", "orderable": false},
            {"title": "时间", "data": "startTime"},//,sClass:"text-center"
            {
                "title": "详情", "data": "startTime", "orderable": false,
                "render": function (data, type, full, meta) {
                    json2.push(full.datas);
                    var html = '<i class="fa  icon cp fa-exchange"  onclick=global_Object.detail(this,'+JSON.stringify(full.datas)+')></i> ';
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
    fTable.queryDataInPage(contextPath+"/paas/queryLastHourTransactionMessageList", {serverAppName:global_Object.serverAppName,transactionTypeName:global_Object.transactionTypeName,serverIpAddress:global_Object.serverIpAddress,clientAppName:global_Object.clientAppName,clientIpAddress:global_Object.clientIpAddress,status:global_Object.status});
});
var global_Object = {
    serverAppName:$("serverAppName").val(),
    transactionTypeName:$("transactionTypeName").val(),
    serverIpAddress:$("serverIpAddress").val(),
    clientAppName:$("clientAppName").val(),
    clientIpAddress:$("clientIpAddress").val(),
    status:$("status").val(),
    type:"",
    value:$("value").val(),
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
        fTable.queryDataInPage(contextPath+"/paas/queryLastHourTransactionMessageList", {serverAppName:global_Object.serverAppName,transactionTypeName:global_Object.transactionTypeName,serverIpAddress:global_Object.serverIpAddress,clientAppName:global_Object.clientAppName,clientIpAddress:global_Object.clientIpAddress,status:global_Object.status});
    },
    bzClick:function(obj,index){
        console.log(json[index]);
        if ($(obj).hasClass("fa-chevron-down")) {
            var tableHtml = '<tr class="" style="display: none"><td colspan="12"><div class="ml15 mr15"> <table class="table table-head  table-condensed flip-content"> <thead class="flip-content ">';
            tableHtml += '<tr>';
            tableHtml += '<th class="">序号</th>';
            tableHtml += '<th class="">服务步骤</th>';
            tableHtml += ' <th class=" ">耗时</th>';
            tableHtml += ' <th class=" ">状态</th>';
            tableHtml += ' <th class=" ">开始时间</th>';
            tableHtml += ' <th class=" ">详情</th>';

            tableHtml += '</tr></thead><tbody>';
            if (json[index] != null && json[index].length > 0) {
                $.each(json[index],function (i, v) {
                    tableHtml += '<tr><td>'+(i+1)+'</td><td>'+ v.transactionName+'</td><td>'+v.useTime+'</td><td>'+v.status+'</td><td>'+v.startTime+'</td><td>'+'<i class="fa  icon cp fa-exchange"  onclick=global_Object.detail(this,'+JSON.stringify(v.datas)+')></i> '+'</td></tr>';
                });
            }
            $(obj).parents("tr").after(tableHtml);
            $(obj).parents("tr").next("tr").fadeIn();
            $(obj).addClass("fa-chevron-up").removeClass("fa-chevron-down");
        }
        else{
            $(obj).parents("tr").next("tr").fadeOut();
            $(obj).parents("tr").next("tr").remove();
            $(obj).addClass("fa-chevron-down").removeClass("fa-chevron-up");
        }
    },
    detail: function (obj,json) {
        $("#xqEdit").modal("show");
        var html ="";
        for(var key in json){
            html +="<tr><td>"+key+"</td><td>"+json[key]+"</td></tr>";
            //name.push(key);
            //json.push(data.durations[key]);
        }
        $("#xqTable tbody").html(html);
    }
}