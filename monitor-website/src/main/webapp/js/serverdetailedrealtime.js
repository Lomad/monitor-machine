/**
 * Created by Admin on 2016/10/25.
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
    if (global_Object.type == "最近一小时") {
        global_Object.url = contextPath+"/paas/queryLastHourTransactionMessageList"
    }
    else if (global_Object.type == "当天") {
        global_Object.url = contextPath+"/paas/queryTodayTransactionMessageList"
    }
    else if (global_Object.type == "指定小时") {
        global_Object.url = contextPath+"/paas/queryHourTransactionMessageList"
    }
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
                    console.log(full.messageId);
                    var id = JSON.stringify(full.messageId);
                    //var html = '<i class="fa  icon cp fa-chevron-down"  onclick="global_Object.bzClick(this,'+index+','+ messageId +')"></i> '+data;
                    var html = '<i class="fa  icon cp fa-chevron-down"  onclick=global_Object.bzClick(this,'+index+ ','+id+ ')></i> '+data;
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
                    console.log(full);
                    json2.push(full.datas);
                    var messageId = JSON.stringify(full.messageId);
                    var html = '<i class="fa  icon cp fa-bar-chart-o"  onclick=global_Object.detail(this,'+ messageId +',-1)></i> ';
                    //var html = '<i class="fa  icon cp fa-bar-chart-o"  onclick=global_Object.detail(this,'+ JSON.stringify(full.datas) +')></i> ';
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
    var data = {serverAppName:global_Object.serverAppName,transactionTypeName:global_Object.transactionTypeName,serverIpAddress:global_Object.serverIpAddress,clientAppName:global_Object.clientAppName,clientIpAddress:global_Object.clientIpAddress,status:global_Object.status,time:global_Object.time};
    fTable.queryDataInPage(global_Object.url,data);
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
    url:contextPath+"/paas/queryLastHourTransactionMessageList",
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
        var datas = {serverAppName:global_Object.serverAppName,transactionTypeName:global_Object.transactionTypeName,serverIpAddress:global_Object.serverIpAddress,clientAppName:global_Object.clientAppName,clientIpAddress:global_Object.clientIpAddress,status:global_Object.status,time:global_Object.time};
        fTable.queryDataInPage(global_Object.url, datas);
    },
    bzClick:function(obj,index,messageId){
//console.log(json[index]);
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
                console.log(v);
                tableHtml += '<tr><td>'+(i+1)+'</td><td>'+ v.transactionName+'</td><td>'+v.useTime+'</td><td>'+v.status+'</td><td>'+v.startTime+'</td><td>'
                             //+'<i class="fa  icon cp fa-bar-chart-o"  onclick=global_Object.detail(this,'+JSON.stringify(v.datas)+')></i> '+'</td></tr>';
                             +'<i class="fa  icon cp fa-bar-chart-o"  onclick=global_Object.detail(this,"'+messageId+'",'+i+')></i> '+'</td></tr>';
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
    //detail: function (obj,json) {
    detail: function (obj, messageId,idx) {
        $("#xqEdit").modal("show");
        var url = "/paas/queryTransactionMessageListDetail";
        var datas = { serverAppName:global_Object.serverAppName,messageId: messageId, index:idx};
        $.post(url, datas, function(result) {
            var html = "";
            var json = result.data;
            //console.log(result);
            //console.log(json);
            var lt = new RegExp("<","g"); // replace(lt, "&lt;")
            var fr = new RegExp("</","g"); // replace(fr, "&lt;&frasl;")
            var gt = new RegExp(">","g"); // replace(gt, "&gt;")
            for (var key in json) {
                var temp = json[key];

                var value;
                if(temp && typeof temp == "string") {
                    console.log(temp);
                    value = temp.trim().replace(/[\r\n]/g, "").replace(fr, "&lt;&frasl;").replace(gt, "&gt;").replace(lt, "&lt;");
                }else{
                    console.log(temp);
                    value = temp;
                    //var jstr = JSON.stringify(temp);
                    //value = jstr;
                }
                html += '<tr><td>' + key + '</td><td>' + value + '</td></tr>';
                //name.push(key);
                //json.push(data.durations[key]);
            }
            html = html+"";
            $("#xqTable tbody").html(html);
        });
    }
}