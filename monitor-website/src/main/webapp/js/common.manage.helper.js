

JqCommon = {
    AllSelect: function (id) {//全选
        $(id).parents("table").on("change", id, function () {
            var isChecked = $(this).prop("checked");
            $(id).parents("table").find("input[type='checkbox']").prop("checked", isChecked);
        });
        $(id).parents("table").on("change", "input[type='checkbox']:gt(0)", function () {
            var isChecked = true;
            $.each($(id).parents("table").find("input[type='checkbox']:gt(0)"), function (i, item) {
                if (!$(this).prop("checked")) {
                    isChecked = false;
                }
            });
            $(id).prop("checked", isChecked);
        });
    },
    ChangeDateFormat: function (cellval) {//时间格式转换
        if (cellval != "" && cellval != null) {
            var date = new Date(parseInt(cellval.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            return date.getFullYear() + "/" + month + "/" + currentDate;
        }
        else {
            return "";
        }
    },
    ChangeDateTimeFormat: function (cellval) {//时间格式转换
        if (cellval != "" && cellval != null) {
            var date = new Date(parseInt(cellval.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
alert(data);
            return date.getFullYear() + "/" + month + "/" + currentDate + " " + hour + ":" + minute + ":" + second;
        }
        else {
            return "";
        }
    },
    UpLoadType: function (filepath) {
        var extStart = filepath.lastIndexOf(".");
        var ext = filepath.substring(extStart, filepath.length).toLocaleLowerCase();
        return ext;
    },
    ConvertInvestmentTerm: function (InvestmentTerm, ExtendedTerm, ExitTerm) {
        var html = ""
        if (InvestmentTerm != 0) {
            if (InvestmentTerm % 30 == 0) {
                html += InvestmentTerm / 30 + "个月";
            }
            else {
                html += InvestmentTerm + "天";
            }
        }
        if (ExtendedTerm != 0) {
            if (ExtendedTerm % 30 == 0) {
                if (html == "") {
                    html += ExtendedTerm / 30 + "个月";
                }
                else {
                    html += "+" + ExtendedTerm / 30 + "个月";
                }
            }
            else {
                if (html == "") {
                    html += ExtendedTerm + "天";
                }
                else {
                    html += "+" + ExtendedTerm + "天";
                }
            }
        }
        if (ExitTerm != 0) {
            if (ExitTerm % 30 == 0) {
                if (html == "") {
                    html += ExitTerm / 30 + "个月";
                }
                else {
                    html += "+" + ExitTerm / 30 + "个月";
                }
            }
            else {
                if (html == "") {
                    html += ExitTerm + "天";
                }
                else {
                    html += "+" + ExitTerm + "天";
                }
            }
        }
        return html;
    },
    ConvertAmount: function (StartAmount, EndAmount) {
        var html = "";
        if (StartAmount != -1 && EndAmount != -1) {
            html = StartAmount + "≤X＜" + EndAmount;
        }
        else if (StartAmount != -1 && EndAmount == -1) {
            html = "X≥" + StartAmount;
        }
        else if (StartAmount == -1 && EndAmount != -1) {
            html = "X＜" + EndAmount;
        }
        return html;
    },
    openPostWindow:function(url,datas){
        var tempForm = document.createElement("form");
        tempForm.id = "tempForm1";
        tempForm.method = "post";
        tempForm.action = contextPath+url;
        //tempForm.target="_blank"; //打开新页面
        var hideInput1 = document.createElement("input");
        hideInput1.type = "hidden";
        hideInput1.name="datas"; //后台要接受这个参数来取值
        hideInput1.value =JSON.stringify(datas); //后台实际取到的值

        tempForm.appendChild(hideInput1);
        //tempForm.appendChild(hideInput2);
        if(document.all){
            tempForm.attachEvent("onsubmit",function(){});        //IE
        }else{
            var subObj = tempForm.addEventListener("submit",function(){},false);    //firefox
        }
        document.body.appendChild(tempForm);
        if(document.all){
            tempForm.fireEvent("onsubmit");
        }else{
            tempForm.dispatchEvent(new Event("submit"));
        }
        tempForm.submit();
        document.body.removeChild(tempForm);
}

}
