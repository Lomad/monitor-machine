/**
 * Created by Evan on 2016/10/27.
 */


$(document).ready(function(){
    global_Object.initDomEvent();
    $.post(contextPath+"/paas/getAllClientNames",{},function(data){
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
            //alert(global_Object.flname);
            global_Object.queryTableData();
        });
    });
});

var global_Object={
    tableData:[],
    totalSize: 0,
    flname:"",
    formatdate:"",
    type:"day",
    value:"",
    url: contextPath+"/paas/queryDayClientReportByClient",
    initDomEvent:function(){
        $("#date_picker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            format: "yyyy-mm-dd",//日期格式
            weekStart:1,
            showWeekNumbers:true,
            endDate:"-1d"
        });
        //var date = new Date();
        //var yesterday = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate()-1);
        //$("#date_picker").datepicker('update',yesterday);
        global_Object.formatdate = global_Object.getYesterdayFormatDate();
        $("#date_picker").datepicker('update',global_Object.formatdate);
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
            global_Object.url = contextPath+"/paas/queryDayClientReportByClient";
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
            });
            global_Object.url = contextPath+"/paas/queryWeekClientReportByClient";
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
            global_Object.url = contextPath+"/paas/queryMonthClientReportByClient";
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
    queryTableData:function(){
        var datas = {clientAppName:global_Object.flname,date:global_Object.formatdate};
        //console.log(datas);
        //console.log(global_Object.url);
        $.post(global_Object.url,datas,function(data){
            //console.log(data);
            global_Object.totalSize = data.totalSize;
            global_Object.tableData = data.transactionStatisticDatas;
            if(global_Object.totalSize>0){
                global_Object.setTable();
            }else{
                $("#fTable tbody").html('<tr class="odd"><td valign="top" colspan="13" class="dataTables_empty">表中数据为空</td></tr>');
            }
        });
    },
    setTable:function(){
        var alltr = function(length,i,data){
            var tr = '<tr data-transactiontypename="'+data.transactionTypeName+'" data-serverAppName="'+data.serverAppName+'">';
            if(i==0){
                tr+='<td rowspan='+length+' class="vam tac">'+data.serverAppName+'</td>';
            }
            tr += '<td>' + data.transactionTypeName + '</td>';
            tr += '<td><a onclick="global_Object.openPostTotalCount(this)" href="javascript:void(0)">' + data.totalCount + '次</a></td>';
            tr += '<td><a onclick="global_Object.openPostAvg(this)" href="javascript:void(0)">' + data.avg + 'ms</a></td>';
            //tr += '<td>' + data.avg + 'ms</td>';
            tr += '<td>' + data.line99Value + 'ms</td>';
            tr += '<td>' + data.line95Value + 'ms</td>';
            tr += '<td>' + data.min + 'ms</td>';
            tr += '<td>' + data.max + 'ms</td>';
            tr += '<td>' + data.tps + '</td>';
            tr += '<td>' + data.failCount + '次</td>';
            tr += '<td>' + data.failPercent + '%</td>';
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
        //var url =contextPath+"/paas/queryTodayTransactionTypeCallTimesReportByServer";
        var url =""
        if(global_Object.type=="day"){
            url =contextPath+"/paas/queryDayTransactionTypeCallTimesReportByClient";
        }
        else if(global_Object.type=="week"){
            url =contextPath+"/paas/queryWeekTransactionTypeCallTimesReportByClient";
        }
        else if(global_Object.type=="month"){
            url =contextPath+"/paas/queryMonthTransactionTypeCallTimesReportByClient";
        }
        var datas = {clientAppName: global_Object.flname,transactionTypeName:$(obj).parents("tr").data("transactiontypename"),serverAppName:$(obj).parents("tr").data("serverappname"),date:global_Object.formatdate};
        //console.log(datas);
        //console.log(url);
        $.post(url, datas, function (data) {
            var json=[];
            var name =[];
            //console.log(data);
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
                        data: name,
                        name: '分',
                        nameLocation: 'end',
                        nameTextStyle:{
                            color: 'black',
                            fontSize: 14,
                            fontWeight: 'bolder'
                        }

                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '次',
                        nameLocation: 'end',
                        nameTextStyle:{
                            color: 'black',
                            fontSize: 14,
                            fontWeight: 'bolder'
                        }
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
        var url =contextPath+"/paas/serverstephistory";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),
            "serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),
            "clientAppName":global_Object.flname,
            "type":(global_Object.type==undefined?"":global_Object.type),
            "value":global_Object.value,
            "historyPageType":"client",
            "dateValue":global_Object.formatdate,
            "serverAppName":$(obj).parents("tr").data("serverappname")==undefined?"":$(obj).parents("tr").data("serverappname")
        };
        JqCommon.openPostWindow(url,datas);
    },
    openPostTotalCount:function(obj){
        global_Object.value = $("#datevalue").val();
        var url =contextPath+"/paas/serverdetailedhistory";
        //alert($(obj).parents("tr").data("transactiontypename"))
        var datas={
            "transactionTypeName":$(obj).parents("tr").data("transactiontypename"),
            "serverIpAddress":"",
            "serverAppName":$(obj).parents("tr").data("serverappname")==undefined?"":$(obj).parents("tr").data("serverappname"),
            "type":(global_Object.type==undefined?"":global_Object.type),value:global_Object.value,"clientAppName":global_Object.flname,"clientIpAddress":"",
            "status":"",
            "historyPageType":"client",
            "dateValue":global_Object.formatdate
            };
        //console.log(datas);
        JqCommon.openPostWindow(url,datas);
    }

}
