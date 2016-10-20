<#import "master/head.ftl" as head/>
<#import "master/foot.ftl" as foot/>
<#import "common/modal.ftl" as modal/>
<@head.head>
    <#assign contextPath=request.contextPath>

</@head.head>
<!-- BEGIN PAGE -->
<div class="row bgf bb1"  style="margin-top:-15px;">
   <div class="col-md-12  lh50">
<span class="dropdown-toggle cp" data-toggle="dropdown" id="">HIS <i class="fa  fa-caret-down"></i></span>
       <ul class="dropdown-menu ml15" role="menu" aria-labelledby="dropdownMenu1">
           <li role="presentation"><a role="menuitem" tabindex="-1" >LIS</a></li>
           <li role="presentation"><a role="menuitem" tabindex="-1" >EMPI</a></li>
           <li role="presentation"><a role="menuitem" tabindex="-1" >CIS</a></li>
           <li role="presentation"><a role="menuitem" tabindex="-1" >CDR</a></li>
       </ul>
       <div class="btn-group btn-group-solid pull-right mt12 mb12">
           <button type="button" class=" btn-sm btn red" style="height:25px">当前一小时</button>
           <button type="button" class=" btn-sm btn yellow" style="height:25px">当天</button>
           <button type="button" data-toggle="dropdown" class=" btn-sm btn green dropdown-toggle" style="height:25px">指定小时</button>
           <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="max-height: 200px;margin-top:12px;overflow:scroll">
               <li role="presentation"><a role="menuitem" tabindex="-1" >0:00-0:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >1:00-1:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >2:00-2:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >3:00-3:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >4:00-4:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >5:00-5:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >6:00-6:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >7:00-7:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >8:00-8:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >9:00-9:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >10:00-10:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >11:00-11:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >12:00-12:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >13:00-13:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >14:00-14:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >15:00-15:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >16:00-16:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >17:00-17:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >18:00-18:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >19:00-19:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >20:00-20:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >21:00-21:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >22:00-22:59</a></li>
               <li role="presentation"><a role="menuitem" tabindex="-1" >23:00-23:59</a></li>
           </ul>
       </div>
   </div>

</div>
<div class="row mt15 ml0 mr0 mb15 bgf b1" >
    <div class="col-md-12 lh50">
        <span>服务列表</span>
    </div>
    <div class="col-md-12 pl0 pr0" >
        <table id="userTable" class="table table-head table-condensed flip-content">
        <thead class="flip-content">
                <tr>
                    <th>权限</th>
                    <th>用户编码</th>
                    <th class="numeric">用户名称</th>
                    <th class="numeric">身份证号</th>
                    <th class="numeric">性别</th>
                    <th class="numeric">记录状态</th>
                    <th class="numeric">操作</th>
                </tr>
                </thead>
<tbody>
<tr><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td></tr>
<tr><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td></tr>
<tr><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td></tr>
<tr><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td></tr><tr><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td></tr>

</tbody>
        </table>
    </div>
</div>
<@foot.foot>
    <#assign contextPath=request.contextPath>
</@foot.foot>