/**
 * Created by Lemod on 2016/12/9.
 */

$(document).ready(function () {

    global_Object.initDomEvent();

    $.post(contextPath+"/paas/queryMachineList",{},function(data){})

})


var global_Object = {
    url: contextPath + "/paas/queryMachineList",
    type: "最近一小时",
    tableData: Object,
    ipAddress: "",
    time: "",

    initDomEvent: function () {

        $("#time1").on("click", function () {
            global_Object.time = "";
            global_Object.url = contextPath + "/paas/queryMachineList";
            global_Object.type = "最近一小时";
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });
    },

    queryTableData: function () {
        $.post(global_Object.url, {ipAddress: global_Object.ipAddress, time: global_Object.time}, function (data) {

            global_Object.tableData = data;

            if (global_Object.tableData != null) {
                global_Object.setTable();
            } else {
                $("#fTable tbody").html('<tr class="odd"><td valign="top" colspan="12" class="dataTables_empty">表中数据为空</td></tr>');
            }
        })
    },
    setTable: function () {
        var alltr = function (data) {
            var tr = '<tr hostName="'+data.getHostName()+'">';

            tr += '<td>' + data.min + 'ms</td>';
            tr += '<td>' + data.max + 'ms</td>';
            tr += '<td>' + data.tps + '</td>';
            tr += '<td><a onclick="global_Object.openPostFalse(this)" href="javascript:void(0)">' + data.failCount + '次</a></td>';
            tr += '<td>' + data.failPercent + '%</td>';
            tr += '<td>' + data.std + 'ms</td>';
            tr += '<td><i class="fa  fa-bar-chart-o cp" onclick="global_Object.queryPic(this)"></i></td>';
            return tr;
        };
        //console.log(global_Object.tableData)
        var html = [];
        $.each(global_Object.tableData, function (i, v) {
            html.push(alltr(v, "transactionTypeName"));
            var tableHtml = '<tr class="" style="display: none"><td colspan="12"><div class="ml15 mr15"> <table class="table table-head  table-condensed flip-content"> <thead class="flip-content ">';
            tableHtml += '<tr>';
            tableHtml += '<th class="">服务器地址</th>';
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
            tableHtml += ' <th class="numeric ">显示图表</th>';
            tableHtml += '</tr></thead><tbody>';
            if (v.transactionStatisticDataDetails != null && v.transactionStatisticDataDetails.length > 0) {
                $.each(v.transactionStatisticDataDetails, function (i2, v2) {
                    tableHtml += alltr(v2, "serverIpAddress");
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
}