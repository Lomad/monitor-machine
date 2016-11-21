package com.winning.monitor.data.api;

import com.winning.monitor.data.api.transaction.domain.LoginMessage;

/**
 * Created by sao something on 2016/11/21.
 */
public interface ILogin {
    /**
     * 登录
     *
     * @param username               用户名
     * @param password               密码
     * @return  登录状态
     */
    LoginMessage login(String username,String password);
}
