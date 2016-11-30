/**
 * Created by InnerPeace on 2016/11/28.
 */



$(document).ready(function(){

    global_Object.loadTotalCount();
    global_Object.loadWrongCount();
    global_Object.loadClientTypePie();
    //$("#idBtnReload").on('click',function(){
    //    global_Object.loadTotalCount();
    //    global_Object.loadWrongCount();
    //    global_Object.loadClientTypePie();
    //});
    global_Object.loadServerAppPie();
    global_Object.loadLastHourAlarm();
});

var global_Object = {

    domains:[],
    // 服务访问总量
    loadTotalCount:function(){
         var url = "/paas/queryTodayYestodayServerCount";
         $.post(url, {}, function(obj){
         console.log(obj);
         var chart = echarts.init(document.getElementById('id_svr_count'));
         var array = [obj.todayCount, obj.yestodayCount];
         console.log(array);
         var option = {
         title: {
         text: '服务总量'
         },
         tooltip : {
         trigger: 'item',
         formatter: "{b} : {c}"
         },
         // 控制图形位置
         grid: {
         left: '3%',
         top: '25%',
         right: '10%',
         bottom: '3%',
         containLabel: true
         },
         xAxis: {
         type: 'value',
         name: '次数',
         nameRotate: '0',
         nameLocation: 'end',
         boundaryGap: [0,1],//调整坐标轴的精度
         show: true
         },
         yAxis: {
         show: true,
         type: 'category',
         axisTick: false,
         data: ['昨天','今天']
         },
         barWidth: '40%',
         series: [
         {
         itemStyle:{
         normal:{
         color:'blue',
         label:{show:true,position: 'right',textStyle:{color:'blue',fontSize:'12'}}
         }
         },
         type: 'bar',
         data: array
         }
         ]
         };
         chart.setOption(option);
         });
     },
    // 服务访问错误量
    loadWrongCount:function(){
        var url = "/paas/queryTodayYestodayWrongCount";
        var wrong = echarts.init(document.getElementById('id_svr_wrong'));
        $.post(url, {}, function(obj){
            var array = [obj.todayCount, obj.yestodayCount];
            var option = {
                title: {
                    text: '服务错误量'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{b} : {c}"
                },
                // 控制图形位置
                grid: {
                    left: '3%',
                    top: '25%',
                    right: '10%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    name: '次数',
                    nameRotate: '0',
                    nameLocation: 'end',
                    boundaryGap: [0, 1],//调整坐标轴的精度
                    show: true
                },
                yAxis: {
                    show: true,
                    type: 'category',
                    axisTick: false,
                    data: ['昨天', '今天']
                },
                barWidth: '40%',
                series: [
                    {
                        itemStyle: {
                            normal: {
                                color: 'red',
                                label: {show: true, position: 'right', textStyle: {color: 'blue', fontSize: '12'}}
                            }
                        },
                        type: 'bar',
                        data: array
                    }
                ]
            };
            wrong.setOption(option);
        });
    },


    // 客户端饼图
    loadClientTypePie:function(){
        var url = "/paas/queryTodayCountByClientType";
        $.post(url, {}, function(obj){
            console.log(obj);
            var client = echarts.init(document.getElementById('id_svr_client'));
            var map = obj.serverCountMap;
            var array = [];
            var types = [];
            console.log("map",map);
            for(var key in map){
                var temp ={};
                temp.name = key;
                temp.value = map[key];
                //data.push(temp);
                array.push(temp);
                types.push(key);
            }
            //var array = [  {value:335, name:'PC端',itemStyle:{normal:{color:'#ccc'}}},
            //               {value:310, name:'手机端',itemStyle:{normal:{color:'#ace'}}},
            //               {value:234, name:'PAD端',itemStyle:{normal:{color:'#bbb'}}}   ];
            console.log("pie-array",array);
            console.log("pie-types",types);
            var option = {
                title : {
                    text: '客户端分布图',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    right: '2%',
                    bottom: '5%',
                    //data: ['PC端','手机端','PAD端']
                    data:types
                },
                series : [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius : '55%',
                        center: ['50%', '40%'],
                        data:array,
                        //data:[
                        //    {value:335, name:'PC端',itemStyle:{normal:{color:'#ccc'}}},
                        //    {value:310, name:'手机端',itemStyle:{normal:{color:'#ace'}}},
                        //    {value:234, name:'PAD端',itemStyle:{normal:{color:'#bbb'}}}
                        //],
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
            client.setOption(option);
        });
    },

    loadServerAppPie: function(){
        var url = "/paas/queryTodayCountByDomain";

        $.post(url, {}, function(datas){

            var array = [];
            var list = datas.serverCountWithDomain;
            var divhtml = [];
            $.each(list, function(i,v){

                 var idchart = "idchart-" + v.serverAppName;
                 var temp = '<div class="col-md-3"> <div  style="border:1px solid #cee3e9;background:#f1f7f9"><div style="margin-left:5px;margin-top:5px"> '
                        + '<a class="title" href="#" style="font-weight:900;color:#000000">'+ v.serverAppName +'</a> </div>'
                        + '<div id="'+ idchart +'" style="height:80px;left:50%;rigth:50%;top:50%;bottom:50%;"></div>'
                        + '<div > <span><i class="fa fa-plus" style="color:red;margin-left:5px;"></i>告警</span><span id="idHisCountAlarm" class="pull-right"  style="color:red;margin-right:5px">'+ v.todayCount +'</span> </div>'
                        + '<div> <span><i class="fa fa-bell-o" style="color:blue;margin-left:5px;"></i>异常</span> <span id="idHisCountTips" class="pull-right" style="color:blue;margin-right:5px">'+ v.todayWrongCount +'</span> </div>'
                        + '<div> <span><i class="fa fa-check" style="color:green;margin-left:5px;margin-bottom: 5px"></i>正常</span> <span id="idHisCountNormal" class="pull-right" style="color:green;margin-right:5px;margin-bottom: 5px">'+ v.todayRightCount +'</span> </div>'
                        + '</div></div>';
                 divhtml.push(temp);

                 var temp = {"name": v.serverAppName,"alarm":v.todayCount, "wrong":v.todayWrongCount, "right":v.todayRightCount};
                 array.push(temp);

            });

            $("#id_svr_app").html(divhtml.join(""));

            console.log("array:",array);
            global_Object.paintServerAppPie(array);
        });

    },
    // 应用服务告警计数
    paintServerAppPie: function(array){

        console.log("paint:",array.length);
        $.each(array, function(i,v) {
            var idchart = "idchart-" + v.name;
            console.log(idchart);
            var chart = echarts.init(document.getElementById(idchart));
            console.log("chart",chart);
            var sum = v.alarm + v.wrong + v.right;
            console.log("sum:",sum);
            var data = [{value: v.alarm, name: '告警'}, {value: v.wrong, name: '异常'}, {value: v.right, name: '正常'}];
            var option = {
                title: {
                    show: true,
                    text: '' + sum,
                    x: 'center',
                    y: 'center',
                    textStyle:{fontSize:8}
                },
                center: ['50%', '50%'],
                color: ['red', 'blue', 'green'],
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'inner'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '4',
                                    fontWeight: 'bold',
                                    color: 'black'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data: data
                    }
                ]
            };
            chart.setOption(option);
        });
    },

    // 当前一小时的告警详情
    loadLastHourAlarm: function(){
        var url = "/paas/queryLastHourWrongMessageOverview";
        $.post(url, {}, function(datas){
            console.log("alarm:",datas.wrongMessages);

            var html = [];
            var wrong = datas.wrongMessages;

            $("#id_alarm_sum").html(wrong.length + '&nbsp;');

            $.each(wrong, function(i,v){
                console.log("map:", v.tips)


                var head = '<li><img class="avatar img-responsive" style="float:left;margin-right:10px" alt="" src="assets/img/avatar2.jpg" />';
                var tail = '<span class="body">'+ v.tips['error'] +'</span></div></li>';

                var isalm = v.tips['alarm'];
                if( isalm ){
                    console.log("isalm:",isalm);
                    head = '<li><img class="avatar img-responsive" style="float:left;margin-right:10px" alt="" src="assets/img/avatar3.jpg" />';
                    tail = '<span class="body">'+ v.tips['alarm'] +'</span></div></li>';
                }

                var body = '<span><a class="avatar img-responsive" style="float:right; margin-right:10px; vertical-align:middle;" >'+ v.domain +'</a></span><div class="message"><a href="#" class="name">'+ v.transactionTypeName +'&brvbar;'+ v.serverIpAddress +'</a><span class="datetime">('+ v.currentTime+')</span>';

                var text = head + body + tail;
                html.push(text);
            });
            console.log(html.join(""));
            $("#id_alarm_list").html(html.join(""));
        });
    }
}
