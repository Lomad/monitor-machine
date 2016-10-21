/**
 * Created by Admin on 2016/10/21.
 */

$(document).ready(function () {

    global_Object.initDomEvent();
    var json={"a":"EMPI基础服务","b":100,"c":10,"d":3,"e":15,"f":100,"g":9,"h":"3%",children:[{"a":"192.168.0.1","b":100,"c":10,"d":3,"e":15,"f":100,"g":9,"h":"3%"},{"a":"192.168.0.2","b":100,"c":10,"d":3,"e":15,"f":100,"g":9,"h":"3%"},{"a":"192.168.0.3","b":100,"c":10,"d":3,"e":15,"f":100,"g":9,"h":"3%"}]};
    //var data=[];
    //for(var i=0;i<20;i++){
    //    global_Object.tableData.push(json);
    //}
    $.post("/paas/qeryAllDomain", {}, function (data) {
        $("#flname").html(data[0]+' <i class="fa  fa-caret-down"></i>');
        global_Object.flname=data[0];
        global_Object.queryTableData();
        var li=[];
        $.each(data,function(i,v){
            var option ='<li role="presentation"><a role="menuitem" tabindex="-1">'+v+'</a></li>';
            li.push(option);
        });
        $("#fl").html(li.join(""));
        $("#fl a").on("click", function () {
            $("#flname").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
            global_Object.flname=$(this).text();
            global_Object.queryTableData();
        });


    });
});
var global_Object = {
    tableData:[],
    flname:"",
    url:"/paas/queryTransactionTypeList",
    initDomEvent: function () {
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

        $("#time1").on("click",function(){
            global_Object.url="/paas/queryTransactionTypeList"
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });
        $("#time2").on("click",function(){
            global_Object.url="/paas/queryTransactionTypeList"
            $(this).addClass("active").siblings().removeClass("active");
            global_Object.queryTableData();
        });
        $("#time3").on("click",function(){
            global_Object.url="/paas/queryTransactionTypeList"
            $(this).addClass("active").siblings().removeClass("active");
            //global_Object.queryTableData();
        });
        $("#time3v li").on("click",function(){
            global_Object.time=$(this).text().split('-')[0];
            $("#time3").html($(this).text()+ ' <i class="fa  fa-caret-down"></i>');
            global_Object.queryTableData();
        });
        $("#picEdit").on("show.bs.modal",function(){
        global_Object.queryPic();
        });
        $("#fTable .sorting").on("click",function(){
            if($(this).hasClass("desc")){
                $(this).addClass("asc").removeClass("desc");
            }
            else{
                $(this).addClass("desc").removeClass("asc");
            }

        });
    },
    queryTableData:function(){
        $.post(global_Object.url, {flname:global_Object.flname}, function (data) {
           global_Object.setTable();
        });
    },
    setTable:function(){
        var alltr=function(data){
            var tr ='<tr><td><i class="fa  icon cp fa-chevron-down"></i>'+data.a+'</td><td>'+data.b+'</td>';

        };
        $.each(global_Object.tableData,function(){
            //$("")
        });
    },
    queryTableDataLocal:function(){},

    queryPic:function(){
        $("#echart").css("width",$("#picEdit").width()*0.6-30);
        var option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']

                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'直接访问',
                    type:'bar',
                    barWidth: '60',
                    data:[10, 52, 200, 334, 390, 330, 220]
                }
            ]
        };
        var myChart = echarts.init(document.getElementById("echart"));
        myChart.setOption(option);
    }
}