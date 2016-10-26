<#import "common/modal.ftl" as modal/>
<#macro foot>
</div>

</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<div class="footer" style="background: #fff; border-top: 1px solid #e2e2e2;">
    <div id="yymc" class="footer-inner">
    </div>
    <div class="footer-tools" style="color: #aaaaaa;">
        <span>©2016 winninghealth</span>
    </div>
</div>
    <#assign contextPath=request.contextPath>
<!--[if lt IE 9]>
<script src="${contextPath}/assets/plugins/respond.min.js"></script>
<script src="${contextPath}/assets/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="${contextPath}/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="${contextPath}/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
        type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${contextPath}/assets/scripts/app.js" type="text/javascript"></script>
<script src="${contextPath}/assets/scripts/index.js" type="text/javascript"></script>
<script src="${contextPath}/assets/scripts/tasks.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME Datatables -->
<script src="${contextPath}/assets/plugins/dataTables/jquery.dataTables.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/dataTables/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/dataTables/dataTables.responsive.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/dataTables/dataTables.tableTools.min.js" type="text/javascript"></script>
<script src="${contextPath}/js/winning-table.js" type="text/javascript"></script>
<!-- END THEME Datatables -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${contextPath}/assets/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<script src="${contextPath}/js/index.js" type="text/javascript"></script>
<script src="${contextPath}/js/common.manage.helper.js" type="text/javascript"></script>
<script type="text/javascript">
    $(".firstMenu").on("click",function(){
        $(this).addClass("active").siblings().removeClass("active");
        var obj =this;
        $.each($(".page-sidebar-menu>li:gt(0)"),function(i,v){
            if($(v).data("level") ==$(obj).data("level")){$(v).show();}
            else{$(v).hide();}
        });
        //$(".page-sidebar-menu").find("li[data-level='"+$(this).data("level")+"']" ).show().siblings("not:li[data-level='"+$(this).data("level"):gt(0)").hide();
    });
    var pathname = window.location.pathname.replace("#", "");
    if(pathname=="/paas/serverdetailedrealtime" || pathname=="/paas/serversysrealtime" || pathname=="/paas/serversteprealtime"){
        pathname="/paas/serverrealtime";
    }
    if(pathname=="/paas/serverdetailedhistory" || pathname=="/paas/serversyshistory" || pathname=="/paas/serverstephistory"){
        pathname="/paas/serverhistory";
    }
    var li = $("a[href='" + pathname + "']").parent("li");
    var level =$(li).data("level");
//    var pli =$(li).parents("li").data("level");
    li.addClass("active");
    $(li).parent("ul").show();
    $(li).parents("li").addClass("open");
    $(li).parents("li").find(".arrow ").addClass("open");
//    console.log( $("#firstMenu>li[data-level='"+level+"']"))
    $("#firstMenu>li[data-level='"+level+"']").trigger("click");
    jQuery(document).ready(function () {
//        $(".page-content").height($(".page-sidebar-menu").height())
//        App.init(); // initlayout and core plugins
//        Index.init();
//        bootbox.setDefaults("locale", "zh_CN");//bootbox默认中文
    <#--var loginId='${Session["href"]}';-->
    <#--if(loginId=="" || loginId=="null"){-->
    <#--location.href="/dologin";-->
    <#--}-->

        function loginOutT() {
            bootbox.confirm("是否退出登录?", function (result) {
                if (result) {
                    $.post("/loginOut", {}, function (data) {
                    });
                    location.href = "/dologin";
                }
            });
        }
    });
</script>

    <#nested >
</body>
</html>
</#macro>