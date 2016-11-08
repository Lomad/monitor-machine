/**
 * Created by Admin on 2016/10/21.
 */

$(document).ready(function () {

    global_Object.initDomEvent();

    //var data=[];
    //for(var i=0;i<20;i++){
    //    global_Object.tableData.push(json);
    //}
    $.post(contextPath+"/paas/qeryAllDomain", {}, function (data) {
        $("#flname").html(data[0] + ' <i class="fa  fa-caret-down"></i>');
        global_Object.flname = data[0];
        global_Object.queryTableData();
        var li = [];
        $.each(data, function (i, v) {
            var option = '<li class="cp" role="presentation"><a role="menuitem" tabindex="-1">' + v + '</a></li>';
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
    url: contextPath+"/paas/queryTransactionTypeList",
    totalSize: 0,
    type:"最近一小时",
    time:"",
    initDomEvent: function () {

        $("#time1").on("click", function () {
            global_Object.time="";
            global_Object.url = contextPath+"/paas/queryTransactionTypeList";
            global_Object.type="最近一小时";
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });
        $("#time2").on("click", function () {
            global_Object.time="";
            global_Object.url = contextPath+"/paas/queryTodayTransactionTypeReportByServer";
            global_Object.type="当天";
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });

        $("#time3v li").on("click", function () {
            global_Object.url = contextPath+"/paas/queryHourTransactionTypeReportByServer";
            $("#time3").addClass("active").siblings().removeClass("active");
            //global_Object.time =$(this).text(); //$(this).text().split('-')[0];
            global_Object.time=global_Object.getNowFormatDate()+" "+$(this).text().split('-')[0]+":00";
            global_Object.type="指定小时";
            $("#time3").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
            global_Object.queryTableData();
        });
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
        //console.log(global_Object.url+"---"+global_Object.flname+"---"+global_Object.time);
        $.post(global_Object.url, {flname: global_Object.flname,time:global_Object.time}, function (data) {
            //console.log(data);
            global_Object.tableDataOld =data.transactionStatisticDatas;
            global_Object.tableData = data.transactionStatisticDatas;
            global_Object.totalSize = data.totalSize;
            if(global_Object.totalSize>0){
                global_Object.setTable();
            }else{
                $("#fTable tbody").html('<tr class="odd"><td valign="top" colspan="13" class="dataTables_empty">表中数据为空</td></tr>');
            }
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
        }
        global_Object.setTable();
    },
    setTable: function () {
        var alltr = function (data, type) {
            var tr = '<tr data-transactiontypename="'+data.transactionTypeName+'" data-serveripaddress="'+data.serverIpAddress+'">';
            if (type == "transactionTypeName") {
                tr += '<td><i class="fa  icon cp fa-chevron-down"></i><a onclick="global_Object.openPostWindow(this)" href="javascript:void(0)">' + data.transactionTypeName + '</a></td>';
            }
            else if (type == "serverIpAddress") {
                tr += '<td><a onclick="global_Object.openPostWindow(this)" href="javascript:void(0)" >' + data.serverIpAddress + '</a></td>';
            }

            tr += '<td><a onclick="global_Object.openPostTotalCount(this)" href="javascript:void(0)">' + data.totalCount + '次</a></td>';
            tr += '<td><a onclick="global_Object.openPostAvg(this)" href="javascript:void(0)">' + data.avg + 'ms</a></td>';
            tr += '<td>' + data.line99Value + 'ms</td>';
            tr += '<td>' + data.line95Value + 'ms</td>';
            tr += '<td>' + data.min + 'ms</td>';
            tr += '<td>' + data.max + 'ms</td>';
            tr += '<td>' + data.tps + '</td>';
            tr += '<td><a onclick="global_Object.openPostFalse(this)" href="javascript:void(0)">' + data.failCount + '次</a></td>';
            tr += '<td>' + data.failPercent*100 + '%</td>';
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

    queryPic: function (obj) {
        $("#echart").css("width", $("#picEdit").width() * 0.6 - 30);
        $("#picEdit").modal("show");
        var url =""
        if(global_Object.type=="最近一小时"){
            url =contextPath+"/paas/queryLastHourTransactionTypeCallTimesReportByServer";
        }
        else if(global_Object.type=="当天"){
            url =contextPath+"/paas/queryTodayTransactionTypeCallTimesReportByServer";
        }
        else if(global_Object.type=="指定小时"){
            url =contextPath+"/paas/queryHourTransactionTypeCallTimesReportByServer";
        }

        //console.log(url);
        var datas = {serverAppName: global_Object.flname,transactionTypeName:$(obj).parents("tr").data("transactiontypename"),serverIpAddress:$(obj).parents("tr").data("serveripaddress"),hour:global_Object.time};
        //console.log(datas);
        $.post(url,datas, function (data) {
            var json=[];
            var name =[]
            for(var key in data.durations){
                name.push(key);
                json.push(data.durations[key]);
            }
            //console.log(name);console.log(json);
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
                        data: name

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
                        //barWidth: '60',
                        data: json
                    }
                ]
            };
            var myChart = echarts.init(document.getElementById("echart"));
            myChart.setOption(option);
        });

    },
    getNowFormatDate:function(){
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    },
    openPostWindow:function(obj){
        var url =contextPath+"/paas/serversysrealtime";
        //alert($(obj).parents("tr").data("transactiontypename"))
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,"time":global_Object.time};
        //console.log(datas);
        JqCommon.openPostWindow(url,datas);
    },
    openPostTotalCount:function(obj){
        var url =contextPath+"/paas/serverdetailedrealtime";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,"time":global_Object.time,"clientAppName":"","clientIpAddress":"","status":""};
        //console.log(datas);
        //console.log("-------------=="+global_Object.time);
        JqCommon.openPostWindow(url,datas);
    },
    openPostFalse:function(obj){
        var url =contextPath+"/paas/serverdetailedrealtime";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,"time":global_Object.time,"clientAppName":"","clientIpAddress":"","status":"失败"};
        JqCommon.openPostWindow(url,datas);
    },
    openPostAvg:function(obj){
        var url =contextPath+"/paas/serversteprealtime";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,"time":global_Object.time};
        //console.log(datas);
        JqCommon.openPostWindow(url,datas);
    }

}
