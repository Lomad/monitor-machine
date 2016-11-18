<#macro head>
<!DOCTYPE html>
<#--<html>-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>统一监控平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>

    <#assign contextPath=request.contextPath>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${contextPath}/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN THEME STYLES -->
    <link href="${contextPath}/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="${contextPath}/assets/css/custom.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME STYLES -->
    <!-- BEGIN THEME Datatables -->
    <link href="${contextPath}/assets/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/plugins/dataTables/dataTables.responsive.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/assets/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet"
          type="text/css"/>
    <!-- END THEME Datatables -->

<#--<style type="text/css">-->

<#--.page-sidebar-closed .menuname, .page-sidebar-hovering .menuname-->
<#--{-->
<#--display: none;-->
<#--}-->
<#--ul.page-sidebar-menu ul.sub-menu > li.active > a, ul.page-sidebar-menu ul.sub-menu > li > a:hover {-->
<#--color: #818181 !important;-->
<#--background: #efefef !important-->
<#--}-->
<#--ul.page-sidebar-menu ul.sub-menu > li.active > a, ul.page-sidebar-menu ul.sub-menu > li > a:hover, ul.page-sidebar-menu ul.sub-menu > li.open > a {-->
<#--color: #818181 !important;-->
<#--background: #efefef url(/images/selected_left.png)no-repeat !important;-->
<#--}-->
<#--</style>-->
    <#nested >
</head>
<body class="page-header-fixed">
<!-- BEGIN HEADER -->
<div class="header navbar navbar-inverse navbar-fixed-top">
    <!-- BEGIN TOP NAVIGATION BAR -->
    <div class="header-inner">
        <!-- BEGIN LOGO -->
        <a class="navbar-brand" href="#" style="padding: 0px">
            <img id="logo_sys" src="${contextPath}/images/1_logo.png" alt="logo" class="img-responsive hidden-480"
                 style="height: 52px; margin-left: 0px; margin-top: 0px"/>
        </a>
        <ul id="firstMenu" class="nav navbar-nav " style="height: 42px">
            <!-- BEGIN USER LOGIN DROPDOWN -->

            <#--<li class="dropdown user  firstMenu" data-level="1">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" >IAAS/系统</a>
            </li>-->
            <li class="dropdown user firstMenu" data-level="2">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">服务监控</a>
            </li>
            <#--<li class="dropdown user  firstMenu" data-level="3">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">SAAS/业务</a>
            </li>
            <li class="dropdown user  firstMenu" data-level="4">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">告警预览</a>
            </li>-->

            <!-- END USER LOGIN DROPDOWN -->
        </ul>
        <!-- END LOGO -->
        <!-- BEGIN TOP NAVIGATION MENU -->
        <ul class="nav navbar-nav pull-right" id="usernav">
            <!-- BEGIN USER LOGIN DROPDOWN -->

            <li class="">
                <div>
                    <div class="icon-info cp" style="position: absolute; top: 10px; right: 100px; height: 32px;
                            width: 32px; background: url(../images/admin.png) no-repeat;">
                    </div>
                    <div style="right: 50px; top: 10px; position: absolute; display: block; color: #143e58;
                            font-size: 12px; line-height: 1.2em; width: 45px" 　>
                            <span style="color: white" class="cp">
                               管理员<br/>
                               00123
                            </span>
                    </div>
                    <div id="shuxian" style="right: 47px; top: 10px; position: absolute; height: 22px;
                            width: 1px; background: #fff; margin-top: 5px; width: 2px">
                    </div>
                    <div class="icon-info" style="position: absolute; top: 17px; right: 5px; height: 32px;
                            width: 32px; background: url(../images/tuichu1.png) no-repeat;">
                    </div>
                    <div style="right: -10px; top: 17px; position: absolute; display: block; color: #143e58;
                            font-size: 14px; line-height: 1.2em; width: 30px">
                        <span style="color: white; cursor: pointer" onclick="loginOutT()">退出 </span>
                    </div>

                </div>
            </li>
            <!-- END USER LOGIN DROPDOWN -->
        </ul>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->
<div class="clearfix"></div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <ul class="page-sidebar-menu">
            <li>
                <!--  BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="row">
                    <div class="col-md-6" style="height: 43px">
                        <div class="menuname hidden-phone" style="padding-left: 20px; height: 29px; padding-top: 20px;
                                font-weight: bold; color: black; font-size: 13px; text-align: center">
                            菜单导航
                        </div>
                    </div>
                    <div class="col-md-5">
                        <a class="sidebar-toggler hidden-phone pull-right" style="margin-bottom: 5px; margin-top: 10px">
                        </a>
                    </div>
                </div>
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
            </li>

            <#--<li class="" data-level="1" style="display:none">
                <a href="javascript:;">
                    <i class="fa fa-desktop fa-lg"></i>
                    <span class="title">权限管理</span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">

                    <li>
                        <a href="/user">
                            用户权限设置
                        </a>
                    </li>
                    <li>
                        <a href="/role">
                            角色权限设置
                        </a>
                    </li>
                </ul>
            </li>-->
            <li class="" data-level="2" style="display:none">
                <a href="${contextPath}/paas/overview">
                    <i class="fa fa-desktop fa-lg"></i>
                    <span class="title">概览
                    </span>
                </a>
            </li>
            <li class="" data-level="2" style="display:none">
                <a href="javascript:void(0)">
                    <i class="fa fa-desktop fa-lg"></i>
                    <span class="title">实时监控</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
                    <li data-level="2">
                        <a href="${contextPath}/paas/serverrealtime"" >服务方监控</a>
                    </li>
                    <li data-level="2">
                        <a href="${contextPath}/paas/clientrealtime"">消费方监控</a>
                    </li>
                </ul>
            </li>
            <li class="" data-level="2" style="display:none">
                <a href="javascript:;">
                    <i class="fa fa-desktop fa-lg"></i>
                    <span class="title">历史监控</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
                    <li data-level="2">
                        <a href="${contextPath}/paas/serverhistory" >服务方监控</a>
                    </li>
                    <li data-level="2">
                        <a href="${contextPath}/paas/clienthistory">消费方监控</a>
                    </li>
                </ul>
            </li>
            <#--<li class="" data-level="3" style="display:none">
                <a href="javascript:;">
                    <i class="fa fa-desktop fa-lg"></i>
                    <span class="title">日志查询</span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="/log/user">职工授权记录</a>
                    </li>
                    <li>
                        <a href="/log/role">角色授权记录</a>
                    </li>
                    <li>
                        <a href="/log/menu">功能权限授权记录</a>
                    </li>
                    <li>
                        <a href="/log/per">
                            数据权限授权记录
                        </a>
                    </li>
                    <li>
                        <a href="/log/prop">
                            属性权限授权记录
                        </a>
                    </li>
                    <li>
                        <a href="/log/operator">
                            操作员授权记录
                        </a>
                    </li>
                </ul>
            </li>-->
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
    <!-- BEGIN PAGE -->
    <div class="page-content">
</#macro>