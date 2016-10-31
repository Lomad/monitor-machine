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
             <i class="fa  fa-caret-down"></i>
         </span>
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

                    <th class="" >服务名称</th>
                    <th class="">服务器IP</th>
                    <th class="">消费方系统名称</th>
                    <th class="">消费方IP地址</th>
                    <th class=""  data-id="avg">耗时</th>
                    <th class="">状态</th>
                    <th>时间</th>
                    <th class="">详情</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
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
<input type="hidden" id="value" value="${value}">
<input type="hidden" id="clientAppName" value="${clientAppName}">
<input type="hidden" id="clientIpAddress" value="${clientIpAddress}">
<input type="hidden" id="serverIpAddress" value="${serverIpAddress}">
<input type="hidden" id="status" value="${status}">
<input type="hidden" id="historypagetype" value="${historyPageType}">
<@foot.foot>

    <#assign contextPath=request.contextPath>
<script src="${contextPath}/js/clientdetailedhistory.js" type="text/javascript"></script>
</@foot.foot>