<#import "master/head.ftl" as head/>
<#import "master/foot.ftl" as foot/>
<#import "common/modal.ftl" as modal/>
<@head.head>
    <#assign contextPath=request.contextPath>
</@head.head>
<div class="row ">
    <div class="col-md-8 col-sm-8">
        <div class="portlet box blue">
            <div class="portlet-title">
                <div class="caption">服务监控</div>
                <div class="tools">
                   <#--<a href="" class="collapse"></a>-->
                   <#--<a href="" class="reload" id="idBtnReload"></a>-->
                </div>
            </div>

            <div class="portlet-body" style="background:#f1f7f9;">
                <div class="row" style="margin-left: inherit;margin-right: inherit">
                    <div class="col-xs-7 col-sm-7 col-md-7" style="height: 25%;">
                        <div class="row" >
                            <div id="id_svr_count" style="height: 150px;width: 100%;"><#--今天昨天服务总量--></div>
                            <div id="id_svr_wrong" style="height: 150px;width: 100%;"><#--今天昨天服务错误总量--></div>
                        </div>
                    </div>
                    <div id="id_svr_client" class="col-xs-5 col-sm-5 col-md-5" style="height: 300px;"><#--今天的服务客户端类型分布图--></div>
                </div>
            </div>
        </div>

        <div class="portlet box blue">
            <div class="portlet-title">
                <div class="caption">服务概况</div>
            </div>
            <div class="portlet-body">
                <div class="row" id="id_svr_app">

                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-4">
        <div class="portlet box blue">
            <div class="portlet-title">
                <div class="caption">告警数(最近1小时)</div>
                <div class="tools" style="font-size: 18px;margin-top: -3px;">
                    共
                    <a href="" id="id_alarm_sum" style="color: #f5f5f5"></a>
                    条
                </div>
            </div>
            <div class="portlet-body" style="background:#f1f7f9;">
                <div class="scroller" style="height: 435px;" data-always-visible="1" data-rail-visible1="1">
                    <ul class="chats" style="margin-top:5px" id="id_alarm_list">

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<@foot.foot>
    <#assign contextPath=request.contextPath>
    <script src="${contextPath}/assets/scripts/echarts.js" type="text/javascript"></script>
    <script src="${contextPath}/js/overview.js" type="text/javascript"></script>
</@foot.foot>