/**
 * Created by InnerPeace on 2016/10/24.
 */

$(document).ready(function () {

    global_Object.initDomEvent();
    //var json={"a":"EMPI基础服务","b":100,"c":10,"d":3,"e":15,"f":100,"g":9,"h":"3%",children:[{"a":"192.168.0.1","b":100,"c":10,"d":3,"e":15,"f":100,"g":9,"h":"3%"},{"a":"192.168.0.2","b":100,"c":10,"d":3,"e":15,"f":100,"g":9,"h":"3%"},{"a":"192.168.0.3","b":100,"c":10,"d":3,"e":15,"f":100,"g":9,"h":"3%"}]};
    //var data=[];
    //for(var i=0;i<20;i++){
    //    global_Object.tableData.push(json);
    //}
    $.post(contextPath+"/paas/qeryAllClient", {}, function (data) {
        $("#flname").html(data[0] + ' <i class="fa  fa-caret-down"></i>');
        global_Object.flname = data[0];
        global_Object.queryTableData();
        console.log("length:"+data.length+";data[0]:"+data[0]);
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
    url: contextPath+"/paas/queryLastHourClientReportByClient",
    totalSize: 0,
    type:"最近一小时",
    time:"",
    initDomEvent: function () {
        $("#time1").on("click", function () {
            global_Object.url = contextPath+"/paas/queryLastHourClientReportByClient";
            global_Object.type="最近一小时";
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });

        $("#time2").on("click", function () {
            global_Object.url = contextPath+"/paas/queryTodayClientReportByClient";
            global_Object.type="当天";
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });

        $("#time3v li").on("click", function () {
            global_Object.url = contextPath+"/paas/queryHourClientReportByClient";
            $("#time3").addClass("active").siblings().removeClass("active");
            //global_Object.time =$(this).text(); //$(this).text().split('-')[0];
            global_Object.time=global_Object.getNowFormatDate()+" "+$(this).text().split('-')[0]+":00";
            global_Object.type="指定小时";
            $("#time3").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
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
    },
    queryTableData:function(){
        $.post(global_Object.url, {clientAppName: global_Object.flname, hour: global_Object.time }, function (data) {
             console.log(data);
             global_Object.tableDataOld =data.transactionStatisticDatas;
             global_Object.tableData = data.transactionStatisticDatas;
             global_Object.totalSize = data.totalSize;
             global_Object.setTable();
         });
    },
    setTable: function () {
        var alltr = function (length, i, data) {
            var tr = '<tr data-transactiontypename="'+data.transactionTypeName+'" data-serveripaddress="'+data.serverIpAddress + '" data-serverappname="'+data.serverAppName +'">';
            if(i==0){
                tr+='<td rowspan='+length+' class="vam tac">'+data.serverAppName+'</td>';
            }
            //if (type == "transactionTypeName") {
            //    tr += '<td>' + flname + '</td>';
            //    tr += '<td><a onclick="global_Object.openPostWindow(this)" href="javascript:void(0)">' + data.transactionTypeName + '</a></td>';
            //}
            tr += '<td>' + data.transactionTypeName + '</td>';
            tr += '<td><a onclick="global_Object.openPostTotalCount(this)" href="javascript:void(0)">' + data.totalCount + '次</a></td>';
            tr += '<td><a onclick="global_Object.openPostAvg(this)" href="javascript:void(0)">' + data.avg + 'ms</a></td>';
            tr += '<td>' + data.line99Value + 'ms</td>';
            tr += '<td>' + data.line95Value + 'ms</td>';
            tr += '<td>' + data.min + 'ms</td>';
            tr += '<td>' + data.max + 'ms</td>';
            tr += '<td>' + data.tps + '</td>';
            tr += '<td><a onclick="global_Object.openPostFalse(this)" href="javascript:void(0)">' + data.failCount + '次</a></td>';
            tr += '<td>' + data.failPercent + '%</td>';
            tr += '<td>' + data.std + 'ms</td>';
            tr += '<td  style="padding-left: 30px"><i class="fa  fa-bar-chart-o cp" onclick="global_Object.queryPic(this)"></i></td>';
            tr += '</tr>';
            return tr;
        };

        var html = [];
        var StatisticDatas = global_Object.tableData;
        var length = StatisticDatas.length;
        if (StatisticDatas != null && StatisticDatas.length > 0) {
            $.each(StatisticDatas, function (i, v) {
                html.push(alltr(length,i,v));
            });
        }
        /*$.each(global_Object.tableData, function (i, v) {
            var StatisticDatas = v.transactionStatisticDataDetails;
            var length =StatisticDatas.length;
            if (StatisticDatas != null && StatisticDatas.length > 0) {
                $.each(StatisticDatas, function (i2, v2) {
                    html.push(alltr(length,i2,v2));
                });
            }
        });*/
        //console.log(html);
        $("#fTable tbody").html(html.join(""));
        /* 子表动作 == 删除
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
        });*/
    },

    /* ‘服务名称’或 ‘服务地址’之跳转锚点
     openPostWindow:function(obj){
     var url =contextPath+"/paas/serversysrealtime";
     //alert($(obj).parents("tr").data("transactiontypename"))
     var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":global_Object.flname,"type":global_Object.type,"time":global_Object.time};
     //console.log(datas);
     //alert($(obj).data("transactionyypename"))
     JqCommon.openPostWindow(url,datas);//通用的超链接功能接口
     },
     */

    /* 模糊过滤、升序标签、降序标签之函数调用 */
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
    /* ‘调用次数’之跳转锚点 */
    openPostTotalCount:function(obj){
        var url =contextPath+"/paas/clientdetailedrealtime";
        console.log($(obj).parents("tr"));
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":$(obj).parents("tr").data("serverappname")==undefined?"":$(obj).parents("tr").data("serverappname"),"type":global_Object.type,"time":global_Object.time,"clientAppName":global_Object.flname,"clientIpAddress":"","status":""};
        JqCommon.openPostWindow(url,datas);
    },
    /* ‘平均耗时’ 之跳转锚点 */
    openPostAvg:function(obj){
        var url =contextPath+"/paas/clientsteprealtime";
        //alert($(obj).parents("tr").data("transactiontypename"))
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":$(obj).parents("tr").data("serverappname")==undefined?"":$(obj).parents("tr").data("serverappname"),"type":global_Object.type,"time":global_Object.time,"clientAppName":global_Object.flname};
        //console.log(datas);
        //alert($(obj).data("transactionyypename"))
        JqCommon.openPostWindow(url,datas);
    },
    /* '显示图表' 之跳转锚点 */
    queryPic: function (obj) {
        $("#echart").css("width", $("#picEdit").width() * 0.6 - 30);
        $("#picEdit").modal("show");
        var url =""
        if(global_Object.type=="最近一小时"){
            url =contextPath+"/paas/queryLastHourTransactionTypeCallTimesReportByClient";
        }
        else if(global_Object.type=="当天"){
            url =contextPath+"/paas/queryTodayTransactionTypeCallTimesReportByClient";
        }
        else if(global_Object.type=="指定小时"){
            url =contextPath+"/paas/queryHourTransactionTypeCallTimesReportByClient";
        }
        var request = { clientAppName: global_Object.flname,
                         serverAppName:$(obj).parents("tr").data("serverappname")==undefined?"":$(obj).parents("tr").data("serverappname"),
                         transactionTypeName:$(obj).parents("tr").data("transactiontypename"),
                         hour:global_Object.time
                        };
        $.post(url, request, function (data) {
            console.log(data);
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
    /* '失败次数' 之跳转锚点 */
    openPostFalse:function(obj){
        var url =contextPath+"/paas/clientdetailedrealtime";
        var datas={"transactionTypeName":$(obj).parents("tr").data("transactiontypename"),"serverIpAddress":$(obj).parents("tr").data("serveripaddress")==undefined?"":$(obj).parents("tr").data("serveripaddress"),"serverAppName":$(obj).parents("tr").data("serverappname")==undefined?"":$(obj).parents("tr").data("serverappname"),"type":global_Object.type,"time":global_Object.time,"clientAppName":"","clientIpAddress":global_Object.flname,"status":"失败"};
        JqCommon.openPostWindow(url,datas);
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
    }

}


