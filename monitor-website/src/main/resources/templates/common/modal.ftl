<#macro editModal
id=""
title=""
buttonId="btnSave"
buttonName="保存"
buttonType="Button"
modalBig=""
modalheader=""
modaltitle="usermodal-title"
modalbody="modal-body-padding"
formId="">
<div id="${id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog ${modalBig}" role="document" style="width:1000px ">
        <div class="modal-content">
            <div class="modal-header ${modalheader}" style="height: 40px;">
                <button type="button" class="close" onclick="$('#${id}').modal('hide')" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title ${modaltitle}">${title}</h4>
            </div>
        <#--<#if action !="" >-->
            <#if formId!="">
            <form id="${formId}">
            </#if>
            <div class="modal-body ${modalbody}">
                <#nested />
            </div>

            <div class="modal-footer">

                <#if buttonId!="">
                    <button id="${buttonId}" type="${buttonType}" class="btn blue">${buttonName}</button>
                </#if>
                <button type="button" class="btn default" onclick="$('#${id}').modal('hide')">关闭</button>
            </div>
            <#if formId!="">
            </form>
            </#if>
        <#--</#if>-->
        </div>
    </div>
</div>
</#macro>