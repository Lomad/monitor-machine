/**
 * Created by Evan on 2016/10/21.
 */
$(document).ready(function () {
    global_Object.initDomEvent();
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
    url: contextPath+"/paas/queryDayTransactionTypeReportByServer",
    totalSize: 0,
    type:"day",
    value:"",
    formatdate:"",
    initDomEvent: function () {
        $("#date_picker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            format: "yyyy-mm-dd",//日期格式
            weekStart:1,
            showWeekNumbers:true,
            endDate:"-1d"


        });
        //alert(new Date())
        global_Object.formatdate = global_Object.getYesterdayFormatDate();
        $("#date_picker").datepicker('update',global_Object.formatdate);
        global_Object.queryTableData();
        $("#sel a").on('click',function(){
            var data =$(this).attr("data");
            $("#selbtn").html($(this).text()+' <i class="fa  fa-caret-down"></i>');
            global_Object.selectType(data);
        });
        $("#date_picker").datepicker().on('hide', function(){
            var date = $('#datevalue').val();
            if(global_Object.type=="week"&&date!=""){
                var newdate = global_Object.getNewDay(date);
                $('#datevalue').val(newdate);

            }else if(global_Object.type=="week"&&date==""){
                var date = new Date();
                var week = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
                $("#datevalue").val(global_Object.getNewDay(week));

            }
            if(global_Object.type=="day"){
                global_Object.formatdate = $("#datevalue").val();
            }else if(global_Object.type=="week"){
                var oldDate = $("#datevalue").val().split("-")[0];
                var newDate = oldDate.split("/");
                global_Object.formatdate = newDate[0]+"-"+newDate[1]+"-"+newDate[2];
            }else if(global_Object.type=="month"){
                global_Object.formatdate = $("#datevalue").val()+"-01";
            }
            global_Object.queryTableData();
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
        //$("#picEdit").on("show.bs.modal",function(){
        //    global_Object.queryPic();
        //});
    },
    selectType:function(data){
        global_Object.type = data;
        if(data == "day"){
            $('#date_picker').datepicker('update','');
            $('#date_picker').datepicker('destroy');
            $("#date_picker").datepicker({
                language: "zh-CN",
                autoclose: true,//选中之后自动隐藏日期选择框
                format: "yyyy-mm-dd",//日期格式
                weekStart:1,
                showWeekNumbers:true,
                endDate:"-1d"

            });
            global_Object.url = contextPath+"/paas/queryDayTransactionTypeReportByServer";
            global_Object.formatdate = global_Object.getYesterdayFormatDate();
            $("#date_picker").datepicker('update',global_Object.formatdate);
            global_Object.queryTableData();
        }else if(data == "week"){
            $('#date_picker').datepicker('update','');
            $('#date_picker').datepicker('destroy');
            $("#date_picker").datepicker({
                language: "zh-CN",
                autoclose: true,//选中之后自动隐藏日期选择框
                format: "yyyy/mm/dd",//日期格式
                weekStart:1,
                showWeekNumbers:true,
                calendarWeeks: true,
                todayHighlight: true,
                endDate:"-1d"
                //daysOfWeekDisabled:[0,2,3,4,5,6]
                //beforeShowDay: function(date){
                //    var day = date.getDay();
                //    if(day!=1){
                //        return {
                //            enabled : false
                //        };
                //    }
                //    return [true,''];
                //}
            });
            global_Object.url = contextPath+"/paas/queryWeekTransactionTypeReportByServer";
            var date = new Date();
            var week = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
            $("#datevalue").val(global_Object.getNewDay(week));
            var oldDate = global_Object.getNewDay(week).split("-")[0];
            var newDate = oldDate.split("/");
            global_Object.formatdate = newDate[0]+"-"+newDate[1]+"-"+newDate[2];
            global_Object.queryTableData();
        }else if(data == "month"){
            $('#date_picker').datepicker('update','');
            $('#date_picker').datepicker('destroy');
            $("#date_picker").datepicker({
                language: "zh-CN",
                autoclose: true,//选中之后自动隐藏日期选择框
                format: "yyyy-mm",//日期格式
                weekStart:1,
                showWeekNumbers:true,
                startView: 'year',
                minViewMode:1,
                endDate:new Date()
            });
            global_Object.url = contextPath+"/paas/queryMonthTransactionTypeReportByServer";
            var date = new Date();
            var month = date.getFullYear()+"-"+(date.getMonth()+1);
            $("#date_picker").datepicker('update',month);
            global_Object.formatdate = month +"-01";
            global_Object.queryTableData();
        }
    },
    getNewDay:function(dateTemp) {
        var dateTemp = dateTemp.split("/");
        var nDate = new Date(dateTemp[1] + '/' + dateTemp[2] + '/' + dateTemp[0]); //转换为MM-DD-YYYY格式
        var week = nDate.getDay();
        var monday = Math.abs(nDate) - ((week-1) * 24 * 60 * 60 * 1000);
        var millSeconds = monday + (6 * 24 * 60 * 60 * 1000);
        function getdate(mills){
            var rDate = new Date(mills);
            var year = rDate.getFullYear();
            var month = rDate.getMonth() + 1;
            if (month < 10) month = "0" + month;
            var date = rDate.getDate();
            var today = new Date();
            //if(date>today.getDate()) date = today.getDate();
            if (date < 10) date = "0" + date;
            var newdate = year + "/" + month + "/" + date;
            return newdate;
        }
        //alert(getdate(monday)+"-"+getdate(millSeconds));
        return (getdate(monday)+"-"+getdate(millSeconds));
    },
    queryTableData: function () {
        //console.log(global_Object.flname)
        //console.log(global_Object.url)
        //console.log(global_Object.formatdate);
        $.post(global_Object.url, {flname: global_Object.flname,date:global_Object.formatdate}, function (data) {
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
    getYesterdayFormatDate:function(){
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate() -1;
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
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
    queryPic: function (obj) {
        $("#echart").css("width", $("#picEdit").width() * 0.6 - 30);
        $("#picEdit").modal("show");
        var url =""
        if(global_Object.type=="day"){
            url =contextPath+"/paas/queryDayTransactionTypeCallTimesReportByServer";
        }
        else if(global_Object.type=="week"){
            url =contextPath+"/paas/queryWeekTransactionTypeCallTimesReportByServer";
        }
        else if(global_Object.type=="month"){
            url =contextPath+"/paas/queryMonthTransactionTypeCallTimesReportByServer";
        }
        var datas = {flname: global_Object.flname,transactionTypeName:$(obj).parents("tr").data("transactiontypename"),serverIpAddress:$(obj).parents("tr").data("serveripaddress"),date:global_Object.formatdate};
        //console.log(datas);
        //console.log(url)
        $.post(url,datas, function (data) {
            var json=[];
            var name =[]
            console.log(data);
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
    openPostWindow:function(obj){
        global_Object.value = $("#datevalue").val();
        var url =contextPath+"/paas/serversyshistory";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,value:global_Object.value,historyPageType:"server",dateValue:global_Object.formatdate};
        //console.log(datas);
        JqCommon.openPostWindow(url,datas);
    },
    openPostAvg:function(obj){
        global_Object.value = $("#datevalue").val();
        var url =contextPath+"/paas/serverstephistory";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,value:global_Object.value,historyPageType:"server",dateValue:global_Object.formatdate};
        //console.log(datas);
        JqCommon.openPostWindow(url,datas);
    },
    openPostTotalCount:function(obj){
        global_Object.value = $("#datevalue").val();
        var url =contextPath+"/paas/serverdetailedhistory";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,value:global_Object.value,"clientAppName":"","clientIpAddress":"","status":"",historyPageType:"server",dateValue:global_Object.formatdate};
        JqCommon.openPostWindow(url,datas);
    },
    openPostFalse:function(obj){
        global_Object.value = $("#datevalue").val();
        var url =contextPath+"/paas/serverdetailedhistory";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,"value":global_Object.value,"clientAppName":"","clientIpAddress":"","status":"失败",historyPageType:"server",dateValue:global_Object.formatdate};
        JqCommon.openPostWindow(url,datas);
    }
}

