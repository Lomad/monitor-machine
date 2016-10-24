/**
 * Created by Admin on 2016/10/21.
 */

$(document).ready(function () {

    global_Object.initDomEvent();

    //var data=[];
    //for(var i=0;i<20;i++){
    //    global_Object.tableData.push(json);
    //}
    $.post("/paas/qeryAllDomain", {}, function (data) {
        $("#flname").html(data[0] + ' <i class="fa  fa-caret-down"></i>');
        global_Object.flname = data[0];
        global_Object.queryTableData();
        var li = [];
        $.each(data, function (i, v) {
            var option = '<li role="presentation"><a role="menuitem" tabindex="-1">' + v + '</a></li>';
            li.push(option);
        });
        $("#fl").html(li.join(""));
        $("#fl a").on("click", function () {
            $("#flname").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
            global_Object.flname = $(this).text();
            global_Object.queryTableData();
        });


    });
});
var global_Object = {
    tableDataOld: [],
    tableData: [],
    flname: "",
    url: "/paas/queryTransactionTypeList",
    totalSize: 0,
    initDomEvent: function () {


        $("#time1").on("click", function () {
            global_Object.url = "/paas/queryTransactionTypeList"
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });
        $("#time2").on("click", function () {
            global_Object.url = "/paas/queryTransactionTypeList"
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });
        $("#time3").on("click", function () {
            global_Object.url = "/paas/queryTransactionTypeList"
            $(this).addClass("active").siblings().removeClass("active");
            //global_Object.queryTableData();
        });
        $("#time3v li").on("click", function () {
            global_Object.time = $(this).text().split('-')[0];
            $("#time3").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
            global_Object.queryTableData();
        });
        $("#picEdit").on("show.bs.modal", function () {
            global_Object.queryPic();
        });
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
        $.post(global_Object.url, {flname: global_Object.flname}, function (data) {
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
            //$.each(global_Object.tableData,function(i,v) {
            //    var a = v[id];
            //    $.each(tableData,function(i2,v2){
            //        var b =v2[id];
            //        if(a)
            //    });
            //});
            //冒泡排序
            var array = global_Object.tableData;
            var temp =0;
            for (var i = 0; i < array.length; i++)
            {
                for (var j = 0; j < array.length - i; j++)
                {
                    if (array[j] > array[j + 1])
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
            var array =global_Object.tableData;
            //var array = [3,5,1,6];
            var temp =0;
            for (var i = 0; i < array.length; i++)
            {
                for (var j = 0; j < array.length - i; j++)
                {
                    if (array[j] < array[j + 1])
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
            var tr = "";
            if (type == "transactionTypeName") {
                tr = '<tr><td><i class="fa  icon cp fa-chevron-down"></i>' + data.transactionTypeName + '</td>';
            }
            else if (type == "serverIpAddress") {
                tr = '<tr><td>' + data.serverIpAddress + '</td>';
            }

            tr += '<td>' + data.totalCount + '</td>';
            tr += '<td>' + data.avg + '</td>';
            tr += '<td>' + data.line99Value + '</td>';
            tr += '<td>' + data.line95Value + '</td>';
            tr += '<td>' + data.min + '</td>';
            tr += '<td>' + data.max + '</td>';
            tr += '<td>' + data.tps + '</td>';
            tr += '<td>' + data.failCount + '</td>';
            tr += '<td>' + data.failPercent*100 + '%</td>';
            tr += '<td>' + data.std + '</td>';
            tr += '<td><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td>';
            return tr;
        };
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
    },
    //queryTableDataLocal: function () {
    //},

    queryPic: function () {
        $("#echart").css("width", $("#picEdit").width() * 0.6 - 30);
        var option = {
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']

                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '直接访问',
                    type: 'bar',
                    barWidth: '60',
                    data: [10, 52, 200, 334, 390, 330, 220]
                }
            ]
        };
        var myChart = echarts.init(document.getElementById("echart"));
        myChart.setOption(option);
    }
}