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

    /* 初始化右上角系统选择下拉框 */
    //$.post("/paas/qeryAllDomain", {}, function (data) {
    //    /* 动态生成的标签 */
    //    var li = [];
    //    $.each(data, function (i, v) {
    //        //<li role="presentation"><a role="menuitem" tabindex="-1">成功</a></li>
    //        var option = '<li role="presentation"><a role="menuitem" tabindex="-1"> ' + v + '</a></li>';
    //        li.push(option);
    //    });
    //    $("#systemselect").html(li.join(""));
    //
    //    /* 消费系统名称 过滤器: 特别注意：必须在同一个ajax里面注册其事件，意思是标签是动态生成的，其事件也必须注册在动态里面 */
    //    $("#systemselect a").on("click", function () {
    //        $("#systemvalue").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
    //        global_Object.clientAppName = $(this).text();
    //        if(global_Object.clientAppName =="请选择消费系统"){
    //            global_Object.clientAppName ="";
    //        }
    //        global_Object.queryTableData();
    //    });
    //});

    global_Object.initDomEvent();

    fTable = $("#fTable").winningTable({
        "pageLength": 10,
        "processing": false,
        "ordering": true, //排序功能
        "order": [[ 6, "desc" ]],
        "columns": [

            {
                "title": "服务名称", "data": "transactionTypeName", "orderable": false,
                "render":function(data, type, full, meta)
                {
                    json.push(full.children);
                    var html = '<i class="fa  icon cp fa-chevron-down"  onclick="global_Object.bzClick(this,'+index+')"></i> '+data;
                    //var html = '' + data;
                    index++;
                    return html;
                }
            },
            {"title": "服务地址", "data": "serverIpAddress", "orderable": false},
            {"title": "消费方名称", "data": "clientAppName", "orderable": false},
            {"title": "消费方地址", "data": "clientIpAddress" , "orderable": false},
            {"title": "耗时", "data": "useTime"},
            {"title": "状态", "data": "status","orderable": false},
            {"title": "时间", "data": "startTime"},
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

        /* 消费数据状态 过滤器 */
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
        var html ="";
        for(var key in json){
            html +="<tr><td>"+key+"</td><td>"+json[key]+"</td></tr>";
            //name.push(key);
            //json.push(data.durations[key]);
        }
        $("#xqTable tbody").html(html);
    },

    bzClick:function(obj,index){

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
}