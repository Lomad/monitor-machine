<#import "master/head.ftl" as head/>
<#import "master/foot.ftl" as foot/>
<#import "common/modal.ftl" as modal/>
<@head.head>
    <#assign contextPath=request.contextPath>
<link rel="stylesheet" type="text/css" href="${contextPath}/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<style>
    .datepicker .cw {
        font-size: 14px;
        color: #332cc6;
        font-weight: bold !important;

    }
</style>
</@head.head>
<!-- BEGIN PAGE -->
<div class="row bgf bb1" style="margin-top: -15px">
    <div class="col-md-12 lh50">
    <span id="flname" class="dropdown-toggle cp" data-toggle="dropdown">选择系统 <i class="fa fa-caret-down"></i></span>
    <ul id="fl" class="dropdown-menu" role="menu" aria-labelledby="flname">
        <#--<li role="presentation"><a role="menuitem" tabindex="-1">CDR</a></li>-->
        <#--<li role="presentation"><a role="menuitem" tabindex="-1">ODR</a></li>-->
    </ul>

    <div class="input-group pull-right col-md-3" style="padding-right: 0px;">
        <div class="input-group-btn dropdown">
            <button id="selbtn" type="button" class="btn dropdown-toggle" data-toggle="dropdown">日查询 <i class="fa fa-angle-down"></i></button>
            <ul id="sel" class="dropdown-menu">
                <li><a data="day" href="#">日查询</a></li>
                <li><a data="week" href="#">周查询</a></li>
                <li><a data="month" href="#">月查询</a></li>
            </ul>
        </div>
        <div id="date_picker"  class="input-group input-medium date date-picker" data-date-end-date="+0d">
            <input id="datevalue" type="text" class="form-control" readonly>
            <span class="input-group-btn">
            <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
            </span>
        </div>
    </div>
    </div>
</div>
<div class="bgf mt15 mr10 ml10 b1">
    <div class="getContent">
          <div class="col-md-9 lh50">
              <span>服务大类明细</span>
          </div>
          <div class="col-md-3 p7 pr15">
                <div class="input-group">
                    <input id="keyword"  class="form-control"  type="text"  placeholder="服务名称">
                    <span class="cp input-group-btn"><button id="querybtn" class="btn btn-primary">查询</button>
                    </span>
                </div>
          </div>
        <div class="pl0 pr0">
          <table id="fTable" class="table table-head table-condensed flip-content">
              <thead class="flip-content">
                  <tr>
                      <th class="tac">服务大类名称</th>
                      <th>服务名称</th>
                      <th data-id="totalCount">调用次数</th>
                      <th data-id="avg">平均耗时</th>
                      <th>99%</th>
                      <th>95%</th>
                      <th>最短耗时</th>
                      <th>最大耗时</th>
                      <th>吞吐量</th>
                      <th data-id="failCount">失败次数</th>
                      <th>失败率</th>
                      <th class="numeric">方差</th>
                      <th class="numeric">显示图表</th>
                  </tr>
              </thead>
              <tbody>
              <#--<tr>-->
                    <#--<td rowspan="2" class="vam tac">EMPI</td>-->
                    <#--<td class="numeric">患者注册</td>-->
                    <#--<td class="numeric">1067</td>-->
                    <#--<td class="numeric">8</td>-->
                    <#--<td class="numeric">8</td>-->
                    <#--<td class="numeric">3</td>-->
                    <#--<td class="numeric">15</td>-->
                    <#--<td class="numeric">100</td>-->
                    <#--<td class="numeric red">11</td>-->
                    <#--<td class="numeric red">11</td>-->
                    <#--<td class="numeric red">11%</td>-->
                    <#--<td class="numeric">1</td>-->
                    <#--<td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td>-->
                <#--</tr>-->
              <#--<tr>-->
                  <#--<td class="numeric">患者注册</td>-->
                  <#--<td class="numeric">1067</td>-->
                  <#--<td class="numeric">8</td>-->
                  <#--<td class="numeric">8</td>-->
                  <#--<td class="numeric">3</td>-->
                  <#--<td class="numeric">15</td>-->
                  <#--<td class="numeric">100</td>-->
                  <#--<td class="numeric red">11</td>-->
                  <#--<td class="numeric red">11</td>-->
                  <#--<td class="numeric red">11%</td>-->
                  <#--<td class="numeric">1</td>-->
                  <#--<td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td>-->
              <#--</tr>-->
              </tbody>
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
<script src="${contextPath}/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript"></script>
<script src="${contextPath}/assets/plugins/echarts/echarts-all.js" type="text/javascript"></script>
<script src="${contextPath}/js/clienthistory.js" type="text/javascript"></script>
</@foot.foot>