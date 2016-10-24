/**
 * Created by Admin on 2016/10/21.
 */

$(document).ready(function () {

    global_Object.initDomEvent();
    //$.post("/paas/qeryAllDomain", {}, function (data) {
    //    $("#flname").html(data[0] + ' <i class="fa  fa-caret-down"></i>');
    //    global_Object.flname = data[0];
        global_Object.queryTableData();
    //    var li = [];
    //    $.each(data, function (i, v) {
    //        var option = '<li role="presentation"><a role="menuitem" tabindex="-1">' + v + '</a></li>';
    //        li.push(option);
    //    });
    //    $("#fl").html(li.join(""));
        $("#serverIpAddresss2 a").on("click", function () {
            $("#serverIpAddresss").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
            global_Object.serverIpAddresss2 = $(this).text();
            global_Object.queryTableData();
        });
    //
    //
    //});
});
var global_Object = {
    tableDataOld: [],
    tableData: [],
    serverIpAddresss: "",
    url: "/paas/queryTransactionTypeList",
    totalSize: 0,
    type:$("#type").val(),
    time:$("#time").val(),
    serverAppName:$("#serverAppName").val(),
    transactionTypeName:$("#transactionTypeName").val(),
    initDomEvent: function () {
        //$("#time1").on("click", function () {
        //    global_Object.url = "/paas/queryTransactionTypeList";
        //    global_Object.type="当前一小时";
        //    $(this).addClass("active").siblings().removeClass("active");
        //    global_Object.queryTableData();
        //});
        //$("#time2").on("click", function () {
        //    global_Object.url = "/paas/queryTransactionTypeList";
        //    global_Object.type="当天";
        //    $(this).addClass("active").siblings().removeClass("active");
        //    global_Object.queryTableData();
        //});
        //$("#time3").on("click", function () {
        //    global_Object.url = "/paas/queryTransactionTypeList";
        //
        //    $(this).addClass("active").siblings().removeClass("active");
        //    //global_Object.queryTableData();
        //});
        //$("#time3v li").on("click", function () {
        //    global_Object.time =$(this).text(); //$(this).text().split('-')[0];
        //    global_Object.type="指定小时";
        //    $("#time3").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
        //    global_Object.queryTableData();
        //});
        //$("#picEdit").on("show.bs.modal", function () {
        //    global_Object.queryPic();
        //});

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
        $.post(global_Object.url, {flname: "test1"}, function (data) {
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
                tr = '<tr><td><i class="fa  icon cp fa-chevron-down"></i><a onclick="global_Object.openPostWindow(this)" href="javascript:void(0)" data-transactionyypename="'+data.transactionTypeName+'">' + data.transactionTypeName + '</a></td>';
            }
            else if (type == "serverIpAddress") {
                tr = '<tr><td><a onclick="global_Object.openPostWindow(this)" href="javascript:void(0)" data-transactionyypename="'+data.transactionTypeName+'" data-serveripaddress="'+data.serverIpAddress+'">' + data.serverIpAddress + '</a></td>';
            }

            tr += '<td>' + data.totalCount + '次</td>';
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
            html.push(alltr(v, "transactionTypeName"));
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
    openPostWindow:function(obj){
        var url ="/paas/serversysrealtime";
        var datas={"transactionTypeName":$(obj).data("transactionyypename"),"serverIpAddress":$(obj).data("serveripaddress")==undefined?"":$(obj).data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,"time":global_Object.time};
        //console.log(datas);
        //alert($(obj).data("transactionyypename"))
        JqCommon.openPostWindow(url,datas);
    }

}
