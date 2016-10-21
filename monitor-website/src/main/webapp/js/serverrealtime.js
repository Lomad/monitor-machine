/**
 * Created by Admin on 2016/10/21.
 */
$(document).ready(function () {
    global_Object.initDomEvent();
});
var global_Object = {
    time:"",
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
        $("#fl a").on("click", function () {
            $("#flname").html($(this).text() + ' <i class="fa  fa-caret-down"></i>');
        });
        $(".time").on("click",function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $("#time3v li").on("click",function(){
            global_Object.time=$(this).text().split('-')[0];
            $("#time3").html($(this).text()+ ' <i class="fa  fa-caret-down"></i>');
        });
        $("#picEdit").on("show.bs.modal",function(){
        global_Object.queryPic();
        });
    },
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