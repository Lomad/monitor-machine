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
    }
}