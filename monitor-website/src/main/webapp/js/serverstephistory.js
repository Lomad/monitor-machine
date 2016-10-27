/**
 * Created by Evan on 2016/10/25.
 */

$(document).ready(function () {

    global_Object.initDomEvent();
    if (global_Object.type == "day") {
        global_Object.url = "/paas/queryLastHourTransactionNameReportByServer"
    }
    else if (global_Object.type == "week") {
        global_Object.url = "/paas/queryLastHourTransactionNameReportByServer"
    }
    else if (global_Object.type == "month") {
        global_Object.url = "/paas/queryLastHourTransactionNameReportByServer"
    }
    $.post("/paas/getAllServerIpAddress", {serverAppName: global_Object.serverAppName}, function (data) {
//alert( $("#serverIpAddresshidden").val());
        if (global_Object.serverIpAddress == "") {
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
            if ($(this).text() == "所有主机") {
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
    url: "/paas/queryLastHourTransactionNameReportByServer",
    totalSize: 0,
    type: $("#type").val(),
    value:$("#value").val(),
    serverAppName: $("#serverAppName").val(),
    transactionTypeName: $("#transactionTypeName").val(),
    initDomEvent: function () {

    },
    queryTableData: function () {
        $.post(global_Object.url, {
            serverAppName: global_Object.serverAppName,
            transactionTypeName: global_Object.transactionTypeName,
            serverIpAddress: global_Object.serverIpAddress
        }, function (data) {
            console.log(data);
            global_Object.tableDataOld = data.transactionStatisticDatas;
            global_Object.tableData = data.transactionStatisticDatas;
            global_Object.totalSize = data.totalSize;
            global_Object.setTable();
            global_Object.setPic();
        });
    },

    setTable: function () {
        var alltr = function (data, type, index) {
            var tr = '<tr>';
            tr += '<td>' + (index + 1) + '</td>';
            tr += '<td>' + data.transactionName + '</td>';

            tr += '<td>' + data.totalCount + '次</td>';
            tr += '<td>' + data.avg + 'ms</td>';
            tr += '<td>' + data.line99Value + 'ms</td>';
            tr += '<td>' + data.line95Value + 'ms</td>';
            tr += '<td>' + data.min + 'ms</td>';
            tr += '<td>' + data.max + 'ms</td>';
            tr += '<td>' + data.tps + '</td>';
            tr += '<td>' + data.failCount + '次</td>';
            tr += '<td>' + data.failPercent * 100 + '%</td>';
            tr += '<td>' + data.std + 'ms</td>';
            //tr += '<td><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td>';
            return tr;
        };
        var html = [];
        $.each(global_Object.tableData, function (i, v) {
            html.push(alltr(v, "transactionName", i));
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
        $("#form").submit({serverAppName: "123", age: "年龄"});
    },
    setPic: function () {
        console.log(global_Object.tableData);
        $("#echart").css("width", $("#fTable").width());
        var name=[];
        var json=[];

        $.each(global_Object.tableData,function(i,v){
            name.push(v.transactionName);
            json.push({value: v.avg,name: v.transactionName});
        });

        var option = {

            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: name,
                show:false
            },
            series : [
                {
                    name: '步骤平均耗时',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:json,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        var myChart = echarts.init(document.getElementById("echart"));
        myChart.setOption(option);
    }
}

