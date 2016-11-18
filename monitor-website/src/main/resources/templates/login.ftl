<!DOCTYPE html>
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8" />
    <title>统一权限登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <meta name="MobileOptimized" content="320">
<#assign contextPath=request.contextPath>
    <link href="${contextPath}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${contextPath}/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN THEME STYLES -->
    <link href="${contextPath}/assets/css/login.css" rel="stylesheet" type="text/css" />
    <link href="${contextPath}/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color" />
    <!-- END THEME STYLES -->
    <link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed" style="overflow:hidden">
<!-- BEGIN HEADER -->
<!-- BEGIN HEADER -->
<div class="header loginheader" style="background-color: #0e508a !important;">
    <div class="header-inner">
        <div class="row">
            <div class="col-md-3">
                <a class="navbar-brand" href="index.html">
                    <img src="assets/img/logo.png" alt="logo" class="float-l" />
                    <span class="logotext margin-left-10">统一监控平台</span>
                </a>
            </div>
            <div class="col-md-9" >
            </div>
        </div>
    </div>
</div>
<!-- END HEADER -->
<div class="clearfix"></div>

<div class="page-container loginbody">
    <div class="row">
        <div class="logincenter">
            <div class="loginbg float-l">
            </div>

            <div class="float-l" style="margin-top:50px;">
                <!--登录框-->
                <div class="loginbox">
                    <div class="loginbox-title">
                        <span class="loginbox-linel"></span>
                        <span class="loginbox-text">登录</span>
                        <span class="loginbox-liner"></span>
                    </div>
                    <div class="loginbox-input">
                        <div class="input-icon loginbox-inputbox">
                            <input class="form-control no-border loginuser" autocomplete="off" placeholder="用户名" id="username" type="text">
                        </div>
                        <div class="input-icon loginbox-inputbox">
                            <input class="form-control no-border loginpsw" autocomplete="off" placeholder="密码" id="password" type="password">
                        </div>
                        <div class="rmpsw">
&nbsp;
                            <#--<input name="a" value="option1" type="checkbox">-->
                            <#--<lable>记住密码</lable>-->

                        </div>
                        <button type="button" class="btn btn-primary loginbutton" id="loginBtn">确定</button>

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</div>
<div class="footer" style="background: #fff; border-top: 1px solid #e2e2e2;">
    <div id="yymc" class="footer-inner">
    </div>
    <div class="footer-tools" style="color: #aaaaaa;">
        <span>&copy;2016 WinningHealth</span>
    </div>
</div>

<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${contextPath}/assets/plugins/respond.min.js"></script>
<script src="${contextPath}/assets/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="${contextPath}/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<!-- END JAVASCRIPTS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${contextPath}/assets/plugins/bootbox/bootbox.min.js" type="text/javascript" ></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- END PAGE LEVEL PLUGINS -->
<script src="${contextPath}/js/login.js" type="text/javascript"></script>
<!-- END JAVASCRIPTS -->

</body>
<!-- END BODY -->
</html>