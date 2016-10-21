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
            <#--<li role="presentation"><a role="menuitem" tabindex="-1">LIS</a></li>-->
            <#--<li role="presentation"><a role="menuitem" tabindex="-1">EMPI</a></li>-->
            <#--<li role="presentation"><a role="menuitem" tabindex="-1">CIS</a></li>-->
            <#--<li role="presentation"><a role="menuitem" tabindex="-1">CDR</a></li>-->
        </ul>
        <div class="btn-group pull-right mt10 mb10">
            <button type="button" class=" btn btn-default time active" id="time1" style="height:30px">当前一小时</button>
            <button type="button" class="  btn btn-default time" id="time2" style="height:30px">当天</button>
            <button type="button" data-toggle="dropdown"  class="  btn  btn-default time" id="time3"
                    style="height:30px;border-radius:0px 4px 4px 0px">指定小时 <i class="fa  fa-caret-down"></i></button>

            <ul class="dropdown-menu" role="menu" id="time3v" aria-labelledby="dropdownMenu1"
                style="max-height: 200px;margin-top:12px;overflow:scroll;">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">0:00-0:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">1:00-1:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">2:00-2:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">3:00-3:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">4:00-4:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">5:00-5:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">6:00-6:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">7:00-7:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">8:00-8:59</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">9:00-9:59</a></li>
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
            <span>服务列表</span>
        </div>
        <div class="col-md-3 p7 pr15">
            <div class="input-group">
                <input type="text" class="form-control" id="keyword" placeholder="服务名称">
                    <span style="cursor: pointer;" class="input-group-btn"><button type="button"
                                                                                   class="btn btn-primary"
                                                                                   id="querybtn">查询
                    </button></span>
            </div>
        </div>
        <div class=" pl0 pr0">
            <table id="fTable" class="table table-head  table-condensed flip-content">
                <thead class="flip-content">
                <tr>
                    <th>服务名称</th>
                    <th class="numeric sorting">调用次数</th>
                    <th class="numeric sorting">平均耗时</th>
                    <th class="numeric">最小耗时</th>
                    <th class="numeric">最大耗时</th>
                    <th class="numeric">吞吐量</th>
                    <th class="numeric sorting">失败次数</th>
                    <th class="numeric">失败率</th>
                    <th class="numeric">显示图表</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><i class="fa  icon cp fa-chevron-down"></i> EMPI基础服务</td>
                    <td class="numeric">100</td>
                    <td class="numeric">10</td>
                    <td class="numeric">3</td>
                    <td class="numeric">15</td>
                    <td class="numeric">100</td>
                    <td class="numeric red">11</td>
                    <td class="numeric">1%</td>
                    <td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td>
                </tr>
                <tr class="" style="display: none">
                    <td colspan="9">
                        <div class="ml15 mr15">
                            <table class="table table-head  table-condensed flip-content">
                                <thead class="flip-content ">
                                <tr>
                                    <th class="">服务名称</th>
                                    <th class="numeric ">调用次数</th>
                                    <th class="numeric ">平均耗时</th>
                                    <th class="numeric ">最小耗时</th>
                                    <th class="numeric ">最大耗时</th>
                                    <th class="numeric ">吞吐量</th>
                                    <th class="numeric ">失败次数</th>
                                    <th class="numeric ">失败率</th>
                                    <th class="numeric">显示图表</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>192.168.0.1</td>
                                    <td class="numeric">100</td>
                                    <td class="numeric">10</td>
                                    <td class="numeric">3</td>
                                    <td class="numeric">15</td>
                                    <td class="numeric">100</td>
                                    <td class="numeric">9</td>
                                    <td class="numeric">1%</td>
                                    <td class="numeric"><i class="fa  fa-bar-chart-o"></i></td>
                                </tr>
                                <tr>
                                    <td>192.168.0.2</td>
                                    <td class="numeric">100</td>
                                    <td class="numeric">10</td>
                                    <td class="numeric">3</td>
                                    <td class="numeric">15</td>
                                    <td class="numeric">100</td>
                                    <td class="numeric">9</td>
                                    <td class="numeric">1%</td>
                                    <td class="numeric"><i class="fa  fa-bar-chart-o"></i></td>
                                </tr>
                                <tr>
                                    <td>192.168.0.3</td>
                                    <td class="numeric">100</td>
                                    <td class="numeric">10</td>
                                    <td class="numeric">3</td>
                                    <td class="numeric">15</td>
                                    <td class="numeric">100</td>
                                    <td class="numeric">9</td>
                                    <td class="numeric">1%</td>
                                    <td class="numeric"><i class="fa  fa-bar-chart-o"></i></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><i class="fa  icon cp fa-chevron-down"></i> EMPI基础服务</td>
                    <td class="numeric">101</td>
                    <td class="numeric">10</td>
                    <td class="numeric">3</td>
                    <td class="numeric">15</td>
                    <td class="numeric">100</td>
                    <td class="numeric">9</td>
                    <td class="numeric">1%</td>
                    <td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td>
                </tr>
                <tr class="" style="display: none"><td colspan="9">
                <div class="ml15 mr15">
                <table class="table table-head  table-condensed flip-content">
                <thead class="flip-content ">
                <tr>
                <th class="">服务名称</th>
                <th class="numeric ">调用次数</th>
                <th class="numeric ">平均耗时</th>
                <th class="numeric ">最小耗时</th>
                <th class="numeric ">最大耗时</th>
                <th class="numeric ">吞吐量</th>
                <th class="numeric ">失败次数</th>
                <th class="numeric ">失败率</th>
                <th class="numeric">显示图表</th>
                </tr>
                </thead>
                <tbody>
                <tr><td >192.168.0.1</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.2</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.3</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                </tbody>
                </table>
                </div>
                </td></tr>
                <tr><td><i class="fa  icon cp fa-chevron-down"></i> EMPI基础服务</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td></tr>
                <tr class="" style="display: none"><td colspan="9">
                <div class="ml15 mr15">
                <table class="table table-head  table-condensed flip-content">
                <thead class="flip-content ">
                <tr>
                <th class="">服务名称</th>
                <th class="numeric ">调用次数</th>
                <th class="numeric ">平均耗时</th>
                <th class="numeric ">最小耗时</th>
                <th class="numeric ">最大耗时</th>
                <th class="numeric ">吞吐量</th>
                <th class="numeric ">失败次数</th>
                <th class="numeric ">失败率</th>
                <th class="numeric">显示图表</th>
                </tr>
                </thead>
                <tbody>
                <tr><td >192.168.0.1</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.2</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.3</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                </tbody>
                </table>
                </div>
                </td></tr>
                <tr><td><i class="fa  icon cp fa-chevron-down"></i> EMPI基础服务</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td></tr>
                <tr class="" style="display: none"><td colspan="9">
                <div class="ml15 mr15">
                <table class="table table-head  table-condensed flip-content">
                <thead class="flip-content ">
                <tr>
                <th class="">服务名称</th>
                <th class="numeric ">调用次数</th>
                <th class="numeric ">平均耗时</th>
                <th class="numeric ">最小耗时</th>
                <th class="numeric ">最大耗时</th>
                <th class="numeric ">吞吐量</th>
                <th class="numeric ">失败次数</th>
                <th class="numeric ">失败率</th>
                <th class="numeric">显示图表</th>
                </tr>
                </thead>
                <tbody>
                <tr><td >192.168.0.1</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.2</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.3</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                </tbody>
                </table>
                </div>
                </td></tr>
                <tr><td><i class="fa  icon cp fa-chevron-down"></i> EMPI基础服务</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td></tr>
                <tr class="" style="display: none"><td colspan="9">
                <div class="ml15 mr15">
                <table class="table table-head  table-condensed flip-content">
                <thead class="flip-content ">
                <tr>
                <th class="">服务名称</th>
                <th class="numeric ">调用次数</th>
                <th class="numeric ">平均耗时</th>
                <th class="numeric ">最小耗时</th>
                <th class="numeric ">最大耗时</th>
                <th class="numeric ">吞吐量</th>
                <th class="numeric ">失败次数</th>
                <th class="numeric ">失败率</th>
                <th class="numeric">显示图表</th>
                </tr>
                </thead>
                <tbody>
                <tr><td >192.168.0.1</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.2</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.3</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                </tbody>
                </table>
                </div>
                </td></tr>
                <tr><td><i class="fa  icon cp fa-chevron-down"></i> EMPI基础服务</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td></tr>
                <tr class="" style="display: none"><td colspan="9">
                <div class="ml15 mr15">
                <table class="table table-head  table-condensed flip-content">
                <thead class="flip-content ">
                <tr>
                <th class="">服务名称</th>
                <th class="numeric ">调用次数</th>
                <th class="numeric ">平均耗时</th>
                <th class="numeric ">最小耗时</th>
                <th class="numeric ">最大耗时</th>
                <th class="numeric ">吞吐量</th>
                <th class="numeric ">失败次数</th>
                <th class="numeric ">失败率</th>
                <th class="numeric">显示图表</th>
                </tr>
                </thead>
                <tbody>
                <tr><td >192.168.0.1</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.2</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.3</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                </tbody>
                </table>
                </div>
                </td></tr>
                <tr><td><i class="fa  icon cp fa-chevron-down"></i> EMPI基础服务</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o cp" data-toggle="modal" href="#picEdit"></i></td></tr>
                <tr class="" style="display: none"><td colspan="9">
                <div class="ml15 mr15">
                <table class="table table-head  table-condensed flip-content">
                <thead class="flip-content ">
                <tr>
                <th class="">服务名称</th>
                <th class="numeric ">调用次数</th>
                <th class="numeric ">平均耗时</th>
                <th class="numeric ">最小耗时</th>
                <th class="numeric ">最大耗时</th>
                <th class="numeric ">吞吐量</th>
                <th class="numeric ">失败次数</th>
                <th class="numeric ">失败率</th>
                <th class="numeric">显示图表</th>
                </tr>
                </thead>
                <tbody>
                <tr><td >192.168.0.1</td><td class="numeric sorting">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.2</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                <tr><td >192.168.0.3</td><td class="numeric">100</td><td class="numeric">10</td><td class="numeric">3</td><td class="numeric">15</td><td class="numeric">100</td><td class="numeric">9</td><td class="numeric">1%</td><td class="numeric"><i class="fa  fa-bar-chart-o"></i></td></tr>
                </tbody>
                </table>
                </div>
                </td></tr>
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
<script src="${contextPath}/assets/plugins/echarts/echarts-all.js" type="text/javascript"></script>
<script src="${contextPath}/js/serverrealtime.js" type="text/javascript"></script>
</@foot.foot>