/**
 * Created by Admin on 2016/10/21.
 */

$(document).ready(function () {

    global_Object.initDomEvent();
    if(global_Object.type=="最近一小时"){
        global_Object.url="/paas/queryLastHourTransactionTypeReportByClient"
    }
    else if(global_Object.type=="当天"){
        global_Object.url="/paas/queryTodayTransactionTypeReportByClient"
    }
    else if(global_Object.type=="指定小时"){
        global_Object.url="/paas/queryLastHourTransactionTypeReportByClient"
    }
    $.post("/paas/getAllServerIpAddress", {serverAppName:global_Object.serverAppName}, function (data) {
//alert( $("#serverIpAddresshidden").val());
        if(global_Object.serverIpAddress==""){
            $("#serverIpAddresss").html("所有主机" + ' <i class="fa  fa-caret-down"></i>');
        }
        else {
            $("#serverIpAddresss").html(global_Object.serverIpAddress + ' <i class="fa  fa-caret-down"></i>');
        }
        //global_Object.serverIpAddress = $();
        global_Object.queryTableData();
        var li = ['<li role="presentation"><a role="menuitem" tabindex="-1">所有主机</a></li>'];
        $.each(data, function (i, v) {
            var option = '<li role="presentation"><a role="menuitem" tabindex="-1">' + v + '</a></li>';
            li.push(option);
        });
        $("#serverIpAddresss2").html(li.join(""));
        $("#serverIpAddresss2 a").on("click", function () {
            $("#serverIpAddresss").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
            global_Object.serverIpAddress = $(this).text();
            $("#serverIpAddresss").html(global_Object.serverIpAddress + ' <i class="fa  fa-caret-down"></i>');
            if($(this).text() =="所有主机"){
                global_Object.serverIpAddress = "";
            }

            //global_Object.serverIpAddress = $(this).text();
            global_Object.queryTableData();
        });
    });
});
var global_Object = {
    tableDataOld: [],
    tableData: [],
    serverIpAddress: $("#serverIpAddresshidden").val(),
    url: "/paas/queryLastHourTransactionTypeReportByClient",
    totalSize: 0,
    type:$("#type").val(),
    time:$("#time").val(),
    serverAppName:$("#serverAppName").val(),
    transactionTypeName:$("#transactionTypeName").val(),
    initDomEvent: function () {

        $("#querybtn").on("click",function(){

            global_Object.setTableData("search",this);
        });
        $("#fTable .sorting").on("click", function () {
            if ($(this).hasClass("desc")) {
                $(this).addClass("asc").removeClass("desc");
                global_Object.setTableData("asc",this);
            }
            else {
                $(this).addClass("desc").removeClass("asc");
                global_Object.setTableData("desc",this);
            }

        });
    },
    queryTableData: function () {
        $.post(global_Object.url, {serverAppName: global_Object.serverAppName,transactionTypeName:global_Object.transactionTypeName,serverIpAddress:global_Object.serverIpAddress}, function (data) {
            console.log(data);
            global_Object.tableDataOld =data.transactionStatisticDatas;
            global_Object.tableData = data.transactionStatisticDatas;
            global_Object.totalSize = data.totalSize;
            global_Object.setTable();
        });
    },
    setTableData:function(type,obj){
        if(type =="search"){
            var tableData=[];
            $.each(global_Object.tableDataOld,function(i,v){
                if(v.transactionTypeName.indexOf($.trim($("#keyword").val()))>-1){
                    tableData.push(v);
                }
            });
            global_Object.tableData=tableData;
            //console.log(global_Object.tableData);
        }
        else if(type=="asc"){
            var id= $(obj).data("id");
            //冒泡排序
            var array = global_Object.tableData;
            var temp =0;
            for (var i = 0; i < array.length; i++)
            {
                for (var j = 0; j < array.length - i-1; j++)
                {
                    if (array[j][id] > array[j + 1][id])
                    {
                        temp = array[j + 1];
                        array[j + 1] = array[j];
                        array[j] = temp;
                    }
                }
            }
            global_Object.tableData=array;
            //console.log(global_Object.tableData);
        }
        else if(type=="desc"){
            var id= $(obj).data("id");
            var array =global_Object.tableData;
            //var array = [3,5,1,6];
            var temp =0;
            for (var i = 0; i < array.length; i++)
            {
                for (var j = 0; j < array.length - i-1; j++)
                {
                    if (array[j][id] < array[j + 1][id])
                    {
                        temp = array[j + 1];
                        array[j + 1] = array[j];
                        array[j] = temp;
                    }
                }
            }
            global_Object.tableData=array;
            //console.log(global_Object.tableData);
        }
        global_Object.setTable();
    },
    setTable: function () {
        var alltr = function (data, type) {
            var tr = '<tr  data-clientappname="'+data.clientAppName+'" data-clientipaddress="'+data.clientIpAddress+'">';
            if (type == "clientAppName") {
                tr += '<td><i class="fa  icon cp fa-chevron-down"></i> ' + data.clientAppName + '</td>';
            }
            else if (type == "clientIpAddress") {
                tr += '<td>' + data.clientIpAddress + '</td>';
            }

            tr += '<td><a onclick="global_Object.openPostTotalCount(this)" href="javascript:void(0)">' + data.totalCount + '次</a></td>';
            tr += '<td>' + data.avg + 'ms</td>';
            tr += '<td>' + data.line99Value + 'ms</td>';
            tr += '<td>' + data.line95Value + 'ms</td>';
            tr += '<td>' + data.min + 'ms</td>';
            tr += '<td>' + data.max + 'ms</td>';
            tr += '<td>' + data.tps + '</td>';
            tr += '<td>' + data.failCount + '次</td>';
            tr += '<td>' + data.failPercent*100 + '%</td>';
            tr += '<td>' + data.std + 'ms</td>';
            //tr += '<td><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td>';
            return tr;
        };
        var html = [];
        $.each(global_Object.tableData, function (i, v) {
            html.push(alltr(v, "clientAppName"));
            var tableHtml = '<tr class="" style="display: none"><td colspan="12"><div class="ml15 mr15"> <table class="table table-head  table-condensed flip-content"> <thead class="flip-content ">';
            tableHtml += '<tr>';
            tableHtml += '<th class="">客户端地址</th>';
            tableHtml += ' <th class="numeric ">调用次数</th>';
            tableHtml += ' <th class="numeric ">平均耗时</th>';
            tableHtml += ' <th class="numeric ">99%</th>';
            tableHtml += ' <th class="numeric ">95%</th>';
            tableHtml += ' <th class="numeric ">最短耗时</th>';
            tableHtml += ' <th class="numeric ">最长耗时</th>';
            tableHtml += ' <th class="numeric ">吞吐量</th>';
            tableHtml += ' <th class="numeric ">失败次数</th>';
            tableHtml += ' <th class="numeric ">失败率</th>';
            tableHtml += ' <th class="numeric ">方差</th>';
            tableHtml += '</tr></thead><tbody>';
            if (v.transactionStatisticDataDetails != null && v.transactionStatisticDataDetails.length > 0) {
                $.each(v.transactionStatisticDataDetails, function (i2, v2) {
                    tableHtml += alltr(v2, "clientIpAddress");
                });
            }
            tableHtml += ' </tbody></table></div></td></tr>';
            html.push(tableHtml);
        });
        //console.log(html);
        $("#fTable tbody").html(html.join(""));
        $("#fTable .icon").on("click", function () {
            var tr = $(this).parents("tr");
            if ($(this).hasClass("fa-chevron-down")) {
                $(tr).next("tr").fadeIn();
                $(this).addClass("fa-chevron-up").removeClass("fa-chevron-down");
            }
            else {
                $(tr).next("tr").fadeOut();
                $(this).addClass("fa-chevron-down").removeClass("fa-chevron-up");
            }
        });
        $("#form").submit({ serverAppName: "123", age: "年龄" });
    },
    openPostWindow:function(obj){
        var url ="/paas/serversysrealtime";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactionyypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,"time":global_Object.time};
        //console.log(datas);
        //alert($(obj).data("transactionyypename"))
        JqCommon.openPostWindow(url,datas);
    },
    openPostTotalCount:function(obj){
        var url ="/paas/serverdetailedrealtime";
        var datas={"transactionTypeName":global_Object.transactionTypeName,"serverIpAddress":global_Object.serverIpAddress==""?"所有主机":global_Object.serverIpAddress,"serverAppName":global_Object.serverAppName,"type":global_Object.type,"time":global_Object.time,"clientAppName":$(obj).parents("tr").data("clientappname"),"clientIpAddress":$(obj).parents("tr").data("clientipaddress")==undefined?"":$(obj).parents("tr").data("clientipaddress")};
        JqCommon.openPostWindow(url,datas);
    }
}
