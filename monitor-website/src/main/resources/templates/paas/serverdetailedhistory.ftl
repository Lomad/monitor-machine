<#import "master/head.ftl" as head/>
<#import "master/foot.ftl" as foot/>
<#import "common/modal.ftl" as modal/>
<@head.head>
    <#assign contextPath=request.contextPath>

</@head.head>
<!-- BEGIN PAGE -->
<div class="row bgf bb1" style="margin-top:-15px;">
    <div class="col-md-12  lh50">
        <span>${serverAppName} > ${transactionTypeName}
        <#if serverIpAddress =="">
            > 所有主机
        <#else>
            > ${serverIpAddress}
        </#if>
        <#if clientAppName =="">
        ${clientAppName}
        <#else>
            > ${clientAppName}
        </#if>
      <#if user =="server">
          <#if clientIpAddress =="">
              > 所有客户端
          <#else>
              > ${clientIpAddress}
          </#if>
      </#if>
            > ${value}
         </span>
            <span class="dropdown-toggle cp pull-right " id="statusvalue" data-toggle="dropdown" id="">
                 <#if status =="">
                     请选择状态
                 <#else>
                 ${status}
                 </#if>
                     <i class="fa  fa-caret-down"></i></span>
        <ul class="dropdown-menu mr15 pull-right"  id="statusselect" role="menu" aria-labelledby="dropdownMenu1">
            <li role="presentation"><a role="menuitem" tabindex="-1">成功</a></li>
            <li role="presentation"><a role="menuitem" tabindex="-1">失败</a></li>
        </ul>


    </div>

