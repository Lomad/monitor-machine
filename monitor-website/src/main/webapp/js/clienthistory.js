/**
 * Created by Evan on 2016/10/27.
 */


$(document).ready(function(){
    global_Object.initDomEvent();
    $.post(contextPath+"/paas/qeryAllDomain",{},function(data){
        $("#flname").html(data[0]+" <i class=\"fa fa-caret-down\"></i>");
        global_Object.flname = data[0];
        global_Object.queryTableData();
        var html ='';
        $.each(data,function(i,v){
         html+='<li class="cp" role="presentation"><a role="menuitem" tabindex="-1">'+v+'</a></li>'
        });
        $("#fl").html(html);
        $("#fl a").on("click",function(){
            $("#flname").html($(this).text()+" <i class=\"fa fa-caret-down\"></i>");
            global_Object.flname =$(this).text();
            global_Object.queryTableData();
        });
    });
});

var global_Object={
    tableDataOld: [],
    tableData:[],
    totalSize: 0,
    flname:"",
    url: contextPath+"/paas/queryTodayTransactionTypeReportByServer",
    dttype:"",
    dttime:"",
    initDomEvent:function(){
        $("#date_picker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            format: "yyyy-mm-dd",//日期格式
            weekStart:1,
            showWeekNumbers:true
        });
        var date = new Date();
        var yesterday = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate()-1);
        $("#date_picker").datepicker('update',yesterday);
        // 日历类型选择器修改时间的触发锚点: 下传时间类型和时间字符串，并post到不同的URL
        $("#sel a").on('click',function(){
            var data =$(this).attr("data");
            $("#selbtn").html($(this).text()+' <i class="fa  fa-caret-down"></i>');
            global_Object.selectType(data);

            global_Object.dttype = data;
            global_Object.dttime = $('#datevalue').val();
            if(global_Object.dttype == "day"){
                global_Object.url = contextPath + "/paas/queryDayTransactionTypeReportByClient";
                global_Object.queryTableData();
            }else if(global_Object.dttype == "week"){
                global_Object.url = contextPath + "/paas/queryWeekTransactionTypeReportByClient";
                global_Object.queryTableData();
            }else if(global_Object.dttype == "month"){
                global_Object.url = contextPath + "/paas/queryMonthTransactionTypeReportByClient";
                global_Object.queryTableData();
            }
            //alert( global_Object.dttime );
        });
        // 日历选择器修改时间的触发锚点
        $("#date_picker").datepicker().on('hide', function(){
            var date = $('#datevalue').val();
            if(global_Object.type=="week" && date!=""){
                var newdate = global_Object.getNewDay(date);
                $('#datevalue').val(newdate);
            }
            //alert($('#datevalue').val());
            global_Object.dttime = $('#datevalue').val();
            if(global_Object.dttype == "day"){
                global_Object.url = contextPath + "/paas/queryDayTransactionTypeReportByClient";
                global_Object.queryTableData();
            }else if(global_Object.dttype == "week"){
                global_Object.url = contextPath + "/paas/queryWeekTransactionTypeReportByClient";
                global_Object.queryTableData();
            }else if(global_Object.dttype == "month"){
                global_Object.url = contextPath + "/paas/queryMonthTransactionTypeReportByClient";
                global_Object.queryTableData();
            }
            //alert( global_Object.dttime );
        });
        //$("#date_picker").datepicker().on('changeDate', function(){
        //    alert("date-picker:"+$('#datevalue').val());
        //});

        /* 查询框的过滤的功能 */
        $("#querybtn").on("click",function(){
            global_Object.setTableData("search",this);
        });
        /* 可排序字段的排序功能 */
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
                showWeekNumbers:true
            });
            var date = new Date();
            var yesterday = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate()-1);
            $("#date_picker").datepicker('update',yesterday);
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
                todayHighlight: true
            });
            var date = new Date();
            var week = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
            $("#datevalue").val(global_Object.getNewDay(week));
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
                minViewMode:1
            });
            var date = new Date();
            var month = date.getFullYear()+"-"+(date.getMonth()+1);
            $("#date_picker").datepicker('update',month);
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
    queryTableData:function(){
        $.post(global_Object.url,{flname:global_Object.flname,dttype:global_Object.dttype, dttime:global_Object.dttime},function(data){
            global_Object.tableDataOld =data.transactionStatisticDatas;
            global_Object.totalSize = data.totalSize;
            global_Object.tableData = data.transactionStatisticDatas;
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
    setTable:function(){
        var alltr = function(length,i,data){
            var tr = '<tr data-transactiontypename="'+data.transactionTypeName+'" data-serveripaddress="'+data.serverIpAddress+'">';
            if(i==0){
                tr+='<td rowspan='+length+' class="vam tac">'+data.transactionTypeName+'</td>';
            }
            tr += '<td>' + data.serverIpAddress + '</td>';
            tr += '<td><a onclick="global_Object.openPostTotalCount(this)" href="javascript:void(0)">' + data.totalCount + '次</a></td>';
            tr += '<td><a onclick="global_Object.openPostAvg(this)" href="javascript:void(0)">' + data.avg + 'ms</a></td>';
            tr += '<td>' + data.line99Value + 'ms</td>';
            tr += '<td>' + data.line95Value + 'ms</td>';
            tr += '<td>' + data.min + 'ms</td>';
            tr += '<td>' + data.max + 'ms</td>';
            tr += '<td>' + data.tps + '</td>';
            //tr += '<td>' + data.failCount + '次</td>';
            tr += '<td><a onclick="global_Object.openPostFalse(this)" href="javascript:void(0)">' + data.failCount + '次</a></td>';
            tr += '<td>' + data.failPercent*100 + '%</td>';
            tr += '<td>' + data.std + 'ms</td>';
            tr += '<td><i class="fa  fa-bar-chart-o cp" onclick="global_Object.queryPic(this)"></i></td>';
            tr += '</tr>';
            return tr;
        }
        var tableHtml = [];
        $.each(global_Object.tableData,function(i,v){
            var StatisticDatas = v.transactionStatisticDataDetails;
            var length =StatisticDatas.length;
            //console.log(StatisticDatas[0].transactionTypeName)
            if (StatisticDatas != null &&StatisticDatas.length > 0) {
                $.each(StatisticDatas, function (i2, v2) {
                    tableHtml.push(alltr(length,i2,v2));
                });
            }
        });
        $("#fTable tbody").html(tableHtml.join(""));
    },
    queryPic: function (obj) {
        $("#echart").css("width", $("#picEdit").width() * 0.6 - 30);
        $("#picEdit").modal("show");
        var url =contextPath+"/paas/queryTodayTransactionTypeCallTimesReportByServer";
        $.post(url, {serverAppName: global_Object.flname,transactionTypeName:$(obj).parents("tr").data("transactiontypename"),serverIpAddress:$(obj).parents("tr").data("serveripaddress")}, function (data) {
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
    openPostAvg:function(obj){
        global_Object.value = $("#datevalue").val();
        var url =contextPath+"/paas/clientstephistory";
        //alert($(obj).parents("tr").data("transactiontypename"))
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":(global_Object.type==undefined?"":global_Object.type),value:global_Object.value,historyPageType:"client"};
        JqCommon.openPostWindow(url,datas);
    },
    openPostTotalCount:function(obj){
        global_Object.value = $("#datevalue").val();
        var url =contextPath+"/paas/clientdetailedhistory";
        //alert($(obj).parents("tr").data("transactiontypename"))
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":(global_Object.type==undefined?"":global_Object.type),value:global_Object.value,"clientAppName":"","clientIpAddress":"","status":"",historyPageType:"client"};
        console.log(datas);
        JqCommon.openPostWindow(url,datas);
    },
    openPostFalse:function(obj){
        global_Object.value = $("#datevalue").val();
        var url =contextPath+"/paas/clientdetailedhistory";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":(global_Object.type==undefined?"":global_Object.type),"value":global_Object.value,"clientAppName":"","clientIpAddress":"","status":"失败",historyPageType:"server"};
        JqCommon.openPostWindow(url,datas);
    }
}
