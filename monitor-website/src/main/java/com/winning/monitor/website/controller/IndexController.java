package com.winning.monitor.website.controller;


import com.winning.monitor.data.api.ILogin;
import com.winning.monitor.data.api.transaction.domain.LoginMessage;
import com.winning.monitor.website.infrastructure.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 2016/10/20.
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/", ""})
    public ModelAndView index() {
        return new ModelAndView("index");
    }



    @Autowired
    private ILogin loginService;

    @RequestMapping(value = {"/login"})
    public ModelAndView doLogin() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = {"/logoff"})
    public @ResponseBody   LoginMessage doLogoff(HttpServletRequest request,String loginId,String passWord) {
//        MessageInfo msg = new MessageInfo();
//        MessageInfo =userPermissionService.existLogin(loginId,passWord);
//        if(msg.getState() !="false"){
//            UserInfo user = userPermissionService.queryUser(loginId);
//            Session.setSession(request.getSession(true));
//            Session.set("loginId", loginId);
//        }
//        return msg;
        LoginMessage msg = new LoginMessage();
        if(loginId == null || "".equals(loginId)){
            msg.setState(false);
            return msg;
        }
        msg = loginService.login(loginId,passWord);
        Session.setSession(request.getSession(true));
        Session.set("loginId", loginId);
        return msg;
    }

    @RequestMapping(value = {"/logout"})
    public @ResponseBody  void doLogout() {
        Session.removeSession();
    }
}
