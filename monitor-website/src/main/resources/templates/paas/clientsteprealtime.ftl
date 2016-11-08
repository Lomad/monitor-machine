<#import "master/head.ftl" as head/>
<#import "master/foot.ftl" as foot/>
<#import "common/modal.ftl" as modal/>
<@head.head>
    <#assign contextPath=request.contextPath>
</@head.head>

<div class="row bgf bb1" style="margin-top:-15px;">
    <div class="col-md-12  lh50">
        <span>
            ${clientAppName} > ${serverAppName} > ${transactionTypeName} >
            <#if type = "指定小时">
               ${time}
            <#else>
               ${type}
            </#if>
         </span>
        <#--<span class="dropdown-toggle cp pull-right " id="serverIpAddresss" data-toggle="dropdown" id="">
            <#if serverIpAddress = "">
                所有主机
            <#else>
                ${serverIpAddress}
            </#if>
            <i class="fa  fa-caret-down"></i>
        </span>
        <ul class="dropdown-menu mr15 pull-right"  id="serverIpAddresss2" role="menu" aria-labelledby="dropdownMenu1">
            <li role="presentation"><a role="menuitem" tabindex="-1">192.168.0.1</a></li>
            <li role="presentation"><a role="menuitem" tabindex="-1">192.168.0.2</a></li>
        </ul>-->
    </div>

</div>
<div class="row mt15 ml0 mr0 bgf b1">
    <div class="getContent">
        <div class="col-md-12 lh50">
            <span>服务步骤耗时占比</span>
        </div>
        <div class=" pl0 pr0" id="echart" style="width:1000px;height:200px">
        </div>
        <div class="col-md-12 lh50">
            <span>服务步骤明细列表</span>
        </div>
        <div class=" pl0 pr0">
            <table id="fTable" class="table table-head  table-condensed flip-content">
                <thead class="flip-content">
                <tr>
                    <th>序号</th>
                    <th>步骤名称</th>
                    <th class="numeric" >调用次数</th>
                    <th class="numeric" >平均耗时</th>
                    <th class="numeric">99%</th>
                    <th class="numeric">95%</th>
                    <th class="numeric">最短耗时</th>
                    <th class="numeric">最大耗时</th>
                    <th class="numeric">吞吐量</th>
                    <th class="numeric">失败次数</th>
                    <th class="numeric">失败率</th>
                    <th class="numeric">方差</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<input type="hidden" id="transactionTypeName" value="${transactionTypeName}">
<input type="hidden" id="clientAppName" value="${clientAppName}">
<input type="hidden" id="serverAppName" value="${serverAppName}">
<input type="hidden" id="type" value="${type}">
<input type="hidden" id="time" value="${time}">
<input type="hidden" id="serverIpAddresshidden" value="${serverIpAddress}">

<@foot.foot>
    <#assign contextPath = request.contextPath>

    <script src="${contextPath}/assets/plugins/echarts/echarts-all.js" type="text/javascript"></script>
    <script src="${contextPath}/js/clientsteprealtime.js" type="text/javascript"></script>
</@foot.foot>