/**
 * Created by Admin on 2016/9/18.
 */
$(document).ready(function () {
    $("#loginBtn").click(function(){
        var username= $("#username").val();
        var password =$("#password").val();
        console.log("login"+username+"/"+password);
        if(username =="" || password==""){
            bootbox.alert("用户名或密码不能为空!");
            return false;
        }

        //alert(username);alert(password)
        $.post("/logoff",{loginId:username,passWord:password},function(data){
            console.log(data);
            //return;
            if(data.state == false){
                bootbox.alert("用户名或密码错误");
                return false;
            }
            else if(data.state =="warning"){
                bootbox.alert(data.msg,function(){
                    //location.href = "/admin/usermanage";
                });
            }
            else {
                location.href = "/";
            }
        });
    });
    $("#cancelBtn").click(function(){
        $("#username").val("");
        $("#password").val("");
    });
});