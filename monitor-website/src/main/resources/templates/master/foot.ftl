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


<script type="text/javascript">
    var pathname = window.location.pathname.replace("#", "");
    if (pathname.indexOf("userwizard") > -1) {
        pathname = "/user";
    }
    var li = $("a[href='" + pathname + "']").parent("li");
    li.addClass("active");
    $(li).parent("ul").show();
    $(li).parents("li").addClass("open");
    $(li).parents("li").find(".arrow ").addClass("open");
    jQuery(document).ready(function () {
        $(".page-content").height($(".page-sidebar-menu").height())
        App.init(); // initlayout and core plugins
        Index.init();
        bootbox.setDefaults("locale", "zh_CN");//bootbox默认中文
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