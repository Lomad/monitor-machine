<#import "master/head.ftl" as head/>
<#import "master/foot.ftl" as foot/>
<#import "common/modal.ftl" as modal/>
<@head.head>
    <#assign contextPath=request.contextPath>

</@head.head>
<!-- BEGIN PAGE -->
<div class="row bgf bb1" style="margin-top:-15px;">
    <div class="col-md-12  lh50">
        <div class="col-md-8">
            <span>
                ${serverAppName} > ${transactionTypeName} >
                <#if type == "指定小时">
                ${time}
                <#else>
                ${type}
                </#if>
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
                <#if clientIpAddress =="">
                    > 所有客户端
                <#else>
                    > ${clientIpAddress}
                </#if>
             </span>
         </div>
         <div class="col-md-2">
             <span class="dropdown-toggle cp pull-right" id="statusvalue" data-toggle="dropdown" id="">
                     <#if status =="">
                         请选择状态
                     <#else>
                         ${status}
                     </#if>
                     <i class="fa  fa-caret-down"></i>
             </span>
             <ul class="dropdown-menu mr15 pull-right"  id="statusselect" role="menu" aria-labelledby="dropdownMenu1">
                <#--<li role="presentation"><a role="menuitem" tabindex="-1" style="color: #808080">请选择状态</a></li>-->
                <li role="presentation"><a role="menuitem" tabindex="-1">成功</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1">失败</a></li>
             </ul>
         </div>
        <div class="col-md-2">
             <span class="dropdown-toggle cp pull-right" id="systemvalue" data-toggle="dropdown" id="">
                     <#if clientAppName =="">
                         请选择消费系统
                     <#else>
                         ${clientAppName}
                     </#if>
                     <i class="fa  fa-caret-down"></i>
             </span>
            <ul class="dropdown-menu mr15 pull-right"  id="systemselect" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-1">His</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1">Lis</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="row mt15 ml0 mr0 bgf b1">
    <div class="getContent">
        <div class="col-md-12 lh50">
            <span>调用次数列表</span>
        </div>
        <div class=" pl0 pr0">
            <table id="fTable" class="table table-head  flip-content">
                <#--<thead class="flip-content">

                </thead>
                <tbody>

                </tbody>-->
            </table>
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
<input type="hidden" id="time" value="${time}">
<input type="hidden" id="clientAppName" value="${clientAppName}">
<input type="hidden" id="clientIpAddress" value="${clientIpAddress}">
<input type="hidden" id="serverIpAddress" value="${serverIpAddress}">
<input type="hidden" id="status" value="${status}">
<@foot.foot>
    <#assign contextPath=request.contextPath>

    <script src="${contextPath}/js/clientdetailedrealtime.js" type="text/javascript"></script>
</@foot.foot>