</div>
<div class="row mt15 ml0 mr0 bgf b1">
    <div class="getContent">
        <div class="col-md-12 lh50">
            <span>服务列表</span>
        </div>
    <#--<div class="col-md-3 p7 pr15">-->
    <#--<div class="input-group">-->
    <#--<input type="text" class="form-control" id="keyword" placeholder="系统名称">-->
    <#--<span style="cursor: pointer;" class="input-group-btn"><button type="button"-->
    <#--class="btn btn-primary"-->
    <#--id="querybtn">查询-->
    <#--</button></span>-->
    <#--</div>-->
    <#--</div>-->
        <div class=" pl0 pr0">
            <table id="fTable" class="table table-head  flip-content">
                <thead class="flip-content">
                <tr>

                    <th class="">服务名称</th>
                    <th class="">服务器IP</th>
                    <th class="">消费方系统名称</th>
                    <th class="">消费方IP地址</th>
                    <th class="numeric sorting"  data-id="avg">耗时</th>
                    <th class="">状态</th>
                    <th>时间</th>
                    <th class="">详情</th>
                </tr>
                </thead>
                <tbody>
               <#--<tr>-->
                <#--<div id="loading"  style="text-align: center">-->
                    <#--<div style="width: 94%">-->
                        <#--<img src="${contextPath}/images/loading.gif">-->
                     <#--</div>-->
                 <#--</div>-->
               <#--</tr>-->
                <#--<tr>-->
                    <#--<td><i class="fa  icon cp fa-chevron-down"></i> EMPI基础服务</td>-->
                    <#--<td class="numeric">100</td>-->
                    <#--<td class="numeric">10</td>-->
                    <#--<td class="numeric">8</td>-->
                    <#--<td class="numeric">8</td>-->
                    <#--<td class="numeric">3</td>-->
                    <#--<td class="numeric">15</td>-->
                    <#--<td class="numeric">100</td>-->
                    <#--<td class="numeric red">11</td>-->
                    <#--<td class="numeric">1%</td>-->
                    <#--<td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td>-->
                <#--</tr>-->
                <#--<tr class="" style="display: none">-->
                    <#--<td colspan="11">-->
                        <#--<div class="ml15 mr15">-->
                            <#--<table class="table table-head  table-condensed flip-content">-->
                                <#--<thead class="flip-content ">-->
                                <#--<tr>-->
                                    <#--<th class="">服务名称</th>-->
                                    <#--<th class="numeric ">调用次数</th>-->
                                    <#--<th class="numeric ">平均耗时</th>-->
                                    <#--<th class="numeric">99%</th>-->
                                    <#--<th class="numeric">95%</th>-->
                                    <#--<th class="numeric ">最短耗时</th>-->
                                    <#--<th class="numeric ">最大耗时</th>-->
                                    <#--<th class="numeric ">吞吐量</th>-->
                                    <#--<th class="numeric ">失败次数</th>-->
                                    <#--<th class="numeric ">失败率</th>-->
                                    <#--<th class="numeric">显示图表</th>-->
                                <#--</tr>-->
                                <#--</thead>-->
                                <#--<tbody>-->
                                <#--<tr>-->
                                    <#--<td>192.168.0.1</td>-->
                                    <#--<td class="numeric">100</td>-->
                                    <#--<td class="numeric">10</td>-->
                                    <#--<td class="numeric">3</td>-->
                                    <#--<td class="numeric">15</td>-->
                                    <#--<td class="numeric">100</td>-->
                                    <#--<td class="numeric">9</td>-->
                                    <#--<td class="numeric">1%</td>-->
                                    <#--<td class="numeric"><i class="fa  fa-bar-chart-o"></i></td>-->
                                <#--</tr>-->
                                <#--<tr>-->
                                    <#--<td>192.168.0.2</td>-->
                                    <#--<td class="numeric">100</td>-->
                                    <#--<td class="numeric">10</td>-->
                                    <#--<td class="numeric">3</td>-->
                                    <#--<td class="numeric">15</td>-->
                                    <#--<td class="numeric">100</td>-->
                                    <#--<td class="numeric">9</td>-->
                                    <#--<td class="numeric">1%</td>-->
                                    <#--<td class="numeric"><i class="fa  fa-bar-chart-o"></i></td>-->
                                <#--</tr>-->
                                <#--<tr>-->
                                    <#--<td>192.168.0.3</td>-->
                                    <#--<td class="numeric">100</td>-->
                                    <#--<td class="numeric">10</td>-->
                                    <#--<td class="numeric">3</td>-->
                                    <#--<td class="numeric">15</td>-->
                                    <#--<td class="numeric">100</td>-->
                                    <#--<td class="numeric">9</td>-->
                                    <#--<td class="numeric">1%</td>-->
                                    <#--<td class="numeric"><i class="fa  fa-bar-chart-o"></i></td>-->
                                <#--</tr>-->
                                <#--</tbody>-->
                            <#--</table>-->
                        <#--</div>-->
                    <#--</td>-->
                <#--</tr>-->

                </tbody>
            </table>

        <div id="loading"  style="text-align: center;padding: 50px;">
        <div style="width: 94%">
        <img src="${contextPath}/images/loading.gif">
        </div>
        </div>

        </div>
    </div>
</div>
<@modal.editModal id="xqEdit" modaltitle=""    title="详情"   buttonId="">
<div class="row p15" >
    <div class="col-md-12" style="height:400px;" >
        <table id="xqTable" class="table table-head  table-condensed flip-content">
            <thead class="flip-content">
            <tr>

                <th class="" >名称</th>
                <th class="">值</th>

            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
</@modal.editModal>
<input type="hidden" id="transactionTypeName" value="${transactionTypeName}">
<input type="hidden" id="serverAppName" value="${serverAppName}">
<input type="hidden" id="type" value="${type}">
<input type="hidden" id="value" value="${value}">
<input type="hidden" id="clientAppName" value="${clientAppName}">
<input type="hidden" id="clientIpAddress" value="${clientIpAddress}">
<input type="hidden" id="serverIpAddress" value="${serverIpAddress}">
<input type="hidden" id="status" value="${status}">
<input type="hidden" id="dateValue" value="${dateValue}">
<input type="hidden" id="historypagetype" value="${historyPageType}">
<@foot.foot>

    <#assign contextPath=request.contextPath>
<script src="${contextPath}/js/serverdetailedhistory.js" type="text/javascript"></script>
</@foot.foot>