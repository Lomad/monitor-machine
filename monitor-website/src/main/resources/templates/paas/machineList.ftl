<#import "master/head.ftl" as head/>
<#import "master/foot.ftl" as foot/>
<#import "common/modal.ftl" as modal/>
<@head.head>
    <#assign contextPath=request.contextPath>

</@head.head>
<!-- BEGIN PAGE -->
<div class="row bgf bb1" style="margin-top:-15px;">
    <div class="col-md-12  lh50">
        <span class="dropdown-toggle cp" id="flname" data-toggle="dropdown" id=""> <i class="fa  fa-caret-down"></i></span>
        <ul class="dropdown-menu ml15" id="fl" role="menu" aria-labelledby="dropdownMenu1">

        </ul>
        <div class="btn-group pull-right mt10 mb10">
            <button type="button" class=" btn btn-default time active" id="time1" style="height:30px">当前一小时</button>
            <button type="button" class="  btn btn-default time" id="time2" style="height:30px">当天</button>
            <button type="button" data-toggle="dropdown"  class="  btn  btn-default time" id="time3"
                    style="height:30px;border-radius:0px 4px 4px 0px">指定小时 <i class="fa  fa-caret-down"></i></button>

            <ul class="dropdown-menu" role="menu" id="time3v" aria-labelledby="dropdownMenu1"
                style="max-height: 200px;margin-top:12px;overflow:scroll;">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">00:00-00:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">01:00-01:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">02:00-02:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">03:00-03:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">04:00-04:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">05:00-05:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">06:00-06:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">07:00-07:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">08:00-08:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">09:00-09:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">10:00-10:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">11:00-11:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">12:00-12:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">13:00-13:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">14:00-14:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">15:00-15:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">16:00-16:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">17:00-17:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">18:00-18:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">19:00-19:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">20:00-20:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">21:00-21:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">22:00-22:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">23:00-23:59</a></li>
            </ul>
        </div>
    </div>

</div>
<div class="row mt15 ml0 mr0 bgf b1">
    <div class="getContent">
        <div class="col-md-9 lh50">
            <span>主机列表</span>
        </div>
        <div class="col-md-3 p7 pr15">
            <div class="input-group">
                <input type="text" class="form-control" id="keyword" placeholder="主机IP">
                <span style="cursor: pointer;" class="input-group-btn"><button type="button"
                                                                               class="btn btn-primary"
                                                                               id="querybtn">查询
                    </button></span>
            </div>
        </div>
        <div class=" pl0 pr0">
        <#--<form  id="form" method="post" action="/paas/serversysrealtime">-->
            <table id="fTable" class="table table-head  table-condensed flip-content">
                <thead class="flip-content">
                <tr>
                    <th class="numeric" data-id="totalCount">主机名称</th>
                    <th class="numeric">操作系统</th>
                    <th class="numeric">CPU厂商</th>
                    <th class="numeric">CPU型号</th>
                    <th class="numeric">CPU块数</th>
                    <th class="numeric">总内存</th>
                    <th class="numeric">交互内存</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<@modal.editModal id="picEdit" modaltitle=""  modalBig="modal-wide"  title="趋势图"   buttonId="">
<div class="row p15" id="echartRow">
    <div class="col-md-12" style="height:400px;" id="echart">

    </div>
</div>
</@modal.editModal>
<@foot.foot>
    <#assign contextPath=request.contextPath>
<script src="${contextPath}/assets/plugins/echarts/echarts-all.js" type="text/javascript"></script>
<script src="${contextPath}/js/serverrealtime.js" type="text/javascript"></script>
</@foot.foot